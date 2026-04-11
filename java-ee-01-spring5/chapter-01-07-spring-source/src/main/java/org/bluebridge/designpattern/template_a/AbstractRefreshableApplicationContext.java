package org.bluebridge.designpattern.template_a;

/**
 * @author lingwh
 * @desc
 * @date   2019/4/15 14:26
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

	@Override
	protected final void refreshBeanFactory()  {
		System.out.println("实现了父类的方法.....");
	}

}