package local.grabrielle.invernada.controller;

import jakarta.servlet.http.HttpServletRequest;
import local.grabrielle.invernada.api.profile.ProfileApi;
import local.grabrielle.invernada.api.profile.model.Profile;
import local.grabrielle.invernada.service.KeycloakService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ProfileController implements ProfileApi {

    private final KeycloakService keycloakService;

    public ProfileController(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @Override
    public ResponseEntity<Profile> getMyProfile(HttpServletRequest request) {
        var perfil = Optional
                .of(request.getHeader("Authorization"))
                .flatMap(keycloakService::getUserInfo);
        return ResponseEntity.of(perfil);
    }
}
