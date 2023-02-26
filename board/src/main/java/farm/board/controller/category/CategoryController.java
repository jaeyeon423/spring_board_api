package farm.board.controller.category;

import farm.board.controller.response.Response;
import farm.board.dto.category.CategoryCreateRequest;
import farm.board.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/api/categories")
    @ResponseStatus(HttpStatus.OK)
    public Response readAll(){
        return Response.success(categoryService.readAll());
    }

    @PostMapping("/api/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(@Valid @RequestBody CategoryCreateRequest req) {
        categoryService.create(req);
        return Response.success();
    }

    @DeleteMapping("/api/categories/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response delete(@PathVariable Long id){
        categoryService.delete(id);
        return Response.success();
    }
}
