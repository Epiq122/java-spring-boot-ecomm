package ca.robertgleason.project.service;

import ca.robertgleason.project.exceptions.APIException;
import ca.robertgleason.project.exceptions.ResourceNotFoundException;
import ca.robertgleason.project.model.Category;
import ca.robertgleason.project.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty())
            throw new APIException("no categories have been created");
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (savedCategory != null) {
            throw new APIException("category with the name " + category.getCategoryName() + " already exists");
        }
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        categoryRepository.delete(category);
        return "category with categoryId: " + categoryId + " has been deleted";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        categoryRepository.findById(categoryId).orElseThrow
                (() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        Category savedCategory;

        category.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(category);
        return savedCategory;


    }

}
