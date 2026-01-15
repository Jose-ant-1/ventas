package org.iesbelen.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import lombok.NoArgsConstructor;
import org.iesbelen.modelo.Cliente;
import org.iesbelen.modelo.Comercial;
import org.iesbelen.modelo.ComercialDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//Anotación lombok para logging (traza) de la aplicación
@Slf4j
@Repository
//Utilizo lombok para generar el constructor
public class ComercialDAOImpl implements ComercialDAO {

	//JdbcTemplate se inyecta por el constructor de la clase automáticamente
	//
    @Autowired
	private JdbcTemplate jdbcTemplate;

    public ComercialDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

	@Override
	public void create(Comercial comercial) {
		String sqlInsert = """
                INSERT INTO comercial (nombre,apellido1,apellido2,comisión) VALUES (?,?,?,?)""";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rows = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[]{"id"});
            int idx = 1;
            ps.setString(idx++, comercial.getNombre());
            ps.setString(idx++, comercial.getApellido1());
            ps.setString(idx++, comercial.getApellido2());
            ps.setBigDecimal(idx++, comercial.getComision());
            return ps;
        }, keyHolder);

        comercial.setId(keyHolder.getKey().intValue());

        log.info("Insertados {} registros.", rows);


	}

	@Override
	public List<Comercial> getAll() {

        String sql = "SELECT * FROM comercial";
		
		List<Comercial> listComercial = jdbcTemplate.query(sql,
                                                            (rs, rowNum) -> new Comercial(rs.getInt("id"),
                                                                                                        rs.getString("nombre"),
                                                                                                        rs.getString("apellido1"),
                                                                                                        rs.getString("apellido2"),
                                                                                                        rs.getBigDecimal("comisión"))
                						 	
                                                          );
		
		log.info("Devueltos {} registros.", listComercial.size());
		
        return listComercial;
	}

	@Override
	public Optional<Comercial> find(int id) {

        String sql = "SELECT * FROM comercial WHERE id = ?";

        List<Comercial> comerciales = jdbcTemplate.query(sql,(rs,rowNum) ->
                        new Comercial(rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getString("apellido1"),
                                rs.getString("apellido2"),
                                rs.getBigDecimal("comisión")), id);


        return comerciales.stream().findFirst();
	}

	@Override
	public void update(Comercial comercial) {
		int rows = jdbcTemplate.update("""
UPDATE comercial SET nombre = ?, apellido1 = ?, apellido2 = ?, comisión = ? WHERE id = ?""",
                comercial.getNombre(),
                comercial.getApellido1(),
                comercial.getApellido2(),
                comercial.getComision(),
                comercial.getId());
    log.info("Update de comerciales con {} registros acutalizados.", rows);
	}

	@Override
	public void delete(long id) {
		int rows = jdbcTemplate.update("DELETE FROM comercial WHERE id = ?", id);

        log.info("Delete de comercial con {} registros eliminados.", rows);
	}

    public List<ComercialDTO> findComercialesPorCliente(int idCliente) {
        String sql = """
            SELECT co.*, COUNT(p.id) AS total_pedidos
            FROM comercial co
            INNER JOIN pedido p ON co.id = p.id_comercial
            WHERE p.id_cliente = ?
            GROUP BY co.id
            """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ComercialDTO dto = new ComercialDTO();
            dto.setId(rs.getInt("id"));
            dto.setNombre(rs.getString("nombre"));
            dto.setApellido1(rs.getString("apellido1"));
            dto.setApellido2(rs.getString("apellido2"));
            // Reutilizamos totalPedidos del DTO para el conteo
            dto.setTotalPedidos(rs.getDouble("total_pedidos"));
            return dto;
        }, idCliente);
    }

}
