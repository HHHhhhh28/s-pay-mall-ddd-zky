package com.zky.infrastructure.adapter.repository;

import com.zky.domain.goods.adapter.repository.IGoodsRepository;
import com.zky.infrastructure.dao.IOrderDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author zky
 * @description 结算仓储服务
 * @create 2025-02-15 09:13
 */
@Repository
public class GoodsRepository implements IGoodsRepository {

    @Resource
    private IOrderDao orderDao;

    @Override
    public void changeOrderDealDone(String orderId) {
        orderDao.changeOrderDealDone(orderId);
    }

}
