package org.iesbelen.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
    private int id;
    private float total;
    private Date fecha;
    private int idCliente;
    private int idComercial;

}
