package com.parameta.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Table;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

/*@Table("empleados")*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {
    @Id
    private Long id;
    private String nombres;
    private String apellidos;
    private String tipoDocumento;
    private String numeroDocumento;
    private LocalDate fechaNacimiento;
    private LocalDate fechaVinculacion;
    private String cargo;
    private Double salario;
}
