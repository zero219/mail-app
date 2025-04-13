package com.mail.coupon.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("mail-gateway")
public interface GatewayFeignService {
    @GetMapping("/api/info")
     String info();
}
