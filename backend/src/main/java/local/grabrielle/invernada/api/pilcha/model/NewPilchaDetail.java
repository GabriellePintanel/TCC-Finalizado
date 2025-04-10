package local.grabrielle.invernada.api.pilcha.model;

public record NewPilchaDetail(
        PilchaEnum pilchaType,
        String description,
        String tag,
        String user,
        String notes
) {
}
