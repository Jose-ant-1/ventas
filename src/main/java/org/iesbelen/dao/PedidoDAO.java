package org.iesbelen.dao;

import org.iesbelen.modelo.Pedido;
import org.iesbelen.modelo.PedidoDTO;

import java.util.List;
import java.util.Optional;

public interface PedidoDAO {

    public void create(Pedido pedido);
    public List<Pedido> getAll();
    public Optional<Pedido> find(int id);
    public void update(Pedido pedido);
    public void delete(long id);
    public List<PedidoDTO> findPorComercialConCliente(int idComercial);

}
