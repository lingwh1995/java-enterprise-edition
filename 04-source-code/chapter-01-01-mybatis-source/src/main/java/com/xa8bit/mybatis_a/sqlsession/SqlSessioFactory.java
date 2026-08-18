package com.xa8bit.mybatis_a.sqlsession;

import com.xa8bit.mybatis_a.entity.Configuration;
import com.xa8bit.mybatis_a.mapper.MapperStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * SqlSession 工厂，负责解析全局配置文件并创建 SqlSession
 *
 * @author lingwh
 * @date 2025/12/20 16:25
 */
public class SqlSessioFactory {

    private final Configuration config = new Configuration();;

    public SqlSessioFactory() {
        // 模拟解析全局配置文件
        try {
            loadGlobalSettings();
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载全局配置文件
     *
     * @throws SAXException
     * @throws IOException
     */
    private void loadGlobalSettings() throws SAXException, IOException {
        SAXReader reader = new SAXReader();
        InputStream resource = SqlSessioFactory.class.getClassLoader().getResourceAsStream("mybatis-config.xml");
        Document document = null;
        try {
            document = reader.read(resource);
            // 获取 configuration 标签，即根节点
            Element root = (Element) document.selectSingleNode("/configuration");
            // 解析<properties></properties>
            parsepropertiesTags(root);
            // 解析 environments 标签
            parseEnvironmentsTag(root);
            // 解析 M
            // appers 标签
            parseMappersTag(root);
        } catch (DocumentException e) {
            e.printStackTrace();
        }finally {
            if(resource!=null){
                resource.close();
            }
        }
    }

    /**
     * 解析 Mappers 标签
     *
     * @param root
     */
    private void parseMappersTag(Element root) throws DocumentException {
        // 获取到全局配置文件中的 mappers 标签
        Element mappersTag = (Element)root.selectSingleNode("mappers");
        // 获取到 mappers 标签下面的所有 mapper 标签，循环处理
        List<Element> mapperTags = mappersTag.selectNodes("mapper");
        for (Element mapperTag : mapperTags) {
            // 获取全局配置文件中 mapper 标签中到每一个 mapper 标签的 resource 属性的值
            String mapperLocation = mapperTag.attributeValue("resource");
            SAXReader reader = new SAXReader();
            // 根据上面获取的 resource 标签的值创建该 Mapper.xml 对应的 document 对象并解析该对象
            Document eachMarrperXmlDocument = reader.read(SqlSessioFactory.class.getClassLoader().getResourceAsStream(mapperLocation));
            Map<String, MapperStatement> mapperStatements = config.getMapStatements();
            // 解析每一个 mapper.xml 中所有的 SELECT | INSERT | DELETE | UPDATE 标签
            List<Element> crudTags = eachMarrperXmlDocument.getRootElement().elements();
            for (Element crudTag : crudTags) {
                // 把每一个标签的属性和 sql 等内容封装到 MapperStatement 对象中
                MapperStatement mapperStatement = new MapperStatement();
                mapperStatement.setTagType(crudTag.getName());
                mapperStatement.setNamespace(eachMarrperXmlDocument.getRootElement().attributeValue("namespace"));
                mapperStatement.setId(crudTag.attributeValue("id"));
                mapperStatement.setResultType(crudTag.attributeValue("resultType"));
                mapperStatement.setSql(crudTag.getStringValue().trim());
                // 把遍历出来的所有的 MapperStatement 标签存放到全局配置文件的 mapperStatements 属性中
                mapperStatements.put(mapperStatement.getNamespace()+"."+mapperStatement.getId(),
                        mapperStatement);
            }
        }
    }

    /**
     * 解析 environments 标签，这里只解析出 environments 下 environment 下 dataSource 中的子标签
     * 并将 dataSource 标签中的<driver></driver> <url></url>等标签设置保存到 map 中
     *
     * @param root
     */
    private void parseEnvironmentsTag(Element root) {
        Element environmentsTag = (Element) root.selectSingleNode("environments");
        List<Element> environmentTags = environmentsTag.selectNodes("environment");
        for (Element environmentTag : environmentTags) {
            if(environmentsTag.attributeValue("default").
                    equals(environmentTag.attributeValue("id"))){
                Element dataSource = environmentTag.element("dataSource");
                List<Element> elements = dataSource.elements();
                Map dbConfigMap = config.getDbConfigMap();
                for (Element element : elements) {
                    // 获取到数据库配置 properties 中 key 的值，根据 key 的值获取 properties 中 value 的值
                    String propertyKey = element.attributeValue("value");
                    Properties properties = config.getProperties();
                    String propertyValue = properties.getProperty(propertyKey
                            .replace("${","").replace("}",""));
                    dbConfigMap.put(element.attributeValue("name"),propertyValue);
                }
            }
        }
    }

    /**
     * 解析所有的 properties 标签
     *
     * @param root 跟节点
     * @throws IOException
     */
    private void parsepropertiesTags(Element root) throws IOException {
        List<Element> propertiesTags = root.selectNodes("properties");
        for (Element propertiesTag : propertiesTags) {
            // 获取 porperties 文件的位置
            String propertiesLocation = propertiesTag.attributeValue("resource");
            // 根据 porperties 文件的位置找到 properties 文件，并进行解析，把每一个键值对保存在内存中
            Properties properties = config.getProperties();
            properties.load(SqlSessioFactory.class.getClassLoader().getResourceAsStream(propertiesLocation));
        }
    }

    public SqlSession openSession(){
        return new DefaultSqlSession(config);
    }
}
