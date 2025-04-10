package local.grabrielle.invernada.api.event.model;

import java.time.LocalDateTime;
import java.util.Set;

public record NewEventDetail(
        String descricao,
        LocalDateTime data,
        String local,
        String observacao,
        Integer invernada,
        Set<String> participants
) {
}
