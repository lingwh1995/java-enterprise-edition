package org.bluebridge.designpattern.proxy.dynamicproxy.cglibproxy.proxyclass.proxyclass_3;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class UserDao {
	//所有人员
	static Map<String, User> users = new HashMap<>();
	
	static {
		users.put("001",new User("001", "张三", 18, "西安财经学院"));
		users.put("002",new User("002", "李四", 18, "西安财经学院"));
	}
	
	public void add(User user) {
		users.put(user.getId(),user);
	}
	
	public void deleteById(String id) {
		System.out.println("根据id = " + id + "删除用户......");
		users.remove(id);
	}
	
	public void update(User user) {
		//Map的put方法会根据key自动去重
		users.put(user.getId(),user);
	}
	
	public User getById(String id) {
		return users.get(id);
	}
	
	public void showUsers() {
		Set<Entry<String, User>> userEntrySet = users.entrySet();
		Iterator<Entry<String, User>> iterator = userEntrySet.iterator();
		while(iterator.hasNext()) {
			Entry<String, User> userEntry = iterator.next();
			System.out.println(userEntry.getValue());
		}
		System.out.println("--------------------------");
	}
}
