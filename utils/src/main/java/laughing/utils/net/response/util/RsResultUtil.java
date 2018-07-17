package laughing.utils.net.response.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import laughing.utils.global.ErrorEnum;

/**
 * 返回数据工具
 *
 * @author laughing
 * @date 2018-03-08 16:01:25
 */
public class RsResultUtil {

    /**
     * 检查返回是否成功
     *
     * @param jsonStr
     * @return
     */
    public static boolean isSuccess(String jsonStr) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        String code = jsonObject.getString("code");
        if (code.equals(ErrorEnum.SUCCESS.getErrorCode())) {
            return true;
        }
        return false;
    }

    /**
     * 返回《返回体》
     *
     * @param json
     * @return
     */
    public static String getResultBody(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        return jsonObject.getString("resultBody");
    }
}
