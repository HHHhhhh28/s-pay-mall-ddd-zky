package com.zky.api;

import com.zky.api.dto.CreatePayRequestDTO;
import com.zky.api.dto.NotifyRequestDTO;
import com.zky.api.response.Response;

public interface IPayService {

    Response<String> createPayOrder(CreatePayRequestDTO createPayRequestDTO);

    /**
     * 拼团结算回调
     *
     * @param requestDTO 请求对象
     * @return 返参，success 成功
     */
    String groupBuyNotify(NotifyRequestDTO requestDTO);

}
