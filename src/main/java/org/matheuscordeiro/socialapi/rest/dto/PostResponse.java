package org.matheuscordeiro.socialapi.rest.dto;

import org.matheuscordeiro.socialapi.domain.model.Post;

import java.time.LocalDateTime;

public record PostResponse(String text, LocalDateTime creationDate) {
    public static PostResponse fromEntity(Post post){
        return new PostResponse(post.getText(), post.getCreationDate());
    }
}
