package com.fater.oto.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.fater.oto.BaseTest;
import com.fater.oto.entity.ProductCategory;

//按照方法名字顺序执行
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryDaoTest extends BaseTest{
	@Autowired
	private ProductCategoryDao productCategoryDao;
	
	@Test
	@Ignore
	public void testQueryProductCategoryList()
	{
		long shopId = 1;
		List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
		for(ProductCategory pc:productCategoryList)
		{
			System.out.println(pc.getProductCategoryName());
		}
	}
	@Test
	@Ignore
	public void testBatchInsertProductCategory() {
		ProductCategory productCategory=new ProductCategory();
		productCategory.setProductCategoryName("商品类别1");
		productCategory.setPriority(1);
		productCategory.setCreateTime(new Date());
		productCategory.setShopId(1L);
		ProductCategory productCategory2=new ProductCategory();
		productCategory2.setProductCategoryName("商品类别2");
		productCategory2.setPriority(2);
		productCategory2.setCreateTime(new Date());
		productCategory2.setShopId(1L);
		List<ProductCategory> productCategoryList=new ArrayList<ProductCategory>();
		productCategoryList.add(productCategory);
		productCategoryList.add(productCategory2);
		int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
		assertEquals(2,effectedNum);
		}
	@Test
	public void testDeleteProductCategory() throws Exception{
		long shopId=1;
		List<ProductCategory> productCategoryList =productCategoryDao.queryProductCategoryList(shopId);
		for(ProductCategory pc:productCategoryList) {
			if("商品类别1".equals(pc.getProductCategoryName())||"商品类别2".equals(pc.getProductCategoryName())) {
				int effectedNum= productCategoryDao.deleteProductCategory(pc.getProductCategoryId(), shopId);
				assertEquals(1,effectedNum);
			}
		}
	}
}
