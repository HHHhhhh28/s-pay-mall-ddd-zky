package com.zky.api;


import com.zky.api.response.Response;

public interface IAuthService {
    Response<String> weixinQrCodeTicket();
    Response<String> checkLogin(String ticket);
}

