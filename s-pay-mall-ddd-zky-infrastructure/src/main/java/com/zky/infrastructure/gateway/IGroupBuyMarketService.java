package com.zky.infrastructure.gateway;

import com.zky.infrastructure.gateway.dto.LockMarketPayOrderRequestDTO;
import com.zky.infrastructure.gateway.dto.LockMarketPayOrderResponseDTO;
import com.zky.infrastructure.gateway.response.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author zky
 * @description 拼团营销
 * @create 2025-02-06 14:03
 */
public interface IGroupBuyMarketService {

    /**
     * 营销锁单
     *
     * @param requestDTO 锁单商品信息
     * @return 锁单结果信息
     */
    @POST("api/v1/gbm/trade/lock_market_pay_order")
    Call<Response<LockMarketPayOrderResponseDTO>> lockMarketPayOrder(@Body LockMarketPayOrderRequestDTO requestDTO);

}
