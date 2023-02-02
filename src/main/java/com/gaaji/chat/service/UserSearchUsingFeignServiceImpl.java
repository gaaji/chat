package com.gaaji.chat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gaaji.chat.adapter.feign.AuthFeignClient;
import com.gaaji.chat.adapter.feign.dto.AuthFeignClientDto;
import com.gaaji.chat.adapter.feign.dto.AuthFeignErrorResponse;
import com.gaaji.chat.domain.User;
import com.gaaji.chat.execption.InternalServerException;
import com.gaaji.chat.execption.UserNotFoundException;
import com.gaaji.chat.repository.UserRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserSearchUsingFeignServiceImpl implements UserSearchUsingFeignService {
    private final AuthFeignClient authFeignClient;
    private final UserRepository userRepository;

    @Override
    public User searchById(String id) {
        return userRepository.findById(id).orElseGet(() -> {
            AuthFeignClientDto authFeignClientDto = findAuthByIdUsingFeign(id);
            return userRepository.save(User.create(authFeignClientDto.getAuthId(), authFeignClientDto.getNickname()));
        });
    }

    private AuthFeignClientDto findAuthByIdUsingFeign(String id) {
        try {
            return authFeignClient.findAuthById(id);
        } catch (FeignException e) {
            try {
                AuthFeignErrorResponse errorResponse = AuthFeignErrorResponse.of(e);
                if(!errorResponse.getErrorName().equals("AUTH_ID_NOT_FOUND")) throw new InternalServerException();
                throw new UserNotFoundException();
            } catch (JsonProcessingException ex) {
                throw new InternalServerException();
            }
        }
    }
}
