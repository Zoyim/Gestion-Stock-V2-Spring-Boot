package org.sid.gestiondestock.dto;

import lombok.Builder;
import lombok.Data;
import org.sid.gestiondestock.model.LigneVente;
import org.sid.gestiondestock.model.Ventes;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class VentesDto {

    private Integer id;
    private String code;
    private Instant dateVente;
    private String commentaire;
    private Integer idEntreprise;
    private List<LigneVenteDto> ligneVentes;

    public static VentesDto fromEntity(Ventes ventes){
        if(ventes == null){
            return  null;
            // TODO thrown an Exception
        }

        return VentesDto.builder()
                .id(ventes.getId())
                .code(ventes.getCode())
                .dateVente(ventes.getDateVente())
                .commentaire(ventes.getCommentaire())
                .idEntreprise(ventes.getIdEntreprise())
                .build();
    }

    public static Ventes toEntity(VentesDto ventesDto){
        if(ventesDto == null){
            return null;
        }

        Ventes ventes = new Ventes();
        ventes.setId(ventesDto.getId());
        ventes.setCode(ventesDto.getCode());
        ventes.setDateVente(ventesDto.getDateVente());
        ventes.setCommentaire(ventesDto.getCommentaire());
        ventes.setIdEntreprise(ventesDto.getIdEntreprise());

        return ventes;
    }
}
