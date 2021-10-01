package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.PointHistoryDto;
import com.hellostore.ecommerce.dto.PointHistorySearchCondition;
import com.hellostore.ecommerce.repository.PointHistoryRepository;
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
public class PointHistoryService {

    private final PointHistoryRepository pointHistoryRepository;

    public Page<PointHistoryDto> getPointHistory(PointHistorySearchCondition pointHistorySearchCondition,
                                                 Pageable pageable) {

        return pointHistoryRepository.getPointHistory(pointHistorySearchCondition, pageable);
    }

    public Integer getUserHavePoint(String username) {

        return pointHistoryRepository.getUserHavePoint(username);
    }
}
