package ca.robertgleason.project.service;

import ca.robertgleason.project.model.Category;
import ca.robertgleason.project.repositories.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {

        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found"));

        categoryRepository.delete(category);
        return "category with categoryId: " + categoryId + " has been deleted";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        categoryRepository.findById(categoryId).orElseThrow
                (() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found"));
        Category savedCategory;

        category.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(category);
        return savedCategory;

    }

}
