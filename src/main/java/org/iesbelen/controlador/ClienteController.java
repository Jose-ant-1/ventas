package org.iesbelen.controlador;

import java.util.List;

import jakarta.validation.Valid;
import org.iesbelen.modelo.Cliente;
import org.iesbelen.modelo.ClienteDTO;
import org.iesbelen.service.ClienteService;
import org.iesbelen.service.ComercialService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
//Se puede fijar ruta base de las peticiones de este controlador.
//Los mappings de los métodos tendrían este valor /clientes como
//prefijo.
@RequestMapping("/clientes")
public class ClienteController {

    private final ComercialService comercialService;
    private ClienteService clienteService;
	
	//Se utiliza inyección automática por constructor del framework Spring.
	//Por tanto, se puede omitir la anotación Autowired
	//@Autowired
	public ClienteController(ClienteService clienteService, ComercialService comercialService) {
		this.clienteService = clienteService;
        this.comercialService = comercialService;
    }
	
	//@RequestMapping(value = "/clientes", method = RequestMethod.GET)
	//equivalente a la siguiente anotación
	@GetMapping({"/",""}) //Al no tener ruta base para el controlador, cada método tiene que tener la ruta completa
	public String listar(Model model) {
		List<Cliente> listaClientes =  clienteService.listAll();
		model.addAttribute("listaClientes", listaClientes);
		return "/clientes/clientes";
	}
    @GetMapping("/{id}")
    public String detalle(Model model, @PathVariable Integer id) {
        /*Cliente cliente = clienteService.one(id);
        model.addAttribute("cliente", cliente);
        return "/clientes/detalle-cliente";*/
        ClienteDTO clienteDTO = clienteService.detalleCliente(id);
        model.addAttribute("cliente", clienteDTO);
        return "/clientes/detalle-cliente";
    }

    @GetMapping("/crear")
    public String crear(Model model) {
        Cliente cliente = new Cliente();
        model.addAttribute("cliente", cliente);
        return "/clientes/crear-cliente";
    }

    @PostMapping("/crear")
    public String submitCrear(@Valid @ModelAttribute("cliente")  Cliente cliente, BindingResult bindingResulted, Model model) {
        if (bindingResulted.hasErrors()) {
            model.addAttribute("cliente", cliente);
            return "/clientes/crear-cliente";
        }
        clienteService.newCliente(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String editar(Model model, @PathVariable Integer id) {
        Cliente cliente = clienteService.one(id);
        model.addAttribute("cliente", cliente);
        return "/clientes/editar-cliente";
    }

    @PostMapping("/editar/{id}")
    public String submitEditar(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("cliente", cliente);
            return "clientes/editar-cliente";
        }
        clienteService.replaceCliente(cliente);
        return "redirect:/clientes";
    }

    @PostMapping("/borrar/{id}")
    public RedirectView borrar(@PathVariable Integer id) {
        clienteService.deleteCliente(id);
        return new RedirectView("/clientes");
    }


}