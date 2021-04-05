$(document).ready(function () {
  PlayThree.init();
});
var layer, table;
var PlayThree = {
  "IsStop": false,
  "SecondTime": 0,
  "SecondTime1": 0,
  "Timer": null,
  "Timer1": null,
  "SettingData": {},
  "RewardData": [],
  "FrontData": null,
  "JoinData": null,
  "WaitData": null,
  "SelectBallData": [],
  "init": function () {
    ThreeNotice.init();
    PlayThreeHeZhi.init();
    PlayThree3Tx.init();
    PlayThree3Dx.init();
    PlayThree3Bt.init();
    PlayThree3Lt.init();
    PlayThree2Fx.init();
    PlayThree2Dx.init();
    PlayThree2Bt.init();
    PlayThree1Tx.init();

    $("#k3_tab").find("li").on('click', function () {
      $("#k3_tab").find("li").removeClass("active");
      $(this).addClass("active");
      $(".betBox").hide();
      $("#panl_" + $(this).attr("name")).show();
    });

    this.initSetting(function(){
      PlayThree.initTodayCount();
      PlayThree.initFront();
      PlayThree.initJoin();
      PlayThree.initWait();
    });
    $("#awardListBtn").on('click', function(){
      if($("#awardList").hasClass("hide")){
        $("#awardList").removeClass("hide");
        $("#awardListBtn").find("i").removeClass("openDetail").addClass("closeDetail");        
      }else{
        $("#awardList").addClass("hide");
        $("#awardListBtn").find("i").removeClass("closeDetail").addClass("openDetail");
      }
    });
    this.initMore();
    this.initToday();
    this.initBottomBox();
    $("#areaOrangeBall").find("a.js_ball").on('click', function () {
      PlayThree.selectOrangeBall($(this));
    });
    $("#clearOrangeBall").on('click', function () {
      PlayThree.clearSelectOrangeBall();
    });
    $("#randomOrangeBall").on('click', function () {
      PlayThree.randomSelectOrangeBall();
    });
    $("#areaGreenBall").find("a.js_ball").on('click', function () {
      PlayThree.selectGreenBall($(this));
    });
    $("#clearGreenBall").on('click', function () {
      PlayThree.clearSelectGreenBall();
    });
    $("#randomGreenBall").on('click', function () {
      PlayThree.randomSelectGreenBall();
    });
    $("#randomOneBall").on('click', function () {
      PlayThree.randomOneBall();
    });
    $("#randomNumberBall").on('click', function () {
      PlayThree.randomNumberBall();
    });
    $("#clearAllBall").on('click', function () {
      PlayThree.clearAllBall();
    });

    $("#submitBall").on('click', function () {
      PlayThree.submitBall();
    });
    DataUtils.getUserIsLogin(function (isLogin, userInfo) {
      if (!isLogin) {
        $("#submitBall").hide();
        $("#liLog").bind('click', function(){
          $("#btn_login").click();
        });
      }
    });
    $("#tabMenu").find("li").bind('click', function(){
      if($(this).attr("id")=="liSelectNumber"){
        $("#gamePlayUrl").show();
      }else{
        $("#gamePlayUrl").hide();
      }
    });

    layui.use(['table', 'element', 'form', 'laydate'], function () {
      var element = layui.element, form = layui.form;
      table = layui.table;
      layer = layui.layer;
      var laydate = layui.laydate;

      //日期控件
      laydate.render({
        elem: '#tbResultLogDate',
        max: FormatUtils.formatterDate(new Date()),
        value: FormatUtils.formatterDate(new Date())
      });
      laydate.render({
        elem: '#tbResultHistoryDate',
        max: FormatUtils.formatterDate(new Date()),
        value: FormatUtils.formatterDate(new Date())
      });      

      //选号列表
      PlayThree.initSelectNumber();
      //走势列表
      PlayThree.initResultLog();
      //历史记录
      PlayThree.initResultHistory();
      //用户参与记录
      PlayThree.initUserLog();

      
    });
  },
  "initSetting": function (callback) {
    // UIUtils.loading();
    var url = Config.WEB_SERVER_API + Config.URI.PLAY_THREE;
    HttpUtils.httpGet(url, {}, function (data) {
      // UIUtils.loaded();
      PlayThree.SettingData = data.rows;
      if (!PlayThree.SettingData) return;
      $("#current_playRate").text(PlayThree.SettingData.playRate+"分钟一期");
      $("#moreLinkRight").text("参与时间：每天" + PlayThree.SettingData.startTime + "~" + PlayThree.SettingData.overTime);
      PlayThree.initReward(callback);
      // $("#info_allmoney").text(PlayThree.formatAllMoney(PlayThree.SettingData.allMoney));
    });
  },
  "initReward": function (callback) {
    // UIUtils.loading();
    var url = Config.WEB_SERVER_API + Config.URI.PLAY_THREE_REWARD;
    HttpUtils.httpGet(url, {}, function (data) {
      // UIUtils.loaded();
      PlayThree.RewardData = data.rows;
      if (!PlayThree.RewardData) return;
      var perMoney = PlayThree.SettingData ? PlayThree.SettingData.perMoney : 0;
      var hzMin = 0;
      var hzMax = 0;
      var rewardMoney = 0;
      for (var i = 0; i < PlayThree.RewardData.length; i++) {
        var reward = PlayThree.RewardData[i];
        rewardMoney = perMoney * reward.rewardTimes;
        //和值
        if (reward.rewardCode >= 3 && reward.rewardCode <= 18) {
          $("#awardSum"+reward.rewardCode).text(rewardMoney+"DG");
          $("#panl_hz").find("li[data-value='" + reward.rewardCode + "']").attr("data-money", rewardMoney).find("span").text("奖金" + rewardMoney);
          if (reward.rewardCode == 3) {
            $("#panl_hz").find(".title em").text(rewardMoney);
            hzMax = rewardMoney;
          } else if (reward.rewardCode == 10) {
            hzMin = rewardMoney;
          }
        } else if (reward.rewardCode == 30) {
          //3同号通选
          $("#awardSum3tx").text(rewardMoney+"DG");
          $("#k3_tab").find("li[name='3tx'] em").text("奖金：" + rewardMoney);
          $("#panl_3tx").find(".title em").text(rewardMoney);
          $("#panl_3tx").find(".js_ball").attr("data-money", rewardMoney);
        }else if (reward.rewardCode == 31) {
          //3同号单选
          $("#awardSum3dx").text(rewardMoney+"DG");
          $("#k3_tab").find("li[name='3dx'] em").text("奖金：" + rewardMoney);
          $("#panl_3dx").find(".title em").text(rewardMoney);
          $("#panl_3dx").find(".js_ball").attr("data-money", rewardMoney);
        }else if (reward.rewardCode == 32) {
          //3不同号
          $("#awardSum3bt").text(rewardMoney+"DG");
          $("#k3_tab").find("li[name='3bt'] em").text("奖金：" + rewardMoney);
          $("#panl_3bt").find(".title em").text(rewardMoney);
          $("#panl_3bt").find(".js_ball").attr("data-money", rewardMoney);
        }else if (reward.rewardCode == 33) {
          //3连号通选
          $("#awardSum3lh").text(rewardMoney+"DG");
          $("#k3_tab").find("li[name='3lt'] em").text("奖金：" + rewardMoney);
          $("#panl_3lt").find(".title em").text(rewardMoney);
          $("#panl_3lt").find(".js_ball").attr("data-money", rewardMoney);
        }else if (reward.rewardCode == 20) {
          //2同号复选
          $("#awardSum2fx").text(rewardMoney+"DG");
          $("#k3_tab").find("li[name='2fx'] em").text("奖金：" + rewardMoney);
          $("#panl_2fx").find(".title em").text(rewardMoney);
          $("#panl_2fx").find(".js_ball").attr("data-money", rewardMoney);
        }else if (reward.rewardCode == 21) {
          //2同号单选
          $("#awardSum2dx").text(rewardMoney+"DG");
          $("#k3_tab").find("li[name='2dx'] em").text("奖金：" + rewardMoney);
          $("#panl_2dx").find(".title em").text(rewardMoney);
          $("#panl_2dx").find(".js_ball").attr("data-money", rewardMoney);
        }else if (reward.rewardCode == 22) {
          //2不同号
          $("#awardSum2bt").text(rewardMoney+"DG");
          $("#k3_tab").find("li[name='2bt'] em").text("奖金：" + rewardMoney);
          $("#panl_2bt").find(".title em").text(rewardMoney);
          $("#panl_2bt").find(".js_ball").attr("data-money", rewardMoney);
        }
        $("#k3_tab").find("li[name='hz'] em").text("奖金：" + hzMin + "-" + hzMax);
      }
      // $("#info_allmoney").text(PlayThree.formatAllMoney(PlayThree.SettingData.allMoney));
      if(callback){
        callback();
      }
    });
  },
  "initTodayCount":function(){
    var url = Config.WEB_SERVER_API + Config.URI.PLAY_THREE_RESULT_TODAY_LAST;
    HttpUtils.httpGet(url, {}, function (data) {
      if(data && data.rows){
        $("#wait_open").text(data.rows.dayIndex);
        var leave = parseInt(PlayThree.SettingData.countTime - data.rows.dayIndex);
        $("#wait_leave").text(leave);
      }else{
        $("#wait_open").text(0);
        $("#wait_leave").text(PlayThree.SettingData.countTime);
      }
    });
  },
  /**
   * 上一期
   */
  "initFront": function () {
    // UIUtils.loading();
    var url = Config.WEB_SERVER_API + Config.URI.PLAY_THREE_RESULT_FRONT;
    HttpUtils.httpGet(url, {}, function (data) {
      // UIUtils.loaded();
      PlayThree.FrontData = data.rows;
      if (!PlayThree.FrontData) return;
      $("#front_time1").text(PlayThree.FrontData.playTime);
      $("#front_time2").text(PlayThree.FrontData.playTime);
      $("#front_numbers").text(PlayThree.FrontData.number1 + " " + PlayThree.FrontData.number2 + " " + PlayThree.FrontData.number3);
      $("#front1_num1").removeClass().addClass("kuai3Ball").addClass("num" + PlayThree.FrontData.number1);
      $("#front1_num2").removeClass().addClass("kuai3Ball").addClass("num" + PlayThree.FrontData.number2);
      $("#front1_num3").removeClass().addClass("kuai3Ball").addClass("num" + PlayThree.FrontData.number3);
      $("#front2_num1").removeClass().addClass("kuai3Ball").addClass("num" + PlayThree.FrontData.number1);
      $("#front2_num2").removeClass().addClass("kuai3Ball").addClass("num" + PlayThree.FrontData.number2);
      $("#front2_num3").removeClass().addClass("kuai3Ball").addClass("num" + PlayThree.FrontData.number3);
      $("#front_sum1").text(PlayThree.FrontData.sumNumber);
      $("#front_sum2").text(PlayThree.FrontData.sumNumber);
      $("#front1_numType1").removeClass('nt_big').removeClass('nt_small');
      $("#front1_numType2").removeClass('nt_odd').removeClass('nt_dual');
      $("#front2_numType1").removeClass('nt_big').removeClass('nt_small');
      $("#front2_numType2").removeClass('nt_odd').removeClass('nt_dual');
      if(PlayThree.FrontData.sumNumber<=10){//小
        $("#front1_numType1").text("小").addClass('nt_small');
        $("#front2_numType1").text("小").addClass('nt_small');
      }else{
        $("#front1_numType1").text("大").addClass('nt_big');
        $("#front2_numType1").text("大").addClass('nt_big');
      }
      if(PlayThree.FrontData.sumNumber%2!=0){//小
        $("#front1_numType2").text("单").addClass('nt_odd');
        $("#front2_numType2").text("单").addClass('nt_odd');
      }else{
        $("#front1_numType2").text("双").addClass('nt_dual');
        $("#front2_numType2").text("双").addClass('nt_dual');
      }
    });
  },
  /**
   * 可参与
   */
  "initJoin": function () {
    $("#wait_timedown").text("00:00:00");
    // UIUtils.loading();
    var url = Config.WEB_SERVER_API + Config.URI.PLAY_THREE_RESULT_JOIN;
    HttpUtils.httpGet(url, {}, function (data) {
      // UIUtils.loaded();
      PlayThree.JoinData = data.rows || {};
      if (!PlayThree.JoinData) {
        $("#wait_timedown").text("服务已停止");
        PlayThree.IsStop = true;
        return;
      }
      $("#current_time1").text(PlayThree.JoinData.playTime);
      PlayThree.initTimerWait();
    });
  },
  /**
   * 开奖倒计时
   */
  "initWait": function (callback) {
    $("#countDownRight1").hide();
    $("#countDownRight2").hide();
    $("#countDownRight3").hide();
    $("#wait_timedown1").text("00:00");
    // UIUtils.loading();
    var url = Config.WEB_SERVER_API + Config.URI.PLAY_THREE_RESULT_WAIT;
    HttpUtils.httpGet(url, {}, function (data) {
      // UIUtils.loaded();
      PlayThree.WaitData = data.rows || {};
      if (!PlayThree.WaitData) {
        $("#wait_timedown").text("服务停止");
        PlayThree.IsStop = true;
        return;
      }
      $("#current_time2").text(PlayThree.WaitData.playTime);
      $("#current_time3").text(PlayThree.WaitData.playTime);
      if (PlayThree.WaitData.nowTime <= PlayThree.WaitData.endTime) {//参与中
        $("#countDownRight1").show();
      }
      else if (PlayThree.WaitData.nowTime < PlayThree.WaitData.publishTime) {//已截止，待开奖
        $("#countDownRight2").show();
        PlayThree.initTimerOpen();
      }
      else{//开奖中
        $("#countDownRight3").show();
        setTimeout(function () {
          PlayThree.afterOpen();
        }, 30000);
      }
      if(callback){
        callback();
      }
    });
  },
  "initTimerWait":function(){
    //开始倒计时
    PlayThree.SecondTime = parseInt(Math.abs(PlayThree.JoinData.nowTime - PlayThree.JoinData.endTime) / 1000);
    PlayThree.Timer = setInterval(function () {
      PlayThree.SecondTime--;
      if (PlayThree.SecondTime <= 0) {
        //截止了
        PlayThree.IsStop = true;
        $("#wait_timedown").text("00:00:00");
        clearInterval(PlayThree.Timer);
        PlayThree.initWait(function(){
          // layer.alert('您好，第 ' + PlayThree.JoinData.playTime + ' 期已截止，当前期是第 ' + (PlayThree.WaitData.playTime + 1) + ' 期，参与时请确认您选择的期号！');
          layer.alert('您好，第 ' + PlayThree.JoinData.playTime + ' 期已截止，当前期是第 ' + (PlayThree.WaitData.playTime + 1) + ' 期，参与时请确认您选择的期号！', {time: 3000});
          PlayThree.initJoin();
        });
      } else {
        //计算出相差天数
        var days = Math.floor(PlayThree.SecondTime / (24 * 3600));
        //计算出小时数
        var leave1 = PlayThree.SecondTime % (24 * 3600);    //计算天数后剩余的秒数
        var hours = Math.floor(leave1 / 3600) + (days > 0 ? days * 24 : 0);
        hours = (hours < 10 ? '0' : '') + hours;
        //计算相差分钟数
        var leave2 = leave1 % 3600;        //计算小时数后剩余的毫秒数
        var minutes = Math.floor(leave2 / 60);
        minutes = (minutes < 10 ? '0' : '') + minutes;
        //计算相差秒数
        var leave3 = leave2 % 60;      //计算分钟数后剩余的毫秒数
        var seconds = Math.round(leave3);
        seconds = (seconds < 10 ? '0' : '') + seconds;
        $("#wait_timedown").text(hours + ":" + minutes + ":" + seconds);
      }
    }, 1000);
  },
  "initTimerOpen":function(){
    //开始倒计时
    PlayThree.SecondTime1 = parseInt(Math.abs(PlayThree.WaitData.nowTime - PlayThree.WaitData.publishTime) / 1000);
    PlayThree.Timer1 = setInterval(function () {
      PlayThree.SecondTime1--;
      if (PlayThree.SecondTime1 <= 0) {
        clearInterval(PlayThree.Timer1);
        $("#wait_timedown1").text("00:00");
        $("#countDownRight2").hide();
        $("#countDownRight3").show();
        setTimeout(function () {
          PlayThree.afterOpen();
        }, 10000);
      } else {
        //计算出相差天数
        var days = Math.floor(PlayThree.SecondTime1 / (24 * 3600));
        //计算出小时数
        var leave1 = PlayThree.SecondTime1 % (24 * 3600);    //计算天数后剩余的秒数
        var hours = Math.floor(leave1 / 3600) + (days > 0 ? days * 24 : 0);
        hours = (hours < 10 ? '0' : '') + hours;
        //计算相差分钟数
        var leave2 = leave1 % 3600;        //计算小时数后剩余的毫秒数
        var minutes = Math.floor(leave2 / 60);
        minutes = (minutes < 10 ? '0' : '') + minutes;
        //计算相差秒数
        var leave3 = leave2 % 60;      //计算分钟数后剩余的毫秒数
        var seconds = Math.round(leave3);
        seconds = (seconds < 10 ? '0' : '') + seconds;
        $("#wait_timedown1").text(minutes + ":" + seconds);
      }
    }, 1000);
  },
  "afterOpen":function(){
    PlayThree.initTodayCount();
    PlayThree.initFront();
    PlayThree.initWait();
    PlayThree.initMore();
    PlayThree.initToday();
    PlayThree.searchResultHistory();
    $("#btnSearchUserLog").click();
  },
  "initMore": function () {
    // UIUtils.loading();
    var url = Config.WEB_SERVER_API + Config.URI.PLAY_THREE_RESULT_FINISHED;
    HttpUtils.httpGet(url, {offset:1,limit:5}, function (data) {
      // UIUtils.loaded();
      $("#moreAward").empty();
      if(data && data.rows){
        for(var i=0;i<data.rows.length;i++){
          var item = data.rows[i];
          var html = ''+
          (i%2==0?'<tr>':'<tr bgcolor="f0f1f2">')+
            '<td>'+item.playTime+'期</td>'+
            '<td><span class="smallball">'+
            ' <em class="kuai3Ball num'+item.number1+'">'+item.number1+'</em>'+
            ' <em class="kuai3Ball num'+item.number2+'">'+item.number2+'</em>'+
            ' <em class="kuai3Ball num'+item.number3+'">'+item.number3+'</em>'+
            '</span></td>'+
            '<td>'+PlayThree.getRewardMode(item.modeName)+'</td>'+
            '<td><strong class="c_ba2636">'+item.sumNumber+'</strong></td>';
            if(item.sumNumber>10){
              html += '<td class="numForm big">大</td>'+
              '<td>&nbsp;</td>';
            }else{
              html += '<td>&nbsp;</td>'+
              '<td class="numForm small">小</td>';
            }
            if(item.sumNumber%2!=0){
              html += '<td class="numForm odd">单</td>'+
              '<td>&nbsp;</td>';
            }else{
              html += '<td>&nbsp;</td>'+
              '<td class="numForm dual">双</td>';
            }
            html += '<td>'+FormatUtils.parseTimestampToDateTime(item.publishTime)+'</td>'+
          '</tr>';
          $("#moreAward").append(html);
        }
      }
    });
  },
  "initToday":function(){
    var value = FormatUtils.formatterDate(new Date());
    $("#todayKaiJiang").find("tbody").empty();
    HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.PLAY_THREE_RESULT_DAY, {date:value}, function(data){
      if(data && data.rows.length>0){
        var html = "";
        for(var i=0;i<40;i++){
          html += "<tr>";
          for(var j=0;j<4;j++){
            var index = j*40 + i;
            var number = index+1;
            if(data.rows.length>index){
              html += '<td class="period">'+(number<10?'0':'')+number+'</td>';
              if(data.rows[index].modeName){
                var item = data.rows[index];
                var css = item.modeName=="三同号"? "c_090" : (item.modeName=="三不同号"?"c_1e50a2":(item.modeName=="三连号"?"c_ba2636":""));
                html += '<td class="awardTd">'+item.number1+' '+item.number2+' '+item.number3+'</td><td>'+item.sumNumber+'</td><td class="'+css+'">'+item.modeName+'</td>';
              }
              else{
                html += '<td>- -</td><td>&nbsp;</td><td>&nbsp;</td>';
              }
            }else{
              html += '<td class="period">&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>';
            }
          }
          html += "</tr>";
        }
        $("#todayKaiJiang").find("tbody").append(html);
      }
    });
  },
  "initSelectNumber":function(){
    table.render({
      elem: '#dg',
      height: 165,
      cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
      cols: [[
        { field: 'id', title: 'ID', hide: true }
        , { field: 'mode', title: '形态', width: 80, align: 'center', templet: function (d) { return PlayThree.formatMode(d); } }
        , { field: 'sixNumber', title: '选号', width: 263, align: 'left', templet: function (d) { return PlayThree.formatSelectNumber(d); } }
        , { field: 'sumMoney', title: '价格', width: 60, align: 'center', templet: function (d) { return FormatUtils.formatDG(d.sumMoney); } }
        , { fixed: 'right', title: '操作', toolbar: '#bar', width: 70, align: 'center' }
      ]],
      even: true, page: false, limit: 10000,
      data: PlayThree.SelectBallData
    });

    //监听行工具事件
    table.on('tool(dg)', function (obj) {
      var data = obj.data;
      if (obj.event === 'delete') {
        for (var i = 0; i < PlayThree.SelectBallData.length; i++) {
          if (PlayThree.SelectBallData[i].id == data.id) {
            PlayThree.SelectBallData.splice(i, 1);
            PlayThree.setTableListData();
            break;
          }
        }
        obj.del();
      } else if (obj.event === 'edit') {

      }
    });
  },
  "initResultLog":function(){
    var widthHistory = 36;
    table.render({
      elem: '#dgResultLog',
      cellMinWidth: 35, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
      cols: [[
        { field: 'playTime', title: '期号', align: 'center', width: 100, rowspan: 2 }
        , { field: 'number1', title: '开奖号码', rowspan: 2, width: 71, align: 'center', templet: function (d) { return d.number1 + ' &nbsp; ' + d.number2 + ' &nbsp; ' + d.number3; } }
        , { title: '开奖号码分布', colspan: 6, align: 'center'}
        , { title: '和值', colspan: 18, align: 'center'}
      ],[
        { field: 'n1', title: '1', width: widthHistory, align: 'center', templet: function (d) { return PlayThree.formatLogNumber('1', d.n1); } }
        , { field: 'n2', title: '2', width: widthHistory, align: 'center', templet: function (d) { return PlayThree.formatLogNumber('2', d.n2); } }
        , { field: 'n3', title: '3', width: widthHistory, align: 'center', templet: function (d) { return PlayThree.formatLogNumber('3', d.n3); } }
        , { field: 'n4', title: '4', width: widthHistory, align: 'center', templet: function (d) { return PlayThree.formatLogNumber('4', d.n4); } }
        , { field: 'n5', title: '5', width: widthHistory, align: 'center', templet: function (d) { return PlayThree.formatLogNumber('5', d.n5); } }
        , { field: 'n6', title: '6', width: widthHistory, align: 'center', templet: function (d) { return PlayThree.formatLogNumber('6', d.n6); } }
        , { field: 'sum3', title: '3', width: widthHistory, align: 'center', templet: function (d) { return PlayThree.formatLogSumNumber('3', d.sum3); } }
        , { field: 'sum4', title: '4', width: widthHistory, align: 'center', templet: function (d) { return PlayThree.formatLogSumNumber('4', d.sum4); } }
        , { field: 'sum5', title: '5', width: widthHistory, align: 'center', templet: function (d) { return PlayThree.formatLogSumNumber('5', d.sum5); } }
        , { field: 'sum6', title: '6', width: widthHistory, align: 'center', templet: function (d) { return PlayThree.formatLogSumNumber('6', d.sum6); } }
        , { field: 'sum7', title: '7', width: widthHistory, align: 'center', templet: function (d) { return PlayThree.formatLogSumNumber('7', d.sum7); } }
        , { field: 'sum8', title: '8', width: widthHistory, align: 'center', templet: function (d) { return PlayThree.formatLogSumNumber('8', d.sum8); } }
        , { field: 'sum9', title: '9', width: widthHistory, align: 'center', templet: function (d) { return PlayThree.formatLogSumNumber('9', d.sum9); } }
        , { field: 'sum10', title: '10', width: widthHistory, align: 'center', templet: function (d) { return PlayThree.formatLogSumNumber('10', d.sum10); } }
        , { field: 'sum11', title: '11', width: widthHistory, align: 'center', templet: function (d) { return PlayThree.formatLogSumNumber('11', d.sum11); } }
        , { field: 'sum12', title: '12', width: widthHistory, align: 'center', templet: function (d) { return PlayThree.formatLogSumNumber('12', d.sum12); } }
        , { field: 'sum13', title: '13', width: widthHistory, align: 'center', templet: function (d) { return PlayThree.formatLogSumNumber('13', d.sum13); } }
        , { field: 'sum14', title: '14', width: widthHistory, align: 'center', templet: function (d) { return PlayThree.formatLogSumNumber('14', d.sum14); } }
        , { field: 'sum15', title: '15', width: widthHistory, align: 'center', templet: function (d) { return PlayThree.formatLogSumNumber('15', d.sum15); } }
        , { field: 'sum16', title: '16', width: widthHistory, align: 'center', templet: function (d) { return PlayThree.formatLogSumNumber('16', d.sum16); } }
        , { field: 'sum17', title: '17', width: widthHistory, align: 'center', templet: function (d) { return PlayThree.formatLogSumNumber('17', d.sum17); } }
        , { field: 'sum18', title: '18', width: widthHistory, align: 'center', templet: function (d) { return PlayThree.formatLogSumNumber('18', d.sum18); } }
      ]],
      even: true, page:false, limit:10000
    });
    var loadResultLogData = function (date) {
      var value = $("#tbResultLogDate").val() || FormatUtils.formatterDate(new Date());
      HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.PLAY_THREE_RESULT_LOG_DAY, {date:value}, function(data){
        //这里以搜索为例
        table.reload("dgResultLog", {
          data: data.rows
        });
      });
    };
    $("#btnSearchResultLog").on('click', function () {
      loadResultLogData();
    });
    $("#btnSearchResultLog").click();
  },
  "initResultHistory":function(){
    $("#btnSearchResultHistory").on('click', function () {
      PlayThree.searchResultHistory();
    });
    PlayThree.searchResultHistory();
  },
  "searchResultHistory":function(){
    var value = $("#tbResultHistoryDate").val() || FormatUtils.formatterDate(new Date());
    $("#resultHistoryTable").find("tbody").empty();
    HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.PLAY_THREE_RESULT_DAY, {date:value}, function(data){
      if(data && data.rows.length>0){
        var html = "";
        for(var i=0;i<40;i++){
          html += "<tr>";
          for(var j=0;j<4;j++){
            var index = j*40 + i;
            var number = index+1;
            if(data.rows.length>index){
              html += '<td class="period">'+(number<10?'0':'')+number+'</td>';
              if(data.rows[index].modeName){
                var item = data.rows[index];
                var css = item.modeName=="三同号"? "c_090" : (item.modeName=="三不同号"?"c_1e50a2":(item.modeName=="三连号"?"c_ba2636":""));
                html += '<td class="awardTd">'+item.number1+' '+item.number2+' '+item.number3+'</td><td>'+item.sumNumber+'</td><td class="'+css+'">'+item.modeName+'</td>';
              }
              else{
                html += '<td>- -</td><td>&nbsp;</td><td>&nbsp;</td>';
              }
            }else{
              html += '<td class="period">&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>';
            }
          }
          html += "</tr>";
        }
        $("#resultHistoryTable").find("tbody").append(html);
      }
    });
  },
  "initUserLog":function(){
    table.render({
      elem: '#dgUserLog',
      limit: 50,
      url: Config.WEB_SERVER_API + Config.URI.USER_PLAY_THREE_LIST,
      // width: 830,
      // height: 580,
      request: {
        pageName: 'offset' //页码的参数名称，默认：page
        , limitName: 'limit' //每页数据量的参数名，默认：limit
      },
      // cellMinWidth: 60, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
      cols: [[
        { field: 'playTime', title: '期号', width: 100, align: 'center', rowspan: 2}
        , { field: 'mode', title: '形态', width: 90, align: 'center', rowspan: 2, templet: function (d) { return PlayThree.formatMode(d); }}
        , { field: 'statusName', title: '开奖结果', align: 'center', rowspan: 2, width: 70, templet: function (d) { return PlayThree.formatPlayStatus(d); }}
        , { field: 'number', title: '选号', width: 217, rowspan: 2, templet: function (d) { return PlayThree.formatSelectNumber(d); } }          
        , { field: 'countNumber', title: '参与数量', width: 70, align:'center', rowspan: 2}
        , { field: 'sumMoney', title: '参与金额', align: 'center', width: 70, rowspan: 2, templet: function (d) { return FormatUtils.formatDG(d.sumMoney); } }
        , { field: 'createTime', title: '参与时间', align: 'center', width: 150, rowspan: 2, templet: function (d) { return FormatUtils.parseTimestampToDateTime(d.createTime); } }
        , { title: '中奖', colspan: 3, align: 'center'}
      ],[
        { field: 'countReward', title: '注数', width: 70, align:'center'}
        , { field: 'rewardCodeName', title: '奖项', width: 70, align: 'center' }
        , { field: 'rewardMoney', title: '金额', width: 70, align: 'center', templet: function (d) { return FormatUtils.formatDG(d.rewardMoney); } }

      ]]
      , page: true, loading: true,
      response: {
        statusCode: "200" //重新规定成功的状态码为 200，table 组件默认为 0
      },
      parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
        return {
          "code": parseInt(res.resultCode), //解析接口状态
          "msg": res.resultMsg, //解析提示文本
          "count": res.total, //解析数据长度
          "data": res.rows //解析数据列表
        };
      }
    });
    var loadLogData = function () {
      //这里以搜索为例
      table.reload("dgUserLog", {
        where: { //设定异步数据接口的额外参数，任意设
          status: $("#comboUserLogStatus").val()
        }
        , page: {
          curr: 1 //重新从第 1 页开始
        }
      });
    };

    $("#btnSearchUserLog").on('click', function () {
      loadLogData();
    });
    $("#btnSearchUserLog").click();
  },
  "initBottomBox":function(){
    $(".bottomBox").find(".more").on('click', function(){
      var box = $(this).closest(".bottomBox");
      if(box.hasClass("active")){//关闭
        box.removeClass("active");
        box.find(".helpContent").hide();
        box.find("em").hide();
        box.find("b").show();
        box.find(".closeIcon").hide();
        box.find(".openIcon").css("display", "inline-block");        
      }else{
        box.addClass("active");
        box.find(".helpContent").show();
        box.find("em").show();
        box.find("b").hide();
        box.find(".closeIcon").css("display", "inline-block");
        box.find(".openIcon").hide();
      }
    });
    $("#awardTableInfo").find(".more").click();
  },
  "getRewardMode":function(modeName){
    switch(modeName){
      case "三同号":
      return "<em>三同号</em><em>二同号复选</em><em>二同号单选</em>";
      case "三不同号":
      return "<em>三不同号</em><em>二不同号</em>";      
      case "三连号":
      return "<em>三连号</em><em>三不同号</em><em>二不同号</em>";     
      case "二同号":
      return "<em>二同号复选</em><em>二同号单选</em><em>二不同号</em>";
    }
    return "";
  },
  "getRewardStrong": function (money) {
    if (money > 0) {
      return '<strong style="color:red;"> ' + money + ' </strong>';
    } else {
      return '<strong style="color:green;"> ' + money + ' </strong>';
    }
  },
  "randomSelectOrangeBall": function () {
    $("#areaOrangeBall").find("a.active").removeClass("active");
    var arr = [];
    for (var i = 1; i < 34; i++) {
      arr.push(i);
    }
    var arrS = [];
    for (var i = 0; i < 6; i++) {
      var random = Math.floor(Math.random() * arr.length + 1);
      arrS.push(arr[random - 1]);
      arr.splice(random - 1, 1);
    }
    for (var i = 0; i < arrS.length; i++) {
      $("#areaOrangeBall").find("a.js_ball").eq(arrS[i] - 1).addClass("active");
    }
    PlayThree.setSureSelectButton();
  },
  "clearSelectOrangeBall": function () {
    $("#areaOrangeBall").find("a.js_ball").removeClass("active");
    PlayThree.setSureSelectButton();
  },
  "selectGreenBall": function (obj) {
    if (obj.hasClass("active")) {
      obj.removeClass("active");
    } else {
      $("#areaGreenBall").find("a.active").removeClass("active");
      obj.addClass("active");
    }
    PlayThree.setSureSelectButton();
  },
  "randomSelectGreenBall": function () {
    $("#areaGreenBall").find("a.active").removeClass("active");
    var random = Math.floor(Math.random() * 16 + 1);
    $("#areaGreenBall").find("a.js_ball").eq(random - 1).addClass("active");
    PlayThree.setSureSelectButton();
  },
  "clearSelectGreenBall": function () {
    $("#areaGreenBall").find("a.js_ball").removeClass("active");
    PlayThree.setSureSelectButton();
  },
  "setSureSelectButton": function () {
    if (PlayThree.IsStop) {
      layer.alert('本期参与已截止！', { title: '出错了', icon: 5 });
      return;
    }
    var len1 = $("#areaOrangeBall").find("a.active").length;
    var len2 = $("#areaGreenBall").find("a.active").length;
    if (len1 == 6 && len2 == 1) {
      $("#selectBall").removeClass("layui-btn-disabled");
      $("#countSum").text("1");
      if (PlayThree.WaitData) {
        $("#countMoney").text(PlayThree.WaitData.perMoney * 1);
      }
    } else {
      $("#selectBall").addClass("layui-btn-disabled");
      $("#countSum").text("0");
      $("#countMoney").text("0");
    }
    $("#countOrange").text(len1);
    $("#countGreen").text(len2);
  },
  "selectBallHeZhi": function () {
    if (PlayThree.IsStop) {
      layer.alert('本期参与已截止！', { title: '出错了', icon: 5 });
      return;
    }
    var len1 = $("#areaOrangeBall").find("a.active").length;
    var len2 = $("#areaGreenBall").find("a.active").length;
    if (len1 == 6 && len2 == 1) {
      var sixNumber = "";
      $.each($("#areaOrangeBall").find("a.active"), function (i, item) {
        sixNumber += $(item).text() + ",";
      });
      sixNumber = sixNumber.substring(0, sixNumber.length - 1);
      var oneNumber = $("#areaGreenBall").find("a.active").eq(0).text();
      var sumMoney = PlayThree.WaitData.perMoney * 1;
      PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 0, sixNumber: sixNumber, oneNumber: oneNumber, sumMoney: sumMoney });
      PlayThree.setTableListData();
      $("#areaOrangeBall").find("a.active").removeClass("active");
      $("#areaGreenBall").find("a.active").removeClass("active");
      PlayThree.setSureSelectButton();
    } else {
      layer.alert('请选择6个橘色球和1个绿色球！', { title: '出错了', icon: 5 });
    }
  },
  "randomOneBall": function () {
    if ($("#k3_tab").find("li[name='hz']").hasClass("active")) {
      PlayThreeHeZhi.randomOneBall();
    } else if ($("#k3_tab").find("li[name='3tx']").hasClass("active")) {
      PlayThree3Tx.randomOneBall();
    }else if ($("#k3_tab").find("li[name='3dx']").hasClass("active")) {
      PlayThree3Dx.randomOneBall();
    }else if ($("#k3_tab").find("li[name='3bt']").hasClass("active")) {
      PlayThree3Bt.randomOneBall();
    }else if ($("#k3_tab").find("li[name='3lt']").hasClass("active")) {
      PlayThree3Lt.randomOneBall();
    }else if ($("#k3_tab").find("li[name='2fx']").hasClass("active")) {
      PlayThree2Fx.randomOneBall();
    }else if ($("#k3_tab").find("li[name='2dx']").hasClass("active")) {
      PlayThree2Dx.randomOneBall();
    }else if ($("#k3_tab").find("li[name='2bt']").hasClass("active")) {
      PlayThree2Bt.randomOneBall();
    }else if ($("#k3_tab").find("li[name='1tx']").hasClass("active")) {
      PlayThree1Tx.randomOneBall();
    }
    
  },
  "randomNumberBall": function () {
    if ($("#k3_tab").find("li[name='hz']").hasClass("active")) {
      PlayThreeHeZhi.randomNumberBall();
    } else if ($("#k3_tab").find("li[name='3tx']").hasClass("active")) {
      PlayThree3Tx.randomNumberBall();
    }else if ($("#k3_tab").find("li[name='3dx']").hasClass("active")) {
      PlayThree3Dx.randomNumberBall();
    }else if ($("#k3_tab").find("li[name='3bt']").hasClass("active")) {
      PlayThree3Bt.randomNumberBall();
    }else if ($("#k3_tab").find("li[name='3lt']").hasClass("active")) {
      PlayThree3Lt.randomNumberBall();
    }else if ($("#k3_tab").find("li[name='2fx']").hasClass("active")) {
      PlayThree2Fx.randomNumberBall();
    }else if ($("#k3_tab").find("li[name='2dx']").hasClass("active")) {
      PlayThree2Dx.randomNumberBall();
    }else if ($("#k3_tab").find("li[name='2bt']").hasClass("active")) {
      PlayThree2Bt.randomNumberBall();
    }else if ($("#k3_tab").find("li[name='1tx']").hasClass("active")) {
      PlayThree1Tx.randomNumberBall();
    }
  },
  "randomBall": function (count) {
    var result = [];
    if (count > 0 && count <= 100) {
      var arrData = [];
      for (var i = 1; i < 34; i++) {
        arrData.push((i < 10 ? '0' : '') + i);
      }
      for (var j = 0; j < count; j++) {
        var arr = Utils.copyJson(arrData);
        var arrS = [];
        for (var i = 0; i < 6; i++) {
          var random = Math.floor(Math.random() * arr.length + 1);
          arrS.push(arr[random - 1]);
          arr.splice(random - 1, 1);
        }
        var random = Math.floor(Math.random() * 16 + 1);
        var arrO = arrData[random - 1];
        arrS = PlayThree.orderBall(arrS);
        result.push({ six: arrS.join(","), one: arrO });
      }
    }
    return result;
  },
  "orderBall": function (arr) {
    for (i = 0; i < arr.length - 1; i++) {
      for (j = 0; j < arr.length - 1 - i; j++) {
        if (parseInt(arr[j]) > parseInt(arr[j + 1])) {
          var temp = arr[j];
          arr[j] = arr[j + 1];
          arr[j + 1] = temp;
        }
      }
    }
    return arr;
  },
  "clearAllBall": function () {
    if (PlayThree.IsStop) {
      layer.alert('本期参与已截止！', { title: '出错了', icon: 5 });
      return;
    }
    if (PlayThree.SelectBallData.length > 0) {
      layer.confirm('您确定删除所有选号？', { icon: 3, title: '提示' }, function (index) {
        PlayThree.SelectBallData = [];
        PlayThree.setTableListData();
        layer.close(index);
      });
    }
  },
  "setTableListData": function () {
    if (PlayThree.IsStop) {
      layer.alert('本期参与已截止！', { title: '出错了', icon: 5 });
      return;
    }
    // table.render({ elem: '#dg', data : PlayThree.SelectBallData })
    table.reload("dg", { data: PlayThree.SelectBallData });
    var sumCount = 0;
    var sumMoney = 0;
    for (var i = 0; i < PlayThree.SelectBallData.length; i++) {
      sumCount += PlayThree.SelectBallData[i].sumCount;
      sumMoney += PlayThree.SelectBallData[i].sumMoney;
    }
    $("#countAllSum").text(sumCount);
    $("#countAllMoney").text(sumMoney);
  },
  "createUUID": function () {
    return Math.round(Math.random() * 100000000);
  },
  "submitBall": function () {
    if (PlayThree.IsStop) {
      layer.alert('本期参与已截止！', { title: '出错了', icon: 5 });
      return false;
    }
    if (PlayThree.SelectBallData.length <= 0) {
      layer.alert('请选择选号！', { title: '出错了', icon: 5 });
      return false;
    }
    DataUtils.getUserMoneyList2(function (data) {
      if (!data || data.money < parseFloat($("#countAllMoney").val())) {
        layer.alert('余额不足，请您先充值！', { title: '出错了', icon: 5 });
        return false;
      }
      var t = new Date().getTime();
      layer.open({
        type: 2
        , title: '我要参与'
        , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
        , area: ['500px', '340px']
        , id: 'layerDemo' //防止重复弹出
        , content: 'threeadd.html?t=' + t + "&count=" + $("#countAllSum").text() + "&money=" + $("#countAllMoney").text()
        , shade: 0.2 //不显示遮罩
        , yes: function () {
          layer.closeAll();
        }
      });
    });
  },
  "formatAllMoney": function (allMoney) {
    if (allMoney != null) {
      if (allMoney < 10000 * 10000) { //小于1亿
        if (allMoney > 10000) {
          allMoneyName = allMoney / 10000 + "万" + allMoney % 10000 + "";
        } else {
          allMoneyName = allMoney + "";
        }
      } else {
        allMoneyName = allMoney / (10000 * 10000) + "亿" + allMoney / 10000 + "万";
      }
    } else {
      allMoneyName = allMoney + "";
    }
    return allMoneyName;
  },
  "formatSelectNumber": function (d) {
    return '<span style="color:red;">' + d.number + '</span>';
  },
  "formatPlayStatus": function (d) {
    if (d.status == 1) {
      return '<span style="color:red;">' + d.statusName + '</span>';
    }
    if (d.status == 0) {
      return '<span style="color:green;">' + d.statusName + '</span>';
    }
    return d.statusName;
  },
  "formatLogNumber": function (number, count) {
    if (count > 0)
      return count;
    return '<span style="color:white;background-color: #1E9FFF; border-radius: 10px; padding: 2px 6px;">' + number + '</span>';
  },
  "formatLogSumNumber": function (number, count) {
    if (count > 0)
      return count;
    return '<div style="color:white;background-color: red; width: 22px;height: 23px;line-height: 22px;margin-top: 4px;">' + number + '</div>';
  },
  "formatMode": function (d) {
    switch (d.mode) {
      case 1:
        return "和值";
      case 10:
      return "一个号通选";
      case 20:
        return "二同号复选";
      case 21:
        return "二同号单选";
      case 22:
        return "二不同号";
      case 30:
        return "三同号通选";
      case 31:
        return "三同号单选";
      case 32:
        return "三不同号";
      case 33:
        return "三连号通选";
    }
    return "";
  }
};

//和值
var PlayThreeHeZhi = {
  "init": function () {
    $(".assistBox").find(".assistBtn").on('click', function(){
      if (!PlayThree.WaitData) return;
      var code = $(this).attr("data-xt");
      if($(this).hasClass("active")){
        $(this).removeClass("active");
      }
      else{
        $(this).addClass("active");
        if(code=="小"){
          $(".assistBox").find(".assistBtn[data-xt='大']").removeClass("active");
        }
        if(code=="大"){
          $(".assistBox").find(".assistBtn[data-xt='小']").removeClass("active");
        }
        if(code=="单"){
          $(".assistBox").find(".assistBtn[data-xt='双']").removeClass("active");
        }
        if(code=="双"){
          $(".assistBox").find(".assistBtn[data-xt='单']").removeClass("active");
        }
      }
      PlayThreeHeZhi.selectType();
    });
    $("#panl_hz").find(".js_ball").on('click', function () {
      if (!PlayThree.WaitData) return;
      if ($(this).hasClass("active")) {
        $(this).removeClass("active");
      } else {
        $(this).addClass("active");
      }
      PlayThreeHeZhi.select();
    });
    $("#panl_hz").find("li[data-action='clear']").on('click', function () {
      $(this).parent().find(".js_ball").removeClass("active");
      PlayThreeHeZhi.select();
    });
    $("#panl_hz #selectBall").on('click', function () {
      PlayThreeHeZhi.selectBall();
    });
  },
  "selectType":function(){
    var type = "";
    $.each($(".assistBox").find(".assistBtn"), function(i, item){
      if($(this).hasClass("active")){
        type+=$(this).attr("data-xt");
      }
    });
    $("#panl_hz").find(".js_ball").removeClass("active").addClass("active");
    if(type.indexOf("小")>=0){//小
      for(var i=11;i<=18;i++){
        $("#panl_hz").find("li[data-value='"+i+"']").removeClass("active");
      }
      if(type.indexOf("单")>=0){
        $("#panl_hz").find("li[data-value='4']").removeClass("active");
        $("#panl_hz").find("li[data-value='6']").removeClass("active");
        $("#panl_hz").find("li[data-value='8']").removeClass("active");
        $("#panl_hz").find("li[data-value='10']").removeClass("active");
      }
      if(type.indexOf("双")>=0){
        $("#panl_hz").find("li[data-value='3']").removeClass("active");
        $("#panl_hz").find("li[data-value='5']").removeClass("active");
        $("#panl_hz").find("li[data-value='7']").removeClass("active");
        $("#panl_hz").find("li[data-value='9']").removeClass("active");
      }
    }
    else if(type.indexOf("大")>=0){//大
      for(var i=3;i<=10;i++){
        $("#panl_hz").find("li[data-value='"+i+"']").removeClass("active");
      }
      if(type.indexOf("单")>=0){
        $("#panl_hz").find("li[data-value='12']").removeClass("active");
        $("#panl_hz").find("li[data-value='14']").removeClass("active");
        $("#panl_hz").find("li[data-value='16']").removeClass("active");
        $("#panl_hz").find("li[data-value='18']").removeClass("active");
      }
      if(type.indexOf("双")>=0){
        $("#panl_hz").find("li[data-value='11']").removeClass("active");
        $("#panl_hz").find("li[data-value='13']").removeClass("active");
        $("#panl_hz").find("li[data-value='15']").removeClass("active");
        $("#panl_hz").find("li[data-value='17']").removeClass("active");
      }
    }
    else if(type.indexOf("单")>=0){//单
      for(var i=3;i<=18;i++){
        if(i%2==0){
          $("#panl_hz").find("li[data-value='"+i+"']").removeClass("active");
        }
      }
    }
    else if(type.indexOf("双")>=0){//双
      for(var i=3;i<=18;i++){
        if(i%2!=0){
          $("#panl_hz").find("li[data-value='"+i+"']").removeClass("active");
        }
      }
    }else{
      $("#panl_hz").find(".js_ball").removeClass("active");
    }
    PlayThreeHeZhi.select();
  },
  "select": function () {
    if (!PlayThree.WaitData) return;
    var count = 0;
    var perMoney = PlayThree.WaitData ? PlayThree.WaitData.perMoney : 0;
    var rewardSum = 0, min = 0, max = 0;
    $.each($("#panl_hz").find(".js_ball"), function (i, item) {
      if ($(this).hasClass("active")) {
        count++;
        var reward = parseFloat($(this).attr("data-money") || 0);
        rewardSum += reward;
        if (i == 0) {
          min = reward;
          max = reward;
        } else {
          if (reward <= min) {
            min = reward;
          } else if (reward >= max) {
            max = reward;
          }
        }
      }
    });
    $("#panl_hz").find(".select_count").text(count);
    $("#panl_hz").find(".select_money").text(count * perMoney);
    if (count > 0) {
      if (count > 1) {
        $("#panl_hz").find(".select_reward").html('若中奖，奖金' + PlayThree.getRewardStrong(min) + '至' + PlayThree.getRewardStrong(max) + 'DG，盈利' + PlayThree.getRewardStrong(min - count * perMoney) + '至' + PlayThree.getRewardStrong(max - count * perMoney) + 'DG');
      } else {
        $("#panl_hz").find(".select_reward").html('若中奖，奖金<strong style="color:red;"> ' + rewardSum + ' </strong>DG，盈利<strong style="color:red;"> ' + (rewardSum - perMoney) + ' </strong>DG');
      }
    } else {
      $("#panl_hz").find(".select_reward").text("");
    }
    PlayThreeHeZhi.setSureSelectButton(count);
  },
  "setSureSelectButton": function (count) {
    if (count > 0) {
      $("#panl_hz #selectBall").removeClass("layui-btn-disabled");
    } else {
      $("#panl_hz #selectBall").addClass("layui-btn-disabled");
    }
  },
  "selectBall": function () {
    var arr = [];
    $.each($("#panl_hz").find(".js_ball"), function (i, item) {
      if ($(this).hasClass("active")) {
        arr.push($(this).attr("data-value"));
      }
    });
    if (arr.length > 0) {
      var sumMoney = PlayThree.WaitData.perMoney * arr.length;
      PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 1, number: arr.join(","), sumMoney: sumMoney, sumCount: arr.length });
      PlayThree.setTableListData();
      $(".assistBox").find(".assistBtn").removeClass("active");
      $("#panl_hz").find(".js_ball").removeClass("active");
      PlayThreeHeZhi.select();
    }
    else {
      layer.alert('请至少选择3-18任意一个和值！', { title: '出错了', icon: 5 });
    }
  },
  "randomOneBall": function () {
    var arr = this.randomBall(1);
    var sumMoney = PlayThree.WaitData.perMoney * 1;
    PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 1, number: arr[0], sumMoney: sumMoney, sumCount: 1 });
    PlayThree.setTableListData();
  },
  "randomNumberBall": function () {
    var count = $("#txtNumberBall").val() || "5";
    if (parseInt(count) > 100 || parseInt(count) <= 0) {
      layer.alert('机选最多100注！', { title: '出错了', icon: 5 });
      return false;
    }
    var arr = this.randomBall(parseInt(count));
    if (arr.length > 0) {
      var sumMoney = PlayThree.WaitData.perMoney * 1;
      for (var i = 0; i < arr.length; i++) {
        PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 1, number: arr[i], sumMoney: sumMoney, sumCount: 1 });
      }
      PlayThree.setTableListData();
    }
    $("#txtNumberBall").val("");
  },
  "randomBall": function (count) {
    var result = [];
    if (count > 0 && count <= 100) {
      var arrData = [];
      for (var i = 3; i < 19; i++) {
        arrData.push(i);
      }
      var arr = Utils.copyJson(arrData);
      for (var j = 0; j < count; j++) {
        var random = Math.floor(Math.random() * arr.length + 1);
        result.push(arr[random - 1]);
      }
    }
    return result;
  }
};


//3通选
var PlayThree3Tx = {
  "init": function () {
    $("#panl_3tx").find(".js_ball").on('click', function () {
      if (!PlayThree.WaitData) return;
      if ($(this).hasClass("active")) {
        $(this).removeClass("active");
      } else {
        $(this).addClass("active");
      }
      PlayThree3Tx.select();
    });
    $("#panl_3tx").find("li[data-action='clear']").on('click', function () {
      $(this).parent().find(".js_ball").removeClass("active");
      PlayThree3Tx.select();
    });
    $("#panl_3tx #selectBall").on('click', function () {
      PlayThree3Tx.selectBall();
    });
  },
  "select": function () {
    if (!PlayThree.WaitData) return;
    var count = 0;
    var perMoney = PlayThree.WaitData ? PlayThree.WaitData.perMoney : 0;
    var rewardSum = 0;
    if ($("#panl_3tx").find(".js_ball").hasClass("active")) {
      var reward = parseFloat($("#panl_3tx").find(".js_ball").attr("data-money") || 0);
      rewardSum += reward;
      count = 1;
    }
    $("#panl_3tx").find(".select_count").text(count);
    $("#panl_3tx").find(".select_money").text(perMoney * count);
    if (count > 0) {
      $("#panl_3tx").find(".select_reward").html('若中奖，奖金' + PlayThree.getRewardStrong(rewardSum) + 'DG，盈利' + PlayThree.getRewardStrong(rewardSum - perMoney) + 'DG');
    } else {
      $("#panl_3tx").find(".select_reward").text("");
    }
    PlayThree3Tx.setSureSelectButton(count);
  },
  "setSureSelectButton": function (count) {
    if (PlayThree.IsStop) {
      layer.alert('本期参与已截止！', { title: '出错了', icon: 5 });
      return;
    }
    if (count > 0) {
      $("#panl_3tx #selectBall").removeClass("layui-btn-disabled");
    } else {
      $("#panl_3tx #selectBall").addClass("layui-btn-disabled");
    }
  },
  "selectBall": function () {
    if (PlayThree.IsStop) {
      layer.alert('本期参与已截止！', { title: '出错了', icon: 5 });
      return;
    }
    if ($("#panl_3tx").find(".js_ball").hasClass("active")) {
      var sumMoney = PlayThree.WaitData.perMoney;
      PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 30, number: "三同号通选", sumMoney: sumMoney, sumCount: 1 });
      PlayThree.setTableListData();
      $("#panl_3tx").find(".js_ball").removeClass("active");
      PlayThree3Tx.select();
    }
    else {
      layer.alert('请选择三同号通选！', { title: '出错了', icon: 5 });
    }
  },
  "randomOneBall": function () {
    var arr = this.randomBall(1);
    var sumMoney = PlayThree.WaitData.perMoney * 1;
    PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 30, number: "三同号通选", sumMoney: sumMoney, sumCount: 1 });
    PlayThree.setTableListData();
  },
  "randomNumberBall": function () {
    var count = $("#txtNumberBall").val() || "5";
    if (parseInt(count) > 100 || parseInt(count) <= 0) {
      layer.alert('机选最多100注！', { title: '出错了', icon: 5 });
      return false;
    }
    count = parseInt(count);
    var sumMoney = PlayThree.WaitData.perMoney * 1;
    for (var i = 0; i < count; i++) {
      PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 30, number: "三同号通选", sumMoney: sumMoney, sumCount: 1 });
    }
    PlayThree.setTableListData();
    $("#txtNumberBall").val("");
  },
  "randomBall": function (count) {
    var result = [];
    if (count > 0 && count <= 100) {
      var arrData = [];
      for (var i = 3; i < 19; i++) {
        arrData.push(i);
      }
      var arr = Utils.copyJson(arrData);
      for (var j = 0; j < count; j++) {
        var random = Math.floor(Math.random() * arr.length + 1);
        result.push(arr[random - 1]);
      }
    }
    return result;
  }
};

//3单选
var PlayThree3Dx = {
  "init": function () {
    $("#panl_3dx").find(".js_ball").on('click', function () {
      if (!PlayThree.WaitData) return;
      if ($(this).hasClass("active")) {
        $(this).removeClass("active");
      } else {
        $(this).addClass("active");
      }
      PlayThree3Dx.select();
    });
    $("#panl_3dx").find("li[data-action='clear']").on('click', function () {
      $(this).parent().find(".js_ball").removeClass("active");
      PlayThree3Dx.select();
    });
    $("#panl_3dx #selectBall").on('click', function () {
      PlayThree3Dx.selectBall();
    });
  },
  "select": function () {
    if (!PlayThree.WaitData) return;
    var count = 0;
    var perMoney = PlayThree.WaitData ? PlayThree.WaitData.perMoney : 0;
    var rewardSum = 0;
    $.each($("#panl_3dx").find(".js_ball"), function(i, item) {
      if($(this).hasClass("active")){
        var reward = parseFloat($(this).attr("data-money") || 0);
        rewardSum = reward;
        count += 1;
      }
    });
    $("#panl_3dx").find(".select_count").text(count);
    $("#panl_3dx").find(".select_money").text(perMoney * count);
    if (count > 0) {
      $("#panl_3dx").find(".select_reward").html('若中奖，奖金' + PlayThree.getRewardStrong(rewardSum) + 'DG，盈利' + PlayThree.getRewardStrong(rewardSum - perMoney * count) + 'DG');
    } else {
      $("#panl_3dx").find(".select_reward").text("");
    }
    PlayThree3Dx.setSureSelectButton(count);
  },
  "setSureSelectButton": function (count) {
    if (PlayThree.IsStop) {
      layer.alert('本期参与已截止！', { title: '出错了', icon: 5 });
      return;
    }
    if (count > 0) {
      $("#panl_3dx #selectBall").removeClass("layui-btn-disabled");
    } else {
      $("#panl_3dx #selectBall").addClass("layui-btn-disabled");
    }
  },
  "selectBall": function () {
    if (PlayThree.IsStop) {
      layer.alert('本期参与已截止！', { title: '出错了', icon: 5 });
      return;
    }
    var count = 0;
    var perMoney = PlayThree.WaitData ? PlayThree.WaitData.perMoney : 0;
    $.each($("#panl_3dx").find(".js_ball"), function(i, item) {
      if($(this).hasClass("active")){
        count += 1;
        var code = $(this).attr("data-value");
        PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 31, number: code+" "+code+" "+code, sumMoney: perMoney, sumCount: 1 });
      }
    });
    if (count > 0) {
      PlayThree.setTableListData();
      $("#panl_3dx").find(".js_ball").removeClass("active");
      PlayThree3Dx.select();
    }
    else {
      layer.alert('请选择一个号码！', { title: '出错了', icon: 5 });
    }
  },
  "randomOneBall": function () {
    var arr = this.randomBall(1);
    var sumMoney = PlayThree.WaitData.perMoney * 1;
    PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 31, number: arr[0]+" "+arr[0]+" "+arr[0], sumMoney: sumMoney, sumCount: 1 });
    PlayThree.setTableListData();
  },
  "randomNumberBall": function () {
    var count = $("#txtNumberBall").val() || "5";
    if (parseInt(count) > 100 || parseInt(count) <= 0) {
      layer.alert('机选最多100注！', { title: '出错了', icon: 5 });
      return false;
    }
    count = parseInt(count);
    var sumMoney = PlayThree.WaitData.perMoney * 1;
    var arr = this.randomBall(count);
    for (var i = 0; i < count; i++) {
      PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 31, number: arr[i]+" "+arr[i]+" "+arr[i], sumMoney: sumMoney, sumCount: 1 });
    }
    PlayThree.setTableListData();
    $("#txtNumberBall").val("");
  },
  "randomBall": function (count) {
    var result = [];
    if (count > 0 && count <= 100) {
      var arrData = [];
      for (var i = 1; i < 7; i++) {
        arrData.push(i);
      }
      var arr = Utils.copyJson(arrData);
      for (var j = 0; j < count; j++) {
        var random = Math.floor(Math.random() * arr.length + 1);
        result.push(arr[random - 1]);
      }
    }
    return result;
  }
};

//3不同号
var PlayThree3Bt = {
  "init": function () {
    $("#panl_3bt").find(".js_ball").on('click', function () {
      if (!PlayThree.WaitData) return;
      if ($(this).hasClass("active")) {
        $(this).removeClass("active");
      } else {
        $(this).addClass("active");
      }
      PlayThree3Bt.select();
    });
    $("#panl_3bt").find("li[data-action='clear']").on('click', function () {
      $(this).parent().find(".js_ball").removeClass("active");
      PlayThree3Bt.select();
    });
    $("#panl_3bt #selectBall").on('click', function () {
      PlayThree3Bt.selectBall();
    });
  },
  "select": function () {
    if (!PlayThree.WaitData) return;
    var count = 0;
    var perMoney = PlayThree.WaitData ? PlayThree.WaitData.perMoney : 0;
    var rewardSum = 0;
    $.each($("#panl_3bt").find(".js_ball"), function(i, item) {
      if($(item).hasClass("active")){
        var reward = parseFloat($(item).attr("data-money") || 0);
        rewardSum = reward;
        count += 1;
      }
    });
    var sumCount = this.getPlayCount(count);
    $("#panl_3bt").find(".select_count").text(sumCount);
    $("#panl_3bt").find(".select_money").text(perMoney * sumCount);
    if (sumCount > 0) {
      $("#panl_3bt").find(".select_reward").html('若中奖，奖金' + PlayThree.getRewardStrong(rewardSum) + 'DG，盈利' + PlayThree.getRewardStrong(rewardSum - perMoney * sumCount) + 'DG');
    } else {
      $("#panl_3bt").find(".select_reward").text("");
    }
    PlayThree3Bt.setSureSelectButton(count);
  },
  "setSureSelectButton": function (count) {
    if (PlayThree.IsStop) {
      layer.alert('本期参与已截止！', { title: '出错了', icon: 5 });
      return;
    }
    if (count >= 3) {
      $("#panl_3bt #selectBall").removeClass("layui-btn-disabled");
    } else {
      $("#panl_3bt #selectBall").addClass("layui-btn-disabled");
    }
  },
  "selectBall": function () {
    if (PlayThree.IsStop) {
      layer.alert('本期参与已截止！', { title: '出错了', icon: 5 });
      return;
    }
    var count = 0;
    var perMoney = PlayThree.WaitData ? PlayThree.WaitData.perMoney : 0;
    var code = "";
    $.each($("#panl_3bt").find(".js_ball"), function(i, item) {
      if($(this).hasClass("active")){
        count += 1;
        code += $(this).attr("data-value") + " ";
      }
    });
    if (count > 0) {
      PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 32, number: code, sumMoney: perMoney*count, sumCount: count });
      PlayThree.setTableListData();
      $("#panl_3bt").find(".js_ball").removeClass("active");
      PlayThree3Bt.select();
    }
    else {
      layer.alert('请至少选择 3 个号码！', { title: '出错了', icon: 5 });
    }
  },
  "randomOneBall": function () {
    var arr = this.randomBall(1);
    var sumMoney = PlayThree.WaitData.perMoney * 1;
    PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 32, number: arr[0], sumMoney: sumMoney, sumCount: 1 });
    PlayThree.setTableListData();
  },
  "randomNumberBall": function () {
    var count = $("#txtNumberBall").val() || "5";
    if (parseInt(count) > 100 || parseInt(count) <= 0) {
      layer.alert('机选最多100注！', { title: '出错了', icon: 5 });
      return false;
    }
    count = parseInt(count);
    var sumMoney = PlayThree.WaitData.perMoney * 1;
    var arr = this.randomBall(count);
    for (var i = 0; i < count; i++) {
      PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 32, number: arr[i], sumMoney: sumMoney, sumCount: 1 });
    }
    PlayThree.setTableListData();
    $("#txtNumberBall").val("");
  },
  "randomBall": function (count) {
    var result = [];
    if (count > 0 && count <= 100) {
      var arrData = [];
      for (var i = 1; i < 7; i++) {
        arrData.push(i);
      }
      var arr = Utils.copyJson(arrData);
      for (var j = 0; j < count; j++) {
        var code = "";
        var arrS = [];

        var random = Math.floor(Math.random() * arr.length + 1);
        arrS.push(arr[random - 1]);
        arr.splice(random - 1, 1);

        random = Math.floor(Math.random() * arr.length + 1);
        arrS.push(arr[random - 1]);
        arr.splice(random - 1, 1);

        random = Math.floor(Math.random() * arr.length + 1);
        arrS.push(arr[random - 1]);
        arr.splice(random - 1, 1);

        arrS = PlayThree.orderBall(arrS);
        result.push(arrS.join(" "));
        arr = Utils.copyJson(arrData);
      }
    }
    return result;
  },
  "getPlayCount":function(count){
    if(count==3) return 1;
    if(count==4) return 4;
    if(count==5) return 10;
    if(count==6) return 20;
    return 0;
  }
};

//3连号通选
var PlayThree3Lt = {
  "init": function () {
    $("#panl_3lt").find(".js_ball").on('click', function () {
      if (!PlayThree.WaitData) return;
      if ($(this).hasClass("active")) {
        $(this).removeClass("active");
      } else {
        $(this).addClass("active");
      }
      PlayThree3Lt.select();
    });
    $("#panl_3lt").find("em[data-action='clear']").on('click', function () {
      $(this).parent().find(".js_ball").removeClass("active");
      PlayThree3Lt.select();
    });
    $("#panl_3lt #selectBall").on('click', function () {
      PlayThree3Lt.selectBall();
    });
  },
  "select": function () {
    if (!PlayThree.WaitData) return;
    var count = 0;
    var perMoney = PlayThree.WaitData ? PlayThree.WaitData.perMoney : 0;
    var rewardSum = 0;
    if ($("#panl_3lt").find(".js_ball").hasClass("active")) {
      var reward = parseFloat($("#panl_3lt").find(".js_ball").attr("data-money") || 0);
      rewardSum += reward;
      count = 1;
    }
    $("#panl_3lt").find(".select_count").text(count);
    $("#panl_3lt").find(".select_money").text(perMoney * count);
    if (count > 0) {
      $("#panl_3lt").find(".select_reward").html('若中奖，奖金' + PlayThree.getRewardStrong(rewardSum) + 'DG，盈利' + PlayThree.getRewardStrong(rewardSum - perMoney) + 'DG');
    } else {
      $("#panl_3lt").find(".select_reward").text("");
    }
    PlayThree3Lt.setSureSelectButton(count);
  },
  "setSureSelectButton": function (count) {
    if (PlayThree.IsStop) {
      layer.alert('本期参与已截止！', { title: '出错了', icon: 5 });
      return;
    }
    if (count > 0) {
      $("#panl_3lt #selectBall").removeClass("layui-btn-disabled");
    } else {
      $("#panl_3lt #selectBall").addClass("layui-btn-disabled");
    }
  },
  "selectBall": function () {
    if (PlayThree.IsStop) {
      layer.alert('本期参与已截止！', { title: '出错了', icon: 5 });
      return;
    }
    if ($("#panl_3lt").find(".js_ball").hasClass("active")) {
      var sumMoney = PlayThree.WaitData.perMoney;
      PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 33, number: "三连号通选", sumMoney: sumMoney, sumCount: 1 });
      PlayThree.setTableListData();
      $("#panl_3lt").find(".js_ball").removeClass("active");
      PlayThree3Lt.select();
    }
    else {
      layer.alert('请选择三连号通选！', { title: '出错了', icon: 5 });
    }
  },
  "randomOneBall": function () {
    var arr = this.randomBall(1);
    var sumMoney = PlayThree.WaitData.perMoney * 1;
    PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 33, number: "三连号通选", sumMoney: sumMoney, sumCount: 1 });
    PlayThree.setTableListData();
  },
  "randomNumberBall": function () {
    var count = $("#txtNumberBall").val() || "5";
    if (parseInt(count) > 100 || parseInt(count) <= 0) {
      layer.alert('机选最多100注！', { title: '出错了', icon: 5 });
      return false;
    }
    count = parseInt(count);
    var sumMoney = PlayThree.WaitData.perMoney * 1;
    for (var i = 0; i < count; i++) {
      PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 33, number: "三连号通选", sumMoney: sumMoney, sumCount: 1 });
    }
    PlayThree.setTableListData();
    $("#txtNumberBall").val("");
  },
  "randomBall": function (count) {
    var result = [];
    if (count > 0 && count <= 100) {
      var arrData = [];
      for (var i = 3; i < 19; i++) {
        arrData.push(i);
      }
      var arr = Utils.copyJson(arrData);
      for (var j = 0; j < count; j++) {
        var random = Math.floor(Math.random() * arr.length + 1);
        result.push(arr[random - 1]);
      }
    }
    return result;
  }
};

//2同号复选
var PlayThree2Fx = {
  "init": function () {
    $("#panl_2fx").find(".js_ball").on('click', function () {
      if (!PlayThree.WaitData) return;
      if ($(this).hasClass("active")) {
        $(this).removeClass("active");
      } else {
        $(this).addClass("active");
      }
      PlayThree2Fx.select();
    });
    $("#panl_2fx").find("li[data-action='clear']").on('click', function () {
      $(this).parent().find(".js_ball").removeClass("active");
      PlayThree2Fx.select();
    });
    $("#panl_2fx #selectBall").on('click', function () {
      PlayThree2Fx.selectBall();
    });
  },
  "select": function () {
    if (!PlayThree.WaitData) return;
    var count = 0;
    var perMoney = PlayThree.WaitData ? PlayThree.WaitData.perMoney : 0;
    var rewardSum = 0;
    $.each($("#panl_2fx").find(".js_ball"), function(i, item) {
      if($(this).hasClass("active")){
        var reward = parseFloat($(this).attr("data-money") || 0);
        rewardSum = reward;
        count += 1;
      }
    });
    $("#panl_2fx").find(".select_count").text(count);
    $("#panl_2fx").find(".select_money").text(perMoney * count);
    if (count > 0) {
      $("#panl_2fx").find(".select_reward").html('若中奖，奖金' + PlayThree.getRewardStrong(rewardSum) + 'DG，盈利' + PlayThree.getRewardStrong(rewardSum - perMoney * count) + 'DG');
    } else {
      $("#panl_2fx").find(".select_reward").text("");
    }
    PlayThree2Fx.setSureSelectButton(count);
  },
  "setSureSelectButton": function (count) {
    if (PlayThree.IsStop) {
      layer.alert('本期参与已截止！', { title: '出错了', icon: 5 });
      return;
    }
    if (count > 0) {
      $("#panl_2fx #selectBall").removeClass("layui-btn-disabled");
    } else {
      $("#panl_2fx #selectBall").addClass("layui-btn-disabled");
    }
  },
  "selectBall": function () {
    if (PlayThree.IsStop) {
      layer.alert('本期参与已截止！', { title: '出错了', icon: 5 });
      return;
    }
    var count = 0, code = [];
    var perMoney = PlayThree.WaitData ? PlayThree.WaitData.perMoney : 0;
    $.each($("#panl_2fx").find(".js_ball"), function(i, item) {
      if($(this).hasClass("active")){
        count += 1;
        code.push($(this).attr("data-value")+$(this).attr("data-value")+"*");
      }
    });
    if (count > 0) {
      PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 20, number: code.join(","), sumMoney: perMoney*count, sumCount: count});
      PlayThree.setTableListData();
      $("#panl_2fx").find(".js_ball").removeClass("active");
      PlayThree2Fx.select();
    }
    else {
      layer.alert('请选择一个号码！', { title: '出错了', icon: 5 });
    }
  },
  "randomOneBall": function () {
    var arr = this.randomBall(1);
    var sumMoney = PlayThree.WaitData.perMoney * 1;
    PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 20, number: arr[0]+""+arr[0]+"*", sumMoney: sumMoney, sumCount: 1 });
    PlayThree.setTableListData();
  },
  "randomNumberBall": function () {
    var count = $("#txtNumberBall").val() || "5";
    if (parseInt(count) > 100 || parseInt(count) <= 0) {
      layer.alert('机选最多100注！', { title: '出错了', icon: 5 });
      return false;
    }
    count = parseInt(count);
    var sumMoney = PlayThree.WaitData.perMoney * 1;
    var arr = this.randomBall(count);
    for (var i = 0; i < count; i++) {
      PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 20, number: arr[i]+""+arr[i]+"*", sumMoney: sumMoney, sumCount: 1 });
    }
    PlayThree.setTableListData();
    $("#txtNumberBall").val("");
  },
  "randomBall": function (count) {
    var result = [];
    if (count > 0 && count <= 100) {
      var arrData = [];
      for (var i = 1; i < 7; i++) {
        arrData.push(i);
      }
      var arr = Utils.copyJson(arrData);
      for (var j = 0; j < count; j++) {
        var random = Math.floor(Math.random() * arr.length + 1);
        result.push(arr[random - 1]);
      }
    }
    return result;
  }
};

//2同号单选
var PlayThree2Dx = {
  "init": function () {
    $("#panl_2dx").find(".js_ball").on('click', function () {
      if (!PlayThree.WaitData) return;
      if ($(this).hasClass("active")) {
        $(this).removeClass("active");
      } else {
        $(this).addClass("active");
        var value = $(this).attr("data-value");
        if($(this).parent().hasClass("twoNum")){
          $("#panl_2dx .twoNumBt").find("li[data-value='"+value+"']").removeClass("active");
        }else{
          $("#panl_2dx .twoNum").find("li[data-value='"+value+"']").removeClass("active");
        }
      }
      PlayThree2Dx.select();
    });
    $("#panl_2dx").find("li[data-action='clear']").on('click', function () {
      $(this).parent().find(".js_ball").removeClass("active");
      PlayThree2Dx.select();
    });
    $("#panl_2dx #selectBall").on('click', function () {
      PlayThree2Dx.selectBall();
    });
  },
  "select": function () {
    if (!PlayThree.WaitData) return;
    var count = 0;
    var perMoney = PlayThree.WaitData ? PlayThree.WaitData.perMoney : 0;
    var reward = parseFloat($("#panl_2dx .title em").text());
    var count1 = $("#panl_2dx .twoNum .active").length;
    var count2 = $("#panl_2dx .twoNumBt .active").length;
    count = count1*count2;
    $("#panl_2dx").find(".select_count").text(count);
    $("#panl_2dx").find(".select_money").text(perMoney * count);
    if (count > 0) {
      $("#panl_2dx").find(".select_reward").html('若中奖，奖金' + PlayThree.getRewardStrong(reward) + 'DG，盈利' + PlayThree.getRewardStrong(reward*count - perMoney * count) + 'DG');
    } else {
      $("#panl_2dx").find(".select_reward").text("");
    }
    PlayThree2Dx.setSureSelectButton(count);
  },
  "setSureSelectButton": function (count) {
    if (PlayThree.IsStop) {
      layer.alert('本期参与已截止！', { title: '出错了', icon: 5 });
      return;
    }
    if (count > 0) {
      $("#panl_2dx #selectBall").removeClass("layui-btn-disabled");
    } else {
      $("#panl_2dx #selectBall").addClass("layui-btn-disabled");
    }
  },
  "selectBall": function () {
    if (PlayThree.IsStop) {
      layer.alert('本期参与已截止！', { title: '出错了', icon: 5 });
      return;
    }
    var count = 0, code1 = [], code2 = [];
    var perMoney = PlayThree.WaitData ? PlayThree.WaitData.perMoney : 0;
    $.each($("#panl_2dx .twoNum .active"), function(i, item) {
      code1.push($(this).attr("data-value")+$(this).attr("data-value"));
    });
    $.each($("#panl_2dx .twoNumBt .active"), function(i, item) {
      code2.push($(this).attr("data-value"));
    });
    count = code1.length * code2.length;
    if (count > 0) {
      PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 21, number: code1.join(" ")+"#"+code2.join(" "), sumMoney: perMoney*count, sumCount: count});
      PlayThree.setTableListData();
      $("#panl_2dx").find(".js_ball").removeClass("active");
      PlayThree2Dx.select();
    }
    else {
      layer.alert('请至少选择一注号码！', { title: '出错了', icon: 5 });
    }
  },
  "randomOneBall": function () {
    var arr = this.randomBall(1);
    var sumMoney = PlayThree.WaitData.perMoney * 1;
    PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 21, number: arr[0], sumMoney: sumMoney, sumCount: 1 });
    PlayThree.setTableListData();
  },
  "randomNumberBall": function () {
    var count = $("#txtNumberBall").val() || "5";
    if (parseInt(count) > 100 || parseInt(count) <= 0) {
      layer.alert('机选最多100注！', { title: '出错了', icon: 5 });
      return false;
    }
    count = parseInt(count);
    var sumMoney = PlayThree.WaitData.perMoney * 1;
    var arr = this.randomBall(count);
    for (var i = 0; i < count; i++) {
      PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 21, number: arr[i], sumMoney: sumMoney, sumCount: 1 });
    }
    PlayThree.setTableListData();
    $("#txtNumberBall").val("");
  },
  "randomBall": function (count) {
    var result = [];
    if (count > 0 && count <= 100) {
      var arrData = [];
      for (var i = 1; i < 7; i++) {
        arrData.push(i);
      }
      var arr = Utils.copyJson(arrData);
      for (var j = 0; j < count; j++) {
        var random = Math.floor(Math.random() * arr.length + 1);
        var code = arr[random - 1] + "" + arr[random - 1] + "#";
        arr.splice(random - 1, 1);

        random = Math.floor(Math.random() * arr.length + 1);
        code += arr[random - 1];

        result.push(code);
        arr = Utils.copyJson(arrData);
      }
    }
    return result;
  }
};

//2不同号
var PlayThree2Bt = {
  "init": function () {
    $("#panl_2bt").find(".js_ball").on('click', function () {
      if (!PlayThree.WaitData) return;
      if ($(this).hasClass("active")) {
        $(this).removeClass("active");
      } else {
        $(this).addClass("active");
      }
      PlayThree2Bt.select();
    });
    $("#panl_2bt").find("li[data-action='clear']").on('click', function () {
      $(this).parent().find(".js_ball").removeClass("active");
      PlayThree2Bt.select();
    });
    $("#panl_2bt #selectBall").on('click', function () {
      PlayThree2Bt.selectBall();
    });
  },
  "select": function () {
    if (!PlayThree.WaitData) return;
    var count = 0;
    var perMoney = PlayThree.WaitData ? PlayThree.WaitData.perMoney : 0;
    var rewardSum = 0;
    $.each($("#panl_2bt").find(".js_ball"), function(i, item) {
      if($(item).hasClass("active")){
        var reward = parseFloat($(item).attr("data-money") || 0);
        rewardSum = reward;
        count += 1;
      }
    });
    var sumCount = this.getPlayCount(count);
    $("#panl_2bt").find(".select_count").text(sumCount);
    $("#panl_2bt").find(".select_money").text(perMoney * sumCount);
    if (sumCount > 0) {
      $("#panl_2bt").find(".select_reward").html('若中奖，奖金' + PlayThree.getRewardStrong(rewardSum) + 'DG，盈利' + PlayThree.getRewardStrong(rewardSum - perMoney * sumCount) + 'DG');
    } else {
      $("#panl_2bt").find(".select_reward").text("");
    }
    PlayThree2Bt.setSureSelectButton(count);
  },
  "setSureSelectButton": function (count) {
    if (PlayThree.IsStop) {
      layer.alert('本期参与已截止！', { title: '出错了', icon: 5 });
      return;
    }
    if (count >= 2) {
      $("#panl_2bt #selectBall").removeClass("layui-btn-disabled");
    } else {
      $("#panl_2bt #selectBall").addClass("layui-btn-disabled");
    }
  },
  "selectBall": function () {
    if (PlayThree.IsStop) {
      layer.alert('本期参与已截止！', { title: '出错了', icon: 5 });
      return;
    }
    var count = 0;
    var perMoney = PlayThree.WaitData ? PlayThree.WaitData.perMoney : 0;
    var code = [];
    $.each($("#panl_2bt").find(".js_ball"), function(i, item) {
      if($(this).hasClass("active")){
        count += 1;
        code.push($(this).attr("data-value"));
      }
    });
    var sumCount = this.getPlayCount(count);
    if (sumCount > 0) {
      PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 22, number: code.join(" "), sumMoney: perMoney*sumCount, sumCount: sumCount });
      PlayThree.setTableListData();
      $("#panl_2bt").find(".js_ball").removeClass("active");
      PlayThree2Bt.select();
    }
    else {
      layer.alert('请至少选择 2 个号码！', { title: '出错了', icon: 5 });
    }
  },
  "randomOneBall": function () {
    var arr = this.randomBall(1);
    var sumMoney = PlayThree.WaitData.perMoney * 1;
    PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 22, number: arr[0], sumMoney: sumMoney, sumCount: 1 });
    PlayThree.setTableListData();
  },
  "randomNumberBall": function () {
    var count = $("#txtNumberBall").val() || "5";
    if (parseInt(count) > 100 || parseInt(count) <= 0) {
      layer.alert('机选最多100注！', { title: '出错了', icon: 5 });
      return false;
    }
    count = parseInt(count);
    var sumMoney = PlayThree.WaitData.perMoney * 1;
    var arr = this.randomBall(count);
    for (var i = 0; i < count; i++) {
      PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 22, number: arr[i], sumMoney: sumMoney, sumCount: 1 });
    }
    PlayThree.setTableListData();
    $("#txtNumberBall").val("");
  },
  "randomBall": function (count) {
    var result = [];
    if (count > 0 && count <= 100) {
      var arrData = [];
      for (var i = 1; i < 7; i++) {
        arrData.push(i);
      }
      var arr = Utils.copyJson(arrData);
      for (var j = 0; j < count; j++) {
        var code = "";
        var arrS = [];

        var random = Math.floor(Math.random() * arr.length + 1);
        arrS.push(arr[random - 1]);
        arr.splice(random - 1, 1);

        random = Math.floor(Math.random() * arr.length + 1);
        arrS.push(arr[random - 1]);
        arr.splice(random - 1, 1);

        arrS = PlayThree.orderBall(arrS);
        result.push(arrS.join(" "));
        arr = Utils.copyJson(arrData);
      }
    }
    return result;
  },
  "getPlayCount":function(count){
    if(count==2) return 1;
    if(count==3) return 3;
    if(count==4) return 6;
    if(count==5) return 10;
    if(count==6) return 15;
    return 0;
  }
};

//1个号通选
var PlayThree1Tx = {
  "init": function () {
    $("#panl_1tx").find(".js_ball").on('click', function () {
      if (!PlayThree.WaitData) return;
      if ($(this).hasClass("active")) {
        $("#panl_1tx").find(".js_ball").removeClass("active");
      } else {
        $("#panl_1tx").find(".js_ball").removeClass("active");
        $(this).addClass("active");
      }
      PlayThree1Tx.select();
    });
    $("#panl_1tx").find("li[data-action='clear']").on('click', function () {
      $(this).parent().find(".js_ball").removeClass("active");
      PlayThree1Tx.select();
    });
    $("#panl_1tx #selectBall").on('click', function () {
      PlayThree1Tx.selectBall();
    });
  },
  "select": function () {
    if (!PlayThree.WaitData) return;
    var perMoney = PlayThree.WaitData ? PlayThree.WaitData.perMoney : 0;
    var rewardMin = parseFloat($("#panl_3bt .title em").text());
    var rewardMax = parseFloat($("#panl_3dx .title em").text());
    var count = $("#panl_1tx .active").length > 0 ? 21 : 0;
    $("#panl_1tx").find(".select_count").text(count);
    $("#panl_1tx").find(".select_money").text(perMoney * count);
    if (count > 0) {
      $("#panl_1tx").find(".select_reward").html('若中奖，奖金' + PlayThree.getRewardStrong(rewardMin) + '至' + PlayThree.getRewardStrong(rewardMax) + 'DG，盈利' + PlayThree.getRewardStrong(rewardMin - perMoney*count) + '至' + PlayThree.getRewardStrong(rewardMax - perMoney*count) + 'DG');
    } else {
      $("#panl_1tx").find(".select_reward").text("");
    }
    PlayThree1Tx.setSureSelectButton(count);
  },
  "setSureSelectButton": function (count) {
    if (PlayThree.IsStop) {
      layer.alert('本期参与已截止！', { title: '出错了', icon: 5 });
      return;
    }
    if (count > 0) {
      $("#panl_1tx #selectBall").removeClass("layui-btn-disabled");
    } else {
      $("#panl_1tx #selectBall").addClass("layui-btn-disabled");
    }
  },
  "selectBall": function () {
    if (PlayThree.IsStop) {
      layer.alert('本期参与已截止！', { title: '出错了', icon: 5 });
      return;
    }
    var count = $("#panl_1tx .active").length > 0 ? 21 : 0;
    var perMoney = PlayThree.WaitData ? PlayThree.WaitData.perMoney : 0;
    if (count > 0) {
      var code = $("#panl_1tx .active").attr("data-value");
      //三同号单选
      PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 31, number: code+" "+code+" "+code, sumMoney: perMoney, sumCount: 1});
      //三不同号
      var arr = [];
      for(var i=1;i<=6;i++){
        if(i!=parseInt(code)){
          arr.push(i);
        }
      }
      var arrThree = this.getCombinationList(arr, 2);
      for(var i=arrThree.length-1;i>=0;i--){
        PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 32, number: code+" "+arrThree[i], sumMoney: perMoney, sumCount: 1});
      }
      //二同号单选
      for(var i=arr.length-1;i>=0;i--){
        PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 21, number: arr[i]+""+arr[i]+"#"+code, sumMoney: perMoney, sumCount: 1});
      }
      for(var i=arr.length-1;i>=0;i--){
        PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 21, number: code+code+"#"+arr[i], sumMoney: perMoney, sumCount: 1});
      }
      
      PlayThree.setTableListData();

      // PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 10, number: code, sumMoney: perMoney*count, sumCount: count});
      // PlayThree.setTableListData();
      $("#panl_1tx").find(".js_ball").removeClass("active");
      PlayThree1Tx.select();
    }
    else {
      layer.alert('请选择一个号码！', { title: '出错了', icon: 5 });
    }
  },
  "randomOneBall": function () {
    this.randomBall(1);
    PlayThree.setTableListData();
  },
  "randomNumberBall": function () {
    var count = $("#txtNumberBall").val() || "5";
    if (parseInt(count) > 100 || parseInt(count) <= 0) {
      layer.alert('机选最多100注！', { title: '出错了', icon: 5 });
      return false;
    }
    this.randomBall(count);
    PlayThree.setTableListData();
    $("#txtNumberBall").val("");
  },
  "randomBall": function (count) {
    var sumMoney = PlayThree.WaitData.perMoney * 1;
    for (var j = 0; j < count; j++) {
      var random = Math.floor(Math.random() * 3 + 1);//3种
      if(random==1){//三同号单选
        var arr = PlayThree3Dx.randomBall(1);
        PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 31, number: arr[0]+" "+arr[0]+" "+arr[0], sumMoney: sumMoney, sumCount: 1 });
      }else if(random==2){//三不同号
        var arr = PlayThree3Bt.randomBall(1);
        PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 32, number: arr[0], sumMoney: sumMoney, sumCount: 1 });
      }else{//二同号单选
        var arr = PlayThree2Dx.randomBall(1);
        PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 21, number: arr[0], sumMoney: sumMoney, sumCount: 1 });
      }
    }
  },
  "getCombinationList":function(arr, len) { 
    var resultArr=[];
    deepCompute(arr, len - 1, 0, "", resultArr);
    console.log(resultArr);
    function deepCompute(arr, choselen, index,str) {
        for (var a = index; a < arr.length - choselen; a++) {
            if (choselen != 0) {
                deepCompute(arr, choselen - 1, a + 1,str+arr[a]+" ");
            } else {
                resultArr.push(str+arr[a]);
            }
        }
    }
    return resultArr;
  }
};

//通知
var ThreeNotice = {
  "init": function () {
      this.getData(function (data) {
          $.each(data, function (i, item) {
              var html = '<a class="btn-view" href="javascript:void(0);">恭喜用户' + ThreeNotice.getMobile(item.userName) + '中奖（'+item.rewardCodeName+' '+item.rewardMoney+'DG）</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
              $("#seven_notice .body").append(html);
          });

          $('#seven_notice .body').marquee({
              speed: 100,
              direction: 'left',
              pauseOnHover: true
          });
      });
  },
  "getMobile":function(mobile){
    return mobile.substring(0, 3) + "****" + mobile.substring(7, 11);
  },
  "getData": function (callback) {
      HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.USER_PLAY_THREE_REWARD_LIST, {offset:1,limit:10}, function (data) {
          if (callback) {
              callback(data.rows || []);
          }
      });
  }
};
