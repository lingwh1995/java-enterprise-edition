package org.bluebridge;

import org.apache.ibatis.session.SqlSession;
import org.bluebridge.domain.Car;
import org.bluebridge.mapper.CarMapper;
import org.bluebridge.utils.SqlSessionUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

/**
 * Mybatis 传递多个参数
 *      1. 直接传递多个参数
 *      2. 使用@Param传递多个参数
 *      3. 使用Map传递多个参数
 *      4. 使用@Param + Map 传递多个参数
 *      5. 使用pojo传递多个参数(这里未做演示)
 */
public class MybatisTransferMultiplyParamsTest {

    /**
     * 使用@Param传递多个参数
     * @throws Exception
     */
    @Test
    public void testTransferMultiplyParamsUseParamAnnotation() throws Exception {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectCarByBrandAndGuidePriceTransferMultiplyParamsUseParamAnnotation("卡罗拉", "32.00");
        System.out.println("cars = " + cars);
        sqlSession.close();
    }

    /**
     * 使用Map传递多个参数
     * @throws Exception
     */
    @Test
    public void testTransferMultiplyParamsUseMap() throws Exception {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        HashMap<String, String> params = new HashMap<>();
        params.put("brand","卡罗拉");
        params.put("guidePrice","32.00");
        List<Car> cars = mapper.selectCarByBrandAndGuidePriceTransferMultiplyParamsUseMap(params);
        System.out.println("cars = " + cars);
        sqlSession.close();
    }
    /**
     * 使用@Param + @Param传递多个参数
     * @throws Exception
     */
    @Test
    public void testTransferMultiplyParamsUseParamAnnotationMap() throws Exception {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        HashMap<String, String> params = new HashMap<>();
        params.put("brand","卡罗拉");
        params.put("guidePrice","32.00");
        List<Car> cars = mapper.selectCarByBrandAndGuidePriceTransferMultiplyParamsUseParamAnnotationMap(params);
        System.out.println("cars = " + cars);
        sqlSession.close();
    }
}
