package local.grabrielle.invernada.api.profile.model;

public record Profile(
        String id,
        String username,
        String fullName,
        String givenName,
        String familyName,
        String email,
        boolean emailVerified,
        String document,
        String phone,
        boolean phoneVerified
) {
}
