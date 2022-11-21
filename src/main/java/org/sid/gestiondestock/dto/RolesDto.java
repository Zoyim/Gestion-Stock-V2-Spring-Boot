package org.sid.gestiondestock.dto;

import lombok.Builder;
import lombok.Data;
import org.sid.gestiondestock.model.Roles;

@Data
@Builder
public class RolesDto {

    private Integer id;
    private String roleName;
    private UtilisateurDto utilisateur;

    public static RolesDto fromEntity(Roles roles){
        if(roles == null){
            return  null;
            // TODO thrown an Exception
        }

        return RolesDto.builder()
                .id(roles.getId())
                .roleName(roles.getRoleName())
                .build();
    }

    public static Roles toEntity(RolesDto rolesDto){
        if(rolesDto == null){
            return null;
        }

        Roles roles = new Roles();
        roles.setId(rolesDto.getId());
        roles.setRoleName(rolesDto.getRoleName());
        roles.setUtilisateur(UtilisateurDto.toEntity(rolesDto.getUtilisateur()));

        return roles;
    }
}
