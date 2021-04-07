package ledoude.controller.views;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.Page;

@JsonView(Views.Base.class)
public interface PageMixInView<T> extends Page<T> {}
