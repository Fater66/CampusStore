package com.fater.oto.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fater.oto.BaseTest;
import com.fater.oto.entity.Area;

public class AreaServiceTest extends BaseTest{
	@Autowired
	private AreaService areaService;
	@Test
	public void testGetAreaList() {
		List<Area> areaList=areaService.getAreaList();
		assertEquals("nyu",areaList.get(0).getAreaName());
		System.out.print(areaList.get(0).getAreaName());
	}
}
