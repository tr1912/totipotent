import com.alibaba.excel.util.CollectionUtils;
import com.wx.lab.view.dto.MatchResultDTO;
import com.wx.lab.view.dto.RegxPatternDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-06-10 14:44
 * @packagename PACKAGE_NAME
 */
@Slf4j
public class RegxTest {

    @Test
    public void testRegxMatch() {
        String agentMatchStr = "15mg*12片";
        List<String> esMatchStrs = new ArrayList<>();
        esMatchStrs.addAll(Arrays.asList(
                "15mg*6s*2板",
                "15mg*6s*3板",
                "15mg*12s*2板",
                "30mg*6s*1板",
                "75mg*7片/板*4板",
                "75mg*7s*4板",
                "400ml/盒",
                "400ml",
                "200丸",
                "200s 浓缩丸",
                "24粒",
                "12s*2板"
        ));
        List<RegxPatternDTO> patterns = getPatterns();
        MatchResultDTO matchResultDTO = singleMatchBatch(agentMatchStr, patterns);
        boolean flag = false;
        if (matchResultDTO == null) {
            assert flag;
            return;
        }
        Map<String, MatchResultDTO> stringMatcherMap = batchMatchBatch(esMatchStrs, patterns);
        // 所有匹配结果都为null，则失败
        if (stringMatcherMap.entrySet().stream().allMatch(m -> m.getValue() == null)) {
            assert flag;
            return;
        }
        for (Map.Entry<String, MatchResultDTO> stringMatcherEntry : stringMatcherMap.entrySet()) {
            if (stringMatcherEntry.getValue() != null) {
                MatchResultDTO matchResult = stringMatcherEntry.getValue();
                Matcher match = matchResult.getMatcher();
                RegxPatternDTO pattern = matchResult.getPattern();
                StringBuilder sb = new StringBuilder();

                for (int i = 0; i < match.groupCount(); i++) {
                    // 第一个不组合
                    if (i == 0) {
                        continue;
                    }
//                    if (i == match.groupCount()-1 && ma) {
//                        continue;
//                    }
                    if (i == 4) {
                        String base = match.group(i);
                        String ratio = match.group(8);
                        if (NumberUtils.isDigits(base) && NumberUtils.isDigits(ratio)) {
                            Integer baseNum = Integer.valueOf(base);
                            Integer ratioNum = Integer.valueOf(ratio);
                            sb.append(baseNum * ratioNum);
                        } else {
                            if (base != null) {
                                sb.append(base);
                            }
                        }
                        continue;
                    }
                    String group = match.group(i);
                    if (group != null) {
                        sb.append(group);
                    }
                }

                log.info(sb.toString());
            }
        }
    }

    /**
     * 多条匹配
     *
     * @param targets  目标值list
     * @param patterns 正则表达式list
     * @return 返回值定义：key：target，value：匹配到的matcher
     */
    private Map<String, MatchResultDTO> batchMatchBatch(List<String> targets, List<RegxPatternDTO> patterns) {
        Map<String, MatchResultDTO> matcherMap = new LinkedHashMap<>();

        if (CollectionUtils.isEmpty(targets) || CollectionUtils.isEmpty(patterns)) {
            return matcherMap;
        }
        for (String target : targets) {
            matcherMap.put(target, singleMatchBatch(target, patterns));
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
    private MatchResultDTO singleMatchBatch(String target, List<RegxPatternDTO> patterns) {
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
            return null;
        } else {
            return matcherList.get(0);
        }
    }

    /**
     * 获得所有的正则表达式
     * 正式的情况从数据库里面取
     *
     * @return
     */
    private List<RegxPatternDTO> getPatterns() {
        List<RegxPatternDTO> patterns = new ArrayList<>();
        patterns.addAll(Arrays.asList(
                RegxPatternDTO.builder()
                        .index(1)
                        .pattern("^(\\d+(\\.\\d+)?)(mg|g|MG|G)\\*([\\d]+)(s|S|片|粒|丸)(/(瓶|盒))?$")
                        .baseNum(4)
                        .ratioNum(8)
                        .joinLast(false)
                        .build(),
                RegxPatternDTO.builder()
                        .index(2)
                        .pattern("^(\\d+(\\.\\d+)?)(mg|g|MG|G)\\*([\\d]+)(s|S|片|粒|丸)(/(板))?\\*([\\d]+)(板)$")
                        .baseNum(4)
                        .ratioNum(8)
                        .joinLast(false)
                        .build(),
                RegxPatternDTO.builder()
                        .index(3)
                        .pattern("^(\\d+(\\.\\d+)?)(mg|g|MG|G)\\*([\\d]+)(s|S|片|粒|丸)(/(袋))?\\*([\\d]+)(袋)$")
                        .baseNum(4)
                        .ratioNum(8)
                        .joinLast(false)
                        .build(),
                RegxPatternDTO.builder()
                        .index(4)
                        .pattern("^([\\d]+)(s|S|片|粒|丸)$")
                        .baseNum(1)
                        .ratioNum(3)
                        .joinLast(true)
                        .build(),
                RegxPatternDTO.builder()
                        .index(5)
                        .pattern("^([\\d]+)(s|S|片|粒|丸)\\*([\\d]+)(板)$")
                        .baseNum(1)
                        .ratioNum(3)
                        .joinLast(false)
                        .build(),
                RegxPatternDTO.builder()
                        .index(6)
                        .pattern("^([\\d]+)(s|S|片|粒|丸)\\*([\\d]+)(袋)$")
                        .baseNum(1)
                        .ratioNum(3)
                        .joinLast(false)
                        .build(),
                RegxPatternDTO.builder()
                        .index(7)
                        .pattern("^(\\d+(\\.\\d+)?)(ml|ML|g|G)(/(盒|袋|瓶|支|罐))?$")
                        .baseNum(1)
                        .ratioNum(3)
                        .joinLast(true)
                        .build()
        ));
        return patterns;
    }

    /**
     * 正则匹配方法
     *
     * @param target  目标值
     * @param pattern 正则表达式
     * @return
     */
    private Matcher regxMatch(String target, String pattern) {
        Pattern r = Pattern.compile(pattern, Pattern.MULTILINE);
        Matcher m = r.matcher(target);
        System.out.println(m.matches());
        if (m.matches()) {
            for (int i = 0; i < m.groupCount(); i++) {
                log.info(m.group(i));
            }
        }
        return m;
    }
}
