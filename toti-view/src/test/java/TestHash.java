import com.wx.lab.view.entity.PictureTaskThreeKeyDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangxiao
 * @email wangxiao@ybm100.com
 * @date 2021-04-22 22:44
 * @packagename PACKAGE_NAME
 */
public class TestHash {

    static final int hash(Object key) {
        int h;
        int i = h = key.hashCode();
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    public static void main(String[] args) {
        Map<PictureTaskThreeKeyDTO, Integer> newMap = getNewMap();
        Map<PictureTaskThreeKeyDTO, Integer> oldMap = getOldMap();
        newMap.forEach((key, value) -> {
            Integer integer = oldMap.get(key);
            System.out.println(integer);
        });
    }

    public static Map<PictureTaskThreeKeyDTO, Integer> getNewMap(){
        Map<PictureTaskThreeKeyDTO, Integer> returnMap = new HashMap<>();
        PictureTaskThreeKeyDTO threeKeyDTO = PictureTaskThreeKeyDTO.builder()
                .applyCode("001")
                .productCode("Y001")
                .approvalProcess(1)
                .build();
        returnMap.put(threeKeyDTO, 1);
        threeKeyDTO = PictureTaskThreeKeyDTO.builder()
                .applyCode("002")
                .productCode("Y002")
                .approvalProcess(1)
                .build();
        returnMap.put(threeKeyDTO, 2);
        threeKeyDTO = PictureTaskThreeKeyDTO.builder()
                .applyCode("003")
                .productCode("Y003")
                .approvalProcess(1)
                .build();
        returnMap.put(threeKeyDTO, 3);
        return returnMap;
    }

    public static Map<PictureTaskThreeKeyDTO, Integer> getOldMap(){
        Map<PictureTaskThreeKeyDTO, Integer> returnMap = new HashMap<>();
        PictureTaskThreeKeyDTO threeKeyDTO = PictureTaskThreeKeyDTO.builder()
                .applyCode("001")
                .productCode("Y001")
                .approvalProcess(1)
                .build();
        returnMap.put(threeKeyDTO, 1);
        threeKeyDTO = PictureTaskThreeKeyDTO.builder()
                .applyCode("002")
                .productCode("Y002")
                .approvalProcess(1)
                .build();
        returnMap.put(threeKeyDTO, 2);
        threeKeyDTO = PictureTaskThreeKeyDTO.builder()
                .applyCode("004")
                .productCode("Y004")
                .approvalProcess(1)
                .build();
        returnMap.put(threeKeyDTO, 4);
        return returnMap;
    }

    @Data
    @NoArgsConstructor
    static
    class User{

        private String name;

        private Integer age;
    }
}
