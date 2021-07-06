package util;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.AviatorEvaluatorInstance;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Before;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-07-05 0005 11:24
 * Project totipotent
 */
@Slf4j
public class ElAnalyseUtil {

    protected static AviatorEvaluatorInstance instance = AviatorEvaluator.newInstance();

    /**
     * 执行el语句合并
     *
     * @param match 表达式
     * @param elExpression 表达式
     * @return
     */
    public static Object executeEl(Matcher match, String elExpression){
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
     * 可以转换数值类型的转换数值类型
     *
     * @param group
     * @return
     */
    private static Object convertDigits(String group){
        if (StringUtils.isEmpty(group)){
            return "";
        }
        if (NumberUtils.isParsable(group)){
            return new BigDecimal(group).setScale(2, RoundingMode.HALF_UP);
        }
        return group.toLowerCase(Locale.ROOT);
    }

    /**
     * 解析El语句
     *
     * @param el 语句
     * @param ctx 环境变量
     * @return
     */
    private static Object AviatorExecuteEl(String el, Map<String, Object> ctx){
        instance.addFunction(new ElAnalyseUtil.SetScaleFunction());
        Expression exp = instance.compile(el);
        return exp.execute(ctx);
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
