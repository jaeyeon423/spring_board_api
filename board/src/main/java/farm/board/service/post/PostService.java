package farm.board.service.post;

import farm.board.domain.post.Image;
import farm.board.domain.post.Post;
import farm.board.dto.post.PostCreateRequest;
import farm.board.dto.post.PostCreateResponse;
import farm.board.repository.category.CategoryRepository;
import farm.board.repository.member.MemberRepository;
import farm.board.repository.post.PostRepository;
import farm.board.service.file.FileService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;

    @Transactional
    public PostCreateResponse create(PostCreateRequest req){
        Post post = postRepository.save(
                PostCreateRequest.toEntity(
                        req,
                        memberRepository,
                        categoryRepository
                )
        );
        uploadImages(post.getImages(), req.getImages());
        return new PostCreateResponse(post.getId());
    }

    private void uploadImages(List<Image> images, List<MultipartFile> filenames) {
        IntStream.range(0, images.size()).forEach(i -> fileService.upload(filenames.get(i), images.get(i).getUniqueName()));
    }


}
