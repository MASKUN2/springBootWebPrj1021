package com.maskun.springboot.service.posts;

import com.maskun.springboot.domain.posts.Posts;
import com.maskun.springboot.domain.posts.PostsRepository;
import com.maskun.springboot.web.dto.PostsSaveRequestDto;
import com.maskun.springboot.web.dto.PostsUpdateRequestDto;
import com.maskun.springboot.web.dto.postsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    // @Transactional은 메소드 안에서의 ACID를 지원해준다.
    @Transactional
    public long save(PostsSaveRequestDto requestDto) {
        //실제 비지니스 로직은 도메인 모델에서 수행한다. 여기서는 단순 저장이니 도메인 엔티티를 repository layer에서 .save 메소드만 수행하게 지정한 것.
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        //postsRepository.findById(id)에서 찾으면 그 객체를 반환하되 .orElseThrow(supplier < x extends Throwable > )예외를 던지게 함
        //JPA는 repository의 리턴값이 Optional<T>이다. 따라서 Posts posts = postsRepository.findById(id) 처럼 타입을 고정시키면 컴파일에러가 발생함. Optional은 Null처리를 편하게 해준다. Optional<Posts> posts = postsRepository.findById(id)로 하면 값이 있으면 해당 타입으로 반환하고 아니면 예외를 발생시켜야하므로 .orElseThrow으로 지정해준다.
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당게시글이 없습니다. id = " + id));
        //JPA는 업데이트에서 postsRepository에서 따로 메소드를 실행하지 않고 해당 퍼시스턴스를 불러와서 더티체킹하는 것으로 업데이트를 완료한다.
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public postsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다. id=" + id));
        //web 레이어로 보내줘야하기 때문에 도메인 자체가 아닌 dto로 보낸다.
        return new postsResponseDto(entity);
    }
}
