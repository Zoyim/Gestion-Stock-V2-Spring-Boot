package org.sid.gestiondestock.controller;

import org.sid.gestiondestock.controller.api.EntrepriseApi;
import org.sid.gestiondestock.dto.EntrepriseDto;
import org.sid.gestiondestock.services.EntrepriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EntrepriseController implements EntrepriseApi {

    private EntrepriseService entrepriseService;

    @Autowired
    public EntrepriseController(EntrepriseService entrepriseService) {
        this.entrepriseService = entrepriseService;
    }

    @Override
    public EntrepriseDto save(EntrepriseDto dto) {
        return entrepriseService.save(dto);
    }

    @Override
    public EntrepriseDto findById(Integer id) {
        return entrepriseService.findById(id);
    }

    @Override
    public EntrepriseDto findByNumTel(String numTel) {
        return entrepriseService.findByNumTel(numTel);
    }

    @Override
    public EntrepriseDto findByEmail(String email) {
        return entrepriseService.findByEmail(email);
    }

    @Override
    public EntrepriseDto findByNom(String nom) {
        return entrepriseService.findByNom(nom);
    }

    @Override
    public List<EntrepriseDto> findAll() {
        return entrepriseService.findAll();
    }

    @Override
    public void delete(Integer id) {
        entrepriseService.delete(id);
    }
}
