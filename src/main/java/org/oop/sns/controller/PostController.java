package org.oop.sns.controller;

import lombok.RequiredArgsConstructor;
import org.oop.sns.controller.request.PostCreateRequest;
import org.oop.sns.controller.response.Response;
import org.oop.sns.service.PostService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public Response<Void> create(@RequestBody PostCreateRequest request, Authentication authentication) {
        postService.create(request.getTitle(), request.getBody(), authentication.getName());

        return Response.success(null);
    }
}
