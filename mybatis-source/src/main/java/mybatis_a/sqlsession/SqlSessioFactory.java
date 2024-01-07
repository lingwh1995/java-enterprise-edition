package mybatis_a.sqlsession;

import mybatis_a.entity.Configuration;
import mybatis_a.mapper.MapperStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author ronin
 */
public class SqlSessioFactory {
    private final Configuration config = new Configuration();;

    public SqlSessioFactory() {
        //模拟解析全局配置文件
        try {
            loadGlobalSettings();
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * 加载全局配置文件
     * @throws SAXException
     * @throws IOException
     */
    private void loadGlobalSettings() throws SAXException, IOException {
        SAXReader reader = new SAXReader();
        InputStream resource = SqlSessioFactory.class.getClassLoader().getResourceAsStream("mybatis-config.xml");
        Document document = null;
        try {
            document = reader.read(resource);
            //获取configuration标签,即根节点
            Element root = (Element) document.selectSingleNode("/configuration");
            //解析<properties></properties>
            parsepropertiesTags(root);
            //解析environments标签
            parseEnvironmentsTag(root);
            //解析Mappers标签
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
     * 解析Mappers标签
     * @param root
     */
    private void parseMappersTag(Element root) throws DocumentException {
        //获取到全局配置文件中的mappers标签
        Element mappersTag = (Element)root.selectSingleNode("mappers");
        //获取到mappers标签下面的所有mapper标签,循环处理
        List<Element> mapperTags = mappersTag.selectNodes("mapper");
        for (Element mapperTag : mapperTags) {
            //获取全局配置文件中mapper标签中到每一个mapper标签的resource属性的值
            String mapperLocation = mapperTag.attributeValue("resource");
            SAXReader reader = new SAXReader();
            //根据上面获取的resource标签的值创建该Mapper.xml对应的document对象并解析该对象
            Document eachMarrperXmlDocument = reader.read(SqlSessioFactory.class.getClassLoader().getResourceAsStream(mapperLocation));
            Map<String, MapperStatement> mapperStatements = config.getMapStatements();
            //解析每一个mapper.xml中所有的SELECT | INSERT | DELETE | UPDATE 标签
            List<Element> crudTags = eachMarrperXmlDocument.getRootElement().elements();
            for (Element crudTag : crudTags) {
                //把每一个标签的属性和sql等内容封装到MapperStatement对象中
                MapperStatement mapperStatement = new MapperStatement();
                mapperStatement.setTagType(crudTag.getName());
                mapperStatement.setNamespace(eachMarrperXmlDocument.getRootElement().attributeValue("namespace"));
                mapperStatement.setId(crudTag.attributeValue("id"));
                mapperStatement.setResultType(crudTag.attributeValue("resultType"));
                mapperStatement.setSql(crudTag.getStringValue().trim());
                //把遍历出来的所有的MapperStatement标签存放到全局配置文件的mapperStatements属性中
                mapperStatements.put(mapperStatement.getNamespace()+"."+mapperStatement.getId(),
                        mapperStatement);
            }
        }
    }

    /**
     * 解析environments标签，这里只解析出environments下environment下dataSource中的子标签
     * 并将dataSource标签中的<driver></driver> <url></url>等标签设置保存到map中
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
                    //获取到数据库配置properties中key的值,根据key的值获取properties中value的值
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
     * 解析所有的properties标签
     * @param root 跟节点
     * @throws IOException
     */
    private void parsepropertiesTags(Element root) throws IOException {
        List<Element> propertiesTags = root.selectNodes("properties");
        for (Element propertiesTag : propertiesTags) {
            //获取porperties文件的位置
            String propertiesLocation = propertiesTag.attributeValue("resource");
            //根据porperties文件的位置找到properties文件,并进行解析,把每一个键值对保存在内存中
            Properties properties = config.getProperties();
            properties.load(SqlSessioFactory.class.getClassLoader().getResourceAsStream(propertiesLocation));
        }
    }

    public SqlSession openSession(){
        return new DefaultSqlSession(config);
    }

}
