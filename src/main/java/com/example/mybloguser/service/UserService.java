package com.example.mybloguser.service;

import com.example.mybloguser.dto.LoginRequestDto;
import com.example.mybloguser.dto.LoginResponseDto;
import com.example.mybloguser.dto.SignupRequestDto;
import com.example.mybloguser.dto.SignupResponseDto;
import com.example.mybloguser.entity.User;
import com.example.mybloguser.entity.UserRoleEnum;
import com.example.mybloguser.jwt.JwtUtil;
import com.example.mybloguser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";


    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
    }
}
