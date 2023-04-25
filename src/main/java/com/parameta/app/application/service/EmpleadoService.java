package com.parameta.app.application.service;

import com.parameta.app.application.dto.EmpleadoDTO;
import reactor.core.publisher.Mono;

public interface EmpleadoService {
    Mono<EmpleadoDTO>registrarEmpleado(EmpleadoDTO empleadoDTO);
}
