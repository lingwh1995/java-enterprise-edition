/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dragonsoft.designpattern.template_a;

public abstract class AbstractApplicationContext implements ConfigurableApplicationContext {

	@Override
	public void refresh() throws IllegalStateException {
		prepareRefresh();
		//实际上调用了抽象方法
		obtainFreshBeanFactory();
		prepareBeanFactory();
	}
	protected void obtainFreshBeanFactory() {
		refreshBeanFactory();
	}
	protected abstract void refreshBeanFactory() throws  IllegalStateException;

	protected void prepareRefresh() {

	}
	protected void prepareBeanFactory() {

	}
}
