package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.ManToManQnADto;
import com.hellostore.ecommerce.dto.ManToManQuestionDto;
import com.hellostore.ecommerce.dto.ManToManQuestionSearchCondition;
import com.hellostore.ecommerce.entity.ManToManAnswer;
import com.hellostore.ecommerce.entity.ManToManQuestion;
import com.hellostore.ecommerce.repository.ManToManQnARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ManToManQnAService {

    private final ManToManQnARepository manToManQnARepository;

    @Transactional
    public void createManToManQuestion(ManToManQuestionDto manToManQuestionDto) {

        ManToManQuestion manToManQuestion = ManToManQuestion.builder()
                .manToManQuestionType(manToManQuestionDto.getManToManQuestionType())
                .title(manToManQuestionDto.getManToManQuestionTitle())
                .content(manToManQuestionDto.getManToManQuestionContent())
                .build();

        manToManQnARepository.createManToManQuestion(manToManQuestion);
    }

    public Page<ManToManQuestionDto> getManToManQuestions(ManToManQuestionSearchCondition manToManQuestionSearchCondition, Pageable pageable) {
        return manToManQnARepository.getManToManQuestions(manToManQuestionSearchCondition, pageable);
    }

    public ManToManQnADto getManToManQnA(Long manToManQuestionId) {
        return manToManQnARepository.getManToManQnA(manToManQuestionId);
    }

    @Transactional
    public void modifyManToManQuestion(ManToManQuestionDto manToManQuestionDto) {
        manToManQnARepository.modifyManToManQuestion(manToManQuestionDto);
    }

    @Transactional
    public void createOrModifyAnswer(ManToManQnADto manToManQnADto) {

        if (!ObjectUtils.isEmpty(manToManQnADto.getManToManAnswerId())) {
            manToManQnARepository.modifyAnswer(manToManQnADto);
        } else {
            ManToManQuestion manToManQuestion
                    = manToManQnARepository.getManToManQuestion(manToManQnADto.getManToManQuestionId());
            ManToManAnswer manToManAnswer = ManToManAnswer.builder()
                    .manToManQuestion(manToManQuestion)
                    .content(manToManQnADto.getManToManAnswerContent())
                    .build();
            manToManQnARepository.createAnswer(manToManAnswer);
        }
    }

    @Transactional
    public void removeManToManQuestion(ManToManQnADto manToManQnADto) {
        manToManQnARepository.removeManToManAnswer(manToManQnADto);
        manToManQnARepository.removeManToManQuestion(manToManQnADto);
    }

    @Transactional
    public void removeManToManAnswer(ManToManQnADto manToManQnADto) {
        manToManQnARepository.removeManToManAnswer(manToManQnADto);
    }
}
