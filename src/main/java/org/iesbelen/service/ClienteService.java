package org.iesbelen.service;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.iesbelen.dao.ClienteDAO;
import org.iesbelen.dao.ComercialDAO;
import org.iesbelen.mapstruct.ClienteMapper;
import org.iesbelen.modelo.Cliente;
import org.iesbelen.modelo.ClienteDTO;
import org.iesbelen.modelo.ComercialDTO;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClienteService {

	private final ClienteDAO clienteDAO;
    private final ComercialDAO comercialDAO;
    private final ClienteMapper clienteMapper;
	//Se utiliza inyección automática por constructor del framework Spring.
	//Por tanto, se puede omitir la anotación Autowired
	//@Autowired
    /*
	public ClienteService(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}*/

    public ClienteDTO detalleCliente(Integer id) {
        Optional<Cliente> optCli = clienteDAO.find(id);

        if (optCli.isPresent()) {
            ClienteDTO dto = clienteMapper.clienteAClienteDTO(optCli.get());

            // 2. Cargamos la funcionalidad extra: la lista de comerciales
            List<ComercialDTO> comerciales = comercialDAO.findComercialesPorCliente(id);
            dto.setListaComerciales(comerciales);

            return dto;
        }
        return null;
    }

	public List<Cliente> listAll() {
		
		return clienteDAO.getAll();
		
	}

    public Cliente one(Integer id) {
        Optional<Cliente> optCli = clienteDAO.find(id);
        if (optCli.isPresent()) {
            return optCli.get();
        } else  {
            return null;
        }

    }

    public void newCliente(Cliente cliente) {
        clienteDAO.create(cliente);
    }

    public void replaceCliente(Cliente cliente) {
        clienteDAO.update(cliente);
    }

    public void deleteCliente(Integer id) {
        clienteDAO.delete(id);
    }
}
