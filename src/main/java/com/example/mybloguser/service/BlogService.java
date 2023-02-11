package com.example.mybloguser.service;


import com.example.mybloguser.dto.BlogRequestDto;
import com.example.mybloguser.dto.BlogResponseDto;
import com.example.mybloguser.dto.SendMessageDto;
import com.example.mybloguser.entity.Blog;
import com.example.mybloguser.entity.User;
import com.example.mybloguser.jwt.JwtUtil;
import com.example.mybloguser.repository.BlogRepository;
import com.example.mybloguser.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

// Spring Framework Annotation
// 서비스 계층에서의 비즈니스 로직을 구현할 클래스에 붙여서 사용.
// @Service 가 붙은 클래스를 빈으로 자동 등록해서 다른 클래스에서 이 클래스의 기능을 사용할 수 있도록 함.
@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // 요구사항1. 전체 게시글 목록 조회
    // (이때 RSP 로 전달해야하는 것이 List 가 아닌, DTO 로 전달해야함.)
    // @Transactional -> 다음 어노테이션이 적용된 메서드에서 수행되는 모든 작업은 하나의 트랜잭션 안에서 수행
    // 즉 한 메서드에서 수행되는 여러 작업이 실패할 경우 이전에 수행된 모든 작업이 롤백되어서 원상태로 돌아간다.
    @Transactional(readOnly = true)

    public List<BlogResponseDto> getBlogs() {
        List <Blog> blogList = blogRepository.findAllByOrderByCreatedAtAsc();
        List <BlogResponseDto> blogResponseDtoList = new ArrayList<>();
        for (Blog blog : blogList) {
            BlogResponseDto tmp = new BlogResponseDto(blog);
            blogResponseDtoList.add(tmp);
        }
        return blogResponseDtoList;
    }

    // 요구사항2. 게시글 작성
    @Transactional
    public BlogResponseDto createBlog(BlogRequestDto blogrequestDto, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        // token 의 정보를 임시로 저장하는 곳.
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 추가 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 요청받은 DTO 로 DB에 저장할 객체 만들기
            Blog blog = blogRepository.saveAndFlush(new Blog(blogrequestDto, user.getId()));

            return new BlogResponseDto(blog);
        } else {
            return null;
        }
    }


    // 요구사항3. 선택한 게시글 조회
    @Transactional(readOnly = true)
    public BlogResponseDto getBlogs(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당글이 없습니다.")
        );
        BlogResponseDto blogResponseDto = new BlogResponseDto(blog);
        return blogResponseDto;
    }

    // 요구사항4. 선택한 게시글 수정
    @Transactional
    public BlogResponseDto updateBlog(Long id, BlogRequestDto blogRequestDto,HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 최저가 업데이트 가능
        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Blog blog = blogRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new NullPointerException("해당 상품은 존재하지 않습니다.")
            );

            blog.update(blogRequestDto);
            return new BlogResponseDto(blog);
        } else {
            return null;
        }
    }

    // 요구사항5. 선택한 게시글 삭제
    @Transactional
    public SendMessageDto deleteBlog(Long id,  HttpServletRequest request) {

        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        SendMessageDto sendMessageDto = new SendMessageDto();
        // 토큰이 있는 경우에만 관심상품 최저가 업데이트 가능
        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Blog blog = blogRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new NullPointerException("해당 상품은 존재하지 않습니다.")
            );

            blogRepository.deleteById(id);
            sendMessageDto.sendMessage("삭제완료",200);
            return sendMessageDto;
        } else {
            return null;
        }



    }

}
