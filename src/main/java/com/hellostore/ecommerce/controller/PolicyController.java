package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.PolicyDto;
import com.hellostore.ecommerce.service.PolicyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/policy")
@RequiredArgsConstructor
@Slf4j
public class PolicyController {

    private final PolicyService policyService;

    @GetMapping("/getPolicy")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public PolicyDto getPolicy() {

        return policyService.getPolicy();
    }

    @PostMapping("/mergePolicy")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void mergePolicy(@RequestBody PolicyDto policyDto) {

        policyService.mergePolicy(policyDto);
    }
}
