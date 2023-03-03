package farm.board.dto.post;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;
import java.util.stream.Collectors;

import static farm.board.factory.dto.PostCreateRequestFactory.*;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;


class PostCreateRequestValidationTest {
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void validateTest(){
        //given
        PostCreateRequest req = createPostCreateRequestWithMemberId(null);

        //when
        Set<ConstraintViolation<PostCreateRequest>> validate = validator.validate(req);

        //then
        assertThat(validate).isEmpty();
    }

    @Test
    public void invalidateByEmptyTitleTest(){
        //given
        String invalidateValue = null;
        PostCreateRequest req = createPostCreateRequestWithTitle(invalidateValue);

        //when
        Set<ConstraintViolation<PostCreateRequest>> validate = validator.validate(req);

        //then
        assertThat(validate.size()).isEqualTo(1);
        assertThat(validate.stream().map(v -> v.getInvalidValue()).collect(toSet())).contains(invalidateValue);
    }

    @Test
    public void invalidateByBlankTitleTest(){
        //given
        String invalidateValue = " ";
        PostCreateRequest req = createPostCreateRequestWithTitle(invalidateValue);

        //when
        Set<ConstraintViolation<PostCreateRequest>> validate = validator.validate(req);

        //then
        assertThat(validate.size()).isEqualTo(1);
        assertThat(validate.stream().map(v -> v.getInvalidValue()).collect(toSet())).contains(invalidateValue);
    }

    @Test
    public void invalidateByEmptyContentTest(){
        //given
        String invalidateValue = null;
        PostCreateRequest req = createPostCreateRequestWithContent(invalidateValue);

        //when
        Set<ConstraintViolation<PostCreateRequest>> validate = validator.validate(req);

        //then
        assertThat(validate.size()).isEqualTo(1);
        assertThat(validate.stream().map(v -> v.getInvalidValue()).collect(toSet())).contains(invalidateValue);
    }

    @Test
    public void invalidateByBlankContentTest(){
        //given
        String invalidateValue = " ";
        PostCreateRequest req = createPostCreateRequestWithContent(invalidateValue);

        //when
        Set<ConstraintViolation<PostCreateRequest>> validate = validator.validate(req);

        //then
        assertThat(validate.size()).isEqualTo(1);
        assertThat(validate.stream().map(v -> v.getInvalidValue()).collect(toSet())).contains(invalidateValue);
    }

    @Test
    void invalidateByNotNullMemberIdTest() {
        // given
        Long invalidValue = 1L;
        PostCreateRequest req = createPostCreateRequestWithMemberId(invalidValue);

        // when
        Set<ConstraintViolation<PostCreateRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
    }

    @Test
    void invalidateByNullCategoryIdTest() {
        // given
        Long invalidValue = null;
        PostCreateRequest req = createPostCreateRequestWithCategoryId(invalidValue);

        // when
        Set<ConstraintViolation<PostCreateRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(v -> v.getInvalidValue()).collect(toSet())).contains(invalidValue);
    }

    @Test
    void invalidateByNegativeCategoryIdTest() {
        // given
        Long invalidValue = -1L;
        PostCreateRequest req = createPostCreateRequestWithCategoryId(invalidValue);

        // when
        Set<ConstraintViolation<PostCreateRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(v -> v.getInvalidValue()).collect(toSet())).contains(invalidValue);
    }
}