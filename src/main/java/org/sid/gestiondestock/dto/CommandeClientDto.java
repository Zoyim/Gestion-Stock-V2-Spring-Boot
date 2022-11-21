package org.sid.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.sid.gestiondestock.model.CommandeClient;
import org.sid.gestiondestock.model.EtatCommande;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class CommandeClientDto {

    private Integer id;
    private String code;
    private Instant dateCommande;
    private EtatCommande etatCommande;
    private ClientDto client;
    private Integer idEntreprise;
    @JsonIgnore
    private List<LigneCommandeClientDto> ligneCommandeClients;

    public static CommandeClientDto fromEntity(CommandeClient commandeClient){
        if(commandeClient == null){
            return  null;
            // TODO thrown an Exception
        }

        return CommandeClientDto.builder()
                .id(commandeClient.getId())
                .code(commandeClient.getCode())
                .dateCommande(commandeClient.getDateCommande())
                .etatCommande(commandeClient.getEtatCommande())
                .client(ClientDto.fromEntity(commandeClient.getClient()))
                .idEntreprise(commandeClient.getIdEntreprise())
                .build();
    }

    public static CommandeClient toEntity(CommandeClientDto commandeClientDto){
        if(commandeClientDto == null){
            return null;
        }

        CommandeClient commandeClient = new CommandeClient();

        commandeClient.setId(commandeClientDto.getId());
        commandeClient.setCode(commandeClientDto.getCode());
        commandeClient.setDateCommande(commandeClientDto.getDateCommande());
        commandeClient.setEtatCommande(commandeClientDto.getEtatCommande());
        commandeClient.setClient(ClientDto.toEntity(commandeClientDto.getClient()));
        commandeClient.setIdEntreprise(commandeClientDto.getIdEntreprise());
        return commandeClient;
    }

    public boolean isCommandeLivree(){
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }
}
