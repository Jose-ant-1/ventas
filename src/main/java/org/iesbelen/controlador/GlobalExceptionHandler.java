package org.iesbelen.controlador;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleAllExceptions(Exception ex, Model model) {
        // Añadimos información del error al modelo para la vista
        model.addAttribute("errorMessage", "Se ha producido un error interno en el servidor.");
        model.addAttribute("exception", ex);

        // Retornamos el nombre de la plantilla HTML de error
        return "error";
    }

    // Ejemplo para una excepción específica (ej. recurso no encontrado)
    // Podrías crear una excepción personalizada para cuando un Cliente o Comercial no existe
    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException ex, Model model) {
        model.addAttribute("errorMessage", "Error de ejecución: " + ex.getMessage());
        return "error";
    }
}
