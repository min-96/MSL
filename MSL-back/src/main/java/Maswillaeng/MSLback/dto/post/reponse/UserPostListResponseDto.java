package Maswillaeng.MSLback.dto.post.reponse;

import java.util.ArrayList;
import java.util.List;


public class UserPostListResponseDto {

    boolean followState;
    List<PostResponseDto> postList = new ArrayList<>();

    public UserPostListResponseDto(boolean followState, List<PostResponseDto> postList) {
        this.followState = followState;
        this.postList = postList;
    }
}
