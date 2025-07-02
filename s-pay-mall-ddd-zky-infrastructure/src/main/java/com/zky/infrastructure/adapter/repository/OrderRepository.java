package com.zky.infrastructure.adapter.repository;

import com.zky.domain.order.adapter.repository.IOrderRepository;
import com.zky.domain.order.model.aggregate.CreateOrderAggregate;
import com.zky.domain.order.model.entity.OrderEntity;
import com.zky.domain.order.model.entity.PayOrderEntity;
import com.zky.domain.order.model.entity.ProductEntity;
import com.zky.domain.order.model.entity.ShopCartEntity;
import com.zky.domain.order.model.valobj.OrderStatusVO;
import com.zky.infrastructure.dao.IOrderDao;
import com.zky.infrastructure.dao.po.PayOrder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Repository
public class OrderRepository implements IOrderRepository {

    @Resource
    private IOrderDao orderDao;

    @Override
    public OrderEntity queryUnPayOrder(ShopCartEntity shopCartEntity) {
        // 1. 封装参数
        PayOrder orderReq = new PayOrder();
        orderReq.setUserId(shopCartEntity.getUserId());
        orderReq.setProductId(shopCartEntity.getProductId());
        // 2. 查询到订单
        PayOrder order = orderDao.queryUnPayOrder(orderReq);
        if (null == order) return null;
        // 3. 返回结果
        return OrderEntity.builder()
                .productId(order.getProductId())
                .productName(order.getProductName())
                .orderId(order.getOrderId())
                .orderStatus(OrderStatusVO.valueOf(order.getStatus()))
                .orderTime(order.getOrderTime())
                .totalAmount(order.getTotalAmount())
                .payUrl(order.getPayUrl())
                .build();
    }

    @Override
    public void doSaveOrder(CreateOrderAggregate orderAggregate) {
        String userId = orderAggregate.getUserId();
        ProductEntity productEntity = orderAggregate.getProductEntity();
        OrderEntity orderEntity = orderAggregate.getOrderEntity();

        PayOrder order = new PayOrder();
        order.setUserId(userId);
        order.setProductId(productEntity.getProductId());
        order.setProductName(productEntity.getProductName());
        order.setOrderId(orderEntity.getOrderId());
        order.setOrderTime(orderEntity.getOrderTime());
        order.setTotalAmount(productEntity.getPrice());
        order.setStatus(orderEntity.getOrderStatus().getCode());

        orderDao.insert(order);
    }

    @Override
    public void updateOrderPayInfo(PayOrderEntity payOrderEntity) {

    }

    @Override
    public void changeOrderPaySuccess(String orderId) {

    }

    @Override
    public List<String> queryNoPayNotifyOrder() {
        return Collections.emptyList();
    }

    @Override
    public List<String> queryTimeoutCloseOrderList() {
        return Collections.emptyList();
    }

    @Override
    public boolean changeOrderClose(String orderId) {
        return false;
    }
}

