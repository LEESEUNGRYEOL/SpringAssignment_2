package com.example.mybloguser.service;

import com.example.mybloguser.dto.*;
import com.example.mybloguser.entity.User;
import com.example.mybloguser.entity.UserRoleEnum;
import com.example.mybloguser.jwt.JwtUtil;
import com.example.mybloguser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
//  private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    // 요구사항 1) 회원가입 기능 (ver. Builder, ResponseEntity)
    @Transactional
    public ResponseEntity<MessageResponseDto> signup(SignupRequestDto signupRequestDto, BindingResult bindingResult) {
        // 1. SignupRequestDto 를 통해서 Client 에게 username 과 password 를 전달받고, User의 Role 지정.
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();
        UserRoleEnum role = UserRoleEnum.USER;

        // 2. 입력한 username, password @Valid 검사를 통과 못한 경우.
        if (bindingResult.hasErrors()) {
//          return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            return ResponseEntity.badRequest()  // status : bad request
                    .body(MessageResponseDto.builder()  // body : SuccessResponseDto (statusCode, msg)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .msg(bindingResult.getAllErrors().get(0).getDefaultMessage())
                            .build());
        }


        // 3. 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            return ResponseEntity.badRequest()  // status : bad request
                    .body(MessageResponseDto.builder()  // body : SuccessResponseDto (statusCode, msg)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .msg("중복된 사용자가 존재합니다.")
                            .build());
        }

        // 4. 새로운 user 객체를 다시 만들어서 줌.
        User user = User.builder()
                .username(username)
                .password(password)
                .role(role)
                .build();

        // 5. DB에 저장.
        userRepository.save(user);

        // 6. ResponseEntity 로 Return
        return ResponseEntity.ok()
                .body(MessageResponseDto.builder()   // status : ok
                        .statusCode(HttpStatus.OK.value())  // body : SuccessResponseDto (statusCode, msg)
                        .msg("회원가입 성공")
                        .build());
    }

    // 요구사항 2) 로그인 기능(ver. Builder, ResponseEntity)
    @Transactional
    public ResponseEntity<MessageResponseDto> login(LoginRequestDto loginRequestDto) {
        // 1. SignupRequestDto 를 통해서 Client 에게 username 과 password 를 전달받고, User의 Role 지정.
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 2. 가져온 Username 을 통해서 Db에 있는 username 과 비교해 사용자가 존재하는지 확인.
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(MessageResponseDto.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .msg("등록된 사용자가 없습니다.")
                            .build());
        }

        // 3. 비밀번호가 Client 에게 받은 비밀번호와 일치하는지 확인.
        if (!user.get().getPassword().equals(password)) {
            return ResponseEntity.badRequest()
                    .body(MessageResponseDto.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .msg("비밀번호가 일치하지 않습니다.")
                            .build());
        }

        // 4. header 에 들어갈 JWT 설정함.
        HttpHeaders headers = new HttpHeaders();
        headers.set(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.get().getUsername(), user.get().getRole()));

        // 5. ResponseEntity 로 Return
        return ResponseEntity.ok()
                .headers(headers)
                .body(MessageResponseDto.builder()
                        .statusCode(HttpStatus.OK.value())
                        .msg("로그인 성공")
                        .build());
    }

}

