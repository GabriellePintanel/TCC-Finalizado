package local.grabrielle.invernada.api.event.model;

import local.grabrielle.invernada.api.model.Participant;
import local.grabrielle.invernada.data.entity.EventEntity;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record EventDetail(
        long id,
        String descricao,
        LocalDateTime data,
        String local,
        String observacao,
        Set<Participant> participants
) {
    public static EventDetail convert(EventEntity entity) {
        final Set<Participant> participants = entity.getParticipants().stream().map(Participant::convert).collect(Collectors.toSet());
        return new EventDetail(entity.getId(), entity.getName(), entity.getDate(), entity.getPlace(), entity.getNotes(), participants);
    }
}
