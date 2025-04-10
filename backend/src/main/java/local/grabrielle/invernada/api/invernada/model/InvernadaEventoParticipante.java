package local.grabrielle.invernada.api.invernada.model;

public record InvernadaEventoParticipante(
        long invernadaid,
        long eventoId,
        String usuariId,
        boolean compareceu
) {
}
