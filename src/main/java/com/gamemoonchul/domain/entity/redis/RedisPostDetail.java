package com.gamemoonchul.domain.entity.redis;

import com.gamemoonchul.common.util.StringUtils;
import com.gamemoonchul.domain.entity.Post;
import com.gamemoonchul.infrastructure.web.dto.response.CommentResponse;
import com.gamemoonchul.infrastructure.web.dto.response.MatchGameResponse;
import com.gamemoonchul.infrastructure.web.dto.response.MemberResponseDto;
import com.gamemoonchul.infrastructure.web.dto.response.PostDetailResponse;
import com.gamemoonchul.infrastructure.web.dto.response.VoteRatioResponse;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "post-detail", timeToLive = 600L) // 10분간 살아 있음
public class RedisPostDetail {
    @Id
    @Indexed
    private Long id;
    private MemberResponseDto author;
    private String videoUrl;
    private String thumbnailUrl;
    private String title;
    private String content;
    private String timesAgo;
    private Long viewCount;
    private Long commentCount;
    private List<CommentResponse> comments;
    private List<VoteRatioResponse> voteDetail;

    public static RedisPostDetail fromResponse(PostDetailResponse postDetailResponse) {
        return RedisPostDetail.builder()
            .id(postDetailResponse.getId())
            .author(postDetailResponse.getAuthor())
            .videoUrl(postDetailResponse.getVideoUrl())
            .thumbnailUrl(postDetailResponse.getThumbnailUrl())
            .title(postDetailResponse.getTitle())
            .content(postDetailResponse.getContent())
            .timesAgo(postDetailResponse.getTimesAgo())
            .viewCount(postDetailResponse.getViewCount())
            .commentCount(postDetailResponse.getCommentCount())
            .comments(postDetailResponse.getComments())
            .voteDetail(postDetailResponse.getVoteDetail())
            .build();
    }

    public static RedisPostDetail toCache(Post post) {
        return RedisPostDetail.builder()
            .id(post.getId())
            .author(new MemberResponseDto(post.getMember()))
            .videoUrl(post.getVideoUrl())
            .thumbnailUrl(post.getThumbnailUrl())
            .commentCount(post.getCommentCount())
            .title(post.getTitle())
            .content(post.getContent())
            .timesAgo(StringUtils.getTimeAgo(post.getCreatedAt()))
            .viewCount(post.getViewCount())
            .voteDetail(getVoteDetail(post))
            .build();
    }

    public static List<VoteRatioResponse> getVoteDetail(Post post) {
        HashMap<Long, Double> voteRatioMap = new HashMap<>();
        post.getVoteOptions()
            .forEach(vo -> {
                voteRatioMap.put(vo.getId(), 0.0);
                Optional.ofNullable(vo.getVotes())
                    .ifPresent(votes -> {
                        votes.forEach(v -> voteRatioMap.put(v.getId(), 0.0));
                    });
            });

        Double totalVoteCnt = voteRatioMap.values()
            .stream()
            .mapToDouble(Double::doubleValue)
            .sum();

        voteRatioMap.forEach((k, v) -> {
            if (totalVoteCnt == 0) {
                voteRatioMap.put(k, 0.0);
            } else {
                voteRatioMap.put(k, (v / totalVoteCnt) * 100);
            }
        });

        List<VoteRatioResponse> result = post.getVoteOptions()
            .stream()
            .map(vo -> {
                Double voteRatio = voteRatioMap.get(vo.getId());
                MatchGameResponse.MatchUserResponse matchUserResponse = MatchGameResponse.MatchUserResponse.toResponseVoId(vo.getMatchUser(), vo.getId());
                return new VoteRatioResponse(matchUserResponse, voteRatio);
            })
            .toList();

        return result;
    }

    public void setComments(List<CommentResponse> comments) {
        this.comments = comments;
    }
}
