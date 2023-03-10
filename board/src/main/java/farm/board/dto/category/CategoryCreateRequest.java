package farm.board.dto.category;

import farm.board.domain.category.Category;
import farm.board.exception.CategoryNotFoundException;
import farm.board.repository.category.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreateRequest {

    @NotBlank(message = "카테고리 명을 입력해주세요.")
    @Size(min = 2, max = 30, message = "카테고리 명의 길이는 2글자에서 30글자 입니다.")
    private String name;

    private Long parentId;

    public static Category toEntity(CategoryCreateRequest req, CategoryRepository categoryRepository) {
        return new Category(req.getName(),
                Optional.ofNullable(req.getParentId())
                        .map(id -> categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new))
                        .orElse(null));
    }
}
