package org.bluebridge.mapper;

import org.bluebridge.entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 订单Mapper接口
 *
 * @author YourName
 * @since 2025-12-12
 */
@Mapper
public interface OrderMapper {

    /**
     * 创建订单
     *
     * @param order 订单信息
     * @return 影响行数
     */
    @Insert("INSERT INTO t_order(order_no, user_id, total_amount, status, shipping_address, create_time, update_time) " +
            "VALUES(#{orderNo}, #{userId}, #{totalAmount}, #{status}, #{shippingAddress}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Order order);

    /**
     * 批量创建订单
     *
     * @param orders 订单列表
     * @return 影响行数
     */
    int insertBatch(List<Order> orders);

    /**
     * 根据ID查询订单
     *
     * @param id 订单ID
     * @return 订单信息
     */
    @Select("SELECT id, order_no, user_id, total_amount, status, shipping_address, create_time, update_time FROM t_order WHERE id = #{id}")
    @Results({
            @Result(property = "orderItems", column = "id", many = @Many(select = "org.bluebridge.order.mapper.OrderItemMapper.selectByOrderId"))
    })
    Order selectById(Long id);

    /**
     * 根据订单编号查询订单
     *
     * @param orderNo 订单编号
     * @return 订单信息
     */
    @Select("SELECT id, order_no, user_id, total_amount, status, shipping_address, create_time, update_time FROM t_order WHERE order_no = #{orderNo}")
    @Results({
            @Result(property = "orderItems", column = "id", many = @Many(select = "org.bluebridge.order.mapper.OrderItemMapper.selectByOrderId"))
    })
    Order selectByOrderNo(String orderNo);

    /**
     * 查询所有订单列表
     *
     * @return 订单列表
     */
    @Select("SELECT id, order_no, user_id, total_amount, status, shipping_address, create_time, update_time FROM t_order")
    @Results({
            @Result(property = "orderItems", column = "id", many = @Many(select = "org.bluebridge.order.mapper.OrderItemMapper.selectByOrderId"))
    })
    List<Order> selectAll();

    /**
     * 根据条件查询订单列表（不分页）
     *
     * @param userId 用户ID
     * @param status 订单状态
     * @return 订单列表
     */
    @Select("<script>" +
            "SELECT id, order_no, user_id, total_amount, status, shipping_address, create_time, update_time FROM t_order " +
            "<where>" +
            "<if test='userId != null'> AND user_id = #{userId}</if>" +
            "<if test='status != null'> AND status = #{status}</if>" +
            "</where>" +
            "</script>")
    @Results({
            @Result(property = "orderItems", column = "id", many = @Many(select = "org.bluebridge.order.mapper.OrderItemMapper.selectByOrderId"))
    })
    List<Order> selectList(@Param("userId") Long userId, @Param("status") Integer status);

    /**
     * 分页查询订单列表
     *
     * @param offset 偏移量
     * @param limit  每页数量
     * @return 订单列表
     */
    @Select("SELECT id, order_no, user_id, total_amount, status, shipping_address, create_time, update_time FROM t_order LIMIT #{offset}, #{limit}")
    @Results({
            @Result(property = "orderItems", column = "id", many = @Many(select = "org.bluebridge.order.mapper.OrderItemMapper.selectByOrderId"))
    })
    List<Order> selectPage(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 根据条件查询订单总数
     *
     * @return 订单总数
     */
    @Select("SELECT COUNT(*) FROM t_order")
    int count();

    /**
     * 更新订单信息（全量更新）
     *
     * @param order 订单信息
     * @return 影响行数
     */
    @Update("UPDATE t_order SET order_no = #{orderNo}, user_id = #{userId}, total_amount = #{totalAmount}, status = #{status}, shipping_address = #{shippingAddress}, create_time = #{createTime}, update_time = #{updateTime} WHERE id = #{id}")
    int updateFull(Order order);

    /**
     * 更新订单信息（部分字段更新）
     *
     * @param order 订单信息
     * @return 影响行数
     */
    @Update("UPDATE t_order SET total_amount = #{totalAmount}, status = #{status}, shipping_address = #{shippingAddress}, update_time = #{updateTime} WHERE id = #{id}")
    int updatePartial(Order order);

    /**
     * 更新订单状态
     *
     * @param order 订单信息
     * @return 影响行数
     */
    @Update("UPDATE t_order SET status = #{status}, update_time = #{updateTime} WHERE id = #{id}")
    int update(Order order);

    /**
     * 批量更新订单状态
     *
     * @param orderIds 订单ID列表
     * @param status 订单状态
     * @return 影响行数
     */
    int updateBatchStatus(@Param("orderIds") List<Long> orderIds, @Param("status") Integer status);

    /**
     * 删除订单
     *
     * @param id 订单ID
     * @return 影响行数
     */
    @Delete("DELETE FROM t_order WHERE id = #{id}")
    int deleteById(Long id);

    /**
     * 批量删除订单
     *
     * @param orderIds 订单ID列表
     * @return 影响行数
     */
    int deleteBatch(List<Long> orderIds);
}