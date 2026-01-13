package org.iesbelen.modelo;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//La anotación @Data de lombok proporcionará el código de:
//getters/setters, toString, equals y hashCode
//propio de los objetos POJOS o tipo Beans
@Data
//Para generar un constructor con lombok con todos los args
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
	
	@Min(value = 0)
    private long id;

    @NotNull(message = "Por favor, nombre no puede ser nulo.")
    @NotBlank(message = "Por favor, introduzca nombre.")
    @Size(max=30, message = "Nombre como máximo de 30 caracteres.")
	private String nombre;

    @NotNull(message = "Por favor, nombre no puede ser nulo.")
    @NotBlank(message = "Por favor, introduzca apellido 1.")
    @Size(max=30, message = "Nombre como máximo de 30 caracteres.")
	private String apellido1;

    private String apellido2;

    @NotNull(message = "Por favor, nombre no puede ser nulo.")
    @NotBlank(message = "Por favor, introduzca a ciudad.")
    @Size(max=50, message = "Nombre como máximo de 50 caracteres.")
	private String ciudad;

    @Min(value=100, message = "categoría debe ser al menos 0.")
    @Max(value=1000, message = "categoría no debe ser mayor de 1000.")
    private int categoria;


}
