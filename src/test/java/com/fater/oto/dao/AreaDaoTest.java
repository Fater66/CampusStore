package com.fater.oto.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fater.oto.BaseTest;
import com.fater.oto.entity.Area;

public class AreaDaoTest extends BaseTest{
	@Autowired
	private AreaDao areaDao;
	
	@Test
	public void testQueryArea() {
		List<Area> areaList = areaDao.queryArea();
		assertEquals(2,areaList.size());
//		System.out.print("areaList第一个名字是"+areaList.get(0).getAreaName());
	}
}
