package org.iesbelen;

import java.math.BigDecimal;
import java.util.Optional;

import org.iesbelen.dao.ClienteDAO;
import org.iesbelen.dao.ComercialDAO;
import org.iesbelen.modelo.Cliente;
import org.iesbelen.modelo.Comercial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class SpringBootWebMvcJdbcVentasApplication implements CommandLineRunner{

	@Autowired
	private ClienteDAO clienteDAO;
	@Autowired
    private ComercialDAO comercialDAO;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebMvcJdbcVentasApplication.class, args);
		
	}
	
	@Override
	public void run(String... args) throws Exception {
		log.info("*******************************");
		log.info("*Prueba de arranque ClienteDAO*");
		log.info("*******************************");
		
		clienteDAO.getAll().forEach(c -> log.info("Cliente: {}", c));
		
		int id = 1;
		Optional<Cliente> cliente = clienteDAO.find(id);
		
		if (cliente.isPresent()) {
			log.info("Cliente {}: {}", id, cliente.get());
			
			String nombreOld = cliente.get().getNombre();
			
			cliente.get().setNombre("Jose M");
			
			clienteDAO.update(cliente.get());
			
			cliente = clienteDAO.find(id);
			
			log.info("Cliente {}: {}", id, cliente.get());
			
			//Volvemos a cargar el nombre antiguo..
			cliente.get().setNombre(nombreOld);
			clienteDAO.update(cliente.get());
			
		}
		
		// Como es un cliente nuevo a persistir, id a 0
		Cliente clienteNew = new Cliente(0, "Jose M", "Martín", null, "Málaga", 100, "Jose@mail.com");
		
		//create actualiza el id
		clienteDAO.create(clienteNew);
		
		log.info("Cliente nuevo con id = {}", clienteNew.getId());
		
		clienteDAO.getAll().forEach(c -> log.info("Cliente: {}", c));
		
		//borrando por el id obtenido de create
		clienteDAO.delete(clienteNew.getId());
		
		clienteDAO.getAll().forEach(c -> log.info("Cliente: {}", c));
		
		log.info("************************************");
		log.info("*FIN: Prueba de arranque ClienteDAO*");
		log.info("************************************");

        log.info("************************************");
        log.info("*INICIO: Prueba de arranque ComercialDAO*");
        log.info("************************************");

// 1. OBTENER Y LISTAR TODOS
        comercialDAO.getAll().forEach(c -> log.info("Comercial: {}", c));

// 2. BUSCAR y 3. ACTUALIZAR (similar a la prueba de ClienteDAO)
        int idComercial = 1;
        Optional<Comercial> comercial = comercialDAO.find(idComercial);

        if (comercial.isPresent()) {
            log.info("Comercial {}: {}", idComercial, comercial.get());

            // Guardamos el nombre original para restaurarlo después
            String nombreOldCom = comercial.get().getNombre();

            // Realizamos la modificación
            comercial.get().setNombre("Juan P.");
            comercialDAO.update(comercial.get());

            // Verificamos la modificación
            comercial = comercialDAO.find(idComercial);
            log.info("Comercial actualizado {}: {}", idComercial, comercial.get());

            // RESTAURAMOS el nombre antiguo
            comercial.get().setNombre(nombreOldCom);
            comercialDAO.update(comercial.get());
        }

// 4. CREAR uno nuevo
        Comercial comercialNew = new Comercial(0, "Ana", "Gómez", "alcazar", new BigDecimal("0.29")); // Asumiendo estructura de Comercial
        comercialDAO.create(comercialNew);
        log.info("Comercial nuevo con id = {}", comercialNew.getId());

// 5. BORRAR el nuevo
        log.info("Comerciales antes de borrar el nuevo:");
        comercialDAO.getAll().forEach(c -> log.info("Comercial: {}", c));
        comercialDAO.delete(comercialNew.getId());
        log.info("Comerciales después de borrar el nuevo:");
        comercialDAO.getAll().forEach(c -> log.info("Comercial: {}", c));


        log.info("************************************");
        log.info("*FIN: Prueba de arranque ComercialDAO*");
        log.info("************************************");

// ... (código existente del final de run())








	}

}
