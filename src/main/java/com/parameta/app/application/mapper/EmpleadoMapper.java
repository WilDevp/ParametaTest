package com.parameta.app.application.mapper;

import com.parameta.app.application.dto.EmpleadoDTO;
import com.parameta.app.domain.model.Empleado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmpleadoMapper {
    @Mapping(target = "id", ignore = true)
    Empleado toEmpleado(EmpleadoDTO empleadoDTO);
    EmpleadoDTO toEmpleadoDTO(Empleado empleado);
}
