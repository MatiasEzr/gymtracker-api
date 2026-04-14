package com.matias.gymtracker.repository;

import com.matias.gymtracker.entity.SetLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetLogRepository extends CrudRepository<SetLog, Long> {

}
