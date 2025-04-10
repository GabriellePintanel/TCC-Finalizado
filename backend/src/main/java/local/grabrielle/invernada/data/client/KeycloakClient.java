package local.grabrielle.invernada.data.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Component
public class KeycloakClient {
    private final RestClient defaultClient;

    public KeycloakClient(@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}") String issuer) {
        this.defaultClient = Optional.ofNullable(issuer)
                .map(baseUrl -> RestClient.builder().baseUrl(baseUrl).build())
                .orElseThrow();

    }

    public UserInfoResponse getPerfil(String token) {
        return defaultClient
                .get()
                .uri("/protocol/openid-connect/userinfo")
                .header("Authorization", token)
                .retrieve()
                .body(UserInfoResponse.class);
    }

    public record UserInfoResponse(
            @JsonProperty("sub")
            String id,
            @JsonProperty("preferred_username")
            String username,
            @JsonProperty("name")
            String fullName,
            @JsonProperty("given_name")
            String givenName,
            @JsonProperty("family_name")
            String familyName,
            @JsonProperty("email")
            String email,
            @JsonProperty("email_verified")
            Boolean emailVerified,
            @JsonProperty("document")
            String document,
            @JsonProperty("phone_number")
            String phone,
            @JsonProperty("phone_number_verified")
            Boolean phoneVerified
    ) {
    }

}
