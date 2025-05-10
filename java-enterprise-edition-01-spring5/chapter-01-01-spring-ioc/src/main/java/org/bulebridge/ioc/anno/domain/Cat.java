package org.bulebridge.ioc.anno.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**  
 * @ClassName: CatDao  
 * @Description: TODO(这里用一句话描述这个类的作用)  
 * @author ronin  
 * @date 2019年3月20日  
 *    
 */
@Component(value="cat")
public class Cat {

	@Value("Tom")
	private String catName;
	
	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}


	public void eat(){
		System.out.println("名字是"+catName+"的猫在喝水....");
	}
}
