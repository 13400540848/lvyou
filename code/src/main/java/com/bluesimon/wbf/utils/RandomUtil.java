package com.bluesimon.wbf.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 随机数辅助类
 */
public class RandomUtil {

    public RandomUtil() {

    }

    public static String createNumber(int size) {
        Random random = new Random();
        String result = "";
        for (int i = 0; i < size; i++) {
            result += random.nextInt(10);
        }
        return result;
    }
    
    public static String createOrderId() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String result = df.format(new Date());// new Date()为获取当前系统时间
        return result + createNumber(6);
    }
}
