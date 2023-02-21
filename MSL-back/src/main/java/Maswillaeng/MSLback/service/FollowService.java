package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.Follow;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.FollowRepository;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public void following(Long userId, Long followingUserId) {
        //구독중인지 확인
        User user = userRepository.findById(userId).get();
        if(alreadyFollow(user,followingUserId))  throw new IllegalStateException("이미 구독중입니다.");

        User followingUser = userRepository.findById(followingUserId).get();
        Follow follow = Follow.builder().follower(user).following(followingUser).build();

        followRepository.save(follow);
    }


    public List<User> followingList(Long userId) {
     List<Follow> followings = followRepository.getFollowingList(userId);
       return followings.stream().map(follow -> follow.getFollowing()).toList();
    }

    public boolean alreadyFollow(User user,Long followingUserId){
        return 1 == user.getFollowingList().stream().filter(f->f.getFollowing().getId().equals(followingUserId)).toList().size();
    }

    public List<User> followerList(Long userId) {
      List<Follow> followers = followRepository.getFollowerList(userId);
      return followers.stream().map(follow -> follow.getFollower()).toList();
    }
}
