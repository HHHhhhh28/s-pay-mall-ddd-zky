package com.zky.domain.order.model.aggregate;

import com.zky.domain.order.model.entity.OrderEntity;
import com.zky.domain.order.model.entity.ProductEntity;
import com.zky.domain.order.model.valobj.OrderStatusVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderAggregate {

    // 用户ID
    private String userId;
    // 商品实体对象
    private ProductEntity productEntity;
    // 订单实体对象
    private OrderEntity orderEntity;

    public static OrderEntity buildOrderEntity(String productId, String productName, Integer marketType){
        return OrderEntity.builder()
                .productId(productId)
                .productName(productName)
                .orderId(RandomStringUtils.randomNumeric(12))
                .orderTime(new Date())
                .orderStatusVO(OrderStatusVO.CREATE)
                .marketType(marketType)
                .build();
    }

}
