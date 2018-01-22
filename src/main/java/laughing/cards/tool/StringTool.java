package laughing.cards.tool;

import java.util.Random;

public class StringTool {
    /**
     * 转换成指定位数的字符串，不够的用0补齐
     *
     * @param num  数字
     * @param size 位数
     * @return
     * @example convertNum(123, 6) <br/>
     * 结果为 000123
     */
    public static String convertNum(int num, int size) {
        String str = String.format("%0" + size + "d", num);
        return str;
    }

    /**
     * 获取验证码
     *
     * @return
     */
    public static String getVerificationCode() {
        Random random = new Random();
        int num = random.nextInt(999999);
        return convertNum(num, 6);
    }
    public static void main(String[] args){
        System.out.println(getVerificationCode());
    }
}
