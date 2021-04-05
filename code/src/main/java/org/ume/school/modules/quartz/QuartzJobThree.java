package org.ume.school.modules.quartz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;
import org.ume.school.modules.model.entity.MoneyType;
import org.ume.school.modules.model.entity.PlayThree;
import org.ume.school.modules.model.entity.PlayThreeResult;
import org.ume.school.modules.model.entity.PlayThreeResultLog;
import org.ume.school.modules.model.entity.PlayThreeResultReward;
import org.ume.school.modules.model.entity.PlayThreeReward;
import org.ume.school.modules.model.entity.UserPlayThree;
import org.ume.school.modules.model.entity.UserPlayThreeInfo;
import org.ume.school.modules.model.enums.MoneyLogType;
import org.ume.school.modules.model.enums.PlayResultStatus;
import org.ume.school.modules.model.enums.PlayThreeMode;
import org.ume.school.modules.model.enums.PlayThreeRewardType;
import org.ume.school.modules.model.enums.UserPlayStatus;
import org.ume.school.modules.moneytype.MoneyTypeService;
import org.ume.school.modules.play.three.PlayThreeService;
import org.ume.school.modules.play.three.result.PlayThreeResultService;
import org.ume.school.modules.play.three.result.log.PlayThreeResultLogService;
import org.ume.school.modules.play.three.result.reward.PlayThreeResultRewardService;
import org.ume.school.modules.play.three.reward.PlayThreeRewardService;
import org.ume.school.modules.user.money.UserMoneyService;
import org.ume.school.modules.user.play.three.UserPlayThreeService;
import org.ume.school.modules.user.play.three.info.UserPlayThreeInfoService;
import org.ume.school.modules.utils.play.ThreeUtils;

/**
 * 胖三定时器
 * 
 * @author Administrator
 * 
 */
@Component
public class QuartzJobThree implements Job {

    private PlayThreeService playThreeService;

    private PlayThreeResultService playThreeResultService;

    private PlayThreeRewardService playThreeRewardService;
    
    private PlayThreeResultRewardService playThreeResultRewardService;
    
    private PlayThreeResultLogService playThreeResultLogService;

    private UserPlayThreeService userPlayThreeService;

    private UserPlayThreeInfoService userPlayThreeInfoService;
    
    private UserMoneyService userMoneyService;
    private MoneyTypeService moneyTypeService;
    
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "★★★★★★★★★★★快3抽奖开始");
        playThreeService = (PlayThreeService) ApplicationContextUtil.getBean("playThreeService");
        playThreeResultService = (PlayThreeResultService) ApplicationContextUtil.getBean("playThreeResultService");
        playThreeRewardService = (PlayThreeRewardService) ApplicationContextUtil.getBean("playThreeRewardService");
        playThreeResultRewardService = (PlayThreeResultRewardService) ApplicationContextUtil.getBean("playThreeResultRewardService");
        playThreeResultLogService = (PlayThreeResultLogService) ApplicationContextUtil.getBean("playThreeResultLogService");
        userPlayThreeService = (UserPlayThreeService) ApplicationContextUtil.getBean("userPlayThreeService");
        userPlayThreeInfoService = (UserPlayThreeInfoService) ApplicationContextUtil.getBean("userPlayThreeInfoService");
        userMoneyService = (UserMoneyService) ApplicationContextUtil.getBean("userMoneyService");
        moneyTypeService = (MoneyTypeService) ApplicationContextUtil.getBean("moneyTypeService");
        
        PlayThree pt = playThreeService.get();
        if (pt == null)
            return;
        //系统币种
        MoneyType mtSystem = moneyTypeService.findBySystem();
        if(mtSystem==null)
            return;
        
        //开奖
        openResult(pt, mtSystem);
        
        //今日78期是否生成，明日也可以生成
        createResult(pt);
        
        //继续开奖
        openResult(pt, mtSystem);
        
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "★★★★★★★★★★★胖三抽奖结束");
    }
    
    private void openResult(PlayThree pt, MoneyType mtSystem){
      //有未开奖
        List<PlayThreeResult> playResults = playThreeResultService.getWaitList();
        if (playResults != null && playResults.size() > 0) {
            Date now = new Date();
            for(PlayThreeResult playResult : playResults){
                // 开奖
                if (playResult.getPublishTime().before(now)) {
                    System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "★★★★★★★★★★★胖三开奖");                    
                    List<PlayThreeReward> rewards = playThreeRewardService.findAll();
                    List<UserPlayThreeInfo> userPlays = userPlayThreeInfoService.findByPlayTime(playResult.getPlayTime());
                    // 更新本期投注总注数、总金额、中奖选号
                    playResult = updateResult(pt, playResult, rewards, userPlays);
                    // 保存中奖详情，并给用户发奖
                    saveResultRewardAndSendToUser(pt, playResult, rewards, userPlays, mtSystem);
                    // 更新走势图
                    updateLog(playResult);
                    System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "★★★★★★★★★★★胖三抽奖开奖结束");
                }
            }
        }
    }

    private void createResult(PlayThree pt){
        //今日78期是否生成
        Date date = new Date();
        PlayThreeResult playResult = playThreeResultService.getLatestResult();//获取最后一期进行判断今天是否已生成记录
        if(playResult == null){//如果没有最后一期，则生成今天78期
            try {
                playThreeResultService.createWaitResult(pt, date);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else{
            //判断最后一期是什么时候
            String dateNow = new SimpleDateFormat("yyyyMMdd").format(date);
            double playTime = Double.parseDouble(dateNow+"000");
            if(playResult.getPlayTime()<playTime){//今天还未生成
                try {
                    playThreeResultService.createWaitResult(pt, date);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        
        //明日78期是否生成
        playResult = playThreeResultService.getLatestResult();//获取最后一期进行判断
        if(playResult!=null){
          //判断最后一期是什么时候
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, 1);
            String dateNow = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
            double playTime = Double.parseDouble(dateNow+"000");
            if(playResult.getPlayTime()<playTime){//明天还未生成
                try {
                    playThreeResultService.createWaitResult(pt, cal.getTime());
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * 开奖并更新本期投注总注数和总金额
     */
     private PlayThreeResult updateResult(PlayThree ps, PlayThreeResult playResult, List<PlayThreeReward> rewards,
            List<UserPlayThreeInfo> userPlays) {
        if (userPlays != null && userPlays.size() > 0) {
            playResult.setCountNumber(userPlays.size());
            playResult.setSumMoney(userPlays.size() * playResult.getPerMoney());
        }
        int[] nums = ThreeUtils.createNumber(ps, rewards, userPlays);
        PlayThreeMode sysMode = ThreeUtils.getNumberMode(nums);
        playResult.setNumber1(nums[0]);
        playResult.setNumber2(nums[1]);
        playResult.setNumber3(nums[2]);
        playResult.setSumNumber(nums[0] +nums[1]+nums[2]);
        playResult.setMode(sysMode.getValue());
        playResult.setStatus(PlayResultStatus.FINISHED.getValue());
        return playThreeResultService.submit(playResult);
    }

    /**
     * 保存中奖详情，并给用户发奖
     */
    private void saveResultRewardAndSendToUser(PlayThree ps, PlayThreeResult playResult, List<PlayThreeReward> rewards,
            List<UserPlayThreeInfo> userPlays, MoneyType mtSystem) {
        if (userPlays != null && userPlays.size() > 0 && rewards != null && rewards.size() > 0) {
            // 生成所有奖项列表
            Map<Integer, PlayThreeResultReward> mapReward = new HashMap<Integer, PlayThreeResultReward>(rewards.size());
            for (PlayThreeReward reward : rewards) {
                PlayThreeResultReward resultReward = new PlayThreeResultReward();
                resultReward.setId(UUID.randomUUID().toString());
                resultReward.setCountNumber((double) 0);
                resultReward.setCreateTime(new Date());
                resultReward.setPlayTime(playResult.getPlayTime());
                resultReward.setRewardCode(reward.getRewardCode());
                resultReward.setPerMoney(reward.getRewardTimes() * playResult.getPerMoney());
                resultReward.setRewardMoney((double) 0);
                resultReward.setCountNumber((double) 0);
                mapReward.put(reward.getRewardCode(), resultReward);
            }
            // 根据用户投注运算中奖总注数和总金额            
            int[] sysNumber = new int[3];
            sysNumber[0] = playResult.getNumber1();
            sysNumber[1] = playResult.getNumber2();
            sysNumber[2] = playResult.getNumber3();
            PlayThreeMode sysMode = ThreeUtils.getNumberMode(sysNumber);
            for (UserPlayThreeInfo userPlay : userPlays) {
                PlayThreeRewardType rewardType = ThreeUtils.getRewardType(ps, userPlay, sysNumber, sysMode);
                if (rewardType != PlayThreeRewardType.NO) {
                    for (PlayThreeReward reward : rewards) {
                        if (reward.getRewardCode().intValue() == rewardType.getValue()) {
                            mapReward.get(reward.getRewardCode()).setCountNumber(mapReward.get(reward.getRewardCode()).getCountNumber() + 1);// 注数+1
                            userPlay.setStatus(UserPlayStatus.YES.getValue());
                            userPlay.setRewardMoney(reward.getRewardTimes() * playResult.getPerMoney());
                            userPlay.setRewardCode(rewardType.getValue());
                            break;
                        }
                    }
                    //给用户发奖
                    userMoneyService.save(userPlay.getUserId(), mtSystem.getTypeId(), userPlay.getRewardMoney(), MoneyLogType.PLAY_REWARD, null);
                }else{
                    userPlay.setStatus(UserPlayStatus.NO.getValue());
                }
                userPlay.setPublishTime(new Date());
                userPlayThreeInfoService.submit(userPlay);
            }
            // 运算奖励金额
            Double rewardAllMoney = (double)0; //总奖励金额
            for (PlayThreeReward reward : rewards) {
                PlayThreeResultReward resultReward = mapReward.get(reward.getRewardCode());
                resultReward.setRewardMoney(resultReward.getPerMoney() * resultReward.getCountNumber());
                if(resultReward.getCountNumber().intValue() == 0) {
                	resultReward.setRewardMoney((double)0);
                }
                rewardAllMoney += resultReward.getPerMoney() * resultReward.getCountNumber();
                playThreeResultRewardService.submit(resultReward);
            }
            playResult.setRewardMoney(rewardAllMoney);
            playResult.setLeaveMoney(playResult.getSumMoney() - playResult.getRewardMoney());
            playThreeResultService.submit(playResult);
            ps.setAllMoney(ps.getAllMoney() + playResult.getSumMoney() -  rewardAllMoney);//更新奖池
            playThreeService.submit(ps);
            
            //更新用户投注主表
            List<UserPlayThree> upsList = userPlayThreeService.findByPlayTime(playResult.getPlayTime());
            if (upsList != null && upsList.size() > 0){
                for(UserPlayThree ups : upsList){
                    userPlays = userPlayThreeInfoService.findByOrderIdAndStatus(ups.getOrderId(), UserPlayStatus.YES.getValue());//所有中奖                    
                    if(userPlays!=null &&userPlays.size()>0){
                        ups.setCountReward((double)userPlays.size());
                        Double rewardMoney = (double)0;
                        for(UserPlayThreeInfo userPlay : userPlays){
                            rewardMoney += userPlay.getRewardMoney();
                            ups.setRewardCode(userPlay.getRewardCode());
                        }
                        ups.setRewardMoney(rewardMoney);
                        ups.setStatus(UserPlayStatus.YES.getValue());
                    }else{
                        ups.setCountReward((double)0);
                        ups.setStatus(UserPlayStatus.NO.getValue());
                    }
                    userPlayThreeService.submit(ups);
                }
            }
            
        }
    }
    
    /**
     * 更新走势图
     */
    private PlayThreeResultLog updateLog(PlayThreeResult playResult) {
    	PlayThreeResultLog logFront = playThreeResultLogService.getFrontLog();
    	PlayThreeResultLog log = new PlayThreeResultLog();
    	log.setCreateTime(new Date());
    	log.setId(UUID.randomUUID().toString());
    	log.setPlayTime(playResult.getPlayTime());
    	log.setNumber1(playResult.getNumber1());
    	log.setNumber2(playResult.getNumber2());
    	log.setNumber3(playResult.getNumber3());
    	String sysNumber = playResult.getNumber1() + "" + playResult.getNumber2() + "" + playResult.getNumber3();
    	if(!sysNumber.contains("1")) {
    		log.setN1(logFront!=null? logFront.getN1()+1 : 1);
    	}
    	if(!sysNumber.contains("2")) {
            log.setN2(logFront!=null? logFront.getN2()+1 : 1);
        }
    	if(!sysNumber.contains("3")) {
            log.setN3(logFront!=null? logFront.getN3()+1 : 1);
        }
    	if(!sysNumber.contains("4")) {
            log.setN4(logFront!=null? logFront.getN4()+1 : 1);
        }
    	if(!sysNumber.contains("5")) {
            log.setN5(logFront!=null? logFront.getN5()+1 : 1);
        }
    	if(!sysNumber.contains("6")) {
            log.setN6(logFront!=null? logFront.getN6()+1 : 1);
        }
    	if(playResult.getSumNumber().intValue() != 3){
    	    log.setSum3(logFront!=null? logFront.getSum3()+1 : 1);
    	}
    	if(playResult.getSumNumber().intValue() != 4){
            log.setSum4(logFront!=null? logFront.getSum4()+1 : 1);
        }
    	if(playResult.getSumNumber().intValue() != 5){
            log.setSum5(logFront!=null? logFront.getSum5()+1 : 1);
        }
    	if(playResult.getSumNumber().intValue() != 6){
            log.setSum6(logFront!=null? logFront.getSum6()+1 : 1);
        }
    	if(playResult.getSumNumber().intValue() != 7){
            log.setSum7(logFront!=null? logFront.getSum7()+1 : 1);
        }
    	if(playResult.getSumNumber().intValue() != 8){
            log.setSum8(logFront!=null? logFront.getSum8()+1 : 1);
        }
    	if(playResult.getSumNumber().intValue() != 9){
            log.setSum9(logFront!=null? logFront.getSum9()+1 : 1);
        }
    	if(playResult.getSumNumber().intValue() != 10){
            log.setSum10(logFront!=null? logFront.getSum10()+1 : 1);
        }
    	if(playResult.getSumNumber().intValue() != 11){
            log.setSum11(logFront!=null? logFront.getSum11()+1 : 1);
        }
    	if(playResult.getSumNumber().intValue() != 12){
            log.setSum12(logFront!=null? logFront.getSum12()+1 : 1);
        }
    	if(playResult.getSumNumber().intValue() != 13){
            log.setSum13(logFront!=null? logFront.getSum13()+1 : 1);
        }
    	if(playResult.getSumNumber().intValue() != 14){
            log.setSum14(logFront!=null? logFront.getSum14()+1 : 1);
        }
    	if(playResult.getSumNumber().intValue() != 15){
            log.setSum15(logFront!=null? logFront.getSum15()+1 : 1);
        }
    	if(playResult.getSumNumber().intValue() != 16){
            log.setSum16(logFront!=null? logFront.getSum16()+1 : 1);
        }
    	if(playResult.getSumNumber().intValue() != 17){
            log.setSum17(logFront!=null? logFront.getSum17()+1 : 1);
        }
    	if(playResult.getSumNumber().intValue() != 18){
            log.setSum18(logFront!=null? logFront.getSum18()+1 : 1);
        }
        return playThreeResultLogService.submit(log);
    }
}