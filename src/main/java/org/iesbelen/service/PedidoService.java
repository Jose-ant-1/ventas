package org.iesbelen.service;

import org.iesbelen.dao.PedidoDAO;
import org.iesbelen.modelo.Pedido;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private PedidoDAO pedidoDAO;

    public PedidoService(PedidoDAO pedidosDAO) {
        this.pedidoDAO = pedidosDAO;
    }

    public List<Pedido> listAll() {
        return pedidoDAO.getAll();
    }

    public Pedido one(Integer id) {
        return pedidoDAO.find(id)
                .orElseThrow(() -> new RuntimeException("Pedido con ID: " + id + " no encontrado"));
    }

    public void newPedido (Pedido pedido) {
        pedidoDAO.create(pedido);
    }

    public void replacePedido (Pedido pedido) {
        pedidoDAO.update(pedido);
    }

    public void deletePedido (Integer id) {
        pedidoDAO.delete(id);
    }

}
