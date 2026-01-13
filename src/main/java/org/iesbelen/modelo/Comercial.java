package org.iesbelen.modelo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comercial {

	private int id;

    @NotNull(message = "Por favor, nombre no puede ser nulo.")
    @NotBlank(message = "Por favor, introduzca apellido 1.")
    @Size(max=30, message = "Nombre como máximo de 30 caracteres.")
    private String nombre;

    @NotNull(message = "Por favor, nombre no puede ser nulo.")
    @NotBlank(message = "Por favor, introduzca apellido 1.")
    @Size(max=30, message = "Apellido como máximo de 30 caracteres.")
    private String apellido1;
	private String apellido2;
	private float comision;

}
