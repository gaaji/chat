package com.gaaji.chat.adapter.feign;

import com.gaaji.chat.adapter.feign.dto.AuthFeignClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("auth-service")
public interface AuthFeignClient {
    @GetMapping("/auth/{authId}")
    AuthFeignClientDto findAuthById(@PathVariable String authId);
}
