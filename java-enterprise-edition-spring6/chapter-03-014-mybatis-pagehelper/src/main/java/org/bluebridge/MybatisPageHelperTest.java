package org.bluebridge;

import org.bluebridge.domain.Car;
import org.bluebridge.mapper.CarMapper;
import org.bluebridge.utils.SqlSessionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

/**
 * mybatis 分页
 */
public class MybatisPageHelperTest {

    /**
     * 分页查询-使用mysql原生方式分页
     */
    @Test
    public void testSelectByPageUseMysqlNative(){
        // 获取每页显示的记录条数
        int pageSize = 3;
        // 显示第几页：页码
        int pageNum = 3;
        // 计算开始下标
        int startIndex = (pageNum - 1) * pageSize;
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectByPageUseMysqlNative(startIndex, pageSize);
        cars.forEach(car -> System.out.println(car));
        sqlSession.close();
    }

    /**
     * 分页查询-使用PageHelper插件分页
     */
    @Test
    public void testSelectByPageUsePageHelper(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);

        // 一定一定一定要注意：在执行DQL语句之前。开启分页功能。
        int pageNum = 2;
        int pageSize = 3;
        // 开启分页功能，参数一：页码，参数二：页面显示的数据条数
        PageHelper.startPage(pageNum, pageSize);

        List<Car> cars = mapper.selectByPageUsePageHelper();
        //cars.forEach(car -> System.out.println(car));

        //将分页查询结果封装到PageInfo对象中
        //new PageInfo(data,navigatepageNum):第一个参数为查询出来的数据,第二个参数为导航选项的个数
        PageInfo<Car> carPageInfo = new PageInfo<>(cars, 3);

        //特别注意： 在控制台可以查询 PageInfo 的结构，这个对象里面封装了特别详细的分页信息，所有常用的不常用的分页信息都在这个参数里面封装的
        System.out.println(carPageInfo);
        sqlSession.close();
    }
}
