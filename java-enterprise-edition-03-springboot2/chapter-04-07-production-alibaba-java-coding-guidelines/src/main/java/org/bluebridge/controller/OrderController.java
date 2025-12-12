package org.bluebridge.controller;

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
import org.bluebridge.service.OrderService;
import org.bluebridge.vo.OrderVO;
import org.bluebridge.vo.OrderPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单控制器
 *
 * @author YourName
 * @since 2025-12-12
 */
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     *
     * @param createOrderDTO 订单信息
     * @return 结果信息
     */
    @PostMapping
    public Map<String, Object> createOrder(@RequestBody OrderCreateDTO createOrderDTO) {
        Map<String, Object> result = new HashMap<>();
        try {
            Long orderId = orderService.createOrder(createOrderDTO);
            result.put("success", true);
            result.put("orderId", orderId);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    /**
     * 批量创建订单
     *
     * @param batchCreateOrderDTO 批量订单信息
     * @return 结果信息
     */
    @PostMapping("/batch")
    public Map<String, Object> batchCreateOrders(@RequestBody OrderBatchCreateDTO batchCreateOrderDTO) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = orderService.batchCreateOrders(batchCreateOrderDTO);
            if (success) {
                result.put("success", true);
                result.put("message", "批量创建订单成功");
            } else {
                result.put("success", false);
                result.put("message", "批量创建订单失败");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    /**
     * 根据ID查询订单
     *
     * @param id 订单ID
     * @return 订单信息
     */
    @GetMapping("/{id}")
    public Map<String, Object> getOrderById(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            OrderVO order = orderService.getOrderById(id);
            if (order != null) {
                result.put("success", true);
                result.put("data", order);
            } else {
                result.put("success", false);
                result.put("message", "订单不存在");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    /**
     * 根据唯一条件查询订单
     *
     * @param orderUniqueQueryDTO 唯一条件查询参数
     * @return 订单信息
     */
    @GetMapping("/unique")
    public Map<String, Object> getOrderByUniqueCondition(OrderUniqueQueryDTO orderUniqueQueryDTO) {
        Map<String, Object> result = new HashMap<>();
        try {
            OrderVO order = orderService.getOrderByUniqueCondition(orderUniqueQueryDTO);
            if (order != null) {
                result.put("success", true);
                result.put("data", order);
            } else {
                result.put("success", false);
                result.put("message", "订单不存在");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    /**
     * 查询所有订单列表（慎用）
     *
     * @return 订单列表
     */
    @GetMapping("/all")
    public Map<String, Object> getAllOrders() {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("success", true);
            result.put("data", orderService.getAllOrders());
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    /**
     * 根据条件查询订单列表（不分页）
     *
     * @param orderListQueryDTO 查询条件
     * @return 订单列表
     */
    @GetMapping("/condition")
    public Map<String, Object> getOrdersByCondition(OrderListQueryDTO orderListQueryDTO) {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("success", true);
            result.put("data", orderService.getOrderListByCondition(orderListQueryDTO));
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    /**
     * 分页查询订单列表
     *
     * @param orderQueryDTO 查询条件
     * @return 订单列表
     */
    @GetMapping("/list")
    public Map<String, Object> getOrderList(OrderQueryDTO orderQueryDTO) {
        Map<String, Object> result = new HashMap<>();
        try {
            PageDTO pageDTO = new PageDTO();
            pageDTO.setPageNum(orderQueryDTO.getPageNum());
            pageDTO.setPageSize(orderQueryDTO.getPageSize());
            
            OrderPageVO<OrderVO> pageResult = orderService.getOrderPage(orderQueryDTO, pageDTO);
            result.put("success", true);
            result.put("data", pageResult.getRecords());
            result.put("total", pageResult.getTotal());
            result.put("pageNum", pageResult.getPageNum());
            result.put("pageSize", pageResult.getPageSize());
            result.put("pages", pageResult.getPages());
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    /**
     * 全量更新订单信息
     *
     * @param fullUpdateOrderDTO 订单信息
     * @return 结果信息
     */
    @PutMapping("/full")
    public Map<String, Object> fullUpdateOrder(@RequestBody OrderUpdateDTO updateOrderDTO) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = orderService.fullUpdateOrder(updateOrderDTO);
            if (success) {
                result.put("success", true);
                result.put("message", "订单全量更新成功");
            } else {
                result.put("success", false);
                result.put("message", "订单全量更新失败");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    /**
     * 局部更新订单信息
     *
     * @param partialUpdateOrderDTO 订单信息
     * @return 结果信息
     */
    @PutMapping("/partial")
    public Map<String, Object> partialUpdateOrder(@RequestBody OrderPartialUpdateDTO partialUpdateOrderDTO) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = orderService.partialUpdateOrder(partialUpdateOrderDTO);
            if (success) {
                result.put("success", true);
                result.put("message", "订单局部更新成功");
            } else {
                result.put("success", false);
                result.put("message", "订单局部更新失败");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    /**
     * 更新订单状态
     *
     * @param id     订单ID
     * @param status 订单状态
     * @return 结果信息
     */
    @PutMapping("/{id}/status/{status}")
    public Map<String, Object> updateOrderStatus(@PathVariable Long id, @PathVariable Integer status) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = orderService.updateOrderStatus(id, status);
            if (success) {
                result.put("success", true);
                result.put("message", "订单状态更新成功");
            } else {
                result.put("success", false);
                result.put("message", "订单状态更新失败");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    /**
     * 批量更新订单状态
     *
     * @param batchUpdateStatusDTO 批量更新参数
     * @return 结果信息
     */
    @PutMapping("/batch/status")
    public Map<String, Object> batchUpdateOrderStatus(@RequestBody OrderBatchUpdateStatusDTO batchUpdateStatusDTO) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = orderService.batchUpdateOrderStatus(batchUpdateStatusDTO);
            if (success) {
                result.put("success", true);
                result.put("message", "批量更新订单状态成功");
            } else {
                result.put("success", false);
                result.put("message", "批量更新订单状态失败");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    /**
     * 删除订单
     *
     * @param id 订单ID
     * @return 结果信息
     */
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteOrder(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = orderService.deleteOrder(id);
            if (success) {
                result.put("success", true);
                result.put("message", "订单删除成功");
            } else {
                result.put("success", false);
                result.put("message", "订单删除失败");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    /**
     * 批量删除订单
     *
     * @param batchDeleteOrderDTO 批量删除参数
     * @return 结果信息
     */
    @DeleteMapping("/batch")
    public Map<String, Object> batchDeleteOrders(@RequestBody OrderBatchDeleteDTO batchDeleteOrderDTO) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = orderService.batchDeleteOrders(batchDeleteOrderDTO);
            if (success) {
                result.put("success", true);
                result.put("message", "批量删除订单成功");
            } else {
                result.put("success", false);
                result.put("message", "批量删除订单失败");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}