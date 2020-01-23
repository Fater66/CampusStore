package com.fater.oto.service;

import java.io.InputStream;

import com.fater.oto.dto.ShopExecution;
import com.fater.oto.entity.Shop;
import com.fater.oto.exceptions.ShopOperationException;

public interface ShopService {
	/**
	 * 根据shopCondition分页返回列表数据
	 * @param shopCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
	ShopExecution addShop(Shop shop,InputStream shopImgInputStream,String fileName);
	//通过店铺id获取店铺信息
	Shop getByShopId(long shopId);
	//更新店铺信息，包括对图片的处理
	ShopExecution modifyShop(Shop shop,InputStream shopImgInputStream,String fileName)throws ShopOperationException;
}
