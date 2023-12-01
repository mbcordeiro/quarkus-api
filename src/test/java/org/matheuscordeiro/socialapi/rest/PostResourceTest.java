package org.matheuscordeiro.socialapi.rest;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.matheuscordeiro.socialapi.domain.model.Follower;
import org.matheuscordeiro.socialapi.domain.model.Post;
import org.matheuscordeiro.socialapi.domain.model.User;
import org.matheuscordeiro.socialapi.domain.repository.FollowerRepository;
import org.matheuscordeiro.socialapi.domain.repository.PostRepository;
import org.matheuscordeiro.socialapi.domain.repository.UserRepository;
import org.matheuscordeiro.socialapi.rest.dto.CreatePostRequest;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(PostResource.class)
class PostResourceTest {
    @Inject
    UserRepository userRepository;
    @Inject
    FollowerRepository followerRepository;
    @Inject
    PostRepository postRepository;

    Long userId;
    Long userNotFollowerId;
    Long userFollowerId;

    @BeforeEach
    @Transactional
    public void setUP(){
        final var user = new User();
        user.setAge(27);
        user.setName("Matheus");
        userRepository.persist(user);
        userId = user.getId();

        final var post = new Post();
        post.setText("Hello");
        post.setUser(user);
        postRepository.persist(post);

        var userNotFollower = new User();
        userNotFollower.setAge(27);
        userNotFollower.setName("Cordeiro");
        userRepository.persist(userNotFollower);
        userNotFollowerId = userNotFollower.getId();

        var userFollower = new User();
        userFollower.setAge(31);
        userFollower.setName("Barros");
        userRepository.persist(userFollower);
        userFollowerId = userFollower.getId();

        final var follower = new Follower();
        follower.setUser(user);
        follower.setFollower(userFollower);
        followerRepository.persist(follower);
    }

    @Test
    @DisplayName("should create a post for a user")
     void shouldCreatePost(){
        final var postRequest = new CreatePostRequest("Some text");
        given()
                .contentType(ContentType.JSON)
                .body(postRequest)
                .pathParam("userId", userId)
                .when()
                .post()
                .then()
                .statusCode(201);
    }

    @Test
    @DisplayName("should return 404 when trying to make a post for an not exist user")
    void shouldPostForAnNotExistUserTest(){
        final var postRequest = new CreatePostRequest("Some text");
        given()
                .contentType(ContentType.JSON)
                .body(postRequest)
                .pathParam("userId", 999)
                .when()
                .post()
                .then()
                .statusCode(404);
    }
}
