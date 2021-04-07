package ledoude.controller;

import com.fasterxml.jackson.annotation.JsonView;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import ledoude.controller.views.Views;
import ledoude.entity.MyResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v0/resources")
public class TestController {

    @GetMapping
    @JsonView(Views.Base.class)
    public Page<MyResource> list() {
        var list = randomList();
        return new PageImpl<>(list, PageRequest.of(0, 10), list.size());
    }

    @GetMapping("/{id}")
    @JsonView(Views.Base.class)
    public MyResource show(@PathVariable("id") Integer id) {
        return makeMyResource(id);
    }

    private List<MyResource> randomList() {
        return IntStream.range(0, new Random().nextInt(10))
                .mapToObj(i -> makeMyResource(i))
                .collect(Collectors.toUnmodifiableList());
    }

    private MyResource makeMyResource(int i) {
        return MyResource.builder().value(i).hiddenValue(i).build();
    }

}
