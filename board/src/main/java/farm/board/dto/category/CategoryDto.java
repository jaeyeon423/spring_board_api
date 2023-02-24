package farm.board.dto.category;

import farm.board.domain.category.Category;
import farm.board.helper.NestedConvertHelper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private List<CategoryDto> children;

    public static List<CategoryDto> toDtoList(List<Category> categories) {
        NestedConvertHelper helper = NestedConvertHelper.newInstance(
                categories,
                category -> new CategoryDto(category.getId(), category.getName(), new ArrayList<>()),
                category -> category.getParent(),
                category -> category.getId(),
                categoryDto -> categoryDto.getChildren());
        return helper.convert();
    }
}
