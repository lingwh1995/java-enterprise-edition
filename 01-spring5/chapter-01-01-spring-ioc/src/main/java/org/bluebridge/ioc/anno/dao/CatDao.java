package org.bluebridge.ioc.anno.dao;

import org.bluebridge.ioc.anno.domain.Cat;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author lingwh
 * @desc
 * @date   2019/3/20 9:30
 */
@Repository(value="catDao")
public class CatDao {

	@Resource(name="cat")
	private Cat cat;
	

	public void eat(){
		System.out.println("名字是"+cat.getCatName()+"的猫在喝水....");
	}
}
