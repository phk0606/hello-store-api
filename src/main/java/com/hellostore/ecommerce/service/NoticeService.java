package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.NoticeDto;
import com.hellostore.ecommerce.entity.Notice;
import com.hellostore.ecommerce.repository.NoticeRepository;
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
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional
    public void createNotice(NoticeDto noticeDto) {
        Notice notice = Notice.builder()
                .title(noticeDto.getTitle())
                .content(noticeDto.getContent())
                .important(noticeDto.isImportant())
                .build();
        noticeRepository.save(notice);
    }

    public Page<NoticeDto> getNotices(Pageable pageable) {
        return noticeRepository.getNotices(pageable);
    }

    public NoticeDto getNotice(Long noticeId) {
        return noticeRepository.getNotice(noticeId);
    }
}
