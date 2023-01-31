package Maswillaeng.MSLback.domain.entity;

import Maswillaeng.MSLback.domain.BaseTimeEntity;
import Maswillaeng.MSLback.dto.user.request.UserUpdateRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phoneNumber;

    @Column(nullable = false, length = 30, unique = true)
    private String nickName;

    private String role;

    @Column(length = 1000)
    private String refreshToken;

    private String userImage;

    @Column(length = 100)
    private String introduction;

    @ColumnDefault("0")
    private int withdrawYn;

    private LocalDateTime withdrawAt;

    @Builder
    public User(String email, String password, String phoneNumber,
                String nickName, String userImage, String introduction) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.nickName = nickName;
        this.role = null;
        this.refreshToken = null;
        this.userImage = userImage;
        this.introduction = introduction;
        this.withdrawYn = 0;
        this.withdrawAt = null;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void update(UserUpdateRequestDto requestDto) {
        this.password = requestDto.getPassword();
        this.phoneNumber = requestDto.getPhoneNumber();
        this.nickName = requestDto.getNickName();
        this.userImage = requestDto.getUserImage();
        this.introduction = requestDto.getIntroduction();
    }

    public void withdraw() {
        this.withdrawYn = 1;
        this.withdrawAt = LocalDateTime.now();
    }
}
