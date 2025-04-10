package local.grabrielle.invernada.api.pilcha.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PilchaEnum {
    UNKNOWN,
    BOMBACHA,
    BOTA,
    CAMISA,
    FAIXA,
    GUAIACA,
    SAPATILHA,
    VESTIDO;

    @JsonValue
    public int toValue() {
        return ordinal();
    }
}
