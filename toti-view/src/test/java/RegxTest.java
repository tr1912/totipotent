import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.util.CollectionUtils;
import com.google.common.collect.Maps;
import com.wx.lab.view.config.ExcelListener;
import com.wx.lab.view.dto.MatchResultDTO;
import com.wx.lab.view.dto.RegxPatternDTO;
import com.wx.lab.view.dto.PresentSpecDTO;
import com.wx.lab.view.dto.RegxSpecDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import util.CharUtil;
import util.ElAnalyseUtil;
import util.RegxMatchUtil;
import util.SpecStrMatchUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
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
        boolean flag = false;

        List<PresentSpecDTO> importList = importExcel();
        if (CollectionUtils.isEmpty(importList)){
            return;
        }
        List<RegxSpecDTO> matchSpecs = importList.stream()
                .filter(n->!StringUtils.isEmpty(n.getSpec()))
                .map(m->{
                    RegxSpecDTO regxSpecDTO = new RegxSpecDTO();
                    regxSpecDTO.setId(m.getPresentId());
                    regxSpecDTO.setSpec(m.getSpec().replace(" ", "").replace("**", "*"));
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



        Map<Integer, MatchResultDTO> stringMatchSpecMap = RegxMatchUtil.batchMatchBatch(matchSpecs, patterns);
        Map<Integer, MatchResultDTO> stringMatchMSpecMap = RegxMatchUtil.batchMatchBatch(matchMSpecs, patterns);
        // 所有匹配结果都为null，则失败
        if (stringMatchSpecMap.entrySet().stream().allMatch(m -> m.getValue() == null)) {
            assert flag;
            return;
        }
        List<PresentSpecDTO> exportList = importList.stream()
                .map(presentItem->{
                    MatchResultDTO specMatchRes = stringMatchSpecMap.get(presentItem.getPresentId());
                    MatchResultDTO middleSpecMatchRes = stringMatchMSpecMap.get(presentItem.getPresentId());
                    PresentSpecDTO specDTO = specMatch(presentItem, specMatchRes, middleSpecMatchRes);
                    if (!specDTO.getIsMatch()){
                        // 如果规格匹配没有成功
                        thirdNameMatch(specDTO, presentItem);
                    }
                    return specDTO;
                })
                .collect(Collectors.toList());

        String outFileName = "D:\\新品上报匹配只有规格不一致的明细-匹配结果.xlsx";
        EasyExcelFactory.write(outFileName, PresentSpecDTO.class).sheet("Sheet1").doWrite(exportList);
    }

    /**
     * 剂型包衣匹配方法
     *
     * @param specDTO
     * @param presentItem
     */
    private void thirdNameMatch(PresentSpecDTO specDTO, PresentSpecDTO presentItem){
        // 业务通用名
        String generalName = CharUtil.regularStr(presentItem.getGeneralName());
        // 业务规格
        String spec = CharUtil.regularStr(presentItem.getSpec().replace(" ", ""));
        // 中台规格
        String matchMiddleSpec = CharUtil.regularStr(presentItem.getMatchMiddleSpec());

        String attachGeneralName = RegxMatchUtil.regxGetBucket(generalName);
        if (StringUtils.isEmpty(attachGeneralName)){
            attachGeneralName = CharUtil.getLastAttachBySpace(generalName);
        }
        String attachSpec = RegxMatchUtil.regxGetBucket(spec);

    }


    /**
     * 规格匹配
     *
     * @param presentItem 当前匹配明细
     * @param specMatchRes 规格匹配结果
     * @param middleSpecMatchRes 中台规格匹配结果
     * @return
     */
    private PresentSpecDTO specMatch(PresentSpecDTO presentItem,
                                     MatchResultDTO specMatchRes,
                                     MatchResultDTO middleSpecMatchRes){
        PresentSpecDTO specDTO = new PresentSpecDTO();
        BeanUtils.copyProperties(presentItem, specDTO);
        specDTO.setMatchMiddleSpec("");
        specDTO.setMatchSpec("");
        specDTO.setMatchPackageUnit("");

        if (specMatchRes != null){
            Matcher matcher = specMatchRes.getMatcher();
            RegxPatternDTO pattern = specMatchRes.getPattern();
            Object spinSpec = ElAnalyseUtil.executeEl(matcher, pattern.getElExpression());
            specDTO.setMatchRegx(pattern.getIndex());
            if (!org.springframework.util.StringUtils.isEmpty(spinSpec)){
                specDTO.setMatchSpec(spinSpec.toString());
            }
            Object spinPackageUnit = ElAnalyseUtil.executeEl(matcher, pattern.getPackageUnitElExpress());
            if (!org.springframework.util.StringUtils.isEmpty(spinPackageUnit)){
                specDTO.setMatchPackageUnit(spinPackageUnit.toString());
            }
        }

        if (middleSpecMatchRes != null){
            Matcher matcher = middleSpecMatchRes.getMatcher();
            RegxPatternDTO pattern = middleSpecMatchRes.getPattern();
            Object spinSpec = ElAnalyseUtil.executeEl(matcher, pattern.getElExpression());
            specDTO.setMatchMiddleRegx(pattern.getIndex());
            if (!org.springframework.util.StringUtils.isEmpty(spinSpec)){
                specDTO.setMatchMiddleSpec(spinSpec.toString());
            }
        }
        specDTO.setIsMatch(SpecStrMatchUtil.matchSpec(specDTO.getMatchSpec(), specDTO.getMatchMiddleSpec()));
        return specDTO;
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
                        .pattern("^(\\d+(\\.\\d+)?)(mg|g|MG|G)\\*([\\d]+)(s|S|t|T|片|粒|丸|枚|袋)(/(瓶|盒|小盒|包))?$")
                        .elExpression("'#{a3==\"g\"?a1*1000:a1}' + '#{a3==\"g\"?\"mg\":a3}*#{a4}#{a5}|' + '##'")
                        .packageUnitElExpress("'#{a7}'")
                        .build(),
                RegxPatternDTO.builder()
                        .index(2)
                        .pattern("^(\\d+(\\.\\d+)?)(mg|g|MG|G)\\*([\\d]+)(s|S|t|T|片|粒|丸|枚)(/(板))?\\*([\\d]+)(板)(/(盒|小盒|包))?$")
                        .elExpression("'#{a3==\"g\"?a1*1000:a1}' + '#{a3==\"g\"?\"mg\":a3}*#{setScale(a4*a8,2)}#{a5}|#{a8}#{a9}'")
                        .packageUnitElExpress("'#{a11}'")
                        .build(),
                RegxPatternDTO.builder()
                        .index(3)
                        .pattern("^(\\d+(\\.\\d+)?)(mg|g|MG|G)\\*([\\d]+)(s|S|片|粒|丸|枚)(/(袋))?\\*([\\d]+)(袋)(/(盒|小盒|包))?$")
                        .elExpression("'#{a3==\"g\"?a1*1000:a1}' + '#{a3==\"g\"?\"mg\":a3}*#{setScale(a4*a8,2)}#{a5}|#{a8}#{a9}'")
                        .packageUnitElExpress("'#{a11}'")
                        .build(),
                RegxPatternDTO.builder()
                        .index(4)
                        .pattern("^([\\d]+)(s|S|t|T|片|粒|丸|枚|袋)(/(瓶|盒|小盒|包))?$")
                        .elExpression("'#{a1}#{a2}|'+'##'")
                        .packageUnitElExpress("'#{a4}'")
                        .build(),
                RegxPatternDTO.builder()
                        .index(5)
                        .pattern("^([\\d]+)(s|S|t|T|片|粒|丸|枚)(/(板))?\\*([\\d]+)(板)(/(盒|小盒|包))?$")
                        .elExpression("'#{setScale(a1*a5,2)}#{a2}|#{a5}#{a6}'")
                        .packageUnitElExpress("'#{a8}'")
                        .build(),
                RegxPatternDTO.builder()
                        .index(6)
                        .pattern("^([\\d]+)(s|S|t|T|片|粒|丸|枚)(/(袋))?\\*([\\d]+)(袋)(/(盒|小盒|包))?$")
                        .elExpression("'#{setScale(a1*a5, 2)}#{a2}|#{a5}#{a6}'")
                        .packageUnitElExpress("'#{a8}'")
                        .build(),
                RegxPatternDTO.builder()
                        .index(7)
                        .pattern("^(\\d+(\\.\\d+)?)(mg|MG|g|G)(/(盒|小盒|袋|瓶|支|罐))?$")
                        .elExpression("'#{a3==\"g\"?a1*1000:a1}' + '#{a3==\"g\"?\"mg\":a3}'")
                        .packageUnitElExpress("'#{a5}'")
                        .build(),
                RegxPatternDTO.builder()
                        .index(71)
                        .pattern("^(\\d+(\\.\\d+)?)(ml|ML|l|L)(/(盒|小盒|袋|瓶|支|罐))?$")
                        .elExpression("'#{a3==\"l\"?a1*1000:a1}' + '#{a3==\"l\"?\"ml\":a3}'")
                        .packageUnitElExpress("'#{a5}'")
                        .build(),
                RegxPatternDTO.builder()
                        .index(8)
                        .pattern("^(\\d+(\\.\\d+)?)(ml|ML|l|L)\\:(\\d+(\\.\\d+)?)(mg|g|MG|G)\\*([\\d]+)(支|瓶)(/(盒|小盒|包|袋))?$")
                        .elExpression("'#{a3==\"l\"?a1*1000:a1}' + '#{a3==\"l\"?\"ml\":a3}:'+'#{a6==\"g\"?a4*1000:a4}'+'#{a6==\"g\"?\"mg\":a6}*#{a7}#{a8}'")
                        .packageUnitElExpress("'#{a10}'")
                        .build(),
                RegxPatternDTO.builder()
                        .index(81)
                        .pattern("^(\\d+(\\.\\d+)?)(mg|g|MG|G)\\:(\\d+(\\.\\d+)?)(ml|ML|l|L)\\*([\\d]+)(支|瓶)(/(盒|小盒|包|袋))?$")
                        .elExpression("'#{a6==\"l\"?a4*1000:a4}' + '#{a6==\"l\"?\"ml\":a6}:'+'#{a3==\"g\"?a1*1000:a1}'+'#{a3==\"g\"?\"mg\":a3}*#{a7}#{a8}'")
                        .packageUnitElExpress("'#{a10}'")
                        .build(),
                RegxPatternDTO.builder()
                        .index(82)
                        .pattern("^(\\d+(\\.\\d+)?)(mg|g|MG|G)\\:(\\d+(\\.\\d+)?)(mg|g|MG|G)\\*([\\d]+)(s|S|t|T|粒|片|丸|枚)(/(盒|小盒|包|袋))?$")
                        .elExpression("'#{a3==\"g\"?a1*1000:a1}' + '#{a3==\"g\"?\"mg\":a3}:'+'#{a6==\"g\"?a4*1000:a4}'+'#{a6==\"g\"?\"mg\":a6}*#{a7}#{a8}|##'")
                        .packageUnitElExpress("'#{a10}'")
                        .build(),
                RegxPatternDTO.builder()
                        .index(83)
                        .pattern("^(\\d+(\\.\\d+)?)(mg|g|MG|G)\\:(\\d+(\\.\\d+)?)(mg|g|MG|G)\\*([\\d]+)(s|S|t|T|粒|片|丸|枚)\\*([\\d]+)(板)(/(盒|小盒|包|袋))?$")
                        .elExpression("'#{a3==\"g\"?a1*1000:a1}' + '#{a3==\"g\"?\"mg\":a3}:'+'#{a6==\"g\"?a4*1000:a4}'+'#{a6==\"g\"?\"mg\":a6}*#{setScale(a7*a9, 2)}#{a8}|#{a9}#{a10}'")
                        .packageUnitElExpress("'#{a12}'")
                        .build(),
                RegxPatternDTO.builder()
                        .index(811)
                        .pattern("^(\\d+(\\.\\d+)?)(ml|ML|l|L)\\:(\\d+(\\.\\d+)?)(mg|g|MG|G)(/(盒|小盒|包|袋))?$")
                        .elExpression("'#{a3==\"l\"?a1*1000:a1}' + '#{a3==\"l\"?\"ml\":a3}:'+'#{a6==\"g\"?a4*1000:a4}'+'#{a6==\"g\"?\"mg\":a6}'")
                        .packageUnitElExpress("'#{a8}'")
                        .build(),
                RegxPatternDTO.builder()
                        .index(812)
                        .pattern("^(\\d+(\\.\\d+)?)(mg|g|MG|G)\\:(\\d+(\\.\\d+)?)(ml|ML|l|L)(/(盒|小盒|包|袋))?$")
                        .elExpression("'#{a6==\"l\"?a4*1000:a4}' + '#{a6==\"l\"?\"ml\":a6}:'+'#{a3==\"g\"?a1*1000:a1}'+'#{a3==\"g\"?\"mg\":a3}'")
                        .packageUnitElExpress("'#{a8}'")
                        .build()
//                ,RegxPatternDTO.builder()
//                        .index(813)
//                        .pattern("(\\s+|\\(+)(.+)(\\s*|\\)+)")
//                        .elExpression("'#{a3==\"g\"?a1*1000:a1}' + '#{a3==\"g\"?\"mg\":a3}:'+'#{a6==\"g\"?a4*1000:a4}'+'#{a6==\"g\"?\"mg\":a6}|##'")
//                        .packageUnitElExpress("'#{a8}'")
//                        .build()
                ,RegxPatternDTO.builder()
                        .index(9)
                        .pattern("^(\\d+(\\.\\d+)?)(mg|g|MG|G)\\*([\\d]+)(支|瓶)(/(盒|小盒|包|袋))?$")
                        .elExpression("'#{a3==\"g\"?a1*1000:a1}' + '#{a3==\"g\"?\"mg\":a3}*#{a4}#{a5}'")
                        .packageUnitElExpress("'#{a7}'")
                        .build()
                ,RegxPatternDTO.builder()
                        .index(91)
                        .pattern("^(\\d+(\\.\\d+)?)(ml|ML|l|L)\\*([\\d]+)(支|瓶)(/(盒|小盒|包|袋))?$")
                        .elExpression("'#{a3==\"l\"?a1*1000:a1}' + '#{a3==\"l\"?\"ml\":a3}*#{a4}#{a5}'")
                        .packageUnitElExpress("'#{a7}'")
                        .build()
                ,RegxPatternDTO.builder()
                        .index(92)
                        .pattern("^(\\d+(\\.\\d+)?)(mm|cm|MM|CM)\\*([\\d]+)(支|瓶)(/(盒|小盒|包|袋|罐))?$")
                        .elExpression("'#{a3==\"cm\"?a1*10:a1}' + '#{a3==\"cm\"?\"mm\":a3}*#{a4}#{a5}'")
                        .packageUnitElExpress("'#{a7}'")
                        .build()
                ,RegxPatternDTO.builder()
                        .index(10)
                        .pattern("^(\\d+(\\.\\d+)?)(mg|g|MG|G)(/粒)\\*([\\d]+)(粒)(/(盒|小盒|包|袋))?$")
                        .elExpression("'#{a3==\"g\"?a1*1000:a1}' + '#{a3==\"g\"?\"mg\":a3}*#{a5}#{a6}|##'")
                        .packageUnitElExpress("'#{a8}'")
                        .build()
                ,RegxPatternDTO.builder()
                        .index(101)
                        .pattern("^(\\d+(\\.\\d+)?)(mg|g|MG|G)(/片)\\*([\\d]+)(片)(/(盒|小盒|包|袋))?$")
                        .elExpression("'#{a3==\"g\"?a1*1000:a1}' + '#{a3==\"g\"?\"mg\":a3}*#{a5}#{a6}|##'")
                        .packageUnitElExpress("'#{a8}'")
                        .build()
                ,RegxPatternDTO.builder()
                        .index(11)
                        .pattern("^(\\d+(\\.\\d+)?)(mm|cm|MM|CM)\\*(\\d+(\\.\\d+)?)(mm|cm|MM|CM)\\*([\\d]+)(片|贴)(/(盒|小盒|包|袋))?$")
                        .elExpression("'#{a3==\"cm\"?a1*10:a1}' + '#{a3==\"cm\"?\"mm\":a3}*' + '#{a6==\"cm\"?a4*10:a4}' + '#{a6==\"cm\"?\"mm\":a6}*#{a7}#{a8}|##'")
                        .packageUnitElExpress("'#{a10}'")
                        .build()
                ,RegxPatternDTO.builder()
                        .index(12)
                        .pattern("^(\\d+(\\.\\d+)?)(mm|cm|MM|CM)\\*(\\d+(\\.\\d+)?)(mm|cm|MM|CM)\\*([\\d]+)(片|贴)\\*([\\d]+)(袋)(/(盒|小盒|包))?$")
                        .elExpression("'#{a3==\"cm\"?a1*10:a1}' + '#{a3==\"cm\"?\"mm\":a3}*' + '#{a6==\"cm\"?a4*10:a4}' + '#{a6==\"cm\"?\"mm\":a6}*#{setScale(a7*a9, 2)}#{a8}|#{a9}#{a10}'")
                        .packageUnitElExpress("'#{a12}'")
                        .build()
                ,RegxPatternDTO.builder()
                        .index(13)
                        .pattern("^(.+)(ug|μg)(.+)$")
                        .elExpression("'#{a1}μg#{a3}'")
                        .build()
        ));
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


