package com.wmp.exam.service;

import com.wmp.exam.domain.CommonResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface ExamService {
  CommonResponseDto generate(String str, boolean removeTag, int digit);
  String getHtmlByUrl(String url, boolean removeTag);
  String extractionStringOrNumber(String str);
  String[] extractionSortString(String str);
  int[] extractionSortNumber(String str);
  String mergeString(String[] str, int[] num);
  String[] shareString(String str, int digit);
}
