package com.example.mybloguser.entity;


import com.example.mybloguser.dto.BlogRequestDto;
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
    @Id  // JPA에서 기본키를 나타내는 필드에 붙인다.
    @GeneratedValue(strategy = GenerationType.AUTO) // JPA에서 자동으로 값을 생성하는 기능을 나타낸다. 주로 PK를 자동으로 생성하기위해 사용.
    // 보통 AUTO, IDENTITY,SEQUENCE, TABLE 방식들이 있다.
    private Long id;

    @Column (nullable = false)
    private String username;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String title;

    // 생성자.
    public Blog (BlogRequestDto blogRequestDto, String username)
    {
        this.contents = blogRequestDto.getContents();
        this.title = blogRequestDto.getTitle();
        this.username = username;
    }
    public void update (BlogRequestDto blogRequestDto,String username)
    {
        this.contents = blogRequestDto.getContents();
        this.title = blogRequestDto.getTitle();
        this.username = username;
    }

}
