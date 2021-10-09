package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.StockQuantityDto;
import com.hellostore.ecommerce.repository.StockQuantityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StockQuantityService {

    private final StockQuantityRepository stockQuantityRepository;

    public Page<StockQuantityDto> getStockQuantities(Pageable pageable) {

        return stockQuantityRepository.getStockQuantities(pageable);
    }
}
