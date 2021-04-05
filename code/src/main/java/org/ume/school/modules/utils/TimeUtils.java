package org.ume.school.modules.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Django on 2017/7/23.
 */
public class TimeUtils {

    private static List<TimePK> timePKs = new ArrayList<>();

    static {
        timePKs.add(new TimePK(60L, "1分钟前"));
        timePKs.add(new TimePK(5 * 60L, "5分钟前"));
        timePKs.add(new TimePK(10 * 60L, "10分钟前"));
        timePKs.add(new TimePK(30 * 60L, "30分钟前"));
        timePKs.add(new TimePK(60 * 60L, "1小时前"));
        timePKs.add(new TimePK(2 * 60 * 60L, "2小时前"));
        timePKs.add(new TimePK(24 * 60 * 60L, "1天前"));
        timePKs.add(new TimePK(2 * 24 * 60 * 60L, "2天前"));
        timePKs.add(new TimePK(3 * 24 * 60 * 60L, ""));
    }

    /**
     * 转化成 多少分钟前，多少天前的模式
     *
     * @return
     */
    public static String parseNLPattern(Date date) {
        String st = "刚刚";
        long time = date.getTime();
        long c = System.currentTimeMillis();
        long s = (c - time) / 1000;
        for (int m = timePKs.size() - 1; m >= 0; m--) {
            TimePK pk = timePKs.get(m);
            if (s > pk.getP()) {
                st = pk.getK();
                break;
            }
        }
        if (StringUtils.isEmpty(st)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            st = sdf.format(date);
        }
        return st;
    }

    static class TimePK {
        private String k;
        private long p;

        public TimePK(long p, String k) {
            this.p = p;
            this.k = k;
        }

        public String getK() {
            return k;
        }

        public void setK(String k) {
            this.k = k;
        }

        public long getP() {
            return p;
        }

        public void setP(long p) {
            this.p = p;
        }
    }

}
