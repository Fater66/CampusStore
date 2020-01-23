/**
 * 
 */
$(function(){
	
	// 从URL里获取shopId参数的值
	var shopId = getQueryString('shopId');
	// 由于店铺注册和编辑使用的是同一个页面，
	// 该标识符用来标明本次是添加还是编辑操作
	var isEdit = shopId ? true : false;
	// 用于店铺注册时候的店铺类别以及区域列表的初始化的URL
	var initUrl = '/oto/shopadmin/getshopinitinfo';
	// 注册店铺的URL
	var registerShopUrl = '/oto/shopadmin/registershop';
	// 编辑店铺前需要获取店铺信息，这里为获取当前店铺信息的URL
	var shopInfoUrl = "/oto/shopadmin/getshopbyid?shopId=" + shopId;
	// 编辑店铺信息的URL
	var editShopUrl = '/oto/shopadmin/modifyshop';
	if(!isEdit){
		getShopInitInfo();
	}else{
		getShopInfo(shopId);
	}
	 function getShopInfo(shopId){
		 $.getJSON(shopInfoUrl,function(data){
			 if(data.success){
				 var shop = data.shop;
				 $('#shop-name').val(shop.shopName);
				 $('#shop-addr').val(shop.shopAddr);
				 $('#shop-phone').val(shop.phone);
				 $('#shop-desc').val(shop.shopDesc);
				var shopCategory ='<option data-id="'+shop.shopCategory.shopCategoryId+'"selected>'+shop.shopCategory.shopCategoryName+'</option>';
				var tempAreaHtml='';
				data.areaList.map(function(item,index){
					tempAreaHtml +='<option data-id="' +item.areaId +'">'+item.areaName+'</option>';
				});
				$('#shop-category').html(shopCategory);
				$('#shop-category').attr('disabled','disabled');
				$('#area').html(tempAreaHtml);
				$("#area operation[data-id='"+shop.area.areaId+"']").attr('data-id',"selected");
			 }
		 });
	 }
	 function getShopInitInfo(){
		 $.getJSON(initUrl,function(data){
			 if(data.success){
				 var tempHtml = '';
				 var tempAreaHtml ='';
//				 alert(initUrl);
				 data.shopCategoryList.map(function(item,index){
					 tempHtml +='<option data-id="'+item.shopCategoryId+'">'+item.shopCategoryName+'</option>'; 
				 });
				data.areaList.map(function(item,index){
					tempAreaHtml +='<option data-id="' +item.areaId +'">'+item.areaName+'</option>';
				});
				$('#shop-category').html(tempHtml);
				$('#area').html(tempAreaHtml);
			 }
		 });
	 }
	 $('#submit').click(function(){
		var shop ={};
		if(isEdit){
			shop.shopId=shopId;
		}
		shop.shopName = $('#shop-name').val();
		shop.shopAddr = $('#shop-addr').val();
		shop.phone = $('#shop-phone').val();
		shop.shopDesc = $('#shop-desc').val();
		shop.shopCategory = {
				shopCategoryId:$('#shop-category').find('option').not(function(){
					return !this.selected;
				}).data('id')
		};
		shop.area = {
				areaId:$('#area').find('option').not(function(){
					return !this.selected;
				}).data('id')
		};
		var shopImg = $('#shop-img')[0].files[0];
		var formData = new FormData();
		formData.append('shopImg',shopImg);
		formData.append('shopStr',JSON.stringify(shop));
		var verifyCodeActual = $('#j_captcha').val();
		if(!verifyCodeActual){
			$.toast('请输入验证码！');
			return;
		}
		formData.append('verifyCodeActual',verifyCodeActual);
		$.ajax({
			url:(isEdit?editShopUrl : registerShopUrl),
			type:'POST',
			data:formData,
			contentType:false,
			processData:false,
			cache:false,
			success:function(data){
				if(data.success){
					$.toast('提交成功！');
				}else{
					$.toast('提交失败'+data.errMsg);
				}
				$('#captcha_img').click();
			}
		});
	});

})