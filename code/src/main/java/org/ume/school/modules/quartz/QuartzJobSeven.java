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
import org.ume.school.modules.model.entity.PlaySeven;
import org.ume.school.modules.model.entity.PlaySevenResult;
import org.ume.school.modules.model.entity.PlaySevenResultLog;
import org.ume.school.modules.model.entity.PlaySevenResultReward;
import org.ume.school.modules.model.entity.PlaySevenReward;
import org.ume.school.modules.model.entity.UserPlaySeven;
import org.ume.school.modules.model.entity.UserPlaySevenInfo;
import org.ume.school.modules.model.enums.MoneyLogType;
import org.ume.school.modules.model.enums.PlayResultStatus;
import org.ume.school.modules.model.enums.PlaySevenRewardMode;
import org.ume.school.modules.model.enums.PlaySevenRewardType;
import org.ume.school.modules.model.enums.UserPlayStatus;
import org.ume.school.modules.moneytype.MoneyTypeService;
import org.ume.school.modules.play.seven.PlaySevenService;
import org.ume.school.modules.play.seven.result.PlaySevenResultService;
import org.ume.school.modules.play.seven.result.log.PlaySevenResultLogService;
import org.ume.school.modules.play.seven.result.reward.PlaySevenResultRewardService;
import org.ume.school.modules.play.seven.reward.PlaySevenRewardService;
import org.ume.school.modules.user.money.UserMoneyService;
import org.ume.school.modules.user.play.seven.UserPlaySevenService;
import org.ume.school.modules.user.play.seven.info.UserPlaySevenInfoService;
import org.ume.school.modules.utils.play.SevenUtils;

/**
 * 双色球定时器
 * 
 * @author Administrator
 * 
 */
@Component
public class QuartzJobSeven implements Job {

    private PlaySevenService playSevenService;

    private PlaySevenResultService playSevenResultService;

    private PlaySevenRewardService playSevenRewardService;
    
    private PlaySevenResultRewardService playSevenResultRewardService;
    
    private PlaySevenResultLogService playSevenResultLogService;

    private UserPlaySevenService userPlaySevenService;

    private UserPlaySevenInfoService userPlaySevenInfoService;
    
    private UserMoneyService userMoneyService;
    private MoneyTypeService moneyTypeService;

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "★★★★★★★★★★★双色球抽奖开始");
        playSevenService = (PlaySevenService) ApplicationContextUtil.getBean("playSevenService");
        playSevenResultService = (PlaySevenResultService) ApplicationContextUtil.getBean("playSevenResultService");
        playSevenRewardService = (PlaySevenRewardService) ApplicationContextUtil.getBean("playSevenRewardService");
        playSevenResultRewardService = (PlaySevenResultRewardService) ApplicationContextUtil.getBean("playSevenResultRewardService");
        playSevenResultLogService = (PlaySevenResultLogService) ApplicationContextUtil.getBean("playSevenResultLogService");
        userPlaySevenService = (UserPlaySevenService) ApplicationContextUtil.getBean("userPlaySevenService");
        userPlaySevenInfoService = (UserPlaySevenInfoService) ApplicationContextUtil
                .getBean("userPlaySevenInfoService");
        userMoneyService = (UserMoneyService) ApplicationContextUtil.getBean("userMoneyService");
        moneyTypeService = (MoneyTypeService) ApplicationContextUtil.getBean("moneyTypeService");
        
        PlaySeven ps = playSevenService.get();
        if (ps == null)
            return;
        //系统币种
        MoneyType mtSystem = moneyTypeService.findBySystem();
        if(mtSystem==null)
            return;

      //开奖
        openResult(ps, mtSystem);
        
        //今日78期是否生成，明日也可以生成
        createResult(ps);
        
        //继续开奖
        openResult(ps, mtSystem);
        
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "★★★★★★★★★★★双色球抽奖结束");
    }
    
    private void openResult(PlaySeven ps, MoneyType mtSystem){
        //有未开奖
          List<PlaySevenResult> playResults = playSevenResultService.getWaitList();
          if (playResults != null && playResults.size() > 0) {
              Date now = new Date();
              for(PlaySevenResult playResult : playResults){
                  // 开奖
                  if (playResult.getPublishTime().before(now)) {
                      System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "★★★★★★★★★★★双色球开奖");                    
                      List<PlaySevenReward> rewards = playSevenRewardService.findAll();
                      List<UserPlaySevenInfo> userPlays = userPlaySevenInfoService.findByPlayTime(playResult.getPlayTime());
                      // 更新本期投注总注数、总金额、中奖选号
                      playResult = updateResult(ps, playResult, rewards, userPlays);
                      // 保存中奖详情，并给用户发奖
                      saveResultRewardAndSendToUser(ps, playResult, rewards, userPlays, mtSystem);
                      // 更新走势图
                      updateLog(playResult);
                      System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "★★★★★★★★★★★双色球抽奖开奖结束");
                  }
              }
          }
      }

      private void createResult(PlaySeven ps){
          //今日78期是否生成
          Date date = new Date();
          PlaySevenResult playResult = playSevenResultService.getLatestResult();//获取最后一期进行判断今天是否已生成记录
          if(playResult == null){//如果没有最后一期，则生成今天78期
              try {
                  playSevenResultService.createWaitResult(ps, date);
              } catch (ParseException e) {
                  e.printStackTrace();
              }
          }else{
              //判断最后一期是什么时候
              String dateNow = new SimpleDateFormat("yyyyMMdd").format(date);
              double playTime = Double.parseDouble(dateNow+"000");
              if(playResult.getPlayTime()<playTime){//今天还未生成
                  try {
                      playSevenResultService.createWaitResult(ps, date);
                  } catch (ParseException e) {
                      e.printStackTrace();
                  }
              }
          }
          
          //明日78期是否生成
          playResult = playSevenResultService.getLatestResult();//获取最后一期进行判断
          if(playResult!=null){
            //判断最后一期是什么时候
              Calendar cal = Calendar.getInstance();
              cal.setTime(date);
              cal.add(Calendar.DATE, 1);
              String dateNow = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
              double playTime = Double.parseDouble(dateNow+"000");
              if(playResult.getPlayTime()<playTime){//明天还未生成
                  try {
                      playSevenResultService.createWaitResult(ps, cal.getTime());
                  } catch (ParseException e) {
                      e.printStackTrace();
                  }
              }
          }
      }

    /**
     * 开奖并更新本期投注总注数和总金额
     */
    private PlaySevenResult updateResult(PlaySeven ps, PlaySevenResult playResult, List<PlaySevenReward> rewards,
            List<UserPlaySevenInfo> userPlays) {
        if (userPlays != null && userPlays.size() > 0) {
            playResult.setCountNumber(userPlays.size());
            playResult.setSumMoney(userPlays.size() * playResult.getPerMoney());
        }
        String[] nums = SevenUtils.createNumber(ps, rewards, userPlays);
        playResult.setSixNumber(nums[0]);
        playResult.setOneNumber(nums[1]);
        playResult.setStatus(PlayResultStatus.FINISHED.getValue());
        return playSevenResultService.submit(playResult);
    }

    /**
     * 保存中奖详情，并给用户发奖
     */
    public void saveResultRewardAndSendToUser(PlaySeven ps, PlaySevenResult playResult, List<PlaySevenReward> rewards,
            List<UserPlaySevenInfo> userPlays, MoneyType mtSystem) {
        if (userPlays != null && userPlays.size() > 0 && rewards != null && rewards.size() > 0) {
            // 生成所有奖项列表
            Map<Integer, PlaySevenResultReward> mapReward = new HashMap<Integer, PlaySevenResultReward>(rewards.size());
            for (PlaySevenReward reward : rewards) {
                PlaySevenResultReward resultReward = new PlaySevenResultReward();
                resultReward.setId(UUID.randomUUID().toString());
                resultReward.setCountNumber((double) 0);
                resultReward.setCreateTime(new Date());
                resultReward.setPlayTime(playResult.getPlayTime());
                resultReward.setRewardCode(reward.getRewardCode());
                if (reward.getRewardMode() == PlaySevenRewardMode.DIFFERENCE.getValue()) { // 浮动奖项
                    resultReward.setPerMoney((double) 0);
                    resultReward.setRewardMoney(playResult.getSumMoney() * reward.getRewardPercent() / 100);
                }
                else {
                    resultReward.setPerMoney(reward.getRewardTimes() * playResult.getPerMoney());
                    resultReward.setRewardMoney((double) 0);
                }
                resultReward.setCountNumber((double) 0);
                mapReward.put(reward.getRewardCode(), resultReward);
            }
            // 根据用户投注运算中奖总注数和总金额（单式）
            for (UserPlaySevenInfo userPlay : userPlays) {
                PlaySevenRewardType rewardType = SevenUtils.getReward(userPlay.getSixNumber(), userPlay.getOneNumber(), playResult.getSixNumber(), playResult.getOneNumber());
                if (rewardType != PlaySevenRewardType.NO) {
                    for (PlaySevenReward reward : rewards) {
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
                userPlaySevenInfoService.submit(userPlay);
            }
            // 运算浮动金额
            Double rewardAllMoney = (double)0; //总奖励金额
            for (PlaySevenReward reward : rewards) {
                PlaySevenResultReward resultReward = mapReward.get(reward.getRewardCode());
                if (reward.getRewardMode() == PlaySevenRewardMode.DIFFERENCE.getValue()) {// 浮动奖项
                    Double perMoney = resultReward.getRewardMoney() / resultReward.getCountNumber();
                    if (perMoney > reward.getMaxMoney()) { // 不能超出最大限制
                        perMoney = reward.getMaxMoney();
                    }
                    resultReward.setPerMoney(perMoney);
                }
                else {
                    resultReward.setRewardMoney(resultReward.getPerMoney() * resultReward.getCountNumber());
                }
                if(resultReward.getCountNumber().intValue() == 0) {
                	resultReward.setRewardMoney((double)0);
                }
                rewardAllMoney += resultReward.getPerMoney() * resultReward.getCountNumber();
                playSevenResultRewardService.submit(resultReward);
            }
            playResult.setRewardMoney(rewardAllMoney);
            playResult.setLeaveMoney(playResult.getSumMoney() - playResult.getRewardMoney());
            playSevenResultService.submit(playResult);
            ps.setAllMoney(ps.getAllMoney() + playResult.getSumMoney() -  rewardAllMoney);//更新奖池
            playSevenService.submit(ps);
            
            //更新用户投注主表
            List<UserPlaySeven> upsList = userPlaySevenService.findByPlayTime(playResult.getPlayTime());
            if (upsList != null && upsList.size() > 0){
                for(UserPlaySeven ups : upsList){
                    userPlays = userPlaySevenInfoService.findByOrderIdAndStatus(ups.getOrderId(), UserPlayStatus.YES.getValue());//所有中奖                    
                    if(userPlays!=null &&userPlays.size()>0){
                        ups.setCountReward((double)userPlays.size());
                        Double rewardMoney = (double)0;
                        for(UserPlaySevenInfo userPlay : userPlays){
                            rewardMoney += userPlay.getRewardMoney();
                            ups.setRewardCode(userPlay.getRewardCode());
                        }
                        ups.setRewardMoney(rewardMoney);
                        ups.setStatus(UserPlayStatus.YES.getValue());
                    }else{
                        ups.setCountReward((double)0);
                        ups.setStatus(UserPlayStatus.NO.getValue());
                    }
                    userPlaySevenService.submit(ups);
                }
            }
            
        }
    }
    
    /**
     * 更新走势图
     */
    private PlaySevenResultLog updateLog(PlaySevenResult playResult) {
    	PlaySevenResultLog logFront = playSevenResultLogService.getFrontLog();
    	PlaySevenResultLog log = new PlaySevenResultLog();
    	log.setCreateTime(new Date());
    	log.setId(UUID.randomUUID().toString());
    	log.setPlayTime(playResult.getPlayTime());
    	if(!playResult.getSixNumber().contains("01")) {
    		log.setSix1(logFront!=null? logFront.getSix1()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("02")) {
    		log.setSix2(logFront!=null? logFront.getSix2()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("03")) {
    		log.setSix3(logFront!=null? logFront.getSix3()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("04")) {
    		log.setSix4(logFront!=null? logFront.getSix4()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("05")) {
    		log.setSix5(logFront!=null? logFront.getSix5()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("06")) {
    		log.setSix6(logFront!=null? logFront.getSix6()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("07")) {
    		log.setSix7(logFront!=null? logFront.getSix7()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("08")) {
    		log.setSix8(logFront!=null? logFront.getSix8()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("09")) {
    		log.setSix9(logFront!=null? logFront.getSix9()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("10")) {
    		log.setSix10(logFront!=null? logFront.getSix10()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("11")) {
    		log.setSix11(logFront!=null? logFront.getSix11()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("12")) {
    		log.setSix12(logFront!=null? logFront.getSix12()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("13")) {
    		log.setSix13(logFront!=null? logFront.getSix13()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("14")) {
    		log.setSix14(logFront!=null? logFront.getSix14()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("15")) {
    		log.setSix15(logFront!=null? logFront.getSix15()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("16")) {
    		log.setSix16(logFront!=null? logFront.getSix16()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("17")) {
    		log.setSix17(logFront!=null? logFront.getSix17()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("18")) {
    		log.setSix18(logFront!=null? logFront.getSix18()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("19")) {
    		log.setSix19(logFront!=null? logFront.getSix19()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("20")) {
    		log.setSix20(logFront!=null? logFront.getSix20()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("21")) {
    		log.setSix21(logFront!=null? logFront.getSix21()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("22")) {
    		log.setSix22(logFront!=null? logFront.getSix22()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("23")) {
    		log.setSix23(logFront!=null? logFront.getSix23()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("24")) {
    		log.setSix24(logFront!=null? logFront.getSix24()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("25")) {
    		log.setSix25(logFront!=null? logFront.getSix25()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("26")) {
    		log.setSix26(logFront!=null? logFront.getSix26()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("27")) {
    		log.setSix27(logFront!=null? logFront.getSix27()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("28")) {
    		log.setSix28(logFront!=null? logFront.getSix28()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("29")) {
    		log.setSix29(logFront!=null? logFront.getSix29()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("30")) {
    		log.setSix30(logFront!=null? logFront.getSix30()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("31")) {
    		log.setSix31(logFront!=null? logFront.getSix31()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("32")) {
    		log.setSix32(logFront!=null? logFront.getSix32()+1 : 1);
    	}
    	if(!playResult.getSixNumber().contains("33")) {
    		log.setSix33(logFront!=null? logFront.getSix33()+1 : 1);
    	}
    	if(!playResult.getOneNumber().contains("01")) {
    		log.setOne1(logFront!=null? logFront.getOne1()+1 : 1);
    	}
    	if(!playResult.getOneNumber().contains("02")) {
    		log.setOne2(logFront!=null? logFront.getOne2()+1 : 1);
    	}
    	if(!playResult.getOneNumber().contains("03")) {
    		log.setOne3(logFront!=null? logFront.getOne3()+1 : 1);
    	}
    	if(!playResult.getOneNumber().contains("04")) {
    		log.setOne4(logFront!=null? logFront.getOne4()+1 : 1);
    	}
    	if(!playResult.getOneNumber().contains("05")) {
    		log.setOne5(logFront!=null? logFront.getOne5()+1 : 1);
    	}
    	if(!playResult.getOneNumber().contains("06")) {
    		log.setOne6(logFront!=null? logFront.getOne6()+1 : 1);
    	}
    	if(!playResult.getOneNumber().contains("07")) {
    		log.setOne7(logFront!=null? logFront.getOne7()+1 : 1);
    	}
    	if(!playResult.getOneNumber().contains("08")) {
    		log.setOne8(logFront!=null? logFront.getOne8()+1 : 1);
    	}
    	if(!playResult.getOneNumber().contains("09")) {
    		log.setOne9(logFront!=null? logFront.getOne9()+1 : 1);
    	}
    	if(!playResult.getOneNumber().contains("10")) {
    		log.setOne10(logFront!=null? logFront.getOne10()+1 : 1);
    	}
    	if(!playResult.getOneNumber().contains("11")) {
    		log.setOne11(logFront!=null? logFront.getOne11()+1 : 1);
    	}
    	if(!playResult.getOneNumber().contains("12")) {
    		log.setOne12(logFront!=null? logFront.getOne12()+1 : 1);
    	}
    	if(!playResult.getOneNumber().contains("13")) {
    		log.setOne13(logFront!=null? logFront.getOne13()+1 : 1);
    	}
    	if(!playResult.getOneNumber().contains("14")) {
    		log.setOne14(logFront!=null? logFront.getOne14()+1 : 1);
    	}
    	if(!playResult.getOneNumber().contains("15")) {
    		log.setOne15(logFront!=null? logFront.getOne15()+1 : 1);
    	}
    	if(!playResult.getOneNumber().contains("16")) {
    		log.setOne16(logFront!=null? logFront.getOne16()+1 : 1);
    	}
        return playSevenResultLogService.submit(log);
    }
}