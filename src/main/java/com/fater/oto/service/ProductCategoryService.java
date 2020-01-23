package com.fater.oto.service;

import java.util.List;

import com.fater.oto.dto.ProductCategoryExecution;
import com.fater.oto.entity.ProductCategory;
import com.fater.oto.exceptions.ProductCategoryOperationException;

public interface ProductCategoryService {
	public List<ProductCategory> getProductCategoryList(long shopId);
	
	ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
	throws ProductCategoryOperationException;
	
	/**
	 * 将此类别下的商品里的类别id置为空，再删除掉该商品类别
	 * @param productCategoryId
	 * @param shopId
	 * @return
	 * @throws ProductCategoryOperationException
	 */
	ProductCategoryExecution deleteProductCategory(long productCategoryId,long shopId)
	throws ProductCategoryOperationException;
}
