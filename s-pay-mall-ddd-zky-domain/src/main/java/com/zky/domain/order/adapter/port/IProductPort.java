package com.zky.domain.order.adapter.port;


import com.zky.domain.order.model.entity.MarketPayDiscountEntity;
import com.zky.domain.order.model.entity.ProductEntity;

public interface IProductPort {

    /**
     * 模拟查询商品信息
     *
     * @param productId 商品ID
     * @return 商品实体对象
     */
    ProductEntity queryProductByProductId(String productId);

    MarketPayDiscountEntity lockMarketPayOrder(String userId, String teamId, Long activityId, String productId, String orderId);


}
