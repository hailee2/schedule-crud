package example.demo.user.service;

import example.demo.user.dto.UserSaveRequest;
import example.demo.user.dto.UserSaveResponse;
import example.demo.user.entity.User;
import example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserSaveResponse saveUser(UserSaveRequest request){
        User user = User.of(request.getNickname(), request.getEmail(), request.getPassword());
        User savedUser = userRepository.save(user);
        return new UserSaveResponse(
                savedUser.getId(),
                savedUser.getNickname(),
                savedUser.getEmail()
        );
    }
}
