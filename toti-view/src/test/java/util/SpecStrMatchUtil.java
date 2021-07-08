package util;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-06-17 0017 15:16
 * Project product-interface-web
 */
public class SpecStrMatchUtil {

    /**
     * 规格对比匹配
     *
     * @param spec       原规格
     * @param middleSpec 中台规格
     * @return 返回是否成功布尔
     */
    public static boolean matchSpec(String spec, String middleSpec) {
        if (org.apache.commons.lang.StringUtils.isEmpty(spec) || org.apache.commons.lang.StringUtils.isEmpty(middleSpec)) {
            return false;
        }
        String[] specSplits = spec.split("\\|");
        String[] middleSpecSplits = middleSpec.split("\\|");
        int s = specSplits.length;
        int j = middleSpecSplits.length;
        int max = Math.max(s, j);
        boolean flag = true;

        for (int i = 0; i < max; i++) {
            String str1 = i > (s - 1) ? specSplits[s - 1] : specSplits[i];
            String str2 = i > (j - 1) ? middleSpecSplits[j - 1] : middleSpecSplits[i];
            flag = flag && partEquals(str1, str2);
        }

        return flag;
    }

    /**
     * 字符按照 | 分组匹配
     *
     * @param str1 字符1
     * @param str2 字符2
     * @return 返回是否成功
     */
    private static boolean partEquals(String str1, String str2) {
        if (org.apache.commons.lang.StringUtils.isEmpty(str1) || org.apache.commons.lang.StringUtils.isEmpty(str2)) {
            return false;
        }
        if ("##".equals(str1) || "##".equals(str2)) {
            return true;
        }
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        if (chars1.length != chars2.length) {
            return false;
        }
        boolean flag = true;
        for (int i = 0; i < chars1.length; i++) {
            flag = flag && charEquals(chars1[i], chars2[i]);
        }
        return flag;
    }

    /**
     * 单个字符char匹配
     *
     * @param char1 字符1
     * @param char2 字符2
     * @return 返回是否成功
     */
    private static boolean charEquals(char char1, char char2) {
        // "s"与"片|粒|丸|枚|t"等价
        if (char1 == 's') {
            if (char2 == '粒' || char2 == '片' || char2 == '丸' || char2 == '枚' || char2 == 't') {
                return true;
            }
        }
        if (char2 == 's') {
            if (char1 == '粒' || char1 == '片' || char1 == '丸' || char1 == '枚' || char1 == 't') {
                return true;
            }
        }
        // t 与 片等价
        if (char1 == 't'){
            if (char2 == '片'){
                return true;
            }
        }
        if (char2 == 't'){
            if (char1 == '片'){
                return true;
            }
        }
        return char1 == char2;
    }
}
