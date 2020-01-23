package com.fater.oto.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fater.oto.entity.ProductCategory;

public interface ProductCategoryDao {

	List<ProductCategory> queryProductCategoryList(long shopId);

	/**
	 * 批量新增商品类别
	 * @param productCategoryList
	 * @return
	 */
	int batchInsertProductCategory(List<ProductCategory> productCategoryList);
	/**
	 * 删除所选商品类别
	 * @param productCategoryId
	 * @param shopId
	 * @return
	 */
	int deleteProductCategory(@Param("productCategoryId")long productCategoryId,@Param("shopId")long shopId);
}
