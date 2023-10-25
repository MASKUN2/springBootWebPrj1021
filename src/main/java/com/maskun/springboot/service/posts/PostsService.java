package com.maskun.springboot.service.posts;

import com.maskun.springboot.domain.posts.Posts;
import com.maskun.springboot.domain.posts.PostsRepository;
import com.maskun.springboot.web.dto.PostsListResponseDto;
import com.maskun.springboot.web.dto.PostsSaveRequestDto;
import com.maskun.springboot.web.dto.PostsUpdateRequestDto;
import com.maskun.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다. id=" + id));
        //web 레이어로 보내줘야하기 때문에 도메인 자체가 아닌 dto로 보낸다.
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true) // @Transactional(readOnly = true) 트랙젝션 범위는 유지시키되 조회를 위해 성능을 올리는 옵션
    public List<PostsListResponseDto> findAllDesc(){
        //먼저 Posts 엔티티를 가져와서 스트림으로 만들고 map 안에서 각각의 entity를 인자로 가지고 new postsResponseDto(entity)실행해서 스트림에 담는다. . collect()괄호 안에는 스트림을 처리할 Collector 인터페이스를 구현하는 객체를 사용할 수 있고 여기서는 리스트 타입으로 받은 것!
        return postsRepository.findAllDesc().stream().map(PostsListResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당게시글이 없습니다"));
        postsRepository.delete(posts); // 리파지토리는 조회 후 삭제한다.
    }
}
