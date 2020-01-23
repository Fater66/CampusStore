package com.fater.oto.util;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {
	public static boolean checkVerifyCode(HttpServletRequest request) {
		String verifyCodeExpected =(String) request.getSession().getAttribute(
				com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		System.out.println("verifyCodeExpected:"+verifyCodeExpected);
		String verifyCodeActual = HttpServletRequestUtil.getString(request,"verifyCodeActual");
		System.out.println("verifyCodeActual:"+verifyCodeActual);
		if(verifyCodeActual == null ||!verifyCodeActual.equals(verifyCodeExpected)) {
			return false;
		}
		return true;
	}
}
