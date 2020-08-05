package com.wmp.exam.service;

import com.wmp.exam.domain.ApiResult;
import com.wmp.exam.domain.CommonResponseDto;
import com.wmp.exam.domain.Result;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class ExamServiceImpl implements ExamService {

  @Override
  public String getHtmlByUrl(String str, boolean removeTag) {
    try {
      Document doc = Jsoup.connect(str).get();
      if (removeTag) {
        return doc.text();
      } else {
        return doc.html();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }

  @Override
  public String extractionStringOrNumber(String str) {
    Pattern pattern = Pattern.compile("[A-Za-z0-9]"); // 영문, 숫자
    Matcher matcher = pattern.matcher(str);
    StringBuilder sb = new StringBuilder();
    while (matcher.find()) {
      sb.append(matcher.group(0));
      if (matcher.group(0) == null) {
        break;
      }
    }
    return sb.toString();
  }

  @Override
  public String[] extractionSortString(String str) {
    String intStr1 = str.replaceAll("[^A-Z]", "");
    String intStr2 = str.replaceAll("[^a-z]", "");

    if (intStr1.equals("") && intStr2.equals("")) {
      return new String[0];
    }
    char charArray1[] = intStr1.toCharArray();
    char charArray2[] = intStr2.toCharArray();

    int size1 = charArray1.length;
    int size2 = charArray2.length;

    String[] result1 = new String[size1];
    for (int i = 0; i < charArray1.length; i++) {
      result1[i] = Character.toString(charArray1[i]);
    }
    String[] result2 = new String[size2];
    for (int i = 0; i < charArray2.length; i++) {
      result2[i] = Character.toString(charArray2[i]);
    }

    Arrays.sort(result1, new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        return o1.charAt(0) - o2.charAt(0);
      }
    });

    Arrays.sort(result2, new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        return o1.charAt(0) - o2.charAt(0);
      }
    });

    int index1 = 0;
    int index2 = 0;
    String[] result = new String[size1 + size2];

    int cnt = 0;
    while (true) {
      if (index1 == result1.length || index2 == result2.length) {
        break;
      }
      if (result1[index1].toUpperCase().charAt(0) < result2[index2].toUpperCase().charAt(0)) {
        result[cnt++] = result1[index1++];
      } else if (result1[index1].toUpperCase().charAt(0) == result2[index2].toUpperCase()
          .charAt(0)) {
        if (result1[index1].charAt(0) < result2[index2].charAt(0)) {
          result[cnt++] = result1[index1++];
        } else {
          result[cnt++] = result2[index2++];
        }
      } else {
        result[cnt++] = result2[index2++];
      }
    }

    for (int i = index1; i < result1.length; i++) {
      result[cnt++] = result1[index1++];
    }

    for (int i = index2; i < result2.length; i++) {
      result[cnt++] = result2[index2++];
    }

    return result;
  }

  @Override
  public int[] extractionSortNumber(String str) {
    String intStr = str.replaceAll("[^0-9]", "");
    if (intStr.equals("")) {
      return new int[0];
    }
    String array[] = intStr.split("");
    int[] result = new int[array.length];
    for (int i = 0; i < array.length; i++) {
      result[i] = Integer.parseInt(array[i]);
    }
    Arrays.sort(result);
    return result;
  }

  @Override
  public String mergeString(String[] str, int[] num) {
    int index1 = 0;
    int index2 = 0;
    StringBuilder sb = new StringBuilder();
    while (true) {
      if (index1 == str.length || index2 == num.length) {
        break;
      }
      sb.append(str[index1++]);
      sb.append(num[index2++]);
    }
    for (int i = index1; i < str.length; i++) {
      sb.append(str[i]);
    }
    for (int i = index2; i < num.length; i++) {
      sb.append(num[i]);
    }
    return sb.toString();
  }

  @Override
  public String[] shareString(String str, int digit) {
    String[] result = new String[2];

    int length = str.length();
    if (digit >= length) {
      result[0] = str;
      result[1] = "";
    } else {
      int reminder = length % digit;
      result[0] = str.substring(0, length - reminder);
      result[1] = str.substring(length - reminder, length);
    }
    return result;
  }

  @Override
  public CommonResponseDto generate(String url, boolean removeTag, int digit) {
    String str = getHtmlByUrl(url, removeTag);
    str = extractionStringOrNumber(str);
    int[] numArray = extractionSortNumber(str);
    String[] stringArray = extractionSortString(str);
    String result = mergeString(stringArray, numArray);
    String[] resultArray = shareString(result, digit);
    return CommonResponseDto.builder().result(
        Result.builder().entry(
            ApiResult.builder().share(resultArray[0])
                .remainder(resultArray[1]).build()
        ).build()
    ).build();
  }
}
