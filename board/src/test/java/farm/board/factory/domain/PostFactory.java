package farm.board.factory.domain;

import farm.board.domain.category.Category;
import farm.board.domain.member.Member;
import farm.board.domain.post.Image;
import farm.board.domain.post.Post;

import java.util.List;

import static farm.board.factory.domain.CategoryFactory.createCategory;
import static farm.board.factory.domain.MemberFactory.createMember;

public class PostFactory {
    public static Post createPost() {
        return createPost(createMember(), createCategory());
    }

    public static Post createPost(Member member, Category category) {
        return new Post("title", "content",  member, category, List.of());    }

    public static Post createPostWithImages(Member member, Category category, List<Image> images) {
        return new Post("title", "content",  member, category, images);
    }

    public static Post createPostWithImages(List<Image> images) {
        return new Post("title", "content", createMember(), createCategory(), images);
    }
}