package local.grabrielle.invernada.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeycloakJwtTokenConverter implements Converter<Jwt, JwtAuthenticationToken> {
    private static final String RESOURCE_ACCESS = "resource_access";
    private static final String ROLE_PREFIX = "ROLE_";
    private static final String ROLES = "roles";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter;
    private final String resourceId;

    public KeycloakJwtTokenConverter(JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter, String resourceId) {
        this.jwtGrantedAuthoritiesConverter = jwtGrantedAuthoritiesConverter;
        this.resourceId = resourceId;
    }

    @Override
    public JwtAuthenticationToken convert(@NonNull Jwt jwt) {
        Stream<SimpleGrantedAuthority> accesses = Optional.of(jwt).map(token -> token.getClaimAsMap(RESOURCE_ACCESS))
                .map(resourceAccessMap ->
                        MAPPER.convertValue(
                                resourceAccessMap.get(resourceId),
                                new TypeReference<Map<String, List<String>>>() {
                                }
                        )
                )
                .map(resourceData -> resourceData.get(ROLES))
                .orElse(List.of()).stream()
                .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role.toLowerCase()));

        Set<GrantedAuthority> authorities = Stream
                .concat(jwtGrantedAuthoritiesConverter.convert(jwt).stream(), accesses)
                .collect(Collectors.toSet());

        String principalClaimName = Optional.of("preferred_username")
                .map(jwt::getClaimAsString)
                .orElse(jwt.getClaimAsString(JwtClaimNames.SUB));

        return new JwtAuthenticationToken(jwt, authorities, principalClaimName);
    }
}
