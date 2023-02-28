package farm.board.domain.post;

import farm.board.exception.UnsupportedImageFormatException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static farm.board.factory.domain.ImageFactory.createImage;
import static farm.board.factory.domain.ImageFactory.createImageWithOriginName;
import static farm.board.factory.domain.PostFactory.createPost;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


class ImageTest {

    @Test
    public void createImageTest(){
        //given
        String validExtension = "JPEG";

        //when //then
        createImageWithOriginName("image." + validExtension);
    }

    @Test
    public void createImageExceptionByUnsupportedFormatTest(){
        //given
        String invalidExtension = "invalid";

        //when //then
        assertThatThrownBy(()->createImageWithOriginName("image." + invalidExtension)).isInstanceOf(UnsupportedImageFormatException.class);

    }

    @Test
    void createImageExceptionByNoneExtensionTest() {
        // given
        String originName = "image";

        // when, then
        assertThatThrownBy(() -> createImageWithOriginName(originName))
                .isInstanceOf(UnsupportedImageFormatException.class);
    }
    @Test
    void initPostTest() {
        // given
        Image image = createImage();

        // when
        Post post = createPost();
        image.initPost(post);

        System.out.println("post            = " + post);
        System.out.println("image.getPost() = " + image.getPost());
        // then
        assertThat(image.getPost()).isSameAs(post);
    }

    @Test
    void initPostNotChangedTest() {
        // given
        Image image = createImage();
        image.initPost(createPost());
        System.out.println("image.getPost() = " + image.getPost());

        // when
        Post post = createPost();
        image.initPost(post);

        System.out.println("post            = " + post);
        System.out.println("image.getPost() = " + image.getPost());

        // then
        assertThat(image.getPost()).isNotSameAs(post);
    }

}