package com.maskun.springboot.domain.posts;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 다른 jpa 엔티티가 이 클래스를 상속할 경우 이 클래스의 필드 (등록일, 수정일)도 컬럼으로 인식하게 만듭니다.
@EntityListeners(AuditingEntityListener.class) // 이 클래스에 Auditing 기능을 포함시킵니다 감시 기능은 각각의 엔티티의 구성에 의존하는 기존 방식이 아닌 하나로 통일된 형식으로 관리할 수 있는 역할을 한다. 모든 엔티티의 공통적인 기능(로그 트래킹)을 하나로 일반화시킨 것이라고 생각했다. 자바에서는 이를 상속으로 편하게 영향을 줄 수 있다. 이를 사용하기 위해서는 Application class main메소드에 @EnableJpaAuditing을 추가해줘야한다.
public class BaseTimeEntity {

    @CreatedDate // 등록일 컬럼
    // LocalDateTime 은 JDK 1.0 : java.util.Date, JDK 1.1 : java.util.Calendar 을 거쳐 JDK 1.8 (JSR-310) : java.time 에 최신으로 만들어진 라이브러리로 강추.
    private LocalDateTime createDate;

    @LastModifiedDate // 수정일 컬럼
    private LocalDateTime modifiedDate;
}
