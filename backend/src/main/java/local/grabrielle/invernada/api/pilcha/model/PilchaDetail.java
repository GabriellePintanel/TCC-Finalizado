package local.grabrielle.invernada.api.pilcha.model;

import local.grabrielle.invernada.data.entity.PersonEntity;
import local.grabrielle.invernada.data.entity.PilchaEntity;

import java.util.Optional;

public record PilchaDetail(
        long id,
        PilchaEnum pilchaType,
        String description,
        String tag,
        String notes,
        String owner
) {
    public static PilchaDetail convert(PilchaEntity entity) {
        return new PilchaDetail(
                entity.getId(),
                entity.getPilchaType(),
                entity.getDescription(),
                entity.getTag(),
                entity.getNotes(),
                Optional.ofNullable(entity
                                .getOwner())
                        .map(PersonEntity::getId)
                        .orElse(null)
        );
    }
}
