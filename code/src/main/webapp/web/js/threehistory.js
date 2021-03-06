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
  "FrontData": {},
  "WaitData": null,
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

    this.initSetting();
    this.initFront();
    this.initWait();
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
        $("#liLog").on('click', function(){
          $("#btn_login").click();
        });
      }
    });

    layui.use(['table', 'element', 'form'], function () {
      var element = layui.element, form = layui.form;
      table = layui.table;
      layer = layui.layer;

      table.render({
        elem: '#dg',
        height: 165,
        cellMinWidth: 80, //?????????????????????????????????????????????layui 2.2.1 ??????
        cols: [[
          { field: 'id', title: 'ID', hide: true }
          , { field: 'mode', title: '??????', width: 80, align: 'center', templet: function (d) { return PlayThree.formatMode(d); } }
          , { field: 'sixNumber', title: '??????', width: 263, align: 'left', templet: function (d) { return PlayThree.formatSelectNumber(d); } }
          , { field: 'sumMoney', title: '??????', width: 60, align: 'center', templet: function (d) { return FormatUtils.formatDG(d.sumMoney); } }
          , { fixed: 'right', title: '??????', toolbar: '#bar', width: 70, align: 'center' }
        ]],
        even: true, page: false, limit: 10000,
        data: PlayThree.SelectBallData
      });

      //?????????????????????
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

      var widthHistory = 30;
      table.render({
        elem: '#dgHistory',
        height: 580,
        limit: 50,
        url: Config.WEB_SERVER_API + Config.URI.PLAY_THREE_RESULT_LOG,
        request: {
          pageName: 'offset' //?????????????????????????????????page
          , limitName: 'limit' //???????????????????????????????????????limit
        },
        cellMinWidth: 30, //?????????????????????????????????????????????layui 2.2.1 ??????
        cols: [[
          { field: 'playTime', title: '??????', align: 'center', width: 88, rowspan: 2 }
          , { field: 'number1', title: '????????????', rowspan: 2, width: 60, align: 'center', templet: function (d) { return d.number1 + ' &nbsp; ' + d.number2 + ' &nbsp; ' + d.number3; } }
          , { title: '??????????????????', colspan: 6, align: 'center'}
          , { title: '??????', colspan: 18, align: 'center'}
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
        ]]
        , page: true, loading: true,
        response: {
          statusCode: "200" //????????????????????????????????? 200???table ??????????????? 0
        },
        parseData: function (res) { //???????????????????????? table ????????????????????????
          return {
            "code": parseInt(res.resultCode), //??????????????????
            "msg": res.resultMsg, //??????????????????
            "count": res.total, //??????????????????
            "data": res.rows //??????????????????
          };
        }
      });

      table.render({
        elem: '#dgLog',
        limit: 50,
        url: Config.WEB_SERVER_API + Config.URI.USER_PLAY_THREE_LIST,
        // width: 830,
        height: 530,
        request: {
          pageName: 'offset' //?????????????????????????????????page
          , limitName: 'limit' //???????????????????????????????????????limit
        },
        // cellMinWidth: 60, //?????????????????????????????????????????????layui 2.2.1 ??????
        cols: [[
          { field: 'playTime', title: '??????', width: 100, align: 'center'}
          , { field: 'mode', title: '??????', width: 80, align: 'center', templet: function (d) { return PlayThree.formatMode(d); }}
          , { field: 'statusName', title: '????????????', align: 'center', width: 70, templet: function (d) { return PlayThree.formatPlayStatus(d); }}
          , { field: 'number', title: '??????', width: 180, templet: function (d) { return PlayThree.formatSelectNumber(d); } }          
          , { field: 'countNumber', title: '??????', width: 50}
          , { field: 'sumMoney', title: '????????????', width: 70, templet: function (d) { return FormatUtils.formatDG(d.sumMoney); } }
          , { field: 'createTime', title: '????????????', width: 150, templet: function (d) { return FormatUtils.parseTimestampToDateTime(d.createTime); } }
          , { field: 'rewardCodeName', title: '????????????', width: 100 }
          , { field: 'rewardMoney', title: '????????????', width: 70, templet: function (d) { return FormatUtils.formatDG(d.rewardMoney); } }

        ]]
        , page: true, loading: true,
        response: {
          statusCode: "200" //????????????????????????????????? 200???table ??????????????? 0
        },
        parseData: function (res) { //???????????????????????? table ????????????????????????
          return {
            "code": parseInt(res.resultCode), //??????????????????
            "msg": res.resultMsg, //??????????????????
            "count": res.total, //??????????????????
            "data": res.rows //??????????????????
          };
        }
      });

      var loadLogData = function () {
        //?????????????????????
        table.reload("dgLog", {
          where: { //???????????????????????????????????????????????????
            status: $("#status").val()
          }
          , page: {
            curr: 1 //???????????? 1 ?????????
          }
        });
      };

      $("#btnSearch").on('click', function () {
        loadLogData();
      });
      $("#btnSearch").click();
    });
  },
  "initSetting": function () {
    UIUtils.loading();
    var url = Config.WEB_SERVER_API + Config.URI.PLAY_THREE;
    HttpUtils.httpGet(url, {}, function (data) {
      UIUtils.loaded();
      PlayThree.SettingData = data.rows;
      if (!PlayThree.SettingData) return;
      $("#current_playRate").text(PlayThree.SettingData.playRate+"????????????");
      PlayThree.initReward();
      // $("#info_allmoney").text(PlayThree.formatAllMoney(PlayThree.SettingData.allMoney));
    });
  },
  "initReward": function () {
    UIUtils.loading();
    var url = Config.WEB_SERVER_API + Config.URI.PLAY_THREE_REWARD;
    HttpUtils.httpGet(url, {}, function (data) {
      UIUtils.loaded();
      PlayThree.RewardData = data.rows;
      if (!PlayThree.RewardData) return;
      var perMoney = PlayThree.SettingData ? PlayThree.SettingData.perMoney : 0;
      var hzMin = 0;
      var hzMax = 0;
      var rewardMoney = 0;
      for (var i = 0; i < PlayThree.RewardData.length; i++) {
        var reward = PlayThree.RewardData[i];
        rewardMoney = perMoney * reward.rewardTimes;
        //??????
        if (reward.rewardCode >= 3 && reward.rewardCode <= 18) {
          $("#panl_hz").find("li[data-value='" + reward.rewardCode + "']").attr("data-money", rewardMoney).find("span").text("??????" + rewardMoney);
          if (reward.rewardCode == 3) {
            $("#panl_hz").find(".title em").text(rewardMoney);
            hzMax = rewardMoney;
          } else if (reward.rewardCode == 10) {
            hzMin = rewardMoney;
          }
        } else if (reward.rewardCode == 30) {
          //3????????????
          $("#k3_tab").find("li[name='3tx'] em").text("?????????" + rewardMoney);
          $("#panl_3tx").find(".title em").text(rewardMoney);
          $("#panl_3tx").find(".js_ball").attr("data-money", rewardMoney);
        }else if (reward.rewardCode == 31) {
          //3????????????
          $("#k3_tab").find("li[name='3dx'] em").text("?????????" + rewardMoney);
          $("#panl_3dx").find(".title em").text(rewardMoney);
          $("#panl_3dx").find(".js_ball").attr("data-money", rewardMoney);
        }else if (reward.rewardCode == 32) {
          //3????????????
          $("#k3_tab").find("li[name='3bt'] em").text("?????????" + rewardMoney);
          $("#panl_3bt").find(".title em").text(rewardMoney);
          $("#panl_3bt").find(".js_ball").attr("data-money", rewardMoney);
        }else if (reward.rewardCode == 33) {
          //3????????????
          $("#k3_tab").find("li[name='3lt'] em").text("?????????" + rewardMoney);
          $("#panl_3lt").find(".title em").text(rewardMoney);
          $("#panl_3lt").find(".js_ball").attr("data-money", rewardMoney);
        }else if (reward.rewardCode == 20) {
          //2????????????
          $("#k3_tab").find("li[name='2fx'] em").text("?????????" + rewardMoney);
          $("#panl_2fx").find(".title em").text(rewardMoney);
          $("#panl_2fx").find(".js_ball").attr("data-money", rewardMoney);
        }else if (reward.rewardCode == 21) {
          //2????????????
          $("#k3_tab").find("li[name='2dx'] em").text("?????????" + rewardMoney);
          $("#panl_2dx").find(".title em").text(rewardMoney);
          $("#panl_2dx").find(".js_ball").attr("data-money", rewardMoney);
        }else if (reward.rewardCode == 22) {
          //2?????????
          $("#k3_tab").find("li[name='2bt'] em").text("?????????" + rewardMoney);
          $("#panl_2bt").find(".title em").text(rewardMoney);
          $("#panl_2bt").find(".js_ball").attr("data-money", rewardMoney);
        }
        $("#k3_tab").find("li[name='hz'] em").text("?????????" + hzMin + "-" + hzMax);
      }
      // $("#info_allmoney").text(PlayThree.formatAllMoney(PlayThree.SettingData.allMoney));
    });
  },
  /**
   * ?????????
   */
  "initFront": function () {
    UIUtils.loading();
    var url = Config.WEB_SERVER_API + Config.URI.PLAY_THREE_RESULT_FRONT;
    HttpUtils.httpGet(url, {}, function (data) {
      UIUtils.loaded();
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
      if(PlayThree.FrontData.sumNumber<=10){//???
        $("#front1_numType1").text("???").addClass('nt_small');
        $("#front2_numType1").text("???").addClass('nt_small');
      }else{
        $("#front1_numType1").text("???").addClass('nt_big');
        $("#front2_numType1").text("???").addClass('nt_big');
      }
      if(PlayThree.FrontData.sumNumber%2!=0){//???
        $("#front1_numType2").text("???").addClass('nt_odd');
        $("#front2_numType2").text("???").addClass('nt_odd');
      }else{
        $("#front1_numType2").text("???").addClass('nt_dual');
        $("#front2_numType2").text("???").addClass('nt_dual');
      }
    });
  },
  /**
   * ?????????????????????
   */
  "initWait": function () {
    $("#countDownRight1").hide();
    $("#countDownRight2").hide();
    $("#countDownRight3").hide();
    $("#wait_timedown").text("00:00:00");
    $("#wait_timedown1").text("00:00");
    UIUtils.loading();
    var url = Config.WEB_SERVER_API + Config.URI.PLAY_THREE_RESULT_WAIT;
    HttpUtils.httpGet(url, {}, function (data) {
      UIUtils.loaded();
      PlayThree.WaitData = data.rows || {};
      if (!PlayThree.WaitData) {
        $("#wait_timedown").text("????????????");
        PlayThree.IsStop = true;
        return;
      }
      $("#current_time1").text(PlayThree.WaitData.playTime);
      $("#current_time2").text(PlayThree.WaitData.playTime);
      $("#current_time3").text(PlayThree.WaitData.playTime);
      $("#wait_open").text(PlayThree.WaitData.dayIndex - 1);
      $("#wait_leave").text(PlayThree.SettingData.countTime - PlayThree.WaitData.dayIndex + 1);
      if (PlayThree.WaitData.nowTime <= PlayThree.WaitData.endTime) {//?????????
        $("#countDownRight1").show();
        PlayThree.initTimerWait();
      }
      else if (PlayThree.WaitData.nowTime < PlayThree.WaitData.publishTime) {//?????????????????????
        $("#countDownRight2").show();
        PlayThree.initTimerOpen();
      }
      else{//?????????
        $("#countDownRight3").show();
      }
    });
  },
  "initTimerWait":function(){
    //???????????????
    PlayThree.SecondTime = parseInt(Math.abs(PlayThree.WaitData.nowTime - PlayThree.WaitData.endTime) / 1000);
    PlayThree.Timer = setInterval(function () {
      PlayThree.SecondTime--;
      if (PlayThree.SecondTime <= 0) {
        //?????????
        PlayThree.IsStop = true;
        $("#wait_timedown").text("00:00:00");
        clearInterval(PlayThree.Timer);
        PlayThree.initWait();
        layer.alert('???????????? ' + PlayThree.WaitData.playTime + ' ?????????????????????????????? ' + (PlayThree.WaitData.playTime + 1) + ' ?????????????????????????????????????????????');
      } else {
        //?????????????????????
        var days = Math.floor(PlayThree.SecondTime / (24 * 3600));
        //??????????????????
        var leave1 = PlayThree.SecondTime % (24 * 3600);    //??????????????????????????????
        var hours = Math.floor(leave1 / 3600) + (days > 0 ? days * 24 : 0);
        hours = (hours < 10 ? '0' : '') + hours;
        //?????????????????????
        var leave2 = leave1 % 3600;        //????????????????????????????????????
        var minutes = Math.floor(leave2 / 60);
        minutes = (minutes < 10 ? '0' : '') + minutes;
        //??????????????????
        var leave3 = leave2 % 60;      //????????????????????????????????????
        var seconds = Math.round(leave3);
        seconds = (seconds < 10 ? '0' : '') + seconds;
        $("#wait_timedown").text(hours + ":" + minutes + ":" + seconds);
      }
    }, 1000);
  },
  "initTimerOpen":function(){
    //???????????????
    PlayThree.SecondTime1 = parseInt(Math.abs(PlayThree.WaitData.nowTime - PlayThree.WaitData.publishTime) / 1000);
    PlayThree.Timer1 = setInterval(function () {
      PlayThree.SecondTime1--;
      if (PlayThree.SecondTime1 <= 0) {
        clearInterval(PlayThree.Timer1);
        $("#wait_timedown1").text("00:00");
        $("#countDownRight2").hide();
        $("#countDownRight3").show();
        setTimeout(function () {
          PlayThree.initFront();
          PlayThree.initWait();
          PlayThree.initMore();
        }, 10000);
      } else {
        //?????????????????????
        var days = Math.floor(PlayThree.SecondTime1 / (24 * 3600));
        //??????????????????
        var leave1 = PlayThree.SecondTime1 % (24 * 3600);    //??????????????????????????????
        var hours = Math.floor(leave1 / 3600) + (days > 0 ? days * 24 : 0);
        hours = (hours < 10 ? '0' : '') + hours;
        //?????????????????????
        var leave2 = leave1 % 3600;        //????????????????????????????????????
        var minutes = Math.floor(leave2 / 60);
        minutes = (minutes < 10 ? '0' : '') + minutes;
        //??????????????????
        var leave3 = leave2 % 60;      //????????????????????????????????????
        var seconds = Math.round(leave3);
        seconds = (seconds < 10 ? '0' : '') + seconds;
        $("#wait_timedown1").text(minutes + ":" + seconds);
      }
    }, 1000);
  },
  "initMore": function () {
    UIUtils.loading();
    var url = Config.WEB_SERVER_API + Config.URI.PLAY_THREE_RESULT_FINISHED;
    HttpUtils.httpGet(url, {offset:1,limit:5}, function (data) {
      UIUtils.loaded();
      $("#moreAward").empty();
      if(data && data.rows){
        for(var i=0;i<data.rows.length;i++){
          var item = data.rows[i];
          var html = ''+
          (i%2==0?'<tr>':'<tr bgcolor="f0f1f2">')+
            '<td>'+item.playTime+'???</td>'+
            '<td><span class="smallball">'+
            ' <em class="kuai3Ball num'+item.number1+'">'+item.number1+'</em>'+
            ' <em class="kuai3Ball num'+item.number2+'">'+item.number2+'</em>'+
            ' <em class="kuai3Ball num'+item.number3+'">'+item.number3+'</em>'+
            '</span></td>'+
            '<td>'+PlayThree.getRewardMode(item.modeName)+'</td>'+
            '<td><strong class="c_ba2636">'+item.sumNumber+'</strong></td>';
            if(item.sumNumber>10){
              html += '<td class="numForm big">???</td>'+
              '<td>&nbsp;</td>';
            }else{
              html += '<td>&nbsp;</td>'+
              '<td class="numForm small">???</td>';
            }
            if(item.sumNumber%2!=0){
              html += '<td class="numForm odd">???</td>'+
              '<td>&nbsp;</td>';
            }else{
              html += '<td>&nbsp;</td>'+
              '<td class="numForm dual">???</td>';
            }
            html += '<td>'+FormatUtils.parseTimestampToDateTime(item.publishTime)+'</td>'+
          '</tr>';
          $("#moreAward").append(html);
        }
      }
    });
  },
  "getRewardMode":function(modeName){
    switch(modeName){
      case "?????????":
      return "<em>?????????</em><em>???????????????</em><em>???????????????</em>";
      case "????????????":
      return "<em>????????????</em><em>????????????</em>";      
      case "?????????":
      return "<em>?????????</em><em>????????????</em><em>????????????</em>";     
      case "?????????":
      return "<em>???????????????</em><em>???????????????</em><em>????????????</em>";
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
      layer.alert('????????????????????????', { title: '?????????', icon: 5 });
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
      layer.alert('????????????????????????', { title: '?????????', icon: 5 });
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
      layer.alert('?????????6???????????????1???????????????', { title: '?????????', icon: 5 });
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
      layer.alert('????????????????????????', { title: '?????????', icon: 5 });
      return;
    }
    if (PlayThree.SelectBallData.length > 0) {
      layer.confirm('??????????????????????????????', { icon: 3, title: '??????' }, function (index) {
        PlayThree.SelectBallData = [];
        PlayThree.setTableListData();
        layer.close(index);
      });
    }
  },
  "setTableListData": function () {
    if (PlayThree.IsStop) {
      layer.alert('????????????????????????', { title: '?????????', icon: 5 });
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
      layer.alert('????????????????????????', { title: '?????????', icon: 5 });
      return false;
    }
    if (PlayThree.SelectBallData.length <= 0) {
      layer.alert('??????????????????', { title: '?????????', icon: 5 });
      return false;
    }
    DataUtils.getUserMoneyList2(function (data) {
      if (!data || data.money < parseFloat($("#countAllMoney").val())) {
        layer.alert('?????????????????????????????????', { title: '?????????', icon: 5 });
        return false;
      }
      var t = new Date().getTime();
      layer.open({
        type: 2
        , title: '????????????'
        , offset: 'auto' //?????????????????????http://www.layui.com/doc/modules/layer.html#offset
        , area: ['500px', '340px']
        , id: 'layerDemo' //??????????????????
        , content: 'threeadd.html?t=' + t + "&count=" + $("#countAllSum").text() + "&money=" + $("#countAllMoney").text()
        , shade: 0.2 //???????????????
        , yes: function () {
          layer.closeAll();
        }
      });
    });
  },
  "formatAllMoney": function (allMoney) {
    if (allMoney != null) {
      if (allMoney < 10000 * 10000) { //??????1???
        if (allMoney > 10000) {
          allMoneyName = allMoney / 10000 + "???" + allMoney % 10000 + "";
        } else {
          allMoneyName = allMoney + "";
        }
      } else {
        allMoneyName = allMoney / (10000 * 10000) + "???" + allMoney / 10000 + "???";
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
        return "??????";
      case 10:
      return "???????????????";
      case 20:
        return "???????????????";
      case 21:
        return "???????????????";
      case 22:
        return "????????????";
      case 30:
        return "???????????????";
      case 31:
        return "???????????????";
      case 32:
        return "????????????";
      case 33:
        return "???????????????";
    }
    return "";
  }
};

//??????
var PlayThreeHeZhi = {
  "init": function () {
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
        $("#panl_hz").find(".select_reward").html('??????????????????' + PlayThree.getRewardStrong(min) + '???' + PlayThree.getRewardStrong(max) + 'DG?????????' + PlayThree.getRewardStrong(min - count * perMoney) + '???' + PlayThree.getRewardStrong(max - count * perMoney) + 'DG');
      } else {
        $("#panl_hz").find(".select_reward").html('??????????????????<strong style="color:red;"> ' + rewardSum + ' </strong>DG?????????<strong style="color:red;"> ' + (rewardSum - perMoney) + ' </strong>DG');
      }
    } else {
      $("#panl_hz").find(".select_reward").text("");
    }
    PlayThreeHeZhi.setSureSelectButton(count);
  },
  "setSureSelectButton": function (count) {
    if (PlayThree.IsStop) {
      layer.alert('????????????????????????', { title: '?????????', icon: 5 });
      return;
    }
    if (count > 0) {
      $("#panl_hz #selectBall").removeClass("layui-btn-disabled");
    } else {
      $("#panl_hz #selectBall").addClass("layui-btn-disabled");
    }
  },
  "selectBall": function () {
    if (PlayThree.IsStop) {
      layer.alert('????????????????????????', { title: '?????????', icon: 5 });
      return;
    }
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
      $("#panl_hz").find(".js_ball").removeClass("active");
      PlayThreeHeZhi.select();
    }
    else {
      layer.alert('???????????????3-18?????????????????????', { title: '?????????', icon: 5 });
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
      layer.alert('????????????100??????', { title: '?????????', icon: 5 });
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


//3??????
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
      $("#panl_3tx").find(".select_reward").html('??????????????????' + PlayThree.getRewardStrong(rewardSum) + 'DG?????????' + PlayThree.getRewardStrong(rewardSum - perMoney) + 'DG');
    } else {
      $("#panl_3tx").find(".select_reward").text("");
    }
    PlayThree3Tx.setSureSelectButton(count);
  },
  "setSureSelectButton": function (count) {
    if (PlayThree.IsStop) {
      layer.alert('????????????????????????', { title: '?????????', icon: 5 });
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
      layer.alert('????????????????????????', { title: '?????????', icon: 5 });
      return;
    }
    if ($("#panl_3tx").find(".js_ball").hasClass("active")) {
      var sumMoney = PlayThree.WaitData.perMoney;
      PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 30, number: "???????????????", sumMoney: sumMoney, sumCount: 1 });
      PlayThree.setTableListData();
      $("#panl_3tx").find(".js_ball").removeClass("active");
      PlayThree3Tx.select();
    }
    else {
      layer.alert('???????????????????????????', { title: '?????????', icon: 5 });
    }
  },
  "randomOneBall": function () {
    var arr = this.randomBall(1);
    var sumMoney = PlayThree.WaitData.perMoney * 1;
    PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 30, number: "???????????????", sumMoney: sumMoney, sumCount: 1 });
    PlayThree.setTableListData();
  },
  "randomNumberBall": function () {
    var count = $("#txtNumberBall").val() || "5";
    if (parseInt(count) > 100 || parseInt(count) <= 0) {
      layer.alert('????????????100??????', { title: '?????????', icon: 5 });
      return false;
    }
    count = parseInt(count);
    var sumMoney = PlayThree.WaitData.perMoney * 1;
    for (var i = 0; i < count; i++) {
      PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 30, number: "???????????????", sumMoney: sumMoney, sumCount: 1 });
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

//3??????
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
      $("#panl_3dx").find(".select_reward").html('??????????????????' + PlayThree.getRewardStrong(rewardSum) + 'DG?????????' + PlayThree.getRewardStrong(rewardSum - perMoney * count) + 'DG');
    } else {
      $("#panl_3dx").find(".select_reward").text("");
    }
    PlayThree3Dx.setSureSelectButton(count);
  },
  "setSureSelectButton": function (count) {
    if (PlayThree.IsStop) {
      layer.alert('????????????????????????', { title: '?????????', icon: 5 });
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
      layer.alert('????????????????????????', { title: '?????????', icon: 5 });
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
      layer.alert('????????????????????????', { title: '?????????', icon: 5 });
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
      layer.alert('????????????100??????', { title: '?????????', icon: 5 });
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

//3?????????
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
      $("#panl_3bt").find(".select_reward").html('??????????????????' + PlayThree.getRewardStrong(rewardSum) + 'DG?????????' + PlayThree.getRewardStrong(rewardSum - perMoney * sumCount) + 'DG');
    } else {
      $("#panl_3bt").find(".select_reward").text("");
    }
    PlayThree3Bt.setSureSelectButton(count);
  },
  "setSureSelectButton": function (count) {
    if (PlayThree.IsStop) {
      layer.alert('????????????????????????', { title: '?????????', icon: 5 });
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
      layer.alert('????????????????????????', { title: '?????????', icon: 5 });
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
      layer.alert('??????????????? 3 ????????????', { title: '?????????', icon: 5 });
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
      layer.alert('????????????100??????', { title: '?????????', icon: 5 });
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

//3????????????
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
      $("#panl_3lt").find(".select_reward").html('??????????????????' + PlayThree.getRewardStrong(rewardSum) + 'DG?????????' + PlayThree.getRewardStrong(rewardSum - perMoney) + 'DG');
    } else {
      $("#panl_3lt").find(".select_reward").text("");
    }
    PlayThree3Lt.setSureSelectButton(count);
  },
  "setSureSelectButton": function (count) {
    if (PlayThree.IsStop) {
      layer.alert('????????????????????????', { title: '?????????', icon: 5 });
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
      layer.alert('????????????????????????', { title: '?????????', icon: 5 });
      return;
    }
    if ($("#panl_3lt").find(".js_ball").hasClass("active")) {
      var sumMoney = PlayThree.WaitData.perMoney;
      PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 33, number: "???????????????", sumMoney: sumMoney, sumCount: 1 });
      PlayThree.setTableListData();
      $("#panl_3lt").find(".js_ball").removeClass("active");
      PlayThree3Lt.select();
    }
    else {
      layer.alert('???????????????????????????', { title: '?????????', icon: 5 });
    }
  },
  "randomOneBall": function () {
    var arr = this.randomBall(1);
    var sumMoney = PlayThree.WaitData.perMoney * 1;
    PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 33, number: "???????????????", sumMoney: sumMoney, sumCount: 1 });
    PlayThree.setTableListData();
  },
  "randomNumberBall": function () {
    var count = $("#txtNumberBall").val() || "5";
    if (parseInt(count) > 100 || parseInt(count) <= 0) {
      layer.alert('????????????100??????', { title: '?????????', icon: 5 });
      return false;
    }
    count = parseInt(count);
    var sumMoney = PlayThree.WaitData.perMoney * 1;
    for (var i = 0; i < count; i++) {
      PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 33, number: "???????????????", sumMoney: sumMoney, sumCount: 1 });
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

//2????????????
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
      $("#panl_2fx").find(".select_reward").html('??????????????????' + PlayThree.getRewardStrong(rewardSum) + 'DG?????????' + PlayThree.getRewardStrong(rewardSum - perMoney * count) + 'DG');
    } else {
      $("#panl_2fx").find(".select_reward").text("");
    }
    PlayThree2Fx.setSureSelectButton(count);
  },
  "setSureSelectButton": function (count) {
    if (PlayThree.IsStop) {
      layer.alert('????????????????????????', { title: '?????????', icon: 5 });
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
      layer.alert('????????????????????????', { title: '?????????', icon: 5 });
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
      layer.alert('????????????????????????', { title: '?????????', icon: 5 });
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
      layer.alert('????????????100??????', { title: '?????????', icon: 5 });
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

//2????????????
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
      $("#panl_2dx").find(".select_reward").html('??????????????????' + PlayThree.getRewardStrong(reward) + 'DG?????????' + PlayThree.getRewardStrong(reward*count - perMoney * count) + 'DG');
    } else {
      $("#panl_2dx").find(".select_reward").text("");
    }
    PlayThree2Dx.setSureSelectButton(count);
  },
  "setSureSelectButton": function (count) {
    if (PlayThree.IsStop) {
      layer.alert('????????????????????????', { title: '?????????', icon: 5 });
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
      layer.alert('????????????????????????', { title: '?????????', icon: 5 });
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
      layer.alert('??????????????????????????????', { title: '?????????', icon: 5 });
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
      layer.alert('????????????100??????', { title: '?????????', icon: 5 });
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

//2?????????
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
      $("#panl_2bt").find(".select_reward").html('??????????????????' + PlayThree.getRewardStrong(rewardSum) + 'DG?????????' + PlayThree.getRewardStrong(rewardSum - perMoney * sumCount) + 'DG');
    } else {
      $("#panl_2bt").find(".select_reward").text("");
    }
    PlayThree2Bt.setSureSelectButton(count);
  },
  "setSureSelectButton": function (count) {
    if (PlayThree.IsStop) {
      layer.alert('????????????????????????', { title: '?????????', icon: 5 });
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
      layer.alert('????????????????????????', { title: '?????????', icon: 5 });
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
      layer.alert('??????????????? 2 ????????????', { title: '?????????', icon: 5 });
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
      layer.alert('????????????100??????', { title: '?????????', icon: 5 });
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

//1????????????
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
      $("#panl_1tx").find(".select_reward").html('??????????????????' + PlayThree.getRewardStrong(rewardMin) + '???' + PlayThree.getRewardStrong(rewardMax) + 'DG?????????' + PlayThree.getRewardStrong(rewardMin - perMoney*count) + '???' + PlayThree.getRewardStrong(rewardMax - perMoney*count) + 'DG');
    } else {
      $("#panl_1tx").find(".select_reward").text("");
    }
    PlayThree1Tx.setSureSelectButton(count);
  },
  "setSureSelectButton": function (count) {
    if (PlayThree.IsStop) {
      layer.alert('????????????????????????', { title: '?????????', icon: 5 });
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
      layer.alert('????????????????????????', { title: '?????????', icon: 5 });
      return;
    }
    var count = $("#panl_1tx .active").length > 0 ? 21 : 0;
    var perMoney = PlayThree.WaitData ? PlayThree.WaitData.perMoney : 0;
    if (count > 0) {
      var code = $("#panl_1tx .active").attr("data-value");
      //???????????????
      PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 31, number: code+" "+code+" "+code, sumMoney: perMoney, sumCount: 1});
      //????????????
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
      //???????????????
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
      layer.alert('????????????????????????', { title: '?????????', icon: 5 });
    }
  },
  "randomOneBall": function () {
    this.randomBall(1);
    PlayThree.setTableListData();
  },
  "randomNumberBall": function () {
    var count = $("#txtNumberBall").val() || "5";
    if (parseInt(count) > 100 || parseInt(count) <= 0) {
      layer.alert('????????????100??????', { title: '?????????', icon: 5 });
      return false;
    }
    this.randomBall(count);
    PlayThree.setTableListData();
    $("#txtNumberBall").val("");
  },
  "randomBall": function (count) {
    var sumMoney = PlayThree.WaitData.perMoney * 1;
    for (var j = 0; j < count; j++) {
      var random = Math.floor(Math.random() * 3 + 1);//3???
      if(random==1){//???????????????
        var arr = PlayThree3Dx.randomBall(1);
        PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 31, number: arr[0]+" "+arr[0]+" "+arr[0], sumMoney: sumMoney, sumCount: 1 });
      }else if(random==2){//????????????
        var arr = PlayThree3Bt.randomBall(1);
        PlayThree.SelectBallData.unshift({ id: PlayThree.createUUID(), mode: 32, number: arr[0], sumMoney: sumMoney, sumCount: 1 });
      }else{//???????????????
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

//??????
var ThreeNotice = {
  "init": function () {
      this.getData(function (data) {
          $.each(data, function (i, item) {
              var html = '<a class="btn-view" href="" target="_blank">' + item.title + '</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
              $("#seven_notice .body").append(html);
          });

          $('#seven_notice .body').marquee({
              speed: 100,
              direction: 'left',
              pauseOnHover: true
          });
      });
  },
  "getData": function (callback) {
      HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.USER_PLAY_THREE_REWARD_LIST, {offset:1,limit:10}, function (data) {
          if (callback) {
              callback(data.rows || []);
          }
      });
  }
};
