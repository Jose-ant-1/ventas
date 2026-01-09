package org.iesbelen.mapstruct;

import org.iesbelen.modelo.Cliente;
import org.iesbelen.modelo.ClienteDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    // ESTA ES LA LÍNEA QUE TE FALTA O TIENE UN ERROR DE ESCRITURA:
    ClienteDTO clienteAClienteDTO(Cliente cliente);

    // (Opcional) Si necesitas el inverso
    Cliente clienteDTOACliente(ClienteDTO clienteDTO);

}
