package org.bluebridge.designpattern.template_a;

/**
 * @author lingwh
 * @desc
 * @date   2019/4/15 14:34
 */
public class GenericApplicationContext extends AbstractApplicationContext {

	@Override
	protected final void refreshBeanFactory() throws IllegalStateException {
		System.out.println("子类实现了父类方法.....");
	}

}