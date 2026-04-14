package com.matias.gymtracker.repository;

import com.matias.gymtracker.entity.WeekTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeekTemplateRepository extends JpaRepository<WeekTemplate, Long> {

    List<WeekTemplate> findByUserId(Long userId);

    Optional<WeekTemplate> findByUserIdAndIsDefaultTrue(Long userId);



    boolean existsByUserIdAndName(Long userId, String name);

}
