# Guia de mappers

## Objetivo

Este proyecto usa **MapStruct** para convertir entre:

- `Request DTO -> Entity`
- `Entity -> Response DTO`
- `Update DTO -> Entity existente`

La idea es sacar esa logica repetitiva de los services y dejarla en interfaces declarativas.

## Que se hizo en los mappers

Se siguio un patron bastante consistente en casi todas las entidades:

1. Se define `toEntity(...)` para altas.
2. Se define `updateEntityFromDto(...)` para updates parciales sobre una entidad ya cargada.
3. Se define `toResponse(...)` para exponer datos al cliente.
4. Se define `toResponseList(...)` para listas.

Eso se ve en:

- `UserMapper`
- `RoutineDayMapper`
- `ExerciseMapper`
- `WeekTemplateMapper`
- `WeekDayAssignmentMapper`
- `WeekLogMapper`
- `SessionLogMapper`
- `ExerciseLogMapper`
- `SetLogMapper`

## Por que existe `CentralMapperConfig`

Archivo: `src/main/java/com/matias/gymtracker/mapper/CentralMapperConfig.java`

Se creo para centralizar reglas comunes de todos los mappers:

```java
@MapperConfig(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
```

### 1. `componentModel = "spring"`

Hace que MapStruct genere beans administrados por Spring.

Eso permite inyectarlos asi:

```java
private final UserMapper userMapper;
```

Sin esta config, cada mapper tendria que repetir `componentModel = "spring"` en su propia anotacion `@Mapper`.

### 2. `unmappedTargetPolicy = ReportingPolicy.IGNORE`

Le dice a MapStruct que no falle ni avise por cada campo del target que no se mapea explicitamente.

Esto se uso porque en tus entidades hay varios campos que:

- se completan en service
- se manejan por JPA
- no deben venir desde el request
- no siempre deben salir enteros en la response

Ejemplos:

- `id`
- `createdAt`
- relaciones como `user`, `weekLog`, `sessionLog`, `exerciseLog`
- colecciones como `days`, `sessions`, `exerciseLogs`, `exercises`

Sin esta configuracion, cada cambio en una entidad te obligaria a tocar muchos mappers aunque el campo no debiera mapearse automaticamente.

### 3. `nullValuePropertyMappingStrategy = IGNORE`

Hace que, cuando un campo del DTO viene en `null`, **no se sobreescriba** el valor actual de la entidad.

Esto es especialmente importante en updates parciales.

Ejemplo conceptual:

- entidad actual: `name = "Press banca"`
- DTO update: `name = null`
- con `IGNORE`: se mantiene `"Press banca"`
- sin `IGNORE`: podria quedar `null`

Aunque en varios mappers tambien se vuelve a declarar en `@BeanMapping`, la idea es reforzar explicitamente que los updates son parciales.

## Por que algunos mappers tambien usan `@BeanMapping(...)`

Ejemplo:

```java
@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
void updateEntityFromDto(..., @MappingTarget Entity entity);
```

Esto se usa en los metodos de update porque ese metodo no crea una entidad nueva: modifica una ya existente.

### `@MappingTarget`

Le indica a MapStruct que debe escribir sobre el objeto recibido y no crear uno nuevo.

Eso sirve para este flujo tipico:

1. El service busca la entidad en BD.
2. El mapper aplica solo los cambios del DTO.
3. Se guarda la misma entidad actualizada.

## Logica general detras de las anotaciones

### `@Mapping(target = "id", ignore = true)`

Se ignora el `id` porque normalmente:

- lo genera la BD
- no debe venir controlado por el cliente
- no debe pisarse en updates

### `@Mapping(target = "relacion", ignore = true)`

Se ignoran relaciones completas cuando no conviene que MapStruct arme el grafo entero automaticamente.

Ejemplos del proyecto:

- `routineDay`
- `user`
- `weekTemplate`
- `weekLog`
- `sessionLog`
- `exerciseLog`
- colecciones asociadas

La razon es evitar:

- ciclos de mapeo
- acoplar el mapper a demasiadas entidades hijas
- crear relaciones incompletas o inconsistentes
- sobreescribir asociaciones que se manejan mejor en service

## Cuando se mapea solo el id de una relacion

En varios casos no queres mapear la entidad completa, solo referenciarla por id.

Ejemplos:

### `ExerciseLogMapper`

```java
@Mapping(source = "exerciseId", target = "exercise.id")
```

Esto le dice a MapStruct:

- el request trae `exerciseId`
- en la entidad destino se debe setear `exercise.id`

Es util cuando queres construir una referencia minima a otra entidad sin cargar todos sus datos en el mapper.

### `WeekDayAssignmentMapper`

```java
@Mapping(source = "routineDayId", target = "routineDay.id")
```

Misma idea: el request trae el id, y el mapper lo coloca dentro de la relacion.

### `SessionLogMapper`

```java
@Mapping(target = "routineDay.id", source = "routineDayId")
```

Se usa para enlazar la sesion con el `RoutineDay` correspondiente.

## Cuando se aplana una relacion para la response

En responses pasa lo contrario: en vez de devolver una entidad anidada completa, se exponen ids o campos puntuales.

Ejemplos:

### `ExerciseMapper`

```java
@Mapping(source = "routineDay.id", target = "routineDayId")
```

La response no devuelve todo `RoutineDay`, solo su id.

### `ExerciseLogMapper`

```java
@Mapping(source = "sessionLog.id", target = "sessionId")
@Mapping(source = "exercise.id", target = "exerciseId")
@Mapping(source = "exercise.name", target = "exerciseName")
```

Aca se aplana una relacion para que el cliente reciba una response mas simple.

### `WeekTemplateMapper`

```java
@Mapping(source = "user.id", target = "userId")
@Mapping(source = "days", target = "dayIds")
```

No se expone toda la entidad relacionada; se expone solo lo necesario.

## Por que hay metodos `default` en algunos mappers

Ejemplos:

- `RoutineDayMapper.mapExercisesToIds`
- `SessionLogMapper.mapExerciseLogsToIds`
- `WeekTemplateMapper.mapDaysToIds`
- `WeekLogMapper.mapSessionsToIds`

Estos metodos existen porque MapStruct no sabe automaticamente que una `List<Entidad>` debe transformarse en `List<Long>` usando el `id`.

Entonces se agrega logica manual, chica y controlada:

```java
return exercises == null ? List.of() : exercises.stream().map(Exercise::getId).toList();
```

La idea es:

- evitar devolver objetos anidados innecesarios
- mantener responses livianas
- controlar el formato exacto del DTO de salida

## Caso especial: `ExerciseLogMapper` usa `SetLogMapper`

Archivo: `src/main/java/com/matias/gymtracker/mapper/ExerciseLogMapper.java`

```java
@Mapper(config = CentralMapperConfig.class, uses = SetLogMapper.class)
```

Esto significa que, si MapStruct necesita mapear partes relacionadas con `SetLog`, puede reutilizar ese mapper en vez de duplicar logica.

La ventaja es:

- composicion entre mappers
- menos duplicacion
- una sola fuente de verdad para el mapeo de `SetLog`

## Criterio de diseño que se ve en este proyecto

El criterio general parece ser este:

- el mapper convierte datos planos
- el service decide relaciones, validaciones y reglas de negocio
- las entidades no se exponen directamente
- las responses se aplanan para no devolver grafos complejos

Ese criterio es sano porque separa responsabilidades:

- **MapStruct**: conversion de estructuras
- **Service**: negocio y consistencia
- **JPA**: persistencia y relaciones

## Resumen corto de por que `CentralMapperConfig` esta bien

`CentralMapperConfig` evita repetir la misma configuracion en todos los mappers y asegura consistencia global:

- todos son beans de Spring
- todos ignoran targets no mapeados por defecto
- todos mantienen el valor actual si un update viene con `null`

Si mañana queres cambiar una politica comun, la cambias una vez sola.

## Recomendacion practica

Si queres mantener este estilo, conviene seguir estas reglas:

1. En `create`, ignorar ids y relaciones administradas por service/JPA.
2. En `update`, usar siempre `@MappingTarget` y `NullValuePropertyMappingStrategy.IGNORE`.
3. En `response`, exponer ids o campos concretos en vez de entidades completas.
4. Cuando una lista de entidades deba salir como lista de ids, agregar un metodo `default`.
5. Si un mapper necesita otro, usar `uses = ...` en vez de duplicar conversiones.

## Archivos a revisar juntos

Si despues queres, la siguiente guia util seria documentar el flujo completo:

- `Controller -> Service -> Mapper -> Repository`

o una guia puntual de:

- cuando ignorar relaciones
- cuando mapear `relacion.id`
- cuando resolver la relacion en el service
