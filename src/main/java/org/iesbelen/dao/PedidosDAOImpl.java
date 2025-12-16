package org.iesbelen.dao;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iesbelen.modelo.Pedido;
import org.iesbelen.modelo.PedidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@AllArgsConstructor
public class PedidosDAOImpl implements PedidoDAO {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create(Pedido pedido) {
        String sqlInsert = """
                INSERT INTO pedidos (total,fecha,id_cliente,id_comercial) VALUES (?,?,?,?)""";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rows = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[]{"id"});
            int idx = 1;
            ps.setFloat(idx++, pedido.getTotal());
            ps.setDate(idx++, pedido.getFecha());
            ps.setInt(idx++, pedido.getIdCliente());
            ps.setInt(idx++, pedido.getIdComercial());
            return ps;
        }, keyHolder);
        pedido.setIdComercial(keyHolder.getKey().intValue());
        log.info("Insertados {} registros.", rows);

    }

    @Override
    public List<Pedido> getAll() {
        String sql = "SELECT * FROM pedidos";
        List<Pedido> listPedido = jdbcTemplate.query(sql,
                (rs, rowNum) ->
                        new Pedido(rs.getInt("id"),
                                    rs.getFloat("total"),
                                    rs.getDate("fecha"),
                                    rs.getInt("id_cliente"),
                                    rs.getInt("id_comercial")));
        log.info("Devualtos {} registros.", listPedido.size());
        return listPedido;
    }

    @Override
    public Optional<Pedido> find(int id) {
        String sql = "SELECT * FROM pedido WHERE id = ?";
        List<Pedido> pedidos = jdbcTemplate.query(sql,(rs, rowNum) ->
                new Pedido(rs.getInt("id"),
                        rs.getFloat("total"),
                        rs.getDate("fecha"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_comercial")));



        return pedidos.stream().findFirst();
    }

    @Override
    public void update(Pedido pedido) {
        int rows = jdbcTemplate.update("""
    UPDATE pedido SET total = ?, fecha = ?, id_cliente = ?, id_comercial = ? WHERE id = ?""",
                pedido.getTotal(),
                pedido.getFecha(),
                pedido.getIdCliente(),
                pedido.getIdComercial(),
                pedido.getId());
        log.info("Update de pedidos con {} registros acutalizados", rows);
    }

    @Override
    public void delete(long id) {
        int rows = jdbcTemplate.update("DELETE FROM pedido WHERE id = ?", id);
        log.info("Delete de pedidos con  {} registros eliminados", rows);
    }

    @Override
    public List<PedidoDTO> findPorComercialConCliente(int idComercial) {
        String sql = """
        SELECT p.id, p.total, p.fecha, p.id_cliente, c.nombre AS nombre_cliente
        FROM pedido p
        JOIN cliente c ON p.id_cliente = c.id
        WHERE p.id_comercial = ?
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            PedidoDTO dto = new PedidoDTO();
            dto.setId(rs.getInt("id"));
            dto.setTotal(rs.getFloat("total"));
            dto.setFecha(rs.getDate("fecha"));
            dto.setIdCliente(rs.getInt("id_cliente"));
            dto.setNombreCliente(rs.getString("nombre_cliente"));
            return dto;
        }, idComercial);
    }

}
