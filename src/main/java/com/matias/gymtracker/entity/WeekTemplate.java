package com.matias.gymtracker.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "week_templates")
@Data
@NoArgsConstructor
public class WeekTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean isDefault;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "weekTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WeekDayAssignment> days = new ArrayList<>();

    public WeekTemplate(String name, Boolean isDefault, User user) {
        this.name = name;
        this.isDefault = isDefault;
        this.user = user;
    }
}
