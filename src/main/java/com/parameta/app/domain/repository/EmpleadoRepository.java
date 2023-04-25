package com.parameta.app.domain.repository;

import com.parameta.app.domain.model.Empleado;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends ReactiveCrudRepository<Empleado, Long> {
}
