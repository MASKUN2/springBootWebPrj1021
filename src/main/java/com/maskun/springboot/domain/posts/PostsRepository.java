package com.maskun.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

//compile('org.springframework.boot:spring-boot-starter-data-jpa')를 사용하면 인터페이스를 상속하는 것으로 CRUD기본 메소드를 제공한다. 데이터베이스 설정을 하지 않으면 H2 인메모리 db가 자동으로 설정된다.
public interface PostsRepository extends JpaRepository<Posts,Long> {
}
