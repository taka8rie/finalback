package xyz.taka8rie.finalback.utils;

import java.util.Random;
//生成指定长度随机字符串，用来给图片命名
public class StringUtils {
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random=new Random();
        StringBuffer stringBuffer=new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            stringBuffer.append(base.charAt(number));
        }
        return stringBuffer.toString();
    }
}
