package ledoude.entity;

import com.fasterxml.jackson.annotation.JsonView;
import ledoude.controller.views.Views;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MyResource {
    @JsonView(Views.Base.class)
    private Integer value;

    private Integer hiddenValue;
}
