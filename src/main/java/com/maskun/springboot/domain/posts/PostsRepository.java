package com.maskun.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//compile('org.springframework.boot:spring-boot-starter-data-jpa')를 사용하면 인터페이스를 상속하는 것으로 CRUD기본 메소드를 제공한다. 데이터베이스 설정을 하지 않으면 H2 인메모리 db가 자동으로 설정된다.
public interface PostsRepository extends JpaRepository<Posts, Long> {

    // Posts는 도메인명으로 썼다. @쿼리 에노테이션은 인터페이스가 내장하고 있는 메소드를 사용하지 않고 직접 쿼리를 메소드화 하는 것.
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
    // 향로는 복잡한 조회용 프레임워크를 위해 querydsl를 선호한다고 한다. 단순 문자열 쿼리를 전송하지 않고 메소드 기반이라서 타입안정성이 좋다. 많은 회사가 사용한다.
}
