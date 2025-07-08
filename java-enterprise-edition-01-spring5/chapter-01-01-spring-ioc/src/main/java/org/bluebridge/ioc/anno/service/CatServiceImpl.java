package org.bluebridge.ioc.anno.service;

import javax.annotation.Resource;

import org.bluebridge.ioc.anno.dao.CatDao;
import org.springframework.stereotype.Component;

/**
 * @author lingwh
 * @desc
 * @date   2019/3/20 9:34
 */
@Component(value="catService")
public class CatServiceImpl implements ICatService{

	//@Autowired
	@Resource(name="catDao")
	private CatDao catDao;

	public void eat() {
		catDao.eat();
	}

}
