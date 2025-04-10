package local.grabrielle.invernada.api.pilcha.model;

import local.grabrielle.invernada.data.entity.PersonEntity;
import local.grabrielle.invernada.data.entity.PilchaEntity;

import java.util.Optional;

public record Pilcha(
        long id,
        PilchaEnum pilchaType,
        String description,
        String owner
) {
    public static Pilcha convert(PilchaEntity entity) {
        return new Pilcha(entity.getId(), entity.getPilchaType(), entity.getDescription(), Optional.ofNullable(entity.getOwner()).map(PersonEntity::getId).orElse(null));
    }
}
