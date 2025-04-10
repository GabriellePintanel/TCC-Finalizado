package local.grabrielle.invernada.service;

import local.grabrielle.invernada.api.profile.model.Profile;
import local.grabrielle.invernada.data.client.KeycloakClient;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KeycloakService {
    private final KeycloakClient keycloakClient;

    public KeycloakService(KeycloakClient keycloakClient) {
        this.keycloakClient = keycloakClient;
    }

    private static Profile convert(@NonNull KeycloakClient.UserInfoResponse userInfoResponse) {
        return new Profile(
                userInfoResponse.id(),
                userInfoResponse.username(),
                userInfoResponse.fullName(),
                userInfoResponse.givenName(),
                userInfoResponse.familyName(),
                userInfoResponse.email(),
                Boolean.TRUE.equals(userInfoResponse.emailVerified()),
                userInfoResponse.document(),
                userInfoResponse.phone(),
                Boolean.TRUE.equals(userInfoResponse.phoneVerified())
        );
    }

    public Optional<Profile> getUserInfo(String token) {
        return Optional.ofNullable(keycloakClient.getPerfil(token))
                .map(KeycloakService::convert);
    }

}
