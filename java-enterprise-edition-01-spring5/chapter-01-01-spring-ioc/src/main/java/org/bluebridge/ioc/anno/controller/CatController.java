package org.bluebridge.ioc.anno.controller;

import javax.annotation.Resource;

import org.bluebridge.ioc.anno.service.ICatService;
import org.springframework.stereotype.Controller;

/**
 * @author lingwh
 * @desc
 * @date   2019/3/20 9:35
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
