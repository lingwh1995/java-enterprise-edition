package org.bluebridge.generator.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;



public class MyBatisGeneratorTest {

	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mysql/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		return new SqlSessionFactoryBuilder().build(inputStream);
	}

	/**
	 * 测试生成mybatis自动生成代码
	 * @throws Exception
	 */
	@Test
	public void testMbg() throws Exception {
		String filePath = "D:\\repository\\workspace\\IDEA\\PERSONAL\\JavaEE\\mybatis\\src\\main\\resources\\mbg.xml";
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		File configFile = new File(filePath);
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
				callback, warnings);
		myBatisGenerator.generate(null);
	}

}
