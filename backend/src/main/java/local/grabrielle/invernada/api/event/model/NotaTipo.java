package local.grabrielle.invernada.api.event.model;

public enum NotaTipo {
    COREOGRAFIA(1),
    CORRECAO(2),
    HARMONIA(3),
    INTERPRETACAO(4),
    FINAL(5);

    private final int value;

    NotaTipo(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
