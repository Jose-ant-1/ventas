package org.iesbelen.modelo;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comercial {

	private int id;

    @NotNull(message = "Por favor, nombre no puede ser nulo.")
    @NotBlank(message = "Por favor, introduzca nombre.")
    @Size(max=30, message = "Nombre como máximo de 30 caracteres.")
    private String nombre;

    @NotNull(message = "Por favor, apellido1 no puede ser nulo.")
    @NotBlank(message = "Por favor, introduzca apellido 1.")
    @Size(max=30, message = "Apellido 1 como máximo de 30 caracteres.")
    private String apellido1;
	private String apellido2;

    @DecimalMax(value="0.276")
    @DecimalMin(value="0.946")
	private BigDecimal comision;

}
