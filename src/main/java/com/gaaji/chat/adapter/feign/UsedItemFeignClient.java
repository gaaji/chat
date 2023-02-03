package com.gaaji.chat.adapter.feign;

import com.gaaji.chat.adapter.feign.dto.UsedItemPostFeignClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("used-item-service")
public interface UsedItemFeignClient {
    @GetMapping("/posts/{postId}")
    UsedItemPostFeignClientDto findUsedItemById(@PathVariable String postId);
}
