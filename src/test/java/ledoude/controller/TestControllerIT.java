package ledoude.controller;

import static org.hamcrest.core.Every.everyItem;
import static org.hamcrest.core.Is.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

@SpringBootTest
@AutoConfigureMockMvc
class TestControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void testList() throws Exception {
        var result = mockMvc.perform(get("/api/v0/resources"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ResultMatcher.matchAll(
                        jsonPath("$.content[*].value").value(everyItem(isA(Integer.class))),
                        jsonPath("$.content[*].hiddenValue").doesNotHaveJsonPath(),
                        jsonPath("$.totalPages").isNumber(),
                        jsonPath("$.totalElements").isNumber(),
                        jsonPath("$.last").isBoolean(),
                        jsonPath("$.size").isNumber(),
                        jsonPath("$.number").isNumber(),
                        jsonPath("$.numberOfElements").isNumber(),
                        jsonPath("$.first").isBoolean(),
                        jsonPath("$.empty").isBoolean()
                )).andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void testShow() throws Exception {
        var result = mockMvc.perform(get("/api/v0/resources/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ResultMatcher.matchAll(
                        jsonPath("$.value").isNumber(),
                        jsonPath("$.hiddenValue").doesNotHaveJsonPath())).andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

}