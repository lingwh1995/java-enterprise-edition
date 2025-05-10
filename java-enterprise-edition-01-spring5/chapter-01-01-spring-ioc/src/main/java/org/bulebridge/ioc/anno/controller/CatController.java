package org.bulebridge.ioc.anno.controller;

import javax.annotation.Resource;

import org.bulebridge.ioc.anno.service.ICatService;
import org.springframework.stereotype.Controller;


/**  
 * @ClassName: CatController  
 * @Description: TODO(这里用一句话描述这个类的作用)  
 * @author ronin  
 * @date 2019年3月20日  
 */

@Controller("catController")
public class CatController {

	//@Autowired
	@Resource(name="catService")
	private ICatService catService;
	
	public void eat(){
		catService.eat();
	}
}
