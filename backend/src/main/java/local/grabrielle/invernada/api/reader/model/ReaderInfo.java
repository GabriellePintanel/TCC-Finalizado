package local.grabrielle.invernada.api.reader.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import local.grabrielle.invernada.data.entity.PilchaEntity;

public record ReaderInfo(
        @JsonProperty("id")
        Integer id,
        @JsonProperty("pilcha")
        String pilchaName,
        @JsonProperty("user")
        String userName
) {
    public static ReaderInfo convert(PilchaEntity entity) {
        return new ReaderInfo(entity.getId(), entity.getDescription(), entity.getOwner().getId());
    }
}
