import com.alibaba.excel.util.CollectionUtils;
import com.google.common.collect.Maps;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.wx.lab.view.dto.MatchResultDTO;
import com.wx.lab.view.dto.RegxPatternDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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

    private static final String REPLACE_RULE = "MG:mg,毫克:mg,KG:kg,千克:kg,G:g,克:g,ML:ml,毫升:ml,L:l,升:l,MM:mm,毫米:mm,CM:cm,厘米:cm,M:米,m:米,×:*,片:s,丸:s,粒:s";

    @Test
    public void testRegxMatch() {
        String agentMatchStr = "15mg*12片";
        List<String> esMatchStrs = new ArrayList<>(Arrays.asList(
                "15mg*6s*2板",
                "15G*6s*3板",
                "15MG*12s*2板",
                "30MG*6s*1板",
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
        executeEl(matchResultDTO.getMatcher(), matchResultDTO.getPattern());
        for (Map.Entry<String, MatchResultDTO> stringMatcherEntry : stringMatcherMap.entrySet()) {
            if (stringMatcherEntry.getValue() != null) {
                MatchResultDTO matchResult = stringMatcherEntry.getValue();
                Matcher match = matchResult.getMatcher();
                RegxPatternDTO pattern = matchResult.getPattern();
                executeEl(match, pattern);
            }
        }
    }

    /**
     * 可以转换数值类型的转换数值类型
     *
     * @param group
     * @return
     */
    private Object convertInteger(String group){
        Map<String, String> productReplaceRuleMap = getProductReplaceRuleMap();
        if (StringUtils.isEmpty(group)){
            return "";
        }
        if (NumberUtils.isDigits(group)){
            return Double.valueOf(group);
        }
        // 替换部分规定字符
        for (Map.Entry<String, String> entry : productReplaceRuleMap.entrySet()) {
            if (group.equals(entry.getKey())){
                group = group.replaceAll(entry.getKey(), entry.getValue());
            }
        }
        return group.toLowerCase(Locale.ROOT);
    }

    /**
     * 执行el语句合并
     *
     * @param match
     * @param pattern
     * @return
     */
    private Object executeEl(Matcher match, RegxPatternDTO pattern){
        // 提取参数
        Map<String,Object> env = new HashMap<>();
        for (int i = 1; i <= match.groupCount(); i++) {
            env.put("a" + i,convertInteger(match.group(i)));
        }
        log.info("当前运算表达式：{}", pattern.getElExpression());
        Object res = AviatorExecuteEl(pattern.getElExpression(), env);
        log.info(match.group(0) + "=>" + res.toString());
        return res;
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
        return new ArrayList<>(Arrays.asList(
                RegxPatternDTO.builder()
                        .index(1)
                        .pattern("^(\\d+(\\.\\d+)?)(mg|g|MG|G)\\*([\\d]+)(s|S|片|粒|丸)(/(瓶|盒))?$")
                        .elExpression("'#{a3==\"g\"?a1*1000:a1}' + '#{a3==\"g\"?\"mg\":a3}*#{a4}#{a5}|' + '##'")
                        .build(),
                RegxPatternDTO.builder()
                        .index(2)
                        .pattern("^(\\d+(\\.\\d+)?)(mg|g|MG|G)\\*([\\d]+)(s|S|片|粒|丸)(/(板))?\\*([\\d]+)(板)$")
                        .elExpression("'#{a3==\"g\"?a1*1000:a1}' + '#{a3==\"g\"?\"mg\":a3}*#{a4*a8}#{a5}|#{a8}#{a9}'")
                        .build(),
                RegxPatternDTO.builder()
                        .index(3)
                        .pattern("^(\\d+(\\.\\d+)?)(mg|g|MG|G)\\*([\\d]+)(s|S|片|粒|丸)(/(袋))?\\*([\\d]+)(袋)$")
                        .elExpression("'#{a3==\"g\"?a1*1000:a1}' + '#{a3==\"g\"?\"mg\":a3}*#{a4*a8}#{a5}|#{a8}#{a9}'")
                        .build(),
                RegxPatternDTO.builder()
                        .index(4)
                        .pattern("^([\\d]+)(s|S|片|粒|丸)$")
                        .elExpression("'#{a1}#{a2}|' + '##'")
                        .build(),
                RegxPatternDTO.builder()
                        .index(5)
                        .pattern("^([\\d]+)(s|S|片|粒|丸)\\*([\\d]+)(板)$")
                        .elExpression("'#{a1*a3}#{a2}|#{a3}#{a4}'")
                        .build(),
                RegxPatternDTO.builder()
                        .index(6)
                        .pattern("^([\\d]+)(s|S|片|粒|丸)\\*([\\d]+)(袋)$")
                        .elExpression("'#{a1*a3}#{a2}|#{a3}#{a4}'")
                        .build(),
                RegxPatternDTO.builder()
                        .index(7)
                        .pattern("^(\\d+(\\.\\d+)?)(ml|ML|g|G)(/(盒|袋|瓶|支|罐))?$")
                        .elExpression("'#{a1}#{a3}'")
                        .build()
        ));
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
            for (int i = 0; i <= m.groupCount(); i++) {
                log.info(m.group(i));
            }
        }
        return m;
    }

    /**
     * 解析El语句
     *
     * @param el 语句
     * @param ctx 环境变量
     * @return
     */
    private Object AviatorExecuteEl(String el, Map<String, Object> ctx){
        Expression exp = AviatorEvaluator.compile(el);
        return exp.execute(ctx);
    }

    /**
     * 获得替换分组
     *
     * @return
     */
    private Map<String, String> getProductReplaceRuleMap() {
        if (StringUtils.isEmpty(REPLACE_RULE)) {
            return Maps.newHashMap();
        }
        String[] ruleArray = REPLACE_RULE.split(",");
        Map<String, String> ruleMap = Maps.newHashMap();
        for (String ruleEntry : ruleArray) {
            if (ruleEntry.contains(":")) {
                String[] ruleEntryArray = ruleEntry.split(":");
                ruleMap.put(ruleEntryArray[0], ruleEntryArray[1]);
            }
        }
        return ruleMap;
    }
}
