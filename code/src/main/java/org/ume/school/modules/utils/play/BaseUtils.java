package org.ume.school.modules.utils.play;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.ume.school.modules.model.enums.PlayThreeRewardType;

/**
 * Created by Zz on 2018/10/25.
 */
public class BaseUtils {

    /**
     * 获取两个数组的交集
     * @return
     */
    public static List<String> getRetainList(String s1, String s2) {
        if(s1.isEmpty() || s2.isEmpty())
        	return null;
        String[] arr1 = s1.split(",");
        List<String> list1 = new ArrayList<String>();
        for(String str1 : arr1){
            list1.add(str1);
        }
        String[] arr2 = s2.split(",");
        List<String> list2 = new ArrayList<String>();
        for(String str2 : arr2){
            list2.add(str2);
        }
        try{
            list1.retainAll(list2);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return list1;
    }
    
    /**
     * 获取两个数组的交集数量
     * @return
     */
    public static int getRetainCount(String s1, String s2) {
    	List<String> arr = getRetainList(s1, s2);
        return arr.isEmpty()?0:arr.size();
    }
    
    /**
     * 获取新快3中了什么奖
     * @return
     */
    public static PlayThreeRewardType getTreeMoney(int userType, String userNum, String sysNum) {
    	int sysSum = getThreeSum(sysNum);
    	int userSum = 0;
//    	if(userType == PlayThreeRewardType.SUM_ALL.getValue()) {
//    		userSum = getThreeSum(userNum);
//    	}
    	//三同号
    	List<String> arrSys = Arrays.asList(new String[]{"111", "222", "333", "444", "555", "666"});
    	if(arrSys.contains(sysNum)) {
    		if(userType == PlayThreeRewardType.THREE_SAME_ALL.getValue()) {
    			return PlayThreeRewardType.THREE_SAME_ALL;
    		}
    	}
    	//
    	return PlayThreeRewardType.NO;
    }
    public static int getThreeSum(String num) {
    	int result = 0;
    	String[] arr = num.split(",");
    	for(String s : arr) {
    		result += Integer.parseInt(s);
    	}
        return result;
    }
    
    public static String createNumber(int size) {
        Random random = new Random();
        String result = "";
        for (int i = 0; i < size; i++) {
            result += random.nextInt(10);
        }
        return result;
    }
    
    
    /**
     * 获取周运行频率
     * @return
     */
    public static String getCronWeek(String s) {
        List<String> result = new ArrayList<String>();
        if(s.indexOf("1") >= 0){
            result.add("MON");
        }
        if(s.indexOf("2") >= 0){
            result.add("TUES");
        }
        if(s.indexOf("3") >= 0){
            result.add("WED");
        }
        if(s.indexOf("4") >= 0){
            result.add("THUR");
        }
        if(s.indexOf("5") >= 0){
            result.add("FRI");
        }
        if(s.indexOf("6") >= 0){
            result.add("SAT");
        }
        if(s.indexOf("7") >= 0){
            result.add("SUN");
        }
        return StringUtils.join(result, ",");
    }
    
    /**
     * 获取时间运行频率(21:00:00)
     * @return
     */
    public static String getCronTime(String s) {
        int hours = Integer.parseInt(s.substring(0,2));
        int minute = Integer.parseInt(s.substring(3,5));
        int second = Integer.parseInt(s.substring(6,8));
        return second+" " +minute + " " + hours;
    }
    
    /**
     * 获取分钟运行频率（23点前结束）
     * @return
     */
    public static String getCronMinute(Integer playRate, String startTime) {
    	int hours = Integer.parseInt(startTime.substring(0,2));
        int minute = Integer.parseInt(startTime.substring(3,5));
        //0 30-59/10 9-23 * * ?   每天9:30开始10分钟一次
        //return "0 "+minute+"-59/"+playRate+" "+hours+"-23 * * ?";
        
        // 每1分钟一次，判断没有数据就插入，有就开奖
        return "0 */1 * * * ?";
    }
    
    /**
     * 整数数组排序
     * @return
     */
    public static int[] orderArray(int[] arr){
        for(int i=0;i<arr.length-1;i++){
            for(int j=0;j<arr.length-1-i;j++){
                if(arr[j]>arr[j+1]){
                    int temp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                    }
                }
            }
        return arr;
    }
}
