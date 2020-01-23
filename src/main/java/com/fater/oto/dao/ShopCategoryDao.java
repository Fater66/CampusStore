package com.fater.oto.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fater.oto.entity.ShopCategory;

public interface ShopCategoryDao {
	List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition")
	ShopCategory shopCategoryCondition);
}
