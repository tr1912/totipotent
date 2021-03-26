import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-03-05 19:51
 * @packagename PACKAGE_NAME
 */
public class StringTest {

    public static void main(String[] args) {

    }

    private boolean match(){
        Map<String, Map<String,String>> originalMap = new HashMap<>();
        String targetSearchPath =
                "1-23-2345-";
        String[] searchPathArr = targetSearchPath.split(",");
        List<String> searchPaths = Arrays.asList(searchPathArr);
        AtomicBoolean flag = new AtomicBoolean(false);
        searchPaths.stream()
        .filter(n->!StringUtils.isEmpty(n))
        .forEach(m->{
            List<String> splitKeys = Arrays.asList(StringUtils.split(m, "-"));
            splitKeys.stream()
                    .filter(p->!StringUtils.isEmpty(p))
                    .forEach(pm->{
                        if (originalMap.get("").containsKey(pm)){
                            flag.set(true);
                        }
                    });
        });


        for (String searchPath : searchPathArr) {
            String[] splitKeys = StringUtils.split(searchPath, "-");
            for (String splitKey : splitKeys) {
                boolean isContainSearchPath = originalMap.get("searchPaths").containsKey(splitKey);
                if (isContainSearchPath) {
                    return true;
                }
            }
        }
        return true;
    }
}
