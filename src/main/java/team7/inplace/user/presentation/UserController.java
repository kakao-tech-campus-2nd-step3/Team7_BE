package team7.inplace.user.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team7.inplace.user.application.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController implements UserControllerApiSepc {
    public final UserService userService;

    @PatchMapping("/nickname")
    public ResponseEntity<Void> updateNickname(
            @RequestParam String nickname
    ){
        userService.updateNickname(nickname);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
