package org.ume.school.modules.utils.play;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.ume.school.modules.model.entity.PlaySeven;
import org.ume.school.modules.model.entity.PlaySevenReward;
import org.ume.school.modules.model.entity.UserPlaySevenInfo;
import org.ume.school.modules.model.enums.PlaySevenRewardMode;
import org.ume.school.modules.model.enums.PlaySevenRewardType;

/**
 * Created by Zz on 2018/10/25.
 */
public class SevenUtils {
    
	/**
     * 抽奖
     * 暂定抽取10次双色球，排除一二等奖（奖池不够时），剩余取最大利润
     * @return
     */
	public static String[] createNumber(PlaySeven ps, List<PlaySevenReward> rewards, List<UserPlaySevenInfo> userPlays) {
		//暂定抽取10次双色球
		String[] result = new String[2];
		double moneyResult = 0;
		for(int i=0;i<10;i++) {
			String sysSix = createSix();
			String sysOne = createOne();
			double money = 0;
			if(userPlays!=null && userPlays.size()>0 && rewards!=null && rewards.size()>0) {
			    boolean isReward = false;
			    for(PlaySevenReward reward : rewards){
			        if(reward.getRewardMode() == PlaySevenRewardMode.DIFFERENCE.getValue()){ //浮动金额
			            if(reward.getRequireMoney() <=  ps.getAllMoney()){ //奖项要求奖池金额未达到奖池金额，不能中该奖项
			                for(UserPlaySevenInfo up : userPlays) {
			                    PlaySevenRewardType rewardType = getReward(up.getSixNumber(), up.getOneNumber(), sysSix, sysOne);
			                    if(rewardType.getValue() == reward.getRewardCode().intValue()){ //用户中了浮动奖项，不合法
			                        isReward = true;
			                        break;
			                    }
			                }
			                if(isReward)
			                    break;
			            }
			        }else  if(reward.getRewardMode() == PlaySevenRewardMode.STATIC.getValue()){
			            for(UserPlaySevenInfo userPlay : userPlays) { //奖项为固定金额，算出收益
		                    money += getMoney(ps, rewards, userPlay, sysSix, sysOne);
		                }
			        }
			    }
			    if(isReward)
                    continue;
			}else {
				result[0] = sysSix;
				result[1] = sysOne;
				break;
			}
			if(money <= moneyResult){
				moneyResult = money;
				result[0] = sysSix;
				result[1] = sysOne;
			}
		}
		return result;
    }
	
	
	
	/**
     * 抽奖6个数（01-33）
     * @return
     */
	private static String createSix() {	    
		List<Integer> arrSix = new ArrayList<Integer>();
		for(int i=1;i<=33;i++) {
		    arrSix.add(i);
		}
        Random random = new Random();
        int[] arr = new int[6];
        for (int i = 0; i < 6; i++) {
            int r = random.nextInt(arrSix.size());
            arr[i] = arrSix.get(r).intValue();
            arrSix.remove(r);
        }
        //排序
        arr = orderArray(arr);
        String[] result = new String[arr.length];
        for(int i=0;i<arr.length;i++){
            result[i] = (arr[i]<10 ? "0" : "") + arr[i];
        }      
        return StringUtils.join(result, ',');
    }
	
	private static int[] orderArray(int[] arr){
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
	
	/**
     * 抽奖1个数（01-16）
     * @return
     */
	private static String createOne() {
		List<String> arrSix = new ArrayList<String>();
		for(int i=1;i<=16;i++) {
			arrSix.add(((i<10) ? "0" : "") + i);
		}
        Random random = new Random();
        int r = random.nextInt(arrSix.size());
        return arrSix.get(r);
    }
	
	
    /**
     * 获取双色球用户中了几等奖（单式）
     * @return
     */
    public static PlaySevenRewardType getReward(String sixNumber, String oneNumber, String sysSix, String sysOne) {
    	List<String> arrSix = BaseUtils.getRetainList(sixNumber, sysSix);
    	boolean isOne = oneNumber.equals(sysOne);
    	if(arrSix!=null) {
    		int iSix = arrSix.size();
    		switch(iSix) {
	    		case 0:
	    		case 1:
	    		case 2:
    				return isOne ? PlaySevenRewardType.SIX : PlaySevenRewardType.NO;
	    		case 3:
    				return isOne ? PlaySevenRewardType.FIVE : PlaySevenRewardType.NO;
	    		case 4:
    				return isOne ? PlaySevenRewardType.FOUR : PlaySevenRewardType.FIVE;
	    		case 5:
    				return isOne ? PlaySevenRewardType.THREE : PlaySevenRewardType.FOUR;
	    		case 6:
    				return isOne ? PlaySevenRewardType.ONE : PlaySevenRewardType.TOW;
    		}
    		return PlaySevenRewardType.NO;
    	}else {
    		return PlaySevenRewardType.NO;
    	}
    }
    
    /**
     * 获取双色球单个用户中奖金额（单式）
     */
    public static Double getMoney(PlaySeven ps, List<PlaySevenReward> rewards, UserPlaySevenInfo userPlay, String sysSix, String sysOne) {
		PlaySevenRewardType rewardType = getReward(userPlay.getSixNumber(), userPlay.getOneNumber(), sysSix, sysOne);
		if(rewardType == PlaySevenRewardType.NO){
		    return (double)0;
		}
		for(PlaySevenReward reward : rewards){
		    if(reward.getRewardCode() == rewardType.getValue()){
		        return reward.getRewardTimes() * ps.getPerMoney();
		    }
		}
    	return (double)0;
    }
    
    public static int getThreeSum(String num) {
    	int result = 0;
    	String[] arr = num.split(",");
    	for(String s : arr) {
    		result += Integer.parseInt(s);
    	}
        return result;
    }
    
    
}
