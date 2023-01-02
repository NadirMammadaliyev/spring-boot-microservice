package com.nadeer.inventoryservice.service;

import com.nadeer.inventoryservice.dto.InventoryResponse;
import com.nadeer.inventoryservice.repository.InventoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> skuCode) {
       /* log.info("isInStock.start: Wait started");
        Thread.sleep(10000);                                 //Testing for circuit breaker
        log.info("isInStock.start: Wait ended");*/
        log.info("isInStock.start skuCode: {}" , skuCode);
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .skuCode(inventory.getSkuCode())
                                .isInStock(inventory.getQuantity() > 0)
                                .build()).toList();
    }
}
