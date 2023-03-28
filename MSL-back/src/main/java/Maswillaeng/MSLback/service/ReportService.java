package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.common.exception.AlreadyExistException;
import Maswillaeng.MSLback.common.exception.EntityNotFoundException;
import Maswillaeng.MSLback.common.exception.NotExistException;
import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.Report;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.PostRepository;
import Maswillaeng.MSLback.domain.repository.ReportRepository;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.utils.auth.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;


    @Transactional
    public void saveReport(Long postId) {
        User user = userRepository.findById(UserContext.userData.get().getUserId()).get();
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new EntityNotFoundException(Post.class.getSimpleName())
        );

        if (reportRepository.existsByUserAndPost(user, post)) {
            throw new AlreadyExistException(Report.class.getSimpleName());
        }

        Report report = Report.builder()
                .user(user)
                .post(post).build();
        reportRepository.save(report);
    }

    @Transactional
    public void deleteReport(Long postId) {
        User user = userRepository.findById(UserContext.userData.get().getUserId()).get();
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new EntityNotFoundException(Post.class.getSimpleName()));
        Report report = reportRepository.findByUserAndPost(user, post).orElseThrow(
                () -> new NotExistException(Report.class.getSimpleName()));

        reportRepository.delete(report);
    }
}
