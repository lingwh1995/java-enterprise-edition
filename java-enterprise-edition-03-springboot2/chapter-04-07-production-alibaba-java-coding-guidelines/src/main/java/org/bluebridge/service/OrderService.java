package org.bluebridge.service;

import org.bluebridge.dto.OrderBatchCreateDTO;
import org.bluebridge.dto.OrderBatchDeleteDTO;
import org.bluebridge.dto.OrderBatchUpdateStatusDTO;
import org.bluebridge.dto.OrderCreateDTO;
import org.bluebridge.dto.OrderUpdateDTO;
import org.bluebridge.dto.OrderListQueryDTO;
import org.bluebridge.dto.OrderQueryDTO;
import org.bluebridge.dto.OrderUniqueQueryDTO;
import org.bluebridge.dto.PageDTO;
import org.bluebridge.dto.OrderPartialUpdateDTO;
import org.bluebridge.vo.OrderVO;
import org.bluebridge.vo.OrderPageVO;

import java.util.List;

/**
 * 订单服务接口
 *
 * @author YourName
 * @since 2025-12-12
 */
public interface OrderService {

    /**
     * 创建订单
     *
     * @param createOrderDTO 订单信息
     * @return 订单ID
     */
    Long createOrder(OrderCreateDTO createOrderDTO);

    /**
     * 批量创建订单
     *
     * @param batchCreateOrderDTO 批量订单信息
     * @return 是否成功
     */
    boolean batchCreateOrders(OrderBatchCreateDTO batchCreateOrderDTO);

    /**
     * 根据ID查询订单
     *
     * @param id 订单ID
     * @return 订单信息
     */
    OrderVO getOrderById(Long id);

    /**
     * 根据唯一条件查询订单
     *
     * @param orderUniqueQueryDTO 唯一条件查询参数
     * @return 订单信息
     */
    OrderVO getOrderByUniqueCondition(OrderUniqueQueryDTO orderUniqueQueryDTO);

    /**
     * 查询所有订单列表（慎用）
     *
     * @return 订单列表
     */
    List<OrderVO> getAllOrders();

    /**
     * 根据条件查询订单列表（不分页）
     *
     * @param orderListQueryDTO 查询条件
     * @return 订单列表
     */
    List<OrderVO> getOrderListByCondition(OrderListQueryDTO orderListQueryDTO);

    /**
     * 分页查询订单列表
     *
     * @param orderQueryDTO 查询条件
     * @return 订单列表
     */
    List<OrderVO> getOrderList(OrderQueryDTO orderQueryDTO);

    /**
     * 获取订单总数
     *
     * @param orderQueryDTO 查询条件
     * @return 订单总数
     */
    int getOrderCount(OrderQueryDTO orderQueryDTO);

    /**
     * 分页查询订单（带分页信息）
     *
     * @param orderQueryDTO 查询条件
     * @param pageDTO       分页参数
     * @return 分页结果
     */
    OrderPageVO<OrderVO> getOrderPage(OrderQueryDTO orderQueryDTO, PageDTO pageDTO);

    /**
     * 全量更新订单信息
     *
     * @param fullUpdateOrderDTO 订单信息
     * @return 是否成功
     */
    boolean fullUpdateOrder(OrderUpdateDTO updateOrderDTO);

    /**
     * 局部更新订单信息
     *
     * @param partialUpdateOrderDTO 订单信息
     * @return 是否成功
     */
    boolean partialUpdateOrder(OrderPartialUpdateDTO partialUpdateOrderDTO);

    /**
     * 更新订单状态
     *
     * @param id     订单ID
     * @param status 订单状态
     * @return 是否成功
     */
    boolean updateOrderStatus(Long id, Integer status);

    /**
     * 批量更新订单状态
     *
     * @param batchUpdateStatusDTO 批量更新参数
     * @return 是否成功
     */
    boolean batchUpdateOrderStatus(OrderBatchUpdateStatusDTO batchUpdateStatusDTO);

    /**
     * 删除订单
     *
     * @param id 订单ID
     * @return 是否成功
     */
    boolean deleteOrder(Long id);

    /**
     * 批量删除订单
     *
     * @param batchDeleteOrderDTO 批量删除参数
     * @return 是否成功
     */
    boolean batchDeleteOrders(OrderBatchDeleteDTO batchDeleteOrderDTO);
}