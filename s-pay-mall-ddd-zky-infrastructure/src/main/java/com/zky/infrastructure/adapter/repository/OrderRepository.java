package com.zky.infrastructure.adapter.repository;

import com.google.common.eventbus.EventBus;
import com.zky.domain.order.adapter.event.PaySuccessMessageEvent;
import com.zky.domain.order.adapter.repository.IOrderRepository;
import com.zky.domain.order.model.aggregate.CreateOrderAggregate;
import com.zky.domain.order.model.entity.OrderEntity;
import com.zky.domain.order.model.entity.PayOrderEntity;
import com.zky.domain.order.model.entity.ProductEntity;
import com.zky.domain.order.model.entity.ShopCartEntity;
import com.zky.domain.order.model.valobj.OrderStatusVO;
import com.zky.infrastructure.dao.IOrderDao;
import com.zky.infrastructure.dao.po.PayOrder;
import com.zky.types.common.Constants;
import com.zky.types.event.BaseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Repository
public class OrderRepository implements IOrderRepository {

    @Resource
    private IOrderDao orderDao;
    @Autowired
    private PaySuccessMessageEvent paySuccessMessageEvent;
    @Resource
    private EventBus eventBus;

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
        PayOrder order = new PayOrder();
        order.setUserId(payOrderEntity.getUserId());
        order.setOrderId(payOrderEntity.getOrderId());
        order.setPayUrl(payOrderEntity.getPayUrl());
        order.setStatus(payOrderEntity.getOrderStatus().getCode());
        orderDao.updateOrderPayInfo(order);

    }

    @Override
    public void changeOrderPaySuccess(String orderId) {
        PayOrder payOrderReq = new PayOrder();
        payOrderReq.setOrderId(orderId);
        payOrderReq.setStatus(OrderStatusVO.PAY_SUCCESS.getCode());
        orderDao.changeOrderPaySuccess(payOrderReq);
        BaseEvent.EventMessage<PaySuccessMessageEvent.PaySuccessMessage> paySuccessMessageEventMessage = paySuccessMessageEvent.buildEventMessage(PaySuccessMessageEvent.PaySuccessMessage.builder().tradeNo(orderId).build());
        PaySuccessMessageEvent.PaySuccessMessage paySuccessMessage = paySuccessMessageEventMessage.getData();
        eventBus.post(paySuccessMessage);
    }

    @Override
    public List<String> queryNoPayNotifyOrder() {
        return orderDao.queryNoPayNotifyOrder();
    }

    @Override
    public List<String> queryTimeoutCloseOrderList() {
        return orderDao.queryTimeoutCloseOrderList();
    }

    @Override
    public boolean changeOrderClose(String orderId) {
        return orderDao.changeOrderClose(orderId);
    }
}

