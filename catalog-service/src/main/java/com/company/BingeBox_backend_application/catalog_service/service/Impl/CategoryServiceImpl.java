package com.company.BingeBox_backend_application.catalog_service.service.Impl;

import com.company.BingeBox_backend_application.catalog_service.dtos.CategoryDto;
import com.company.BingeBox_backend_application.catalog_service.entity.Category;
import com.company.BingeBox_backend_application.catalog_service.repository.CategoryRepository;
import com.company.BingeBox_backend_application.catalog_service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;


    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Category not found with id: "+id));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(category->modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Category not found with id: "+id));

        category.setName(categoryDto.getName());
        Category updatedCategory = categoryRepository.save(category);
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Long id) {
       Category category = categoryRepository.findById(id)
               .orElseThrow(()->new RuntimeException("Category not found with id:  "+id));
       categoryRepository.delete(category);
    }
}
