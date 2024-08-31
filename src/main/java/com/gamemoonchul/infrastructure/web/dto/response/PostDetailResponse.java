package com.gamemoonchul.infrastructure.web.dto.response;

import com.gamemoonchul.application.converter.MemberConverter;
import com.gamemoonchul.common.util.StringUtils;
import com.gamemoonchul.domain.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Getter
@Builder
public class PostDetailResponse {
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

    public static PostDetailResponse toResponse(Post post) {
        return PostDetailResponse.builder()
            .id(post.getId())
            .author(MemberConverter.toResponseDto(post.getMember()))
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

    public static PostDetailResponse toResponse(Post post, List<CommentResponse> comments) {
        return PostDetailResponse.builder()
            .id(post.getId())
            .author(MemberConverter.toResponseDto(post.getMember()))
            .videoUrl(post.getVideoUrl())
            .thumbnailUrl(post.getThumbnailUrl())
            .commentCount(post.getCommentCount())
            .title(post.getTitle())
            .content(post.getContent())
            .timesAgo(StringUtils.getTimeAgo(post.getCreatedAt()))
            .viewCount(post.getViewCount())
            .comments(comments)
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
