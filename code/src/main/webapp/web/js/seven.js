$(document).ready(function () {
  PlaySeven.init();
});
var layer, table;
var PlaySeven = {
  "IsStop": false,
  "SecondTime": 0,
  "SecondTime1": 0,
  "SecondTime3": 0,
  "Timer": null,
  "Timer1": null,
  "Timer3": null,
  "SettingData": {},
  "RewardData": [],
  "FrontData": null,
  "JoinData": null,
  "WaitData": null,
  "SelectBallData": [],
  "init": function () {
    SevenNotice.init();
    this.initSetting(function(){
      PlaySeven.initTodayCount();
      PlaySeven.initFront();
      PlaySeven.initJoin();
      PlaySeven.initWait();
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
      PlaySeven.selectOrangeBall($(this));
    });
    $("#clearOrangeBall").on('click', function () {
      PlaySeven.clearSelectOrangeBall();
    });
    $("#randomOrangeBall").on('click', function () {
      PlaySeven.randomSelectOrangeBall();
    });
    $("#areaGreenBall").find("a.js_ball").on('click', function () {
      PlaySeven.selectGreenBall($(this));
    });
    $("#clearGreenBall").on('click', function () {
      PlaySeven.clearSelectGreenBall();
    });
    $("#randomGreenBall").on('click', function () {
      PlaySeven.randomSelectGreenBall();
    });
    $("#randomOneBall").on('click', function () {
      PlaySeven.randomOneBall();
    });
    $("#randomNumberBall").on('click', function () {
      PlaySeven.randomNumberBall();
    });
    $("#clearAllBall").on('click', function () {
      PlaySeven.clearAllBall();
    });
    $("#selectBall").on('click', function () {
      PlaySeven.selectBall();
    });
    $("#submitBall").on('click', function () {
      PlaySeven.submitBall();
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
       PlaySeven.initSelectNumber();
       //走势列表
       PlaySeven.initResultLog();
       //历史记录
       PlaySeven.initResultHistory();
       //用户参与记录
       PlaySeven.initUserLog();
      
    });
  },
  "initSetting": function (callback) {
    // UIUtils.loading();
    var url = Config.WEB_SERVER_API + Config.URI.PLAY_SEVEN;
    HttpUtils.httpGet(url, {}, function (data) {
      // UIUtils.loaded();
      PlaySeven.SettingData = data.rows;
      if (!PlaySeven.SettingData) return;
      $("#info_allmoney").text(PlaySeven.formatAllMoney(PlaySeven.SettingData.allMoney));
      $("#current_playRate").text(PlaySeven.SettingData.playRate+"分钟一期");
      $("#moreLinkRight").text("参与时间：每天" + PlaySeven.SettingData.startTime + "~" + PlaySeven.SettingData.overTime);
      PlaySeven.initReward(callback);
    });
  },
  "initReward": function (callback) {
    // UIUtils.loading();
    var url = Config.WEB_SERVER_API + Config.URI.PLAY_SEVEN_REWARD;
    HttpUtils.httpGet(url, {}, function (data) {
      // UIUtils.loaded();
      PlaySeven.RewardData = data.rows;
      if (!PlaySeven.RewardData) return;
      var perMoney = PlaySeven.SettingData ? PlaySeven.SettingData.perMoney : 0;
      var hzMin = 0;
      var hzMax = 0;
      var rewardMoney = 0;
      for (var i = 0; i < PlaySeven.RewardData.length; i++) {
        var reward = PlaySeven.RewardData[i];
        rewardMoney = perMoney * reward.rewardTimes;
        if(reward.rewardMode==1){//浮动金额
          $("#awardDG"+reward.rewardCode).text("最高"+PlaySeven.formatAllMoney(reward.maxMoney)+"DG");
          $("#awardBS"+reward.rewardCode).text("浮动");
        }else{
          $("#awardDG"+reward.rewardCode).text(rewardMoney+"DG");
          $("#awardBS"+reward.rewardCode).text(reward.rewardTimes+"倍");
        }
      }
      // $("#info_allmoney").text(PlaySeven.formatAllMoney(PlaySeven.SettingData.allMoney));
      if(callback){
        callback();
      }
    });
  },
  "initTodayCount":function(){
    var url = Config.WEB_SERVER_API + Config.URI.PLAY_SEVEN_RESULT_TODAY_LAST;
    HttpUtils.httpGet(url, {}, function (data) {
      if(data && data.rows){
        $("#wait_open").text(data.rows.dayIndex);
        var leave = parseInt(PlaySeven.SettingData.countTime - data.rows.dayIndex);
        $("#wait_leave").text(leave);
      }else{
        $("#wait_open").text(0);
        $("#wait_leave").text(PlaySeven.SettingData.countTime);
      }
    });
  },
  "initFront": function () {
    // UIUtils.loading();
    var url = Config.WEB_SERVER_API + Config.URI.PLAY_SEVEN_RESULT_FRONT;
    HttpUtils.httpGet(url, {}, function (data) {
      // UIUtils.loaded();
      PlaySeven.FrontData = data.rows;
      if (!PlaySeven.FrontData) return;
      $("#front_time1").text(PlaySeven.FrontData.playTime);
      $("#front_time2").text(PlaySeven.FrontData.playTime);
      // var date = FormatUtils.parseTimestampToDate(PlaySeven.FrontData.publishTime);
      // var week = PlaySeven.FrontData.publishTimeWeek;
      // $("#front_date").text(date + "(" + PlaySeven.formatWeek(week) + ")");
      var arr = PlaySeven.FrontData.sixNumber.split(",");
      if (arr && arr.length >= 6) {
        $("#front_six1").text(arr[0]);
        $("#front_six2").text(arr[1]);
        $("#front_six3").text(arr[2]);
        $("#front_six4").text(arr[3]);
        $("#front_six5").text(arr[4]);
        $("#front_six6").text(arr[5]);

        $("#front2_six1").text(arr[0]);
        $("#front2_six2").text(arr[1]);
        $("#front2_six3").text(arr[2]);
        $("#front2_six4").text(arr[3]);
        $("#front2_six5").text(arr[4]);
        $("#front2_six6").text(arr[5]);
      }
      $("#front_one").text(PlaySeven.FrontData.oneNumber);
      $("#front2_one").text(PlaySeven.FrontData.oneNumber);
    });
  },
  "initJoin": function () {
    $("#wait_timedown").text("00:00:00");
    // UIUtils.loading();
    var url = Config.WEB_SERVER_API + Config.URI.PLAY_SEVEN_RESULT_JOIN;
    HttpUtils.httpGet(url, {}, function (data) {
      // UIUtils.loaded();
      PlaySeven.JoinData = data.rows || {};
      if (!PlaySeven.JoinData) {
        $("#wait_timedown").text("服务已停止");
        PlaySeven.IsStop = true;
        return;
      }
      $("#current_time1").text(PlaySeven.JoinData.playTime);
      PlaySeven.initTimerWait();
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
    var url = Config.WEB_SERVER_API + Config.URI.PLAY_SEVEN_RESULT_WAIT;
    HttpUtils.httpGet(url, {}, function (data) {
      // UIUtils.loaded();
      PlaySeven.WaitData = data.rows || {};
      if (!PlaySeven.WaitData) {
        $("#wait_timedown").text("服务停止");
        PlaySeven.IsStop = true;
        return;
      }
      $("#current_time2").text(PlaySeven.WaitData.playTime);
      $("#current_time3").text(PlaySeven.WaitData.playTime);
      if (PlaySeven.WaitData.nowTime <= PlaySeven.WaitData.endTime) {//参与中
        $("#countDownRight1").show();
      }
      else if (PlaySeven.WaitData.nowTime < PlaySeven.WaitData.publishTime) {//已截止，待开奖
        $("#countDownRight2").show();
        PlaySeven.initTimerOpen();
      }
      else{//开奖中
        $("#countDownRight3").show();
        PlaySeven.initTimerPlay();
        setTimeout(function () {
          PlaySeven.afterOpen();
        }, 10000);
      }
      if(callback){
        callback();
      }
    });
  },
  "initTimerWait":function(){
    //开始倒计时
    PlaySeven.SecondTime = parseInt(Math.abs(PlaySeven.JoinData.nowTime - PlaySeven.JoinData.endTime) / 1000);
    PlaySeven.Timer = setInterval(function () {
      PlaySeven.SecondTime--;
      if (PlaySeven.SecondTime <= 0) {
        //截止了
        PlaySeven.IsStop = true;
        $("#wait_timedown").text("00:00:00");
        clearInterval(PlaySeven.Timer);
        PlaySeven.initWait(function(){
          //layer.alert('您好，第 ' + PlaySeven.JoinData.playTime + ' 期已截止，当前期是第 ' + (PlaySeven.WaitData.playTime + 1) + ' 期，参与时请确认您选择的期号！');
          layer.alert('您好，第 ' + PlaySeven.JoinData.playTime + ' 期已截止，当前期是第 ' + (PlaySeven.WaitData.playTime + 1) + ' 期，参与时请确认您选择的期号！', {time: 3000});
          PlaySeven.initJoin();
        });
      } else {
        //计算出相差天数
        var days = Math.floor(PlaySeven.SecondTime / (24 * 3600));
        //计算出小时数
        var leave1 = PlaySeven.SecondTime % (24 * 3600);    //计算天数后剩余的秒数
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
    PlaySeven.SecondTime1 = parseInt(Math.abs(PlaySeven.WaitData.nowTime - PlaySeven.WaitData.publishTime) / 1000);
    PlaySeven.Timer1 = setInterval(function () {
      PlaySeven.SecondTime1--;
      if (PlaySeven.SecondTime1 <= 0) {
        clearInterval(PlaySeven.Timer1);
        $("#wait_timedown1").text("00:00");
        $("#countDownRight2").hide();
        $("#countDownRight3").show();
        PlaySeven.initTimerPlay();
        setTimeout(function () {
          PlaySeven.afterOpen();
        }, 10000);
      } else {
        //计算出相差天数
        var days = Math.floor(PlaySeven.SecondTime1 / (24 * 3600));
        //计算出小时数
        var leave1 = PlaySeven.SecondTime1 % (24 * 3600);    //计算天数后剩余的秒数
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
    clearInterval(PlaySeven.Timer3);
    PlaySeven.initTodayCount();
    PlaySeven.initFront();
    PlaySeven.initWait();
    PlaySeven.initMore();
    PlaySeven.initToday();
    PlaySeven.searchResultHistory();
    $("#btnSearchUserLog").click();
  },
  "initTimerPlay":function(){
    PlaySeven.Timer3 = setInterval(function () {
      var random1 = Math.floor(Math.random() * 33 + 1);
      $("#front3_six1").text(random1);
      var random2 = Math.floor(Math.random() * 33 + 1);
      $("#front3_six2").text(random2);
      var random3 = Math.floor(Math.random() * 33 + 1);
      $("#front3_six3").text(random3);
      var random4 = Math.floor(Math.random() * 33 + 1);
      $("#front3_six4").text(random4);
      var random5 = Math.floor(Math.random() * 33 + 1);
      $("#front3_six5").text(random5);
      var random6 = Math.floor(Math.random() * 33 + 1);
      $("#front3_six6").text(random6);
      var random7 = Math.floor(Math.random() * 16 + 1);
      $("#front3_one").text(random7);
    }, 100);
  },
  "initMore": function () {
    // UIUtils.loading();
    var url = Config.WEB_SERVER_API + Config.URI.PLAY_SEVEN_RESULT_FINISHED;
    HttpUtils.httpGet(url, {offset:1,limit:5}, function (data) {
      // UIUtils.loaded();
      $("#moreAward").empty();
      if(data && data.rows){
        for(var i=0;i<data.rows.length;i++){
          var item = data.rows[i];
          var arr = item.sixNumber.split(",");
          if (arr && arr.length >= 6) {
            var html = ''+
            (i%2==0?'<tr>':'<tr bgcolor="f0f1f2">')+
              '<td>'+item.playTime+'期</td>'+
              '<td><span class="currenAward">'+
              ' <em class="smallOrangeball">'+arr[0]+'</em>'+
              ' <em class="smallOrangeball">'+arr[1]+'</em>'+
              ' <em class="smallOrangeball">'+arr[2]+'</em>'+
              ' <em class="smallOrangeball">'+arr[3]+'</em>'+
              ' <em class="smallOrangeball">'+arr[4]+'</em>'+
              ' <em class="smallOrangeball">'+arr[5]+'</em>'+
              ' <em class="smallGreenball">'+item.oneNumber+'</em>'+
              '</span></td>'+
              // '<td>'+item.countNumber+'</td>'+
              '<td>'+FormatUtils.parseTimestampToDateTime(item.endTime)+'</td>'+
              '<td>'+FormatUtils.parseTimestampToDateTime(item.publishTime)+'</td>'+
            '</tr>';
            $("#moreAward").append(html);
          }
        }
      }
    });
  },
  "initToday":function(){
    var value = FormatUtils.formatterDate(new Date());
    $("#todayKaiJiang").find("tbody").empty();
    HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.PLAY_SEVEN_RESULT_DAY, {date:value}, function(data){
      if(data && data.rows.length>0){
        var html = "";
        for(var i=0;i<10;i++){
          html += "<tr>";
          for(var j=0;j<4;j++){
            var index = j*10 + i;
            var number = index+1;
            if(data.rows.length>index){
              html += '<td class="period">'+(number<10?'0':'')+number+'</td>';
              if(data.rows[index].sixNumber){
                var item = data.rows[index];
                // var arr = item.sixNumber.split(",");
                // if (arr && arr.length >= 6) {
                //   html += '<td><span class="currenAward">'+
                //   ' <em class="smallOrangeball">'+arr[0]+'</em>'+
                //   ' <em class="smallOrangeball">'+arr[1]+'</em>'+
                //   ' <em class="smallOrangeball">'+arr[2]+'</em>'+
                //   ' <em class="smallOrangeball">'+arr[3]+'</em>'+
                //   ' <em class="smallOrangeball">'+arr[4]+'</em>'+
                //   ' <em class="smallOrangeball">'+arr[5]+'</em>'+
                //   ' <em class="smallGreenball">'+item.oneNumber+'</em>'+
                //   '</span></td>';
                // }else{
                //   html += '<td>'+item.sixNumber+'</td>';
                // }
                html+='<td><span class="select_orange">'+item.sixNumber.replace(/,/g, " ")+'</span> <span class="select_green">'+item.oneNumber+'</span></td>';
              }else{
                html += '<td>- -</td>';
              }
            }else{
              html += '<td class="period">&nbsp;</td><td>&nbsp;</td>';
            }
          }
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
        , { field: 'mode', title: '模式', width: 60, align: 'center', templet: function (d) { return PlaySeven.formatMode(d); } }
        , { field: 'sixNumber', title: '选号', width: 283, align: 'left', templet: function (d) { return PlaySeven.formatSelectNumber(d); } }
        , { field: 'sumMoney', title: '价格', width: 60, align: 'center', templet: function (d) { return FormatUtils.formatDG(d.sumMoney); } }
        , { fixed: 'right', title: '操作', toolbar: '#bar', width: 70, align: 'center' }
      ]],
      even: true, page:false, limit:10000,
      data: PlaySeven.SelectBallData
    });

    //监听行工具事件
    table.on('tool(dg)', function (obj) {
      var data = obj.data;
      if (obj.event === 'delete') {
        for (var i = 0; i < PlaySeven.SelectBallData.length; i++) {
          if (PlaySeven.SelectBallData[i].id == data.id) {
            PlaySeven.SelectBallData.splice(i, 1);
            break;
          }
        }
        obj.del();
      } else if (obj.event === 'edit') {

      }
    });
  },
  "initResultLog":function(){
    var widthHistory = 18;
    table.render({
      elem: '#dgResultLog',
      cellMinWidth: 18, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
      cols: [[
        { field: 'playTime', title: '期号', align:'center',width: 85, rowspan: 2 }
        , { title: '橘球', colspan: 33, align: 'center'}
        , { title: '绿球', colspan: 16, align: 'center'}
      ],[
        { field: 'six1', title: '01', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('01', d.six1); }}
        , { field: 'six2', title: '02', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('02', d.six2); }}
        , { field: 'six3', title: '03', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('03', d.six3); }}
        , { field: 'six4', title: '04', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('04', d.six4); }}
        , { field: 'six5', title: '05', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('05', d.six5); }}
        , { field: 'six6', title: '06', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('06', d.six6); }}
        , { field: 'six7', title: '07', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('07', d.six7); }}
        , { field: 'six8', title: '08', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('08', d.six8); }}
        , { field: 'six9', title: '09', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('09', d.six9); }}
        , { field: 'six10', title: '10', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('10', d.six10); }}
        , { field: 'six11', title: '11', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('11', d.six11); }}
        , { field: 'six12', title: '12', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('12', d.six12); }}
        , { field: 'six13', title: '13', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('13', d.six13); }}
        , { field: 'six14', title: '14', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('14', d.six14); }}
        , { field: 'six15', title: '15', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('15', d.six15); }}
        , { field: 'six16', title: '16', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('16', d.six16); }}
        , { field: 'six17', title: '17', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('17', d.six17); }}
        , { field: 'six18', title: '18', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('18', d.six18); }}
        , { field: 'six19', title: '19', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('19', d.six19); }}
        , { field: 'six20', title: '20', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('20', d.six20); }}
        , { field: 'six21', title: '21', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('21', d.six21); }}
        , { field: 'six22', title: '22', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('22', d.six22); }}
        , { field: 'six23', title: '23', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('23', d.six23); }}
        , { field: 'six24', title: '24', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('24', d.six24); }}
        , { field: 'six25', title: '25', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('25', d.six25); }}
        , { field: 'six26', title: '26', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('26', d.six26); }}
        , { field: 'six27', title: '27', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('27', d.six27); }}
        , { field: 'six28', title: '28', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('28', d.six28); }}
        , { field: 'six29', title: '29', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('29', d.six29); }}
        , { field: 'six30', title: '30', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('30', d.six30); }}
        , { field: 'six31', title: '31', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('31', d.six31); }}
        , { field: 'six32', title: '32', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('32', d.six32); }}
        , { field: 'six33', title: '33', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogSixNumber('33', d.six33); }}
        , { field: 'one1', title: '01', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogOneNumber('01', d.one1); }}
        , { field: 'one2', title: '02', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogOneNumber('02', d.one2); }}
        , { field: 'one3', title: '03', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogOneNumber('03', d.one3); }}
        , { field: 'one4', title: '04', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogOneNumber('04', d.one4); }}
        , { field: 'one5', title: '05', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogOneNumber('05', d.one5); }}
        , { field: 'one6', title: '06', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogOneNumber('06', d.one6); }}
        , { field: 'one7', title: '07', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogOneNumber('07', d.one7); }}
        , { field: 'one8', title: '08', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogOneNumber('08', d.one8); }}
        , { field: 'one9', title: '09', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogOneNumber('09', d.one9); }}
        , { field: 'one10', title: '10', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogOneNumber('10', d.one10); }}
        , { field: 'one11', title: '11', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogOneNumber('11', d.one11); }}
        , { field: 'one12', title: '12', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogOneNumber('12', d.one12); }}
        , { field: 'one13', title: '13', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogOneNumber('13', d.one13); }}
        , { field: 'one14', title: '14', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogOneNumber('14', d.one14); }}
        , { field: 'one15', title: '15', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogOneNumber('15', d.one15); }}
        , { field: 'one16', title: '16', width: widthHistory, align:'center',templet: function (d) { return PlaySeven.formatLogOneNumber('16', d.one16); }}
      ]],
      even: true, page:false, limit:10000
    });
    var loadResultLogData = function (date) {
      var value = $("#tbResultLogDate").val() || FormatUtils.formatterDate(new Date());
      HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.PLAY_SEVEN_RESULT_LOG_DAY, {date:value}, function(data){
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
      PlaySeven.searchResultHistory();
    });
    PlaySeven.searchResultHistory();
  },
  "searchResultHistory":function(){
    var value = $("#tbResultHistoryDate").val() || FormatUtils.formatterDate(new Date());
    $("#resultHistoryTable").find("tbody").empty();
    HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.PLAY_SEVEN_RESULT_DAY, {date:value}, function(data){
      if(data && data.rows.length>0){
        var html = "";
        for(var i=0;i<10;i++){
          html += "<tr>";
          for(var j=0;j<4;j++){
            var index = j*10 + i;
            var number = index+1;
            if(data.rows.length>index){
              html += '<td class="period">'+(number<10?'0':'')+number+'</td>';
              if(data.rows[index].sixNumber){
                var item = data.rows[index];
                // var arr = item.sixNumber.split(",");
                // if (arr && arr.length >= 6) {
                //   html += '<td><span class="currenAward">'+
                //   ' <em class="smallOrangeball">'+arr[0]+'</em>'+
                //   ' <em class="smallOrangeball">'+arr[1]+'</em>'+
                //   ' <em class="smallOrangeball">'+arr[2]+'</em>'+
                //   ' <em class="smallOrangeball">'+arr[3]+'</em>'+
                //   ' <em class="smallOrangeball">'+arr[4]+'</em>'+
                //   ' <em class="smallOrangeball">'+arr[5]+'</em>'+
                //   ' <em class="smallGreenball">'+item.oneNumber+'</em>'+
                //   '</span></td>';
                // }else{
                //   html += '<td>'+item.sixNumber+'</td>';
                // }
                html+='<td><span class="select_orange">'+item.sixNumber.replace(/,/g, " ")+'</span> <span class="select_green">'+item.oneNumber+'</span></td>';
              }else{
                html += '<td>- -</td>';
              }
            }else{
              html += '<td class="period">&nbsp;</td><td>&nbsp;</td>';
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
      limit:50,
      url: Config.WEB_SERVER_API + Config.URI.USER_PLAY_SEVEN_LIST,
      // width: 830,
      // height:530,        
      request: {
        pageName: 'offset' //页码的参数名称，默认：page
        , limitName: 'limit' //每页数据量的参数名，默认：limit
      },
      // cellMinWidth: 60, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
      cols: [[
        { field: 'playTime', title: '期号', width: 100, align:'center', rowspan: 2}
        , { field: 'mode', title: '模式', width: 50, align:'center', rowspan: 2, templet: function (d) { return PlaySeven.formatMode(d); }}
        , { field: 'statusName', title: '开奖结果', width: 70, align:'center', rowspan: 2, templet: function(d){return PlaySeven.formatPlayStatus(d);}}
        , { field: 'sixNumber', title: '选号', width: 287, rowspan: 2, templet: function (d) { return PlaySeven.formatSelectNumber(d); } }
        , { field: 'countNumber', title: '参与数量', width: 70, align:'center', rowspan: 2}
        , { field: 'sumMoney', title: '参与金额', width: 70, align:'center', rowspan: 2, templet: function (d) { return FormatUtils.formatDG(d.sumMoney); } }
        , { field: 'createTime', title: '参与时间', width: 150, align:'center', rowspan: 2, templet: function (d) { return FormatUtils.parseTimestampToDateTime(d.createTime); } }                  
        , { title: '中奖', colspan: 3, align: 'center'}
      ],[
        { field: 'countReward', title: '注数', width: 70, align:'center'}
        ,{ field: 'rewardCodeName', title: '奖项', width: 70, align:'center'}
        , { field: 'rewardMoney', title: '金额', width: 70, align:'center', templet: function (d) { return FormatUtils.formatDG(d.rewardMoney); } }        
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
  "selectOrangeBall": function (obj) {
    if (obj.hasClass("active")) {
      obj.removeClass("active");
    } else {
      var len = $("#areaOrangeBall").find("a.active").length;
      if (len < 6) {
        obj.addClass("active");
      } else {
        layer.alert('只能选择6个橘色球', { title: '出错了', icon: 5 });
      }
    }
    PlaySeven.setSureSelectButton();
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
    PlaySeven.setSureSelectButton();
  },
  "clearSelectOrangeBall": function () {
    $("#areaOrangeBall").find("a.js_ball").removeClass("active");
    PlaySeven.setSureSelectButton();
  },
  "selectGreenBall": function (obj) {
    if (obj.hasClass("active")) {
      obj.removeClass("active");
    } else {
      $("#areaGreenBall").find("a.active").removeClass("active");
      obj.addClass("active");
    }
    PlaySeven.setSureSelectButton();
  },
  "randomSelectGreenBall": function () {
    $("#areaGreenBall").find("a.active").removeClass("active");
    var random = Math.floor(Math.random() * 16 + 1);
    $("#areaGreenBall").find("a.js_ball").eq(random - 1).addClass("active");
    PlaySeven.setSureSelectButton();
  },
  "clearSelectGreenBall": function () {
    $("#areaGreenBall").find("a.js_ball").removeClass("active");
    PlaySeven.setSureSelectButton();
  },
  "setSureSelectButton": function () {
    if(PlaySeven.IsStop){
      layer.alert('本期参与已截止！', { title: '出错了', icon: 5 });
      return;
    }
    var len1 = $("#areaOrangeBall").find("a.active").length;
    var len2 = $("#areaGreenBall").find("a.active").length;
    if (len1 == 6 && len2 == 1) {
      $("#selectBall").removeClass("layui-btn-disabled");
      $("#countSum").text("1");
      if (PlaySeven.WaitData) {
        $("#countMoney").text(PlaySeven.WaitData.perMoney * 1);
      }
    } else {
      $("#selectBall").addClass("layui-btn-disabled");
      $("#countSum").text("0");
      $("#countMoney").text("0");
    }
    $("#countOrange").text(len1);
    $("#countGreen").text(len2);
  },
  "selectBall": function () {
    if(PlaySeven.IsStop){
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
      var sumMoney = PlaySeven.WaitData.perMoney * 1;
      PlaySeven.SelectBallData.unshift({ id: PlaySeven.createUUID(), mode: 0, sixNumber: sixNumber, oneNumber: oneNumber, sumMoney: sumMoney });
      PlaySeven.setTableListData();
      $("#areaOrangeBall").find("a.active").removeClass("active");
      $("#areaGreenBall").find("a.active").removeClass("active");
      PlaySeven.setSureSelectButton();
    } else {
      layer.alert('请选择6个橘色球和1个绿色球！', { title: '出错了', icon: 5 });
    }
  },
  "randomOneBall": function () {
    var arr = PlaySeven.randomBall(1);
    var sumMoney = PlaySeven.WaitData.perMoney * 1;
    PlaySeven.SelectBallData.unshift({ id: PlaySeven.createUUID(), mode: 0, sixNumber: arr[0].six, oneNumber: arr[0].one, sumMoney: sumMoney });
    PlaySeven.setTableListData();
  },
  "randomNumberBall": function () {
    var count = $("#txtNumberBall").val() || "5";
    if(parseInt(count)>100 || parseInt(count)<=0){
      layer.alert('机选最多100注！', { title: '出错了', icon: 5 });
      return false;
    }
    var arr = PlaySeven.randomBall(parseInt(count));
    if (arr.length > 0) {
      var sumMoney = PlaySeven.WaitData.perMoney * 1;
      for (var i = 0; i < arr.length; i++) {
        PlaySeven.SelectBallData.unshift({ id: PlaySeven.createUUID(), mode: 0, sixNumber: arr[i].six, oneNumber: arr[i].one, sumMoney: sumMoney });
        PlaySeven.setTableListData();
      }
    }
    $("#txtNumberBall").val("");
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
        arrS = PlaySeven.orderBall(arrS);
        result.push({ six: arrS.join(","), one: arrO });
      }
    }
    return result;
  },
  "orderBall":function(arr){
    for(i=0;i<arr.length-1;i++){
        for(j=0;j<arr.length-1-i;j++){
            if(parseInt(arr[j])>parseInt(arr[j+1])){
                var temp=arr[j];
                arr[j]=arr[j+1];
                arr[j+1]=temp;
            }
        }
    }
    return arr;
  },
  "clearAllBall": function () {
    if(PlaySeven.IsStop){
      layer.alert('本期参与已截止！', { title: '出错了', icon: 5 });
      return;
    }
    if (PlaySeven.SelectBallData.length > 0) {
      layer.confirm('您确定删除所有选号？', { icon: 3, title: '提示' }, function (index) {
        PlaySeven.SelectBallData = [];
        PlaySeven.setTableListData();
        layer.close(index);
      });
    }
  },
  "setTableListData": function () {
    if(PlaySeven.IsStop){
      layer.alert('本期参与已截止！', { title: '出错了', icon: 5 });
      return;
    }
    table.reload("dg", { data: PlaySeven.SelectBallData });
    var sumMoney = PlaySeven.WaitData.perMoney * 1;
    $("#countAllSum").text(PlaySeven.SelectBallData.length);
    $("#countAllMoney").text(PlaySeven.SelectBallData.length * sumMoney);
  },
  "createUUID": function () {
    return Math.round(Math.random() * 100000000);
  },
  "submitBall": function () {
    if(PlaySeven.IsStop){
      layer.alert('本期参与已截止！', { title: '出错了', icon: 5 });
      return false;
    }
    if (PlaySeven.SelectBallData.length <= 0) {
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
        , content: 'sevenadd.html?t=' + t + "&count=" + $("#countAllSum").text() + "&money=" + $("#countAllMoney").text()
        , shade: 0.2 //不显示遮罩
        , yes: function () {
          layer.closeAll();
        }
      });
    });
  },
  "formatWeek": function (week) {
    var arr = ["周一", "周一", "周二", "周三", "周四", "周五", "周六", "周天"];
    return arr[parseInt(week)];
  },
  "formatAllMoney": function (allMoney) {
    if (allMoney != null) {
      if (allMoney < 10000 * 10000) { //小于1亿
        if (allMoney > 10000) {
          allMoneyName = allMoney / 10000 + "万" + (allMoney % 10000!=0?allMoney % 10000:"") + "";
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
    return '<span style="color:orange;">' + d.sixNumber + '</span> | <span style="color:green;">' + d.oneNumber + '</span>';
  },
  "formatPlayStatus": function (d) {
    if(d.status==1){
      return '<span style="color:red;">' + d.statusName + '</span>';
    }
    if(d.status==0){
      return '<span style="color:green;">' + d.statusName + '</span>';
    }
    return d.statusName;
  },
  "formatLogSixNumber": function (number, count) {
    if(count>0)
      return count;
    return '<span style="color:white;background-color: orange;border-radius: 8px;padding: 2px;width: 10px;">' + number + '</span>';
  },
  "formatLogOneNumber": function (number, count) {
    if(count>0)
      return count;
    return '<span style="color:white;background-color: green;border-radius: 8px;padding: 2px;width: 10px;">' + number + '</span>';
  },
  "formatMode": function (d) {
    return d.mode == 1 ? "复式" : "单式";
  }
};

//通知
var SevenNotice = {
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
      HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.USER_PLAY_SEVEN_REWARD_LIST, {offset:1,limit:10}, function (data) {
          if (callback) {
              callback(data.rows || []);
          }
      });
  }
};
