package com.fater.oto.dao;

import java.util.List;

import com.fater.oto.entity.ProductImg;

public interface ProductImgDao {
	/**
	 * 批量添加商品详情图片
	 * @param productImgList
	 * @return
	 */
	int batchInsertProductImg(List<ProductImg> productImgList);
	
	List<ProductImg> queryProductImgList(long productId);
	
	int deleteProductImgByProductId(long productId);
}
