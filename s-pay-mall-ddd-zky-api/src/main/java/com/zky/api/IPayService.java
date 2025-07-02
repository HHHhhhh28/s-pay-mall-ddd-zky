package com.zky.api;

import com.zky.api.dto.CreatePayRequestDTO;
import com.zky.api.response.Response;

public interface IPayService {

    Response<String> createPayOrder(CreatePayRequestDTO createPayRequestDTO);

}
