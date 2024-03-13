package ru.yandex.practicum.catsgram.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.model.Post;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PostService {
    private final List<Post> posts = new ArrayList<>();

    public List<Post> findAll() {
        log.debug("Текущее количество постов: {}", posts.size());
        return posts;
    }

    public Post create(Post post) {
        posts.add(post);
        return post;
    }
}
