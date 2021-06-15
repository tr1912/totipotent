import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-04-18 11:48
 * @packagename PACKAGE_NAME
 */
public class ZipedCharTest {

    public static String unZipChar(String sourceStr){
        if (sourceStr == null || sourceStr.length() == 0){
            return "";
        }

        List<String> match = new ArrayList<>();
        char[] sourceArray = sourceStr.toCharArray();
        for (char c : sourceArray) {
            if (c == ')'){
                int i = match.size();
                // 找左括号拼接
                StringBuilder pin = new StringBuilder();
                for (; i > 0; i--) {
                    if (match.get(i - 1).equals("(")){
                        match.remove(i - 1);
                        break;
                    }
                    // 为了防止后面反转时候倒序，提前先反转
                    StringBuilder sb = new StringBuilder();
                    sb.append(match.get(i - 1));
                    pin.append(sb.reverse().toString());
                    match.remove(i - 1);
                }
                match.add(pin.reverse().toString());
            }
            else if (NumberUtils.isDigits(String.valueOf(c))){
                // 获取需要扩展的字符 就是list里面最后存的
                String numericSir = match.get(match.size() - 1);
                StringBuilder pin = new StringBuilder();
                for (int i = 0; i < (c - '0'); i++) {
                    pin.append(numericSir);
                }
                match.set(match.size() - 1, pin.toString());
            }else {
                match.add(String.valueOf(c));
            }
        }
        if (match.size() > 0){
            StringBuilder pin = new StringBuilder();
            for (String s : match) {
                pin.append(s);
            }
            return pin.toString();
        }else {
            return "";
        }
    }

    public static void main(String[] args) {
        String sourceStr = "((AB)2D)2C";
        System.out.println(unZipChar(sourceStr));
    }
}
