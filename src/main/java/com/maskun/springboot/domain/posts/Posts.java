package com.maskun.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity // 도메인 엔티티임을 명시하고 이를 바탕으로 DB와 매핑됨 테이블이름과 클래스이름이 같이매핑
public class Posts extends BaseTimeEntity{
    @Id // PrimaryKey임을 지정함
    @GeneratedValue(strategy = GenerationType.IDENTITY)// 생성전략을 지정하고. DB쪽에서 오토인크리스임을 명시
    private long id;

    @Column(length = 500, nullable = false) // 일반적으로 필드선언만으로 자동으로 카멜타입이 언더스코어 타입으로 컬럼이 매핑되지만 length = 500 와같이 제약조건 등을 지정할 때 사용하는 어노테이션
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
    //업데이트는 jpa 퍼시스턴스 기능으로 인해 최초 단일 객체 조회 후 checkout할 때 변경사항을 자동으로 업데이트하는 더티체킹 전략으로 관리됨
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
