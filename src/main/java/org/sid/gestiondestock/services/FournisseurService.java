package org.sid.gestiondestock.services;

import org.sid.gestiondestock.dto.FournisseurDto;

import java.util.List;

public interface FournisseurService {

    FournisseurDto save(FournisseurDto dto);
    FournisseurDto findById(Integer id);
    FournisseurDto findByNumTel(String numTel);
    FournisseurDto findByEmail(String email);
    List<FournisseurDto> findAll();
    void delete(Integer id);
}
