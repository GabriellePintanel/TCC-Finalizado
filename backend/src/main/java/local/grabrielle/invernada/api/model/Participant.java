package local.grabrielle.invernada.api.model;

import local.grabrielle.invernada.data.entity.PersonEntity;

import java.util.Set;

public record Participant(
        String id,
        String notes
) {
    public static Participant convert(PersonEntity personEntity) {
        return new Participant(personEntity.getId(), personEntity.getNotes());
    }

    public static PersonEntity convert(Participant participant) {
        return new PersonEntity(participant.id(), participant.notes(), Set.of(), Set.of(), Set.of());
    }
}
