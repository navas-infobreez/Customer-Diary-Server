package com.planet.customer.diary.customer_diary.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planet.customer.diary.customer_diary.entity.Product;
import com.planet.customer.diary.customer_diary.entity.ProductCategory;
import com.planet.customer.diary.customer_diary.model.dto.ProductCategoryDTO;
import com.planet.customer.diary.customer_diary.repository.GenericRepository;
import com.planet.customer.diary.customer_diary.service.ProductCategoryService;

@Service(value = "productCategoryService")
public class ProductCategoryServiceImpl extends BasicServiceImpl implements ProductCategoryService {

	@Autowired
	private GenericRepository genericRepository;
	
	
	
	@Override
	public List<ProductCategoryDTO> findAll() {
		return mapProductCategoryEntitiesToDTOs(genericRepository.findAll(ProductCategory.class));
	}

	
	private List<ProductCategoryDTO> mapProductCategoryEntitiesToDTOs(List<ProductCategory> productCategoryList) {
		List<ProductCategoryDTO> productCategoryDTOList = (List<ProductCategoryDTO>) mapEntitiesToDTOs(
				productCategoryList, ProductCategoryDTO.class);
		return productCategoryDTOList;
	}

	@Override
	@Transactional(readOnly = true)
	public ProductCategoryDTO createOrUpdateProductCategory(final ProductCategoryDTO productCategoryDTO,
			Product product) {
		if(productCategoryDTO == null)
			return null;
		ProductCategory productCategory = mapProductCategoryDTOToEntity(productCategoryDTO, product);
		Long id = productCategoryDTO.getId();		
		if(id != null && id > 0) {
			genericRepository.saveOrUpdate(productCategory);
		}else {			
			id = (Long) genericRepository.save(productCategory);
		}
		return mapEntityToDTO(findById(id), ProductCategoryDTO.class);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ProductCategoryDTO findCategoryId(final Long id) {
		return mapEntityToDTO(findById(id), ProductCategoryDTO.class);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		final ProductCategory productCategory = findById(id);
		if (productCategory != null) {
			genericRepository.delete(productCategory);
		}

	}
	
	private ProductCategory findById(final Long id) {
		return genericRepository.findById(ProductCategory.class, id);
	}

	public ProductCategory mapProductCategoryDTOToEntity(ProductCategoryDTO productCategoryDTO, Product product) {
		ProductCategory productCategory = null;
		if (product.getProductCategory() != null) {
			productCategory = product.getProductCategory();
		} else {
			productCategory = new ProductCategory();
			productCategory = (ProductCategory) super.mapDTOToEntity(productCategoryDTO, productCategory);
		}
		return productCategory;
	}
	
}