package com.fater.oto.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fater.oto.dao.ShopDao;
import com.fater.oto.dto.ShopExecution;
import com.fater.oto.entity.Shop;
import com.fater.oto.enums.ShopStateEnum;
import com.fater.oto.exceptions.ShopOperationException;
import com.fater.oto.service.ShopService;
import com.fater.oto.util.ImageUtil;
import com.fater.oto.util.PageCalculator;
import com.fater.oto.util.PathUtil;

@Service
public class ShopServiceImpl implements ShopService{
	@Autowired
	private ShopDao shopDao;
	
	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, InputStream shopImgInputStream,String fileName) {
		//空值判断
		if(shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			//初始化店铺信息
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			int effectedNum = shopDao.insertShop(shop);
			if (effectedNum <= 0) {
				throw new ShopOperationException("店铺创建失败");
			}else {
				if(shopImgInputStream!= null) {
					//存储图片
					try {
						addShopImg(shop,shopImgInputStream,fileName);
//						shop.getShopImg();
					}catch(Exception e) {
						throw new ShopOperationException("addShopImg error"+e.getMessage());
					}
					effectedNum = shopDao.updateShop(shop);
					if(effectedNum <= 0) {
						throw new ShopOperationException("更新图片地址失败");
					}
				}
			}
		}catch(Exception e) {
			throw new ShopOperationException("addShop error"+e.getMessage());
		}
		return new ShopExecution(ShopStateEnum.CHECK,shop);
	}

	private void addShopImg(Shop shop, InputStream shopImgInputStream,String fileName) {
		// 获取shop图片目录的相对值路径
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(shopImgInputStream,fileName, dest);
		shop.setShopImg(shopImgAddr);
	}

	@Override
	public Shop getByShopId(long shopId) {
		// TODO Auto-generated method stub
		return shopDao.queryByShopId(shopId);
	}

	@Override
	public ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName)
			throws ShopOperationException {
		if(shop==null||shop.getShopId()==null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}else {
			//1.判断是否需要处理图片
			try {
			if(shopImgInputStream!=null &&fileName !=null &&!"".equals(fileName)) {
				Shop tempShop = shopDao.queryByShopId(shop.getShopId());
				if(tempShop.getShopImg()!=null) {
					ImageUtil.deleteFileOrPath(tempShop.getShopImg());
				}
				addShopImg(shop,shopImgInputStream,fileName);
			}
			//2.更新店铺信息
			shop.setLastEditTime(new Date());
			int effectedNum = shopDao.updateShop(shop);
			if(effectedNum <=0) {
				return new ShopExecution(ShopStateEnum.INNER_ERROR);
			}else {
				shop = shopDao.queryByShopId(shop.getShopId());
				return new ShopExecution(ShopStateEnum.SUCCESS,shop);
			}}catch(Exception e) {
				throw new ShopOperationException("modifyShop error"+e.getMessage());
			}
		}
	}

	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
		int count = shopDao.queryShopCount(shopCondition);
		ShopExecution se = new ShopExecution();
		if(shopList!=null) {
			se.setShopList(shopList);
			se.setCount(count);
		}else {
			se.setState(ShopStateEnum.INNER_ERROR.getState());
		}
		return se;
	}

}
