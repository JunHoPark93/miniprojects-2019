package com.woowacourse.zzazanstagram.model.article.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static com.woowacourse.zzazanstagram.model.article.ArticleConstant.*;
import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ArticleControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void 게시글_등록_페이지_이동_테스트() {
        webTestClient.get().uri("/articles/new")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void 게시글_등록이_되는지_테스트() {
        webTestClient.post().uri("/articles")
                .body(BodyInserters.fromFormData("imageUrl", IMAGE_URL)
                        .with("contents", CONTENTS)
                        .with("hashTag", HASHTAG))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueMatches("Location", "http://localhost:[0-9]+/");
    }

    @Test
    void 게시글_조회_페이지_이동_테스트() {
        게시글_조회();
    }

    @Test
    void 게시글_조회가_잘되는지_테스트() {
        게시글_등록();

        게시글_조회().expectBody().consumeWith(res -> {
            String body = new String(res.getResponseBody());
            assertThat(body.contains(IMAGE_URL)).isTrue();
        });
    }

    private WebTestClient.ResponseSpec 게시글_조회() {
        return webTestClient.get().uri("/")
                .exchange()
                .expectStatus().isOk();
    }

    private void 게시글_등록() {
        webTestClient.post().uri("/articles")
                .body(BodyInserters.fromFormData("imageUrl", IMAGE_URL)
                        .with("contents", CONTENTS)
                        .with("hashTag", HASHTAG))
                .exchange()
                .expectStatus().is3xxRedirection();
    }
}