package ru.yandex.practicum.catsgram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.PostNotFoundException;
import ru.yandex.practicum.catsgram.exception.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final UserService userService;
    private final List<Post> posts = new ArrayList<>();
    private static Integer globalId = 0;

    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }

    public List<Post> findAll(Integer size, Integer from, String sort) {
        return posts.stream()
                .sorted((p0, p1) -> compareByCreationDate(p0, p1, sort))
                .skip(from)
                .limit(size)
                .collect(Collectors.toList());
    }

    public List<Post> getFeed(String sort, int size, List<String> friends) {
        return posts.stream()
                .sorted((p0, p1) -> compareByCreationDate(p0, p1, sort))
                .filter((post -> friends.contains(post.getAuthor())))
                .limit(size).collect(Collectors.toList());
    }

    private int compareByCreationDate(Post p0, Post p1, String sort) {
        int comp = p0.getCreationDate().compareTo(p1.getCreationDate()); //прямой порядок сортировки
        if (sort.equals("desc")) {
            comp = -1 * comp; //обратный порядок сортировки
        }
        return comp;
    }

    private static Integer getNextId() {
        return globalId++;
    }

    public Post create(Post post) {
        User postAuthor = userService.findUserByEmail(post.getAuthor());
        if (postAuthor == null) {
            throw new UserNotFoundException(String.format(
                    "Пользователь %s не найден",
                    post.getAuthor()));
        }

        post.setId(getNextId());
        posts.add(post);
        return post;
    }

    public Post findPostById(Integer postId) {
        return posts.stream()
                .filter(p -> p.getId().equals(postId))
                .findFirst()
                .orElseThrow(() -> new PostNotFoundException(String.format("Пост № %d не найден", postId)));
    }

}
