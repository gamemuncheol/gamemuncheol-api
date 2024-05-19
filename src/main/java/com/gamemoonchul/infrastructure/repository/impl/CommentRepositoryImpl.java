package com.gamemoonchul.infrastructure.repository.impl;

import com.gamemoonchul.common.exception.ApiException;
import com.gamemoonchul.domain.entity.Comment;
import com.gamemoonchul.domain.status.PostStatus;
import com.gamemoonchul.infrastructure.repository.ifs.CommentRepositoryIfs;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
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
        try {
            Comment result = queryFactory.select(comment)
                    .from(comment)
                    .join(member)
                    .on(comment.member.id.eq(member.id))
                    .fetchJoin()
                    .join(post)
                    .on(comment.post.id.eq(post.id))
                    .fetchJoin()
                    .where(comment.id.eq(commentId))
                    .fetchOne();

            if(result == null) {
                throw new Exception(new NullPointerException());
            }

            return result;
        } catch (Exception e) {
            throw new ApiException(PostStatus.COMMENT_NOT_FOUND);
        }
    }
}
