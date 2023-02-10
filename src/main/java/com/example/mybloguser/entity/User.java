package com.example.mybloguser.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.users.GenericRole;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
@Getter
@NoArgsConstructor
public class User {

    // 1. pk 설정.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 2.
    @OneToMany(mappedBy = "user")
    private List<Blog> BlogList = new ArrayList<>();

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

}
