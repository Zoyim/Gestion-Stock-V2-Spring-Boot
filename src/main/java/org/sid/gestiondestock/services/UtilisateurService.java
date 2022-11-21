package org.sid.gestiondestock.services;

import org.sid.gestiondestock.dto.ChangerMotDePasseUtilisateurDto;
import org.sid.gestiondestock.dto.UtilisateurDto;

import java.util.List;

public interface UtilisateurService {

    UtilisateurDto save(UtilisateurDto dto);
    UtilisateurDto findById(Integer id);
    UtilisateurDto findByNumTel(String numTel);
    UtilisateurDto findByEmail(String email);
    List<UtilisateurDto> findAll();
    void delete(Integer id);

    UtilisateurDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto);
}
