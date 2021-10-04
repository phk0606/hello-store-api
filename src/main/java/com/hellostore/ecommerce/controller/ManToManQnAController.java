package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.ManToManQnADto;
import com.hellostore.ecommerce.dto.ManToManQuestionDto;
import com.hellostore.ecommerce.dto.ManToManQuestionSearchCondition;
import com.hellostore.ecommerce.dto.ManToManQuestionTypeDto;
import com.hellostore.ecommerce.enumType.ManToManQuestionType;
import com.hellostore.ecommerce.service.ManToManQnAService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/manToManQnA")
@RequiredArgsConstructor
@Slf4j
public class ManToManQnAController {

    private final ManToManQnAService manToManQnAService;

    @GetMapping("/getManToManQuestionTypes")
    public List<ManToManQuestionTypeDto> getManToManQuestionTypes() {

        List<ManToManQuestionTypeDto> manToManQuestionTypes = new ArrayList<>();
        List<ManToManQuestionType> collect1 = Arrays.asList(ManToManQuestionType.values())
                .stream()
                .sorted(Comparator.comparing(ManToManQuestionType::getSequence))
                .collect(Collectors.toList());
        for (ManToManQuestionType manToManQuestionType : collect1) {
            log.debug("manToManQuestionType: {}", manToManQuestionType);
            manToManQuestionTypes.add(new ManToManQuestionTypeDto(manToManQuestionType, manToManQuestionType.getValue()));
        }

        return manToManQuestionTypes;
    }

    @PostMapping("/createManToManQuestion")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void createManToManQuestion(@RequestBody ManToManQuestionDto manToManQuestionDto) {
        log.debug("manToManQuestionDto: {}", manToManQuestionDto);
        manToManQnAService.createManToManQuestion(manToManQuestionDto);
    }

    @GetMapping("/getManToManQuestions")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Page<ManToManQuestionDto> getManToManQuestions(ManToManQuestionSearchCondition manToManQuestionSearchCondition,
                                             Pageable pageable) {
        log.debug("manToManQuestionSearchCondition: {}", manToManQuestionSearchCondition);
        return manToManQnAService.getManToManQuestions(manToManQuestionSearchCondition, pageable);
    }

    @GetMapping("/getManToManQnA")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ManToManQnADto getManToManQnA(@RequestParam Long manToManQuestionId) {
        log.debug("manToManQuestionId: {}", manToManQuestionId);
        return manToManQnAService.getManToManQnA(manToManQuestionId);
    }

    @PostMapping("/createOrModifyAnswer")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void createOrModifyAnswer(@RequestBody ManToManQnADto manToManQnADto) {
        log.debug("manToManQnADto: {}", manToManQnADto);
        manToManQnAService.createOrModifyAnswer(manToManQnADto);
    }

    @PutMapping("/modifyManToManQuestion")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void modifyManToManQuestion(@RequestBody ManToManQuestionDto manToManQuestionDto) {
        manToManQnAService.modifyManToManQuestion(manToManQuestionDto);
    }

    @DeleteMapping("/removeManToManQuestion")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void removeManToManQuestion(@RequestBody ManToManQnADto manToManQnADto) {
        manToManQnAService.removeManToManQuestion(manToManQnADto);
    }

    @DeleteMapping("/removeManToManAnswer")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void removeManToManAnswer(@RequestBody ManToManQnADto manToManAnswerDto) {
        manToManQnAService.removeManToManAnswer(manToManAnswerDto);
    }
}
