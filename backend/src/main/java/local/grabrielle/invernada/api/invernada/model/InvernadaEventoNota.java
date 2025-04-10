package local.grabrielle.invernada.api.invernada.model;

import local.grabrielle.invernada.api.event.model.NotaTipo;

public record InvernadaEventoNota(
        long invernadaId,
        long eventoId,
        NotaTipo notaTipo,
        float notaValor,
        String observavao
) {
}
