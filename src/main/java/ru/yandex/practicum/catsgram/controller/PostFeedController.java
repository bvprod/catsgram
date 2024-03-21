package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.exception.InvalidFeedRequestBody;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.List;
import java.util.Map;

@RestController
public class PostFeedController {

    private final PostService postService;

    @Autowired
    public PostFeedController(PostService feed) {
        this.postService = feed;
    }

    @PostMapping(value = "/feed/friends")
    public List<Post> getFeed(@RequestBody String requestBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String feedAsStr = objectMapper.readValue(requestBody, String.class);
            Map<String, Object> o = objectMapper.readValue(feedAsStr, new TypeReference<>() {
            });

            if (!o.containsKey("size") || !o.containsKey("sort") || !o.containsKey("friends")) {
                throw new InvalidFeedRequestBody("Тело запроса не содержит необходимые параметры");
            } else {
                return postService.getFeed((String) o.get("sort"), (Integer) o.get("size"), (List<String>) o.get("friends"));
            }
        } catch (JsonProcessingException e) {
            throw new InvalidFeedRequestBody(e);
        }
    }

}
