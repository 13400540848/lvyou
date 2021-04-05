
$(function () {
	PlayManage.init();
});

var PlayManage = {
	"SevenData":null,
	"init": function () {
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.PLAY_SEVEN, {}, function(data){
			UIUtils.loaded();
			if (data && data.rows) {
				Utils.setFormFieldValue("tbForm1", data.rows);
				var playRate = data.rows.playRate;
				var rates = $("#tbPlayRate").find("input[type=checkbox]");
				$.each(rates, function(i, item){
					if(playRate.indexOf($(item).val())>=0){
						$(item).prop("checked", true);
					}
				});
			}
		});

		$("#btnSave1").click(function () {
			PlayManage.saveData1();
		});
		$("#btnStart1").click(function () {
			PlayManage.startService1();
		});
		
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.PLAY_THREE, {}, function(data){
			UIUtils.loaded();
			if (data && data.rows) {
				Utils.setFormFieldValue("tbForm2", data.rows);
			}
		});

		$("#btnSave2").click(function () {
			PlayManage.saveData2();
		});
		$("#btnStart2").click(function () {
			PlayManage.startService2();
		});
	},
	"saveData1": function () {
		if(!Utils.validateFormFieldValue("tbForm1")){
			return;
		}		
		// var rates = $("#tbPlayRate").find("input[type=checkbox]:checked");
		// if(!rates || rates.length == 0 ){
		// 	UIUtils.alert("请选择开奖频率！");
		// 	return;
		// }
		// var playRate = "";
		// $.each(rates, function(i, item){
		// 	playRate += $(item).val() + ",";
		// });
		// playRate = playRate.substring(0, playRate.length - 1);
		var query = Utils.getFormFieldValue("tbForm1");
		UIUtils.loading('正在保存请稍候......');
		HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.PLAY_SEVEN, query, function (data, success) {
			UIUtils.loaded();
			if(success){
				PlayManage.startService1();
			}else{
				UIUtils.alert(data.resultMsg);
			}
		});
	},
	"startService1": function () {
		UIUtils.loading('正在重新启动服务，请稍候......');
		HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.PLAY_SEVEN_START_SERVICE, {}, function (data, success) {
			UIUtils.loaded();
			if(success){
				UIUtils.alert("保存成功，服务已正常运行！", function(){
					window.location.reload();
				});	
			}else{
				UIUtils.alert("服务启动失败：" + data.resultMsg, function(){
					// window.location.reload();
				});	
			}
		});
	},
	"saveData2": function () {
		if(!Utils.validateFormFieldValue("tbForm2")){
			return;
		}
		var query = Utils.getFormFieldValue("tbForm2");
		UIUtils.loading('正在保存请稍候......');
		HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.PLAY_THREE, query, function (data, success) {
			UIUtils.loaded();
			if(success){
				PlayManage.startService2();
			}else{
				UIUtils.alert(data.resultMsg);
			}
		});
	},
	"startService2": function () {
		UIUtils.loading('正在重新启动服务，请稍候......');
		HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.PLAY_THREE_START_SERVICE, {}, function (data, success) {
			UIUtils.loaded();
			if(success){
				UIUtils.alert("保存成功，服务已正常运行！", function(){
					window.location.reload();
				});	
			}else{
				UIUtils.alert("服务启动失败：" + data.resultMsg, function(){
					// window.location.reload();
				});	
			}
		});
	}
};