package org.iesbelen.service;

import lombok.NoArgsConstructor;
import org.iesbelen.dao.ComercialDAO;
import org.iesbelen.dao.PedidoDAO;
import org.iesbelen.modelo.Comercial;
import org.iesbelen.modelo.PedidoDTO;
import org.springframework.stereotype.Service;
import org.iesbelen.modelo.*;

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
        return comercialDAO.find(id).orElseThrow(() -> new RuntimeException("Comercial con ID " + id + " no encontrado"));
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

    public ComercialDTO obtenerDetalleConEstadisticas(Integer id) {
        Comercial comercial = comercialDAO.find(id).orElseThrow(() -> new RuntimeException("Comercial con ID " + id + " no encontrado"));

        List<PedidoDTO> pedidos = pedidoDAO.findPorComercialConCliente(id);

        ComercialDTO dto = new ComercialDTO();
        dto.setId(comercial.getId());
        dto.setNombre(comercial.getNombre());
        dto.setApellido1(comercial.getApellido1());
        dto.setApellido2(comercial.getApellido2());
        dto.setComision(comercial.getComision());
        dto.setListaPedidos(pedidos);

        double total = pedidos.stream()
                .mapToDouble(PedidoDTO::getTotal)
                .sum();
        double media = pedidos.isEmpty() ? 0.0 : total / pedidos.size();

        dto.setTotalPedidos(total);
        dto.setMediaPedidos(media);

        return dto;
    }

}
