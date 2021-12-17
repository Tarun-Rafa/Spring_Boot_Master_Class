package com.example.starter.jsonplaceholder;

import com.example.starter.jsonplaceholder.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "jsonplaceholder",url="https://jsonplaceholder.typicode.com/")
public interface JSONPlaceHolderClient {
    @GetMapping("posts")
    List<Post> getPosts();

    @GetMapping("posts/{postId}")
    Post getPost(@PathVariable("postId")Integer postId);
}
