package com.gaaji.chat.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gaaji.chat.adapter.feign.AuthFeignClient;
import com.gaaji.chat.adapter.feign.dto.AuthFeignClientDto;
import com.gaaji.chat.adapter.feign.dto.AuthFeignErrorResponse;
import com.gaaji.chat.domain.User;
import com.gaaji.chat.execption.InternalServerException;
import com.gaaji.chat.utils.TransactionExecutor;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryUsingFeignImpl implements UserRepositoryUsingFeign {
    private final AuthFeignClient authFeignClient;
    private final UserRepository userRepository;
    private final TransactionExecutor transactionExecutor;

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id).or(() -> Optional.of(transactionExecutor.execute(() -> findAuthByIdUsingFeign(id))));
    }

    private User findAuthByIdUsingFeign(String id) {
        try {
            AuthFeignClientDto authById = authFeignClient.findAuthById(id);
            return userRepository.save(User.create(authById.getAuthId(), authById.getNickname()));
        } catch (FeignException e) {
            try {
                AuthFeignErrorResponse errorResponse = AuthFeignErrorResponse.of(e);
                if(!errorResponse.getErrorName().equals("AUTH_ID_NOT_FOUND")) throw new InternalServerException();
                return null;
            } catch (JsonProcessingException ex) {
                throw new InternalServerException();
            }
        }
    }
}
