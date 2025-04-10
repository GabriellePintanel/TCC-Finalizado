package local.grabrielle.invernada.api.invernada.model;

import local.grabrielle.invernada.data.entity.InvernadaEntity;

public record Invernada(
        long id,
        String nome
) {
    public static Invernada convert(InvernadaEntity entity) {
        return new Invernada(entity.getId(), entity.getName());
    }
}
