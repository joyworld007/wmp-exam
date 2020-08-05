package com.wmp.exam;

import static org.assertj.core.api.Assertions.assertThat;

import com.wmp.exam.service.ExamServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@EnableAutoConfiguration
@ActiveProfiles("local")
public class ExamServiceTest {

  @InjectMocks
  private ExamServiceImpl examService;

  private String url = "https://www.wemakeprice.com";

  @BeforeEach
  public void setup(TestInfo testInfo) {
    rawSetup(testInfo);
  }

  private void rawSetup(TestInfo testInfo) {
    switch (testInfo.getDisplayName()) {
    }
  }

  @DisplayName("get html by url test")
  @Test
  public void getHtmlByUrlTest() {
    String result = examService.getHtmlByUrl(url, true);
    assertThat(result).isNotEmpty();
  }

  @DisplayName("include tag test")
  @Test
  public void includeTagTest() {
    String result = examService.getHtmlByUrl(url, false);
    assertThat(result.indexOf("<body>")).isGreaterThan(0);
  }

  @DisplayName("remove tag test")
  @Test
  public void removeTagTest() {
    String result = examService.getHtmlByUrl(url, true);
    System.out.println(result);
    assertThat(result.indexOf("<body>")).isEqualTo(-1);
  }

  @DisplayName("extract string or number test")
  @Test
  public void extractStringOrNumberTest() {
    String str = "가나다라마Agdsf234234AA가나다라AAggg2352354가나다라";
    String result = examService.extractionStringOrNumber(str);
    assertThat(result.indexOf("가나다라")).isEqualTo(-1);
  }

  @DisplayName("extract sort String array test")
  @Test
  public void extractSortStringArrayTest() {
    String str = "A010Aaa201bB";
    String[] array = examService.extractionSortString(str);
    assertThat(array[5]).isEqualTo("b");
  }

  @DisplayName("extract sort number array test")
  @Test
  public void extractSortNumberArrayTest() {
    String str = "A010Aaa201bB";
    int[] array = examService.extractionSortNumber(str);
    assertThat(array[3]).isEqualTo(1);
  }

  @DisplayName("merge string test")
  @Test
  public void mergeStringTest() {
    String str = "A010Aaa201bB";
    String result = examService.mergeString(
        examService.extractionSortString(str), examService.extractionSortNumber(str)
    );
    assertThat(result).isEqualTo("A0A0a0a1B1b2");
  }

  @DisplayName("merge string test2")
  @Test
  public void mergeStringTest2() {
    String str = "A010Aaa201bBCdef";
    String result = examService.mergeString(
        examService.extractionSortString(str), examService.extractionSortNumber(str)
    );
    assertThat(result).isEqualTo("A0A0a0a1B1b2Cdef");
  }

  @DisplayName("merge string test3")
  @Test
  public void mergeStringTest3() {
    String str = "A010Aaa201bB5436";
    String result = examService.mergeString(
        examService.extractionSortString(str), examService.extractionSortNumber(str)
    );
    assertThat(result).isEqualTo("A0A0a0a1B1b23456");
  }

  @DisplayName("remainder test")
  @Test
  public void remainderTest() {
    String str = "A0A0a0a1B1b2";
    String[] result = examService.shareString("A0A0a0a1B1b2", 5);
    assertThat(result[0]).isEqualTo("A0A0a0a1B1");
    assertThat(result[1]).isEqualTo("b2");
  }

  @DisplayName("integration test")
  @Test
  public void integrationTest() {
    String str = examService.getHtmlByUrl(url, false);
    str = examService.extractionStringOrNumber(str);
    int[] numArray = examService.extractionSortNumber(str);
    String[] stringArray = examService.extractionSortString(str);
    String result = examService.mergeString(stringArray, numArray);
    String[] array = examService.shareString(result, 5);
    assertThat(array[1]).isEqualTo("zzz");
  }
  
}
