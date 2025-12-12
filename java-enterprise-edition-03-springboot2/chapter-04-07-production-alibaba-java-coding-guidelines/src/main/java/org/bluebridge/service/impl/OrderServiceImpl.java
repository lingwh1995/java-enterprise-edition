package org.bluebridge.service.impl;

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
import org.bluebridge.entity.Order;
import org.bluebridge.entity.OrderItem;
import org.bluebridge.mapper.OrderItemMapper;
import org.bluebridge.mapper.OrderMapper;
import org.bluebridge.service.OrderService;
import org.bluebridge.vo.OrderItemVO;
import org.bluebridge.vo.OrderVO;
import org.bluebridge.vo.OrderPageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单服务实现类
 *
 * @author YourName
 * @since 2025-12-12
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createOrder(OrderCreateDTO createOrderDTO) {
        // 转换DTO到Entity
        Order order = new Order();
        order.setUserId(createOrderDTO.getUserId());
        order.setTotalAmount(createOrderDTO.getTotalAmount());
        order.setShippingAddress(createOrderDTO.getShippingAddress());

        // 设置创建时间和更新时间
        Date now = new Date();
        order.setCreateTime(now);
        order.setUpdateTime(now);

        // 插入订单
        orderMapper.insert(order);

        // 插入订单项
        if (createOrderDTO.getOrderItems() != null && !createOrderDTO.getOrderItems().isEmpty()) {
            for (OrderItem item : createOrderDTO.getOrderItems()) {
                item.setOrderId(order.getId());
            }
            orderItemMapper.insertBatch(createOrderDTO.getOrderItems());
        }

        return order.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchCreateOrders(OrderBatchCreateDTO batchCreateOrderDTO) {
        // 设置创建时间和更新时间
        Date now = new Date();
        for (Order order : batchCreateOrderDTO.getOrders()) {
            order.setCreateTime(now);
            order.setUpdateTime(now);
        }

        // 批量插入订单
        return orderMapper.insertBatch(batchCreateOrderDTO.getOrders()) > 0;
    }

    @Override
    public OrderVO getOrderById(Long id) {
        Order order = orderMapper.selectById(id);
        if (order == null) {
            return null;
        }
        
        // 转换Entity到VO
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order, orderVO);
        
        // 转换订单项
        if (order.getOrderItems() != null) {
            List<OrderItemVO> orderItemVOS = new ArrayList<>();
            for (OrderItem item : order.getOrderItems()) {
                OrderItemVO itemVO = new OrderItemVO();
                BeanUtils.copyProperties(item, itemVO);
                orderItemVOS.add(itemVO);
            }
            orderVO.setOrderItems(orderItemVOS);
        }
        
        return orderVO;
    }

    @Override
    public OrderVO getOrderByUniqueCondition(OrderUniqueQueryDTO orderUniqueQueryDTO) {
        Order order = orderMapper.selectByOrderNo(orderUniqueQueryDTO.getOrderNo());
        if (order == null) {
            return null;
        }
        
        // 转换Entity到VO
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order, orderVO);
        
        // 转换订单项
        if (order.getOrderItems() != null) {
            List<OrderItemVO> orderItemVOS = new ArrayList<>();
            for (OrderItem item : order.getOrderItems()) {
                OrderItemVO itemVO = new OrderItemVO();
                BeanUtils.copyProperties(item, itemVO);
                orderItemVOS.add(itemVO);
            }
            orderVO.setOrderItems(orderItemVOS);
        }
        
        return orderVO;
    }

    @Override
    public List<OrderVO> getAllOrders() {
        List<Order> orders = orderMapper.selectAll();
        
        // 转换Entity列表到VO列表
        List<OrderVO> orderVOS = new ArrayList<>();
        for (Order order : orders) {
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(order, orderVO);
            
            // 转换订单项
            if (order.getOrderItems() != null) {
                List<OrderItemVO> orderItemVOS = new ArrayList<>();
                for (OrderItem item : order.getOrderItems()) {
                    OrderItemVO itemVO = new OrderItemVO();
                    BeanUtils.copyProperties(item, itemVO);
                    orderItemVOS.add(itemVO);
                }
                orderVO.setOrderItems(orderItemVOS);
            }
            
            orderVOS.add(orderVO);
        }
        
        return orderVOS;
    }

    @Override
    public List<OrderVO> getOrderListByCondition(OrderListQueryDTO orderListQueryDTO) {
        List<Order> orders = orderMapper.selectList(orderListQueryDTO.getUserId(), orderListQueryDTO.getStatus());
        
        // 转换Entity列表到VO列表
        List<OrderVO> orderVOS = new ArrayList<>();
        for (Order order : orders) {
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(order, orderVO);
            
            // 转换订单项
            if (order.getOrderItems() != null) {
                List<OrderItemVO> orderItemVOS = new ArrayList<>();
                for (OrderItem item : order.getOrderItems()) {
                    OrderItemVO itemVO = new OrderItemVO();
                    BeanUtils.copyProperties(item, itemVO);
                    orderItemVOS.add(itemVO);
                }
                orderVO.setOrderItems(orderItemVOS);
            }
            
            orderVOS.add(orderVO);
        }
        
        return orderVOS;
    }

    @Override
    public List<OrderVO> getOrderList(OrderQueryDTO orderQueryDTO) {
        int offset = (orderQueryDTO.getPageNum() - 1) * orderQueryDTO.getPageSize();
        List<Order> orders = orderMapper.selectPage(offset, orderQueryDTO.getPageSize());
        
        // 转换Entity列表到VO列表
        List<OrderVO> orderVOS = new ArrayList<>();
        for (Order order : orders) {
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(order, orderVO);
            
            // 转换订单项
            if (order.getOrderItems() != null) {
                List<OrderItemVO> orderItemVOS = new ArrayList<>();
                for (OrderItem item : order.getOrderItems()) {
                    OrderItemVO itemVO = new OrderItemVO();
                    BeanUtils.copyProperties(item, itemVO);
                    orderItemVOS.add(itemVO);
                }
                orderVO.setOrderItems(orderItemVOS);
            }
            
            orderVOS.add(orderVO);
        }
        
        return orderVOS;
    }

    @Override
    public int getOrderCount(OrderQueryDTO orderQueryDTO) {
        return orderMapper.count();
    }

    @Override
    public OrderPageVO<OrderVO> getOrderPage(OrderQueryDTO orderQueryDTO, PageDTO pageDTO) {
        // 计算偏移量
        int offset = (pageDTO.getPageNum() - 1) * pageDTO.getPageSize();
        
        // 查询当前页数据
        List<Order> orders = orderMapper.selectPage(offset, pageDTO.getPageSize());
        
        // 查询总记录数
        int total = orderMapper.count();
        
        // 转换Entity列表到VO列表
        List<OrderVO> orderVOS = new ArrayList<>();
        for (Order order : orders) {
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(order, orderVO);
            
            // 转换订单项
            if (order.getOrderItems() != null) {
                List<OrderItemVO> orderItemVOS = new ArrayList<>();
                for (OrderItem item : order.getOrderItems()) {
                    OrderItemVO itemVO = new OrderItemVO();
                    BeanUtils.copyProperties(item, itemVO);
                    orderItemVOS.add(itemVO);
                }
                orderVO.setOrderItems(orderItemVOS);
            }
            
            orderVOS.add(orderVO);
        }
        
        // 构造分页结果
        OrderPageVO<OrderVO> pageVO = new OrderPageVO<>();
        pageVO.setRecords(orderVOS);
        pageVO.setTotal((long) total);
        pageVO.setPageNum(pageDTO.getPageNum());
        pageVO.setPageSize(pageDTO.getPageSize());
        pageVO.setPages((total + pageDTO.getPageSize() - 1) / pageDTO.getPageSize());
        
        return pageVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean fullUpdateOrder(OrderUpdateDTO updateOrderDTO) {
        // 转换DTO到Entity
        Order order = new Order();
        order.setId(updateOrderDTO.getId());
        order.setOrderNo(updateOrderDTO.getOrderNo());
        order.setUserId(updateOrderDTO.getUserId());
        order.setTotalAmount(updateOrderDTO.getTotalAmount());
        order.setStatus(updateOrderDTO.getStatus());
        order.setShippingAddress(updateOrderDTO.getShippingAddress());
        order.setCreateTime(updateOrderDTO.getCreateTime());
        order.setUpdateTime(updateOrderDTO.getUpdateTime());

        // 更新订单
        return orderMapper.updateFull(order) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean partialUpdateOrder(OrderPartialUpdateDTO partialUpdateOrderDTO) {
        // 转换DTO到Entity
        Order order = new Order();
        order.setId(partialUpdateOrderDTO.getId());
        order.setTotalAmount(partialUpdateOrderDTO.getTotalAmount());
        order.setStatus(partialUpdateOrderDTO.getStatus());
        order.setShippingAddress(partialUpdateOrderDTO.getShippingAddress());
        order.setUpdateTime(new Date());

        // 更新订单
        return orderMapper.updatePartial(order) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOrderStatus(Long id, Integer status) {
        Order order = new Order();
        order.setId(id);
        order.setStatus(status);
        order.setUpdateTime(new Date());
        return orderMapper.update(order) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchUpdateOrderStatus(OrderBatchUpdateStatusDTO batchUpdateStatusDTO) {
        // 批量更新订单状态
        return orderMapper.updateBatchStatus(batchUpdateStatusDTO.getOrderIds(), batchUpdateStatusDTO.getStatus()) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteOrder(Long id) {
        // 先删除订单项
        orderItemMapper.deleteByOrderId(id);
        // 再删除订单
        return orderMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchDeleteOrders(OrderBatchDeleteDTO batchDeleteOrderDTO) {
        // 批量删除订单
        return orderMapper.deleteBatch(batchDeleteOrderDTO.getOrderIds()) > 0;
    }
}