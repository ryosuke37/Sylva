package io.github.ryosuke37.sylva.api.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ApiError implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String message;

    private record Detail(String target, String message) implements Serializable {
            @Serial
            private static final long serialVersionUID = 1L;
    }

    @JsonProperty("documentation_url")
    private String documentationUrl;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<Detail> details = new ArrayList<>();

    public void addDetail(String target, String message){
        details.add(new Detail(target, message));
    }
}
