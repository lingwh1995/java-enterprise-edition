package org.bulebridge.ioc.anno.dao;

import org.bulebridge.ioc.anno.domain.Cat;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**  
 * @ClassName: CatDao  
 * @Description: TODO(这里用一句话描述这个类的作用)  
 * @author ronin  
 * @date 2019年3月20日  
 *    
 */
@Repository(value="catDao")
public class CatDao {

	@Resource(name="cat")
	private Cat cat;
	

	public void eat(){
		System.out.println("名字是"+cat.getCatName()+"的猫在喝水....");
	}
}
