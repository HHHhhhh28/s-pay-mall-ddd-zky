package com.zky.domain.order.model.entity;

import com.zky.domain.order.model.valobj.OrderStatusVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

    // 用户ID
    private String userId;
    /** 商品ID */
    private String productId;
    /** 商品 */
    private String productName;
    /** 订单编号 */
    private String orderId;
    /** 下单时间 */
    private Date orderTime;
    /** 订单状态；create-创建完成、pay_wait-等待支付、pay_success-支付成功、deal_done-交易完成、close-订单关单 */
    private OrderStatusVO orderStatusVO;
    /** 订单金额 */
    private BigDecimal totalAmount;
    /** 支付信息 */
    private String payUrl;
    // 营销类型；0无营销、1拼团营销
    private Integer marketType;
    // 营销金额；优惠金额
    private BigDecimal marketDeductionAmount;
    // 支付金额
    private BigDecimal payAmount;

}
