package ca.robertgleason.project.service;

import ca.robertgleason.project.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CategoryServiceImpl implements CategoryService {
    private final List<Category> categories = new ArrayList<>();
    private Long nextID = 1L;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        category.setCategoryID(nextID++);
        categories.add(category);
    }

    @Override
    public String deleteCategory(Long categoryID) {
        Category category = categories.stream()
                .filter(c -> c.getCategoryID().equals(categoryID))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found"));
        categories.remove(category);
        return "category with categoryID: " + categoryID + " has been deleted";
    }

    @Override
    public Category updateCategory(Category category, Long categoryID) {
        Optional<Category> optionalCategory = categories.stream()
                .filter(c -> c.getCategoryID().equals(categoryID))
                .findFirst();

        if (optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            existingCategory.setCategoryName(category.getCategoryName());
            return existingCategory;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found");
        }
    }

}
