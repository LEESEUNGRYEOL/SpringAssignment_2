package com.example.mybloguser.entity;


import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter

// JPA annotation, 기본적인 속성을 가지는 entity Class 가 상속할 수 있는 추상적인 entity Class 정의
// 이때 다음 정의된 클래스는 JPA에서 테이블로 매핑하지 않음, 그러나 이를 상속하는 클래스 (Blog) 는 해당 테이블에 매핑
// 결론적으로 entity classs 들이 공통적으로 가지는 Property를 정의할 때 사용할 수 있음.
@MappedSuperclass

// JPA annotation, entity class 에서 일어나는 특정 이벤트 (entity의 저장, 수정, 삭제)에 대한 리스너를 정의할때 사용
// 이는 특정 엔티티에 대한 변경이력을 추적하는 기능을 구현한 클래스이다. (엔티티의 생성일자,수정일자를 추적하기위해서 사용)
@EntityListeners(AuditingEntityListener.class)
public class Timestamped {
    @CreatedDate // 테이블의 데이터가 처음 생성된 시각을 기록하는데 사용
    private LocalDateTime createdAt;

    @LastModifiedDate // 테이블의 데이터가 수정된 시각을 기록하는데 사용
    private LocalDateTime modifiedAt;
}
