package com.parameta.app.application.service.impl;

import com.parameta.app.application.dto.EmpleadoDTO;
import com.parameta.app.application.mapper.EmpleadoMapper;
import com.parameta.app.application.service.EmpleadoService;
import com.parameta.app.domain.model.Empleado;
import com.parameta.app.domain.repository.EmpleadoRepository;
import com.parameta.app.infrastructure.soap.client.SoapClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.Period;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {
    private final EmpleadoRepository empleadoRepository;
    private final EmpleadoMapper empleadoMapper;
    private final SoapClient soapClient;

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository, EmpleadoMapper empleadoMapper, SoapClient soapClient) {
        this.empleadoRepository = empleadoRepository;
        this.empleadoMapper = empleadoMapper;
        this.soapClient = soapClient;
    }
    @Override
    public Mono<EmpleadoDTO> registrarEmpleado(EmpleadoDTO empleadoDTO) {
        // Convierte el DTO a la entidad Empleado
        Empleado empleado = empleadoMapper.toEmpleado(empleadoDTO);

        // Valida y registra el empleado en la base de datos
        Mono<Empleado> empleadoGuardadoMono = empleadoRepository.save(empleado);

        return empleadoGuardadoMono.flatMap(empleadoGuardado -> {
            // Invoca al servicio SOAP
            soapClient.registrarEmpleado(empleadoGuardado);

            // Calcula la edad y antigüedad del empleado
            LocalDate fechaNacimiento = empleadoGuardado.getFechaNacimiento();
            LocalDate fechaVinculacion = empleadoGuardado.getFechaVinculacion();
            LocalDate ahora = LocalDate.now();

            Period edad = Period.between(fechaNacimiento, ahora);
            Period antiguedad = Period.between(fechaVinculacion, ahora);

            // Convierte la entidad Empleado a DTO y añade la edad y antigüedad
            EmpleadoDTO empleadoGuardadoDTO = empleadoMapper.toEmpleadoDTO(empleadoGuardado);
            empleadoGuardadoDTO.setEdad(formatPeriod(edad));
            empleadoGuardadoDTO.setAntiguedad(formatPeriod(antiguedad));

            return Mono.just(empleadoGuardadoDTO);
        });
    }

    private String formatPeriod(Period period) {
        return String.format("%d años, %d meses y %d días",
                period.getYears(), period.getMonths(), period.getDays());
    }
}
