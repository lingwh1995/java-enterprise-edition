package org.bluebridge.mapper;

import org.bluebridge.entity.OrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 订单项Mapper接口
 *
 * @author YourName
 * @since 2025-12-12
 */
@Mapper
public interface OrderItemMapper {

    /**
     * 批量插入订单项
     *
     * @param orderItems 订单项列表
     * @return 影响行数
     */
    int insertBatch(List<OrderItem> orderItems);

    /**
     * 根据订单ID查询订单项列表
     *
     * @param orderId 订单ID
     * @return 订单项列表
     */
    @Select("SELECT id, order_id, product_id, product_name, price, quantity, subtotal FROM t_order_item WHERE order_id = #{orderId}")
    List<OrderItem> selectByOrderId(Long orderId);

    /**
     * 根据订单ID删除订单项
     *
     * @param orderId 订单ID
     * @return 影响行数
     */
    @Delete("DELETE FROM t_order_item WHERE order_id = #{orderId}")
    int deleteByOrderId(Long orderId);
}