package org.ume.school.modules.utils.play;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.ume.school.modules.model.entity.PlayThree;
import org.ume.school.modules.model.entity.PlayThreeReward;
import org.ume.school.modules.model.entity.UserPlayThreeInfo;
import org.ume.school.modules.model.enums.PlayThreeMode;
import org.ume.school.modules.model.enums.PlayThreeRewardType;
import org.ume.school.modules.model.enums.UserPlayThreeType;

/**
 * Created by Zz on 2018/10/25.
 */
public class ThreeUtils {
    
    /**
     * 抽奖
     * 暂定抽取10次胖3，剩余取最大利润
     * @return
     */
    public static int[] createNumber(PlayThree ps, List<PlayThreeReward> rewards, List<UserPlayThreeInfo> userPlays) {
        //暂定抽取10次
        int[] result = new int[3];
        double moneyResult = 0;
        for(int i=0;i<10;i++) {
            int[] sysNumber = createNumber();
            PlayThreeMode sysMode = getNumberMode(sysNumber);
            double money = 0;
            if(userPlays!=null && userPlays.size()>0 && rewards!=null && rewards.size()>0) {
                for(UserPlayThreeInfo userPlay : userPlays) { //算出收益
                    money += getMoney(ps, rewards, userPlay, sysNumber, sysMode);
                }
            }else {
                result = sysNumber;
                break;
            }
            if(money <= moneyResult){
                moneyResult = money;
                result = sysNumber;
            }
        }
        return result;
    }
    
    /**
     * 抽奖3个数（1-6）
     * @return
     */
    private static int[] createNumber() {     
        int[] arrResult = new int[3];
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            int r = random.nextInt(6) + 1;
            arrResult[i] = r;
        }
        return BaseUtils.orderArray(arrResult);
    }
    
    /**
     * 快3开奖结果模式
     * @return
     */
    public static PlayThreeMode getNumberMode(int[] sysNumber) {
        int num1 = sysNumber[0];
        int num2 = sysNumber[1];
        int num3 = sysNumber[2];
        String number = num1+""+num2+""+num3;
        if(num1 == num2 && num1 == num3){
            return PlayThreeMode.THREE_SAME;
        }
        else if(num1 != num2 && num1 != num3 && num2 != num3){
            List<String> arrSys = Arrays.asList(new String[]{"123", "234", "345", "456"});
            if(arrSys.contains(number)){
                return PlayThreeMode.THREE_LINK_ALL;
            }
            return PlayThreeMode.THREE_NO_SAME;
        }
        else{
            return PlayThreeMode.TWO_SAME;
        }
    }
    
    /**
     * 获取新快3中奖奖金
     * @return
     */
    public static double getMoney(PlayThree ps, List<PlayThreeReward> rewards, UserPlayThreeInfo userPlay, int[] sysNumber, PlayThreeMode sysMode) {
        PlayThreeRewardType p = getRewardType(ps, userPlay, sysNumber, sysMode);
        for(PlayThreeReward reward : rewards){
            if(reward.getRewardCode() == p.getValue()){
                return reward.getRewardTimes() * ps.getPerMoney();
            }
        }
        return (double)0;
    }
    
    /**
     * 获取新快3中了什么奖
     * @return
     */
    public static PlayThreeRewardType getRewardType(PlayThree ps, UserPlayThreeInfo userPlay, int[] sysNumber, PlayThreeMode sysMode) {
        String strSysNumber = sysNumber[0] + "" + sysNumber[1] + "" + sysNumber[2];
        //和值
        if(userPlay.getMode().intValue() == UserPlayThreeType.SUM.getValue()) {
            int sysSum = getThreeSum(sysNumber);
            int userSum = Integer.parseInt(userPlay.getNumber());
            if(userSum == sysSum){
                return getRewardType(sysSum);
            }else{
                return PlayThreeRewardType.NO;
            }
        }
        List<String> arrSys = Arrays.asList(new String[]{"111", "222", "333", "444", "555", "666"});
        //三同号通选
        if(userPlay.getMode().intValue() == UserPlayThreeType.THREE_SAME_ALL.getValue()) {
            if(sysMode == PlayThreeMode.THREE_SAME){
                return PlayThreeRewardType.THREE_SAME_ALL;
            }else{
                return PlayThreeRewardType.NO;
            }
        }
        //三同号单选
        if(userPlay.getMode().intValue() == UserPlayThreeType.THREE_SAME_SINGLE.getValue()) {
            if(strSysNumber.equals(userPlay.getNumber())){
                return PlayThreeRewardType.THREE_SAME_SINGLE;
            }else{
                return PlayThreeRewardType.NO;
            }
        }
        //三不同号
        if(userPlay.getMode().intValue() == UserPlayThreeType.THREE_NO_SAME.getValue()) {
            if(sysMode == PlayThreeMode.THREE_NO_SAME){
                if(strSysNumber.equals(userPlay.getNumber())){
                    return PlayThreeRewardType.THREE_NO_SAME;
                }else{
                    return PlayThreeRewardType.NO;
                }
            }
        }
        //三连号通选
        if(userPlay.getMode().intValue() == UserPlayThreeType.THREE_LINK_ALL.getValue()) {
            if(sysMode == PlayThreeMode.THREE_LINK_ALL){
                return PlayThreeRewardType.THREE_LINK_ALL;
            }else{
                return PlayThreeRewardType.NO;
            }
        }
        //二同号复选
        if(userPlay.getMode().intValue() == UserPlayThreeType.TWO_SAME_ALL.getValue()) {
            if(strSysNumber.contains(userPlay.getNumber())){
                return PlayThreeRewardType.TWO_SAME_ALL;
            }else{
                return PlayThreeRewardType.NO;
            }
        }
        //二同号单选
        if(userPlay.getMode().intValue() == UserPlayThreeType.TWO_SAME_SINGLE.getValue()) {
            if(sysMode == PlayThreeMode.TWO_SAME){
                if(strSysNumber.equals(userPlay.getNumber())){
                    return PlayThreeRewardType.TWO_SAME_SINGLE;
                }else{
                    return PlayThreeRewardType.NO;
                }
            }else{
                return PlayThreeRewardType.NO;
            }
        }
        //二不同号
        if(userPlay.getMode().intValue() == UserPlayThreeType.TWO_NO_SAME_ALL.getValue()) {
            if(strSysNumber.equals(userPlay.getNumber())){
                return PlayThreeRewardType.TWO_SAME_SINGLE;
            }else{
                return PlayThreeRewardType.NO;
            }
        }        
        return PlayThreeRewardType.NO;
    }
    private static PlayThreeRewardType getRewardType(int value){
        for(PlayThreeRewardType p : PlayThreeRewardType.values()){
            if(p.getValue() == value){
                return p;
            }
        }
        return PlayThreeRewardType.NO;
    }
    
    public static int getThreeSum(int[] num) {
    	int result = 0;
    	for(int s : num) {
    		result += s;
    	}
        return result;
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
}
