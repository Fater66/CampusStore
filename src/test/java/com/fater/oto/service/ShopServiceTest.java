package com.fater.oto.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fater.oto.BaseTest;
import com.fater.oto.dto.ShopExecution;
import com.fater.oto.entity.Area;
import com.fater.oto.entity.PersonInfo;
import com.fater.oto.entity.Shop;
import com.fater.oto.entity.ShopCategory;
import com.fater.oto.enums.ShopStateEnum;
import com.fater.oto.exceptions.ShopOperationException;

public class ShopServiceTest extends BaseTest{
	@Autowired
	private ShopService shopService;
	
	@Test
	public void testQueryShopListAndCount() {
		Shop shopCondition = new Shop();
		ShopCategory sc = new ShopCategory();
		sc.setShopCategoryId(1L);
		shopCondition.setShopCategory(sc);
		ShopExecution se = shopService.getShopList(shopCondition, 1, 2);
		System.out.println("店铺列表数为:"+se.getShopList().size());
		System.out.println("店铺总数为:"+se.getCount());
		
	}
	@Test
	@Ignore
	public void testModifyShop()throws ShopOperationException,FileNotFoundException{
		Shop shop = new Shop();
		shop.setShopId(1L);
		shop.setShopName("修改后的店");
		File shopImg = new File("/Users/fater/eclipse-workspace/oto/src/main/resources/testimgnew.png");
		InputStream is = new FileInputStream(shopImg);
		ShopExecution shopExecution = shopService.modifyShop(shop, is, "testimgnew.png");
		System.out.println("新的图片地址为"+shopExecution.getShop().getShopImg());
	}
	@Test
	@Ignore
	public void testAddShop() throws ShopOperationException,FileNotFoundException {
		Shop shop = new Shop(); 
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(2);
		shopCategory.setShopCategoryId(1L);
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopName("测试店铺3");
		shop.setShopDesc("test3");
		shop.setShopAddr("test3");
		shop.setPhone("test3");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("审核中");
		File shopImg = new File("/Users/fater/eclipse-workspace/oto/src/main/resources/testimg.png");
		InputStream is = new FileInputStream(shopImg);
		ShopExecution se = shopService.addShop(shop,is,shopImg.getName());
		assertEquals(ShopStateEnum.CHECK.getState(),se.getState());
	}
}
