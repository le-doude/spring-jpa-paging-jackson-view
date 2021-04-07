package ledoude.controller.views;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import lombok.Getter;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

public class PageMixInViewTest {

    private Page<Obj> generateTestData() {
        return new PageImpl<>(
                Arrays.asList(new Obj("1"), new Obj("2"), new Obj("3")),
                PageRequest.of(0, 10), 3);
    }

    @Getter
    public static class Obj {

        @JsonView(Views.Base.class)
        private final String value;

        private final String hiddenValue;

        public Obj(String value) {
            this.value = value;
            this.hiddenValue = value;
        }
    }

    @Nested
    class UnitTest {

        @Test
        void shouldSucceedWithMixIn() throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
            mapper.addMixIn(Page.class, PageMixInView.class);

            Page<Obj> page = generateTestData();
            String jsonStr = mapper
                    .writerWithView(Views.Base.class)
                    .withDefaultPrettyPrinter()
                    .writeValueAsString(page);

            System.out.println(jsonStr);
            assertThat(jsonStr, hasJsonPath("$.content[0].value", is("1")));
            assertThat(jsonStr, not(hasJsonPath("$.content[0].hiddenValue")));
            assertThat(jsonStr, hasJsonPath("$.content[1].value", is("2")));
            assertThat(jsonStr, not(hasJsonPath("$.content[1].hiddenValue")));
            assertThat(jsonStr, hasJsonPath("$.content[2].value", is("3")));
            assertThat(jsonStr, not(hasJsonPath("$.content[2].hiddenValue")));
            assertThat(jsonStr, hasJsonPath("$.totalPages", is(1)));
            assertThat(jsonStr, hasJsonPath("$.totalElements", is(3)));
            assertThat(jsonStr, hasJsonPath("$.last", is(true)));
            assertThat(jsonStr, hasJsonPath("$.size", is(10)));
            assertThat(jsonStr, hasJsonPath("$.number", is(0)));
            assertThat(jsonStr, hasJsonPath("$.numberOfElements", is(3)));
            assertThat(jsonStr, hasJsonPath("$.first", is(true)));
            assertThat(jsonStr, hasJsonPath("$.empty", is(false)));
        }

        @Test
        void shouldFailWithoutMixIn() throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

            Page<Obj> page = generateTestData();
            String jsonStr = mapper
                    .writerWithView(Views.Base.class)
                    .withDefaultPrettyPrinter()
                    .writeValueAsString(page);
            assertThat(jsonStr, not(hasJsonPath("$.content[0].value")));
            assertThat(jsonStr, not(hasJsonPath("$.content[0].hiddenValue")));
            assertThat(jsonStr, not(hasJsonPath("$.content[1].value")));
            assertThat(jsonStr, not(hasJsonPath("$.content[1].hiddenValue")));
            assertThat(jsonStr, not(hasJsonPath("$.content[2].value")));
            assertThat(jsonStr, not(hasJsonPath("$.content[2].hiddenValue")));
            assertThat(jsonStr, not(hasJsonPath("$.totalPages")));
            assertThat(jsonStr, not(hasJsonPath("$.totalElements")));
            assertThat(jsonStr, not(hasJsonPath("$.last")));
            assertThat(jsonStr, not(hasJsonPath("$.size")));
            assertThat(jsonStr, not(hasJsonPath("$.number")));
            assertThat(jsonStr, not(hasJsonPath("$.numberOfElements")));
            assertThat(jsonStr, not(hasJsonPath("$.first")));
            assertThat(jsonStr, not(hasJsonPath("$.empty")));
        }
    }

    @Nested
    @SpringBootTest
    class IntegrationTest {

        @Autowired
        ObjectMapper mapper;

        @Test
        void shouldBeUsingMixin() throws JsonProcessingException {
            Page<Obj> page = generateTestData();
            String jsonStr = mapper
                    .writerWithView(Views.Base.class)
                    .withDefaultPrettyPrinter()
                    .writeValueAsString(page);

            assertThat(jsonStr, hasJsonPath("$.content[0].value", is("1")));
            assertThat(jsonStr, not(hasJsonPath("$.content[0].hiddenValue")));
            assertThat(jsonStr, hasJsonPath("$.content[1].value", is("2")));
            assertThat(jsonStr, not(hasJsonPath("$.content[1].hiddenValue")));
            assertThat(jsonStr, hasJsonPath("$.content[2].value", is("3")));
            assertThat(jsonStr, not(hasJsonPath("$.content[2].hiddenValue")));
            assertThat(jsonStr, hasJsonPath("$.totalPages", is(1)));
            assertThat(jsonStr, hasJsonPath("$.totalElements", is(3)));
            assertThat(jsonStr, hasJsonPath("$.last", is(true)));
            assertThat(jsonStr, hasJsonPath("$.size", is(10)));
            assertThat(jsonStr, hasJsonPath("$.number", is(0)));
            assertThat(jsonStr, hasJsonPath("$.numberOfElements", is(3)));
            assertThat(jsonStr, hasJsonPath("$.first", is(true)));
            assertThat(jsonStr, hasJsonPath("$.empty", is(false)));
        }
    }
}