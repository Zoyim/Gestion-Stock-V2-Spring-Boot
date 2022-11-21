package org.sid.gestiondestock.controller.auth;

import static org.sid.gestiondestock.utils.Constants.APP_ROOT;
import static org.sid.gestiondestock.utils.Constants.AUTHENTICATION_ENDPOINT;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.sid.gestiondestock.dto.ArticleDto;
import org.sid.gestiondestock.dto.UtilisateurDto;
import org.sid.gestiondestock.dto.auth.AuthenticationRequest;
import org.sid.gestiondestock.dto.auth.AuthenticationResponse;
import org.sid.gestiondestock.model.auth.ExtendedUser;
import org.sid.gestiondestock.services.auth.ApplicationUserDetailsService;
import org.sid.gestiondestock.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(AUTHENTICATION_ENDPOINT)
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ApplicationUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(value = AUTHENTICATION_ENDPOINT + "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Connecter un utilisateur", notes = "Cette methode permet de connecter un utilisateur par son email et mot de passe", response = AuthenticationRequest.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'utilisateur est connecté"),
            @ApiResponse(code = 400, message = "L'utilisateur n'est pas valide")
    })
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getLogin());

        final String jwt = jwtUtil.generateToken((ExtendedUser) userDetails);

        return ResponseEntity.ok(AuthenticationResponse.builder().accessToken(jwt).build());
    }

}
