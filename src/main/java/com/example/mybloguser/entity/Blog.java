package com.example.mybloguser.entity;


import com.example.mybloguser.dto.BlogRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// 1. JPA(Java Persistence API)
// JPA는 데이터베이스와 자바 객체의 매핑을 위한 기술이다.
// 2. Lombok
// java 언어를 사용하는 프로젝트에서 코드를 줄이고 유지보수성을 높이기 위한 라이브러리
// 주로 사용되는 것은 getter,setter,toSTtring,equals 등의 메서드를 자동으로 생성해서 코드의 양을 줄임.

@Getter // 클래스에 Property에 대한 getter 메서드를 자동으로 생성해주는 것.
@Entity // Blog라는 클래스가 JPA Entity 클래스로 사용될 것이라는 것, 즉 데이터베이스에 저장할 데이터의 구조를 말한다.
@NoArgsConstructor // 기본생성자를 자동으로 생성할 수 있게하는 Lombok 에서 사용하는 것.
public class Blog extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn (name = "user_name",nullable = false)
    private User user;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String title;

    // 생성자.
    @Builder
    public Blog (BlogRequestDto blogRequestDto, User user)
    {
        this.content = blogRequestDto.getContent();
        this.title = blogRequestDto.getTitle();
        this.user = user;
    }
    public void update (BlogRequestDto blogRequestDto, User user)
    {
        this.content = blogRequestDto.getContent();
        this.title = blogRequestDto.getTitle();
        this.user = user;
    }

}
