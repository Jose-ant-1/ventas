package org.iesbelen.controlador;


import org.iesbelen.modelo.Comercial;
import org.iesbelen.service.ComercialService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ComercialController {


    private ComercialService comercialService;


    public ComercialController(ComercialService comercialService) {
        this.comercialService = comercialService;
    }

    @GetMapping("/comerciales")
    public String listar(Model model){
        List<Comercial> listaComerciales = comercialService.listAll();
        model.addAttribute("listaComerciales",listaComerciales);
        return "comerciales";
    }

}
