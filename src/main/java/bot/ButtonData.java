package bot;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ButtonData {
    Long id;
    @JsonProperty("t")
    Long toScreenId;
    @JsonProperty("b")
    Boolean isBackButton;
}
