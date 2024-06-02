package com.gamemoonchul.infrastructure.repository.impl;

import com.gamemoonchul.common.exception.BadRequestException;
import com.gamemoonchul.common.exception.NotFoundException;
import com.gamemoonchul.domain.entity.Comment;
import com.gamemoonchul.domain.status.PostStatus;
import com.gamemoonchul.infrastructure.repository.ifs.CommentRepositoryIfs;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.hibernate.annotations.NotFound;
import org.springframework.stereotype.Repository;

import static com.gamemoonchul.domain.entity.QComment.comment;
import static com.gamemoonchul.domain.entity.QMember.member;
import static com.gamemoonchul.domain.entity.QPost.post;

@Repository
public class CommentRepositoryImpl implements CommentRepositoryIfs {
    JPAQueryFactory queryFactory;

    public CommentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * Member, Post를 모두 fetchJoin 함
     * 찾는 아이디가 없으면 COMMENT_NOT_FOUND 에러 발생
     */
    @Override
    public Comment searchByIdOrThrow(Long commentId) {
        Comment result = queryFactory.select(comment)
                .from(comment)
                .join(comment.member, member)
                .fetchJoin()
                .join(comment.post, post)
                .fetchJoin()
                .where(comment.id.eq(commentId))
                .fetchOne();

        if (result == null) {
            throw new NotFoundException(PostStatus.COMMENT_NOT_FOUND);
        }

        return result;
    }
}
