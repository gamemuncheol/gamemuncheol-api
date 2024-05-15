package com.gamemoonchul.domain.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class VoteOptionsDummy {
    public static List<VoteOptions> createHotVoteOptions(int firstCount, int secondCount) {
        List<VoteOptions> hotVoteOptions = new ArrayList<>();
        List<Vote> firstVotes = IntStream.range(0, firstCount)
                .mapToObj(i -> VoteDummy.createVote())
                .toList();
        List<Vote> secondVotes = IntStream.range(0, secondCount)
                .mapToObj(i -> VoteDummy.createVote())
                .toList();
        VoteOptions firstVoteOptions = VoteOptions.builder()
                .votes(firstVotes)
                .build();
        VoteOptions secondVoteOptions = VoteOptions.builder()
                .votes(secondVotes)
                .build();
        hotVoteOptions.add(firstVoteOptions);
        hotVoteOptions.add(secondVoteOptions);
        return hotVoteOptions;
    }

    public static List<VoteOptions> createVoteOptionsEmptyVote() {
        List<VoteOptions> voteOptions = new ArrayList<>();
        VoteOptions firstVoteOptions = VoteOptions.builder()
                .votes(new ArrayList<>())
                .build();
        VoteOptions secondVoteOptions = VoteOptions.builder()
                .votes(new ArrayList<>())
                .build();
        voteOptions.add(firstVoteOptions);
        voteOptions.add(secondVoteOptions);
        return voteOptions;
    }

}
