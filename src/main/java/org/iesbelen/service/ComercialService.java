package org.iesbelen.service;

import org.iesbelen.dao.ComercialDAO;
import org.iesbelen.dao.PedidoDAO;
import org.iesbelen.modelo.Comercial;
import org.iesbelen.modelo.PedidoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComercialService {

    private ComercialDAO comercialDAO;
    private PedidoDAO pedidoDAO;

    public ComercialService(ComercialDAO comercialDAO, PedidoDAO pedidoDAO) {
        this.comercialDAO = comercialDAO;
        this.pedidoDAO = pedidoDAO;
    }

    public List<Comercial> listAll() {
        return comercialDAO.getAll();
    }

    public Comercial one(Integer id) {
        return comercialDAO.find(id).orElse(null);
    }

    public void newComercial (Comercial comercial) {
        comercialDAO.create(comercial);
    }

    public void replaceComercial  (Comercial comercial) {
        comercialDAO.update(comercial);
    }

    public void deleteComercial(Integer id) {
        comercialDAO.delete(id);
    }

    public List<PedidoDTO> obtenerPedidosPorComercial(Integer id) {
        return pedidoDAO.findPorComercialConCliente(id);
    }

}
