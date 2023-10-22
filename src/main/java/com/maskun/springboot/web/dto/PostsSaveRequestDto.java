package com.maskun.springboot.web.dto;

import com.maskun.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter // 필드명을 지을 때 aA 등 소문자 1자+대문자로 짓지 않는다. 그러면 getter 이름이 public getAA가 되며 나중에 @ResponseBody가 필드 매핑을 할 때 AA 2연속 대문자의 경우 "aA"가 아닌 "AA"그대로 필드명을 찾아서 null이 입력된다.
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder // 빌더 패턴. 클래스위에 선언한 경우 필드 전체를 Builder로 가져오지만 Id 등은 빌드할 필요가 없으므로 원하는 필드로만 빌더 객체를 만들 때에 생성자를 지정해서 @Builder 붙인다. 메소드 체이닝이 가능.
    public  PostsSaveRequestDto(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    //도메인 엔티티인 Posts를 web-service레이어에서 쓰는 우를 범하지 않는다. view는 빈번히 출력이 수정될 수 있고 join을 수행하기 위한 수정이 되므로 반드시 dto를 사용하는 이유다. 서비스레이어에 도착한 dto를 이제 엔티티로 변환시켜 서비스레이어 이후 리포지토리레이어에서 활용해야하므로 toEntity()를 사용한다.
    public Posts toEntity() {

        return Posts.builder().title(title).content(content).author(author).build();
    }
}
