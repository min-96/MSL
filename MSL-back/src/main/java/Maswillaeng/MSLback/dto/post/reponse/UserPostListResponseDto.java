package Maswillaeng.MSLback.dto.post.reponse;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
public class UserPostListResponseDto {

    boolean followState;
    List<PostResponseDto> postList;

    public UserPostListResponseDto(boolean followState, List<PostResponseDto> postList) {
        this.followState = followState;
        this.postList = postList;
    }
}
