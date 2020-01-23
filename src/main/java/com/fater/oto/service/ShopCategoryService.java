package com.fater.oto.service;

import java.util.List;

import com.fater.oto.entity.ShopCategory;

public interface ShopCategoryService {
	List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
