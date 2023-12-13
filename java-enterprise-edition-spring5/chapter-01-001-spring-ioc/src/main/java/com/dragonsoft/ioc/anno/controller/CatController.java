/**  
 * @Title: CatController.java  
 * @Package com.dragonsoft.spring.anno.controller  
 * @Description: TODO(用一句话描述该文件做什么)  
 * @author ronin  
 * @date 2019年3月20日  
 * @version V1.0  
 */ 
package com.dragonsoft.ioc.anno.controller;

import javax.annotation.Resource;

import com.dragonsoft.ioc.anno.service.ICatService;
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
