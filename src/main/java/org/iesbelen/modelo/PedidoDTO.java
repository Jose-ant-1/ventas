package org.iesbelen.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    private int id;
    private float total;
    private Date fecha;
    private int idCliente;
    private String nombreCliente;
}
