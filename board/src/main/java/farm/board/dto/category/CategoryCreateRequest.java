package farm.board.dto.category;

import farm.board.domain.category.Category;
import farm.board.exception.CategoryNotFoundException;
import farm.board.repository.category.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreateRequest {

    private String name;

    private Long parentId;

    public static Category toEntity(CategoryCreateRequest req, CategoryRepository categoryRepository) {
        return new Category(req.getName(),
                Optional.ofNullable(req.getParentId())
                        .map(id -> categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new))
                        .orElse(null));
    }
}
