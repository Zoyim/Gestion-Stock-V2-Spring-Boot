package org.sid.gestiondestock.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.sid.gestiondestock.dto.ClientDto;
import org.sid.gestiondestock.exception.EntityNotFoundException;
import org.sid.gestiondestock.exception.ErrorCodes;
import org.sid.gestiondestock.exception.InvalidEntityException;
import org.sid.gestiondestock.exception.InvalidOperationException;
import org.sid.gestiondestock.model.CommandeClient;
import org.sid.gestiondestock.repository.ClientRepository;
import org.sid.gestiondestock.repository.CommandeClientRepository;
import org.sid.gestiondestock.services.ClientService;
import org.sid.gestiondestock.validator.ClientValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private CommandeClientRepository commandeClientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, CommandeClientRepository commandeClientRepository) {
        this.clientRepository = clientRepository;
        this.commandeClientRepository = commandeClientRepository;
    }

    @Override
    public ClientDto save(ClientDto dto) {
        List<String> errors = ClientValidator.validate(dto);
        if(!errors.isEmpty()){
            log.error("Client is not valid {}", dto);
            throw new InvalidEntityException("Le client n'est pas valide", ErrorCodes.CLIENT_NOT_VALID, errors);
        }
        return ClientDto.fromEntity(
                clientRepository.save(
                        ClientDto.toEntity(dto)
                )
        );
    }

    @Override
    public ClientDto findById(Integer id) {
        if(id == null){
            log.error("Client ID is null");
            return null;
        }
        return clientRepository.findById(id)
                .map(ClientDto::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException(
                        "Aucun client avec l'ID = " + id + " n'a pas ete trouve dans la BDD",
                        ErrorCodes.CLIENT_NOT_FOUND
                ));
    }

    @Override
    public ClientDto findByNumTel(String numTel) {
        if(!StringUtils.hasLength(numTel)){
            log.error("Client Numero Telephone is null");
            return null;
        }
        return clientRepository.findClientByNumTel(numTel)
                .map(ClientDto::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException(
                        "Aucun client avec le Numero Telelphone = " + numTel + " n'a pas ete trouve dans la BDD",
                        ErrorCodes.CLIENT_NOT_FOUND
                ));
    }

    @Override
    public ClientDto findByEmail(String email) {
        if(!StringUtils.hasLength(email)){
            log.error("Client Email is null");
            return null;
        }
        return clientRepository.findClientByEmail(email)
                .map(ClientDto::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException(
                        "Aucun client avec l'email = " + email + " n'a pas ete trouve dans la BDD",
                        ErrorCodes.CLIENT_NOT_FOUND
                ));
    }

    @Override
    public List<ClientDto> findAll() {
        return clientRepository.findAll().stream()
                .map(ClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("Client ID is null");
            return;
        }

        List<CommandeClient> commandeClients = commandeClientRepository.findAllByClientId(id);
        if(!commandeClients.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer ce client qui a deja des commandes clients", ErrorCodes.CLIENT_ALREADY_IN_USE);
        }

        clientRepository.deleteById(id);
    }
}
