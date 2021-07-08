package util;

import com.alibaba.excel.util.CollectionUtils;
import com.wx.lab.view.dto.MatchResultDTO;
import com.wx.lab.view.dto.RegxPatternDTO;
import com.wx.lab.view.dto.RegxSpecDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-07-05 0005 11:21
 * Project totipotent
 */
@Slf4j
public class RegxMatchUtil {

    /**
     * 多条匹配
     *
     * @param targets  目标值list
     * @param patterns 正则表达式list
     * @return 返回值定义：key：target，value：匹配到的matcher
     */
    public static Map<Integer, MatchResultDTO> batchMatchBatch(List<RegxSpecDTO> targets, List<RegxPatternDTO> patterns) {
        Map<Integer, MatchResultDTO> matcherMap = new LinkedHashMap<>();

        if (CollectionUtils.isEmpty(targets) || CollectionUtils.isEmpty(patterns)) {
            return matcherMap;
        }
        for (RegxSpecDTO target : targets) {
            matcherMap.put(target.getId(), singleMatchBatch(target.getSpec(), patterns));
        }
        return matcherMap;
    }

    /**
     * 多值匹配，一个字符匹配多个正则
     *
     * @param target   目标值
     * @param patterns 正则表达式list
     * @return
     */
    public static MatchResultDTO singleMatchBatch(String target, List<RegxPatternDTO> patterns) {
        if (CollectionUtils.isEmpty(patterns)) {
            return null;
        }
        List<MatchResultDTO> matcherList = new ArrayList<>();
        for (RegxPatternDTO pattern : patterns) {
            Matcher matcher = regxMatch(target, pattern.getPattern());
            if (matcher.matches()) {
                log.info("字符：{}匹配到了正则，匹配到的正则index：{}", target, pattern.getIndex());
                matcherList.add(
                        MatchResultDTO.builder()
                                .matcher(matcher)
                                .pattern(pattern)
                                .build()
                );
            }
        }
        // 保证唯一匹配
        if (CollectionUtils.isEmpty(matcherList) || matcherList.size() > 1) {
            if (matcherList.size()>1){
                List<RegxPatternDTO> patters = matcherList.stream().map(MatchResultDTO::getPattern).collect(Collectors.toList());
                List<Integer> indexs = patters.stream().map(RegxPatternDTO::getIndex).collect(Collectors.toList());
                log.info("匹配到了多个regx, {}", indexs);
            }
            return null;
        } else {
            return matcherList.get(0);
        }
    }

    /**
     * 通过正则匹配括号，拿到最后一个括号的值
     *
     * @param source
     * @return
     */
    public static String regxGetBucket(String source){
        if (StringUtils.isEmpty(source)){
            return "";
        }
        String pattern = "\\((.+?)\\)";
        Matcher matcher = regxMatch(source, pattern);
        if (!matcher.find()){
            return "";
        }
        int matcher_start = 0;
        // 获取最后一个括号中的内容
        String res = "";
        while (matcher.find(matcher_start)){
            res = matcher.group(0);
            matcher_start = matcher.end();
        }
        return res;
    }

    /**
     * 正则匹配方法
     *
     * @param target  目标值
     * @param pattern 正则表达式
     * @return
     */
    private static Matcher regxMatch(String target, String pattern) {
        Pattern r = Pattern.compile(pattern, Pattern.MULTILINE);
        Matcher m = r.matcher(target);
        if (m.matches()) {
            for (int i = 0; i <= m.groupCount(); i++) {
                log.info(m.group(i));
            }
        }
        if (m.find()){
            int matcher_start = 0;
            while (m.find(matcher_start)){
                log.info(m.group(0));
                matcher_start = m.end();
            }
        }
        return m;
    }

    public static void main(String args[]) {
        String str = "60片 (薄膜衣片)(是副科级)";
        RegxPatternDTO build = RegxPatternDTO.builder()
                .index(1)
                .pattern("\\((.+?)\\)")
                .elExpression("")
                .build();
        Matcher m = regxMatch(str, build.getPattern());
        System.out.println(m.find());
    }
}
