package com.fater.oto.web.shopadmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fater.oto.dto.ProductCategoryExecution;
import com.fater.oto.dto.Result;
import com.fater.oto.entity.ProductCategory;
import com.fater.oto.entity.Shop;
import com.fater.oto.enums.ProductCategoryStateEnum;
import com.fater.oto.exceptions.ProductCategoryOperationException;
import com.fater.oto.service.ProductCategoryService;

@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {
	@Autowired
	private ProductCategoryService productCategoryService;
	
	@RequestMapping(value="/getproductcategorylist",method=RequestMethod.GET)
	@ResponseBody
	private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request){
		Shop shop = new Shop();
		shop.setShopId(1L);
		request.getSession().setAttribute("currentShop",shop);
		
		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
		List<ProductCategory> list =null;
		if(currentShop!=null&&currentShop.getShopId()>0)
		{
			list = productCategoryService.getProductCategoryList(currentShop.getShopId());
			return new Result<List<ProductCategory>>(true,list);
		}
		else {
			ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
			return new Result<List<ProductCategory>>(false,ps.getState(),ps.getStateInfo());
		}
		}
	@RequestMapping(value="/addproductcategorys",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList,
			HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String,Object>();
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		for(ProductCategory pc:productCategoryList) {
			pc.setShopId(currentShop.getShopId());
		}
		//先判断是否为空
		if(productCategoryList!=null&&productCategoryList.size()>0) {
			try {
				ProductCategoryExecution pe = productCategoryService.batchAddProductCategory(productCategoryList);
				//如果前端传来的state是success
				if(pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()) 
				{
				modelMap.put("success", true);
				}
				else {
					//否则，输出错误原因
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}}catch(ProductCategoryOperationException e) {
					modelMap.put("success", false);
					modelMap.put("errMsg", e.toString());
					return modelMap;
				}
			
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请至少输入一个商品列表");
		}
		return modelMap;
	}
	@RequestMapping(value="/removeproductcategory",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> removeProductCategory(Long productCategoryId,
			HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String,Object>();
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		//先判断是否为空
		if(productCategoryId!=null&&productCategoryId>0) {
			try {
				ProductCategoryExecution pe = productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
				//如果前端传来的state是success
				if(pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()) 
				{
				modelMap.put("success", true);
				}
				else {
					//否则，输出错误原因
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}}catch(ProductCategoryOperationException e) {
					modelMap.put("success", false);
					modelMap.put("errMsg", e.toString());
					return modelMap;
				}
			
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请至少选择一个商品列表");
		}
		return modelMap;
	}
}
