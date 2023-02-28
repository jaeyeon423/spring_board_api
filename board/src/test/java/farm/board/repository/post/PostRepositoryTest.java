package farm.board.repository.post;

import farm.board.domain.category.Category;
import farm.board.domain.member.Member;
import farm.board.domain.post.Image;
import farm.board.domain.post.Post;
import farm.board.exception.PostNotFoundException;
import farm.board.repository.category.CategoryRepository;
import farm.board.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static farm.board.factory.domain.CategoryFactory.createCategory;
import static farm.board.factory.domain.ImageFactory.createImage;
import static farm.board.factory.domain.MemberFactory.createMember;
import static farm.board.factory.domain.PostFactory.createPost;
import static farm.board.factory.domain.PostFactory.createPostWithImages;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest {
    @Autowired PostRepository postRepository;
    @Autowired ImageRepository imageRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired CategoryRepository categoryRepository;
    @PersistenceContext EntityManager em;

    Member member;
    Category category;

    @BeforeEach
    void beforeEach(){
        member = memberRepository.save(createMember());
        category = categoryRepository.save(createCategory());
    }

    @Test
    public void createAndReadTest(){
        //given
        Post post = postRepository.save(createPost(member, category));
        clear();

        //when
        Post foundPost = postRepository.findById(post.getId()).orElseThrow(PostNotFoundException::new);

        //then
        assertThat(foundPost.getId()).isEqualTo(post.getId());
        assertThat(foundPost.getTitle()).isEqualTo(post.getTitle());
    }

    @Test
    public void deleteTest(){
        //given
        Post post = postRepository.save(createPost(member, category));
        clear();

        //when
        postRepository.deleteById(post.getId());
        clear();

        //then
        assertThatThrownBy(()->postRepository.findById(post.getId()).orElseThrow(PostNotFoundException::new)).isInstanceOf(PostNotFoundException.class);
    }

    @Test
    public void createCascadeImageTest(){
        //given
        Post post = postRepository.save(createPostWithImages(member, category, List.of(createImage(), createImage())));
        clear();

        //when
        Post foundPost = postRepository.findById(post.getId()).orElseThrow(PostNotFoundException::new);

        //then
        List<Image> images = foundPost.getImages();
        for (Image image : images) {
            assertThat(image.getOriginName()).isEqualTo("origin_filename.jpg");
        }
    }

    @Test
    void deleteCascadeImageTest() { // 이미지도 연쇄적으로 제거되는지 검증
        // given
        Post post = postRepository.save(createPostWithImages(member, category, List.of(createImage(), createImage())));
        clear();

        // when
        postRepository.deleteById(post.getId());
        clear();

        // then
        List<Image> images = imageRepository.findAll();
        assertThat(images.size()).isZero();
    }

    @Test
    void deleteCascadeByMemberTest() { // Member가 삭제되었을 때 연쇄적으로 Post도 삭제되는지 검증
        // given
        postRepository.save(createPostWithImages(member, category, List.of(createImage(), createImage())));
        clear();

        // when
        memberRepository.deleteById(member.getId());
        clear();

        // then
        List<Post> result = postRepository.findAll();
        Assertions.assertThat(result.size()).isZero();
    }

    @Test
    void deleteCascadeByCategoryTest() { // Category가 삭제되었을 때 연쇄적으로 Post도 삭제되는지 검증
        // given
        postRepository.save(createPostWithImages(member, category, List.of(createImage(), createImage())));
        clear();

        // when
        categoryRepository.deleteById(category.getId());
        clear();

        // then
        List<Post> result = postRepository.findAll();
        assertThat(result.size()).isZero();
    }

    void clear(){
        em.flush();
        em.clear();
    }
}