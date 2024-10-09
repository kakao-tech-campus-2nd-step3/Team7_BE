package team7.inplace.user.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.inplace.global.exception.InplaceException;
import team7.inplace.global.exception.code.UserErroCode;
import team7.inplace.user.application.dto.UserCommand;
import team7.inplace.user.domain.User;
import team7.inplace.user.persistence.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void registerUser(UserCommand.Create userCreate) {
        User user = userCreate.toEntity();
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public boolean isExistUser(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public UserCommand.Info getUserByUsername(String username) {
        return UserCommand.Info.of(userRepository.findByUsername(username)
            .orElseThrow(() -> InplaceException.of(UserErroCode.NOT_FOUND)));
    }
}
