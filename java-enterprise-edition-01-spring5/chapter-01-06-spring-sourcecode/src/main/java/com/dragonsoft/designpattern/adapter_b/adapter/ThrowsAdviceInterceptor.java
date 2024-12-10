/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dragonsoft.designpattern.adapter_b.adapter;

import com.dragonsoft.designpattern.adapter_b.incepter.MethodInterceptor;
import com.dragonsoft.designpattern.adapter_b.incepter.MethodInvocation;
import com.dragonsoft.designpattern.adapter_b.target.AfterAdvice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ThrowsAdviceInterceptor implements MethodInterceptor, AfterAdvice {

	private static final String AFTER_THROWING = "afterThrowing";


	/** Methods on throws advice, keyed by exception class */
	private final Map<Class, Method> exceptionHandlerMap = new HashMap<Class, Method>();

	public ThrowsAdviceInterceptor(Object throwsAdvice) {

	}

	public int getHandlerMethodCount() {
		return this.exceptionHandlerMap.size();
	}

	private Method getExceptionHandler(Throwable exception) {
		return null;
	}

	public Object invoke(MethodInvocation mi) throws Throwable {
		return null;
	}

	private void invokeHandlerMethod(MethodInvocation mi, Throwable ex, Method method) throws Throwable {

	}

}
