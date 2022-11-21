package org.sid.gestiondestock.controller;

import org.sid.gestiondestock.controller.api.ClientApi;
import org.sid.gestiondestock.dto.ClientDto;
import org.sid.gestiondestock.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientController implements ClientApi {

    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public ClientDto save(ClientDto dto) {
        return clientService.save(dto);
    }

    @Override
    public ClientDto findById(Integer id) {
        return clientService.findById(id);
    }

    @Override
    public ClientDto findByNumTel(String numTel) {
        return clientService.findByNumTel(numTel);
    }

    @Override
    public ClientDto findByEmail(String email) {
        return clientService.findByEmail(email);
    }

    @Override
    public List<ClientDto> findAll() {
        return clientService.findAll();
    }

    @Override
    public void delete(Integer id) {
        clientService.delete(id);
    }
}
