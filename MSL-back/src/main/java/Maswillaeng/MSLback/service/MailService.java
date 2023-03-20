package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.common.exception.EntityNotFoundException;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender javaMailSender;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public void sendPasswordResetMail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException(User.class.getSimpleName())
        );

        String token = jwtTokenProvider.createPasswordResetToken();
        String url = "http://localhost:3000/login/reset-password?token=" + token;

        String subject = "비밀변호 변경 요청";
        String text = "<p>" + user.getNickName() + "님 안녕하세요.</p>" +
                "<p>아래 링크를 통해 비밀번호를 변경해주세요.</p>" +
                "<div><a href='" + url + "'>링크</a></div>";

        this.sendMail(email, subject, text);
    }

    private void sendMail(String email, String subject, String text) {

        MimeMessagePreparator msg = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) {
                try {
                    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                    mimeMessageHelper.setTo(email);
                    mimeMessageHelper.setSubject(subject);
                    mimeMessageHelper.setText(text, true);
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        };
        try {
            javaMailSender.send(msg);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
