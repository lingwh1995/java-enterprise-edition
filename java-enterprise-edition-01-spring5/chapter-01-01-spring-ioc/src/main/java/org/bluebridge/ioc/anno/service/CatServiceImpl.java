package org.bluebridge.ioc.anno.service;

import javax.annotation.Resource;

import org.bluebridge.ioc.anno.dao.CatDao;
import org.springframework.stereotype.Component;

/**  
 * @ClassName: CatServiceImpl  
 * @Description: TODO(这里用一句话描述这个类的作用)  
 * @author ronin  
 * @date 2019年3月20日  
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
