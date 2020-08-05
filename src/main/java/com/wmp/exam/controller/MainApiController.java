package com.wmp.exam.controller;

import com.wmp.exam.domain.ApiRequest;
import com.wmp.exam.domain.CommonResponseDto;
import com.wmp.exam.domain.CommonResponseEntity;
import com.wmp.exam.service.ExamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/exam")
public class MainApiController {

  private final ExamService examService;

  @PostMapping
  public ResponseEntity createShoutUrl(@RequestBody ApiRequest apiRequest)
      throws Exception {
    CommonResponseDto commonResponseDto =
        examService.generate(
            apiRequest.getUrl(), apiRequest.getType() == 0, apiRequest.getCount());
    return CommonResponseEntity.ok(commonResponseDto.getResult());
  }
}
