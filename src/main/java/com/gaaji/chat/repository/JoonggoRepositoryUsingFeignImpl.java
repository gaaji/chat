package com.gaaji.chat.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gaaji.chat.adapter.feign.UsedItemFeignClient;
import com.gaaji.chat.adapter.feign.dto.FeignErrorResponse;
import com.gaaji.chat.adapter.feign.dto.UsedItemPostFeignClientDto;
import com.gaaji.chat.domain.User;
import com.gaaji.chat.domain.post.Joonggo;
import com.gaaji.chat.execption.AuthAndUsedItemUnmatchedException;
import com.gaaji.chat.execption.InternalServerException;
import com.gaaji.chat.utils.TransactionExecutor;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class JoonggoRepositoryUsingFeignImpl implements JoonggoRepositoryUsingFeign {
    private final JoonggoRepository joonggoRepository;
    private final UsedItemFeignClient usedItemFeignClient;
    private final PostRepository postRepository;
    private final UserRepositoryUsingFeign userRepositoryUsingFeign;
    private final TransactionExecutor transactionExecutor;

    @Override
    public Optional<Joonggo> findById(String id) {
        return joonggoRepository.findById(id).or(() -> Optional.of(transactionExecutor.execute(() -> findJoonggoByIdUsingFeign(id))));
    }

    private Joonggo findJoonggoByIdUsingFeign(String id) {
        try {
            UsedItemPostFeignClientDto usedItemById = usedItemFeignClient.findUsedItemById(id);
            User user = userRepositoryUsingFeign.findById(usedItemById.getSellerId()).orElseThrow(AuthAndUsedItemUnmatchedException::new);
            return postRepository.save(Joonggo.createJoonggo(usedItemById.getPostId(), user));
        } catch (FeignException e) {
            try {
                FeignErrorResponse errorResponse = FeignErrorResponse.of(e);
                if(!errorResponse.getErrorName().equals("NO_SEARCH_POST_EXCEPTION")) throw new InternalServerException();
                return null;
            } catch (JsonProcessingException ex) {
                throw new InternalServerException();
            }
        }
    }
}
