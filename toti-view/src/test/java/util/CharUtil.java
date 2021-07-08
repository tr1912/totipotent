package util;

import org.springframework.util.StringUtils;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-07-07 0007 15:47
 * Project totipotent
 */
public class CharUtil {

    /**
     * 正常去掉空格和替换中文括号
     *
     * @param source
     * @return
     */
    public static String regularStr(String source){
        if (StringUtils.isEmpty(source)){
            return "";
        }
        return replaceCnBracket(dropSpace(source));
    }

    /**
     * 提取最后一个空格之后的字符
     *
     * @param source
     * @return
     */
    public static String getLastAttachBySpace(String source){
        if (org.apache.commons.lang.StringUtils.isEmpty(source)){
            return "";
        }
        String[] s = source.split(" ");
        if (s.length == 1){
            return "";
        }
        return s[s.length - 1];
    }

    public static String dropSpace(String source){
        if (StringUtils.isEmpty(source)){
            return "";
        }
        return source.trim();
    }

    public static String replaceCnBracket(String source){
        if (StringUtils.isEmpty(source)){
            return "";
        }
        return source.replace("（", "（")
                .replace("）", ")");
    }

    public static void main(String[] args) {
        String s = "askdf";
        String[] s1 = s.split(" ");
        System.out.println(s1.length);
    }
}
