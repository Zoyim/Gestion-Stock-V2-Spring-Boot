package org.sid.gestiondestock.services;

import org.sid.gestiondestock.dto.ClientDto;

import java.util.List;

public interface ClientService {
    ClientDto save(ClientDto dto);
    ClientDto findById(Integer id);
    ClientDto findByNumTel(String numTel);
    ClientDto findByEmail(String email);
    List<ClientDto> findAll();
    void delete(Integer id);
}
