package com.gamemoonchul.application;

import com.gamemoonchul.common.exception.NotFoundException;
import com.gamemoonchul.common.exception.UnauthorizedException;
import com.gamemoonchul.domain.entity.Comment;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.domain.status.PostStatus;
import com.gamemoonchul.infrastructure.repository.CommentRepository;
import com.gamemoonchul.infrastructure.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostDeleteService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public String delete(Long postId, Member member) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> {
                            return new NotFoundException(PostStatus.POST_NOT_FOUND);
                        }
                );

        deleteComments(post);

        if (member.getId()
                .equals(post.getMember()
                        .getId())) {
            postRepository.delete(post);
            return "Delete Complete";
        }
        throw new UnauthorizedException(PostStatus.UNAUTHORIZED_REQUEST);
    }

    private void deleteComments(Post post) {
        List<Comment> comments = commentRepository.findAllByPost(post);
        if (!comments.isEmpty()) {
            commentRepository.deleteAll(comments);
        }
    }
}
