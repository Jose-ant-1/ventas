package org.iesbelen.service;

import org.iesbelen.dao.ComercialDAO;
import org.iesbelen.modelo.Comercial;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComercialService {

    private ComercialDAO comercialDAO;

    public ComercialService(ComercialDAO comercialDAO) {
        this.comercialDAO = comercialDAO;
    }

    public List<Comercial> listAll() {
        return comercialDAO.getAll();
    }

}
