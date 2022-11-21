package org.sid.gestiondestock.services;

import org.sid.gestiondestock.dto.EntrepriseDto;

import java.util.List;

public interface EntrepriseService {

    EntrepriseDto save(EntrepriseDto dto);
    EntrepriseDto findById(Integer id);
    EntrepriseDto findByNumTel(String numTel);
    EntrepriseDto findByEmail(String email);
    EntrepriseDto findByNom(String nom);
    List<EntrepriseDto> findAll();
    void delete(Integer id);
}
