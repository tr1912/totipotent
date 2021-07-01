import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.util.CollectionUtils;
import com.google.common.collect.Maps;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.AviatorEvaluatorInstance;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;
import com.wx.lab.view.config.ExcelListener;
import com.wx.lab.view.dto.MatchResultDTO;
import com.wx.lab.view.dto.RegxPatternDTO;
import com.wx.lab.view.dto.PresentSpecDTO;
import com.wx.lab.view.dto.RegxSpecDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-06-10 14:44
 * @packagename PACKAGE_NAME
 */
@Slf4j
public class RegxTest {

    private static final String REPLACE_RULE = "MG:mg,毫克:mg,KG:kg,千克:kg,G:g,克:g,ML:ml,毫升:ml,L:l,升:l,MM:mm,毫米:mm,CM:cm,厘米:cm,M:米,m:米,×:*,片:s,丸:s,粒:s";

    protected AviatorEvaluatorInstance instance;

    @Before
    public void setup() {
        this.instance = AviatorEvaluator.newInstance();
    }

    @Test
    public void testDigit(){
        String num = "0.35";
        boolean digits = NumberUtils.isParsable(num);
        log.info(digits + "");
    }

    private List<PresentSpecDTO> importExcel() throws FileNotFoundException {
        String fileName = "D:\\新品上报匹配只有规格不一致的明细-模板.xlsx";
        File file = new File(fileName);
        InputStream in = new FileInputStream(file);
        List<PresentSpecDTO> list = new ArrayList<>();
        EasyExcelFactory.read(in, PresentSpecDTO.class, new ExcelListener<PresentSpecDTO>(list::addAll)).sheet().doRead();
        return list;
    }

    @Test
    public void testRegxMatch() throws FileNotFoundException {
        List<RegxPatternDTO> patterns = getPatterns();
//        String agentMatchStr = "15mg*12片";
//        MatchResultDTO matchResultDTO = singleMatchBatch(agentMatchStr, patterns);
        boolean flag = false;
//        if (matchResultDTO == null) {
//            assert flag;
//            return;
//        }
//        List<String> esMatchStrs = new ArrayList<>(Arrays.asList(
//                "15mg*6s*2板",
//                "15G*6.5s*3板",
//                "15MG*12s*2板",
//                "30MG*6s*1板",
//                "75mg*7片/板*4板",
//                "75mg*7s*4板",
//                "400ml/盒",
//                "400ml",
//                "200丸",
//                "200s 浓缩丸",
//                "24粒",
//                "12s*2板"
//        ));

        List<PresentSpecDTO> importList = importExcel();
        if (CollectionUtils.isEmpty(importList)){
            return;
        }
        List<RegxSpecDTO> matchSpecs = importList.stream()
                .filter(n->!StringUtils.isEmpty(n.getSpec()))
                .map(m->{
                    RegxSpecDTO regxSpecDTO = new RegxSpecDTO();
                    regxSpecDTO.setId(m.getPresentId());
                    regxSpecDTO.setSpec(m.getSpec().replace(" ", ""));
                    return regxSpecDTO;
                })
                .collect(Collectors.toList());
        List<RegxSpecDTO> matchMSpecs = importList.stream()
                .filter(n->!StringUtils.isEmpty(n.getMiddleSpec()))
                .map(m->{
                    RegxSpecDTO regxSpecDTO = new RegxSpecDTO();
                    regxSpecDTO.setId(m.getPresentId());
                    regxSpecDTO.setSpec(m.getMiddleSpec());
                    return regxSpecDTO;
                })
                .collect(Collectors.toList());



        Map<Integer, MatchResultDTO> stringMatchSpecMap = batchMatchBatch(matchSpecs, patterns);
        Map<Integer, MatchResultDTO> stringMatchMSpecMap = batchMatchBatch(matchMSpecs, patterns);
        // 所有匹配结果都为null，则失败
        if (stringMatchSpecMap.entrySet().stream().allMatch(m -> m.getValue() == null)) {
            assert flag;
            return;
        }
        List<PresentSpecDTO> exportList = importList.stream()
                .map(presentItem->{
                    PresentSpecDTO specDTO = new PresentSpecDTO();
                    BeanUtils.copyProperties(presentItem, specDTO);
                    specDTO.setMatchMiddleSpec("");
                    specDTO.setMatchSpec("");
                    specDTO.setMatchPackageUnit("");
                    MatchResultDTO specMatchRes = stringMatchSpecMap.get(presentItem.getPresentId());
                    if (specMatchRes != null){
                        Matcher matcher = specMatchRes.getMatcher();
                        RegxPatternDTO pattern = specMatchRes.getPattern();
                        Object spinSpec = executeEl(matcher, pattern.getElExpression());
                        specDTO.setMatchRegx(pattern.getIndex());
                        if (!org.springframework.util.StringUtils.isEmpty(spinSpec)){
                            specDTO.setMatchSpec(spinSpec.toString());
                        }
                        Object spinPackageUnit = executeEl(matcher, pattern.getPackageUnitElExpress());
                        if (!org.springframework.util.StringUtils.isEmpty(spinPackageUnit)){
                            specDTO.setMatchPackageUnit(spinPackageUnit.toString());
                        }
                    }
                    MatchResultDTO middleSpecMatchRes = stringMatchMSpecMap.get(presentItem.getPresentId());
                    if (middleSpecMatchRes != null){
                        Matcher matcher = middleSpecMatchRes.getMatcher();
                        RegxPatternDTO pattern = middleSpecMatchRes.getPattern();
                        Object spinSpec = executeEl(matcher, pattern.getElExpression());
                        specDTO.setMatchMiddleRegx(pattern.getIndex());
                        if (!org.springframework.util.StringUtils.isEmpty(spinSpec)){
                            specDTO.setMatchMiddleSpec(spinSpec.toString());
                        }
                    }
                    specDTO.setIsMatch(matchSpec(specDTO.getMatchSpec(), specDTO.getMatchMiddleSpec()));
                    return specDTO;
                })
                .collect(Collectors.toList());

        String outFileName = "D:\\新品上报匹配只有规格不一致的明细-匹配结果.xlsx";
        EasyExcelFactory.write(outFileName, PresentSpecDTO.class).sheet("Sheet1").doWrite(exportList);
//        executeEl(matchResultDTO.getMatcher(), matchResultDTO.getPattern());
//        for (Map.Entry<Integer, MatchResultDTO> stringMatcherEntry : stringMatchSpecMap.entrySet()) {
//            if (stringMatcherEntry.getValue() != null) {
//                MatchResultDTO matchResult = stringMatcherEntry.getValue();
//                Matcher match = matchResult.getMatcher();
//                RegxPatternDTO pattern = matchResult.getPattern();
//                executeEl(match, pattern.getElExpression());
//                executeEl(match, pattern.getPackageUnitElExpress());
//            }
//        }
    }

    private boolean matchSpec(String spec,String middleSpec){
        if (StringUtils.isEmpty(spec) || StringUtils.isEmpty(middleSpec)){
            return false;
        }
        String[] specSplits = spec.split("\\|");
        String[] middleSpecSplits = middleSpec.split("\\|");
        int s = specSplits.length;
        int j = middleSpecSplits.length;
        int max = Math.max(s, j);
        boolean flag = true;

        for (int i = 0; i < max; i++) {
            String str1 = i > (s-1)? specSplits[s-1] : specSplits[i];
            String str2 = i > (j-1) ? middleSpecSplits[j -1]: middleSpecSplits[i];
            flag = flag && partEquals(str1, str2);
        }

        return flag;
    }

    private boolean partEquals(String str1, String str2){
        if (StringUtils.isEmpty(str1) || StringUtils.isEmpty(str2)){
            return false;
        }
        if ("##".equals(str1) || "##".equals(str2)){
            return true;
        }
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        if (chars1.length != chars2.length){
            return false;
        }
        boolean flag = true;
        for (int i = 0; i < chars1.length; i++) {
            flag = flag && charEquals(chars1[i], chars2[i]);
        }
        return flag;
    }

    private boolean charEquals(char char1, char char2){
        if (char1 == 's'){
            if (char2 == '粒' || char2 == '片' || char2 == '丸' || char2 == '枚'){
                return true;
            }
        }
        if (char2 == 's'){
            if (char1 == '粒' || char1 == '片' || char1 == '丸' || char1 == '枚'){
                return true;
            }
        }
        return char1 == char2;
    }

    /**
     * 可以转换数值类型的转换数值类型
     *
     * @param group
     * @return
     */
    private Object convertDigits(String group){
        if (StringUtils.isEmpty(group)){
            return "";
        }
        if (NumberUtils.isParsable(group)){
            return new BigDecimal(group).setScale(2, RoundingMode.HALF_UP);
        }
        return group.toLowerCase(Locale.ROOT);
    }

    /**
     * 执行el语句合并
     *
     * @param match 表达式
     * @param elExpression 表达式
     * @return
     */
    private Object executeEl(Matcher match, String elExpression){
        if (StringUtils.isEmpty(elExpression)){
            return "";
        }
        // 提取参数
        Map<String,Object> env = new HashMap<>();
        log.info("==========================运算参数开始插入======================");
        for (int i = 1; i <= match.groupCount(); i++) {
            env.put("a" + i, convertDigits(match.group(i)));
            log.info("a{}：{}", i, convertDigits(match.group(i)));
        }
        log.info("==========================运算参数插入结束======================");
        log.info("当前运算表达式：{}", elExpression);
        Object res = AviatorExecuteEl(elExpression, env);
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
    private Map<Integer, MatchResultDTO> batchMatchBatch(List<RegxSpecDTO> targets, List<RegxPatternDTO> patterns) {
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
            if (matcherList.size()>1){
                log.info("匹配到了多个regx");
            }
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
                        .pattern("^(\\d+(\\.\\d+)?)(mg|g|MG|G)\\*([\\d]+)(s|S|片|粒|丸|枚|袋)(/(瓶|盒))?$")
                        .elExpression("'#{a3==\"g\"?a1*1000:a1}' + '#{a3==\"g\"?\"mg\":a3}*#{a4}#{a5}|' + '##'")
                        .packageUnitElExpress("'#{a7}'")
                        .build(),
                RegxPatternDTO.builder()
                        .index(2)
                        .pattern("^(\\d+(\\.\\d+)?)(mg|g|MG|G)\\*([\\d]+)(s|S|片|粒|丸|枚|袋)(/(板))?\\*([\\d]+)(板)(/(盒|小盒|瓶|板|包))?$")
                        .elExpression("'#{a3==\"g\"?a1*1000:a1}' + '#{a3==\"g\"?\"mg\":a3}*#{setScale(a4*a8,2)}#{a5}|#{a8}#{a9}'")
                        .packageUnitElExpress("'#{a11}'")
                        .build(),
                RegxPatternDTO.builder()
                        .index(3)
                        .pattern("^(\\d+(\\.\\d+)?)(mg|g|MG|G)\\*([\\d]+)(s|S|片|粒|丸|枚|袋)(/(袋))?\\*([\\d]+)(袋)(/(盒|小盒|瓶|板|包))?$")
                        .elExpression("'#{a3==\"g\"?a1*1000:a1}' + '#{a3==\"g\"?\"mg\":a3}*#{setScale(a4*a8,2)}#{a5}|#{a8}#{a9}'")
                        .packageUnitElExpress("'#{a11}'")
                        .build(),
                RegxPatternDTO.builder()
                        .index(4)
                        .pattern("^([\\d]+)(s|S|片|粒|丸|枚|袋)(/(瓶|盒))?$")
                        .elExpression("'#{a1}#{a2}|'+'##'")
                        .packageUnitElExpress("'#{a4}'")
                        .build(),
                RegxPatternDTO.builder()
                        .index(5)
                        .pattern("^([\\d]+)(s|S|片|粒|丸|枚|袋)\\*([\\d]+)(板)(/(盒|小盒|瓶|板|包))?$")
                        .elExpression("'#{setScale(a1*a3,2)}#{a2}|#{a3}#{a4}'")
                        .packageUnitElExpress("'#{a6}'")
                        .build(),
                RegxPatternDTO.builder()
                        .index(6)
                        .pattern("^([\\d]+)(s|S|片|粒|丸|枚|袋)\\*([\\d]+)(袋)$")
                        .elExpression("'#{setScale(a1*a3, 2)}#{a2}|#{a3}#{a4}'")
                        .build(),
                RegxPatternDTO.builder()
                        .index(7)
                        .pattern("^(\\d+(\\.\\d+)?)(ml|ML|g|G)(/(盒|袋|瓶|支|罐))?$")
                        .elExpression("'#{a1}#{a3}'")
                        .packageUnitElExpress("'#{a5}'")
                        .build(),
                RegxPatternDTO.builder()
                        .index(8)
                        .pattern("^(\\d+(\\.\\d+)?)(ml|ML|mg|g|MG|G)\\:(\\d+(\\.\\d+)?)(mg|g|MG|G)\\*([\\d]+)(支|瓶)(/(盒|小盒|板|包))?$")
                        .elExpression("'#{a3==\"g\"?a1*1000:a1}' + '#{a3==\"g\"?\"mg\":a3}:'+'#{a6==\"g\"?a4*1000:a4}'+'#{a6==\"g\"?\"mg\":a6}*#{a7}#{a8}'")
                        .packageUnitElExpress("'#{a10}'")
                        .build()
//                RegxPatternDTO.builder()
//                        .index(9)
//                        .pattern("^(\\d+(\\.\\d+)?)(ml|ML|g|G)\\*(\\d+(\\.\\d+)?)(盒|袋|瓶|支|罐)?\\s*(\\(*([\\u4E00-\\u9FA5]+)\\)*)$")
//                        .elExpression("'#{a3==\"g\"?a1*1000:a1}' + '#{a3==\"g\"?\"mg\":a3}*#{a4}#{a6}|#{8}'")
//                        .build()
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
        this.instance.addFunction(new SetScaleFunction());
        Expression exp = instance.compile(el);
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

    static class SetScaleFunction extends AbstractFunction {
        @Override
        public AviatorObject call(Map<String, Object> env, AviatorObject inNum, AviatorObject scale) {
            Number numberValue = FunctionUtils.getNumberValue(inNum, env);
            Number scaleValue = FunctionUtils.getNumberValue(scale, env);
            Double v = numberValue.doubleValue();
            BigDecimal bd=new BigDecimal(v).setScale(scaleValue.intValue(), RoundingMode.HALF_UP);
            //setScale(保留位数,BigDecimal.ROUND_HALF_UP) 四舍五入
            //例如：setScale(1,BigDecimal.ROUND_HALF_UP) 如：2.33，则为2.3
            return new AviatorString(bd.toString());
        }

        public String getName() {
            return "setScale";
        }
    }
}


