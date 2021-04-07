package ledoude.configuration;

import ledoude.controller.views.PageMixInView;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

@Configuration
public class JacksonConfiguration {

    @Bean
    Jackson2ObjectMapperBuilderCustomizer addPageViewMixIn() {
        return builder -> {
            builder.mixIn(Page.class, PageMixInView.class);
        };
    }
}
