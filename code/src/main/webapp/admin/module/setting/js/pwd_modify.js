$(function () {
	PwdModify.init();
});

var PwdModify = {
	"init": function () {
		$("#btnSave").click(function () {
			PwdModify.saveData();
		});
	},
	"saveData": function () {
		if(!Utils.validateFormFieldValue("tbForm")){
			return;
		}
		var query = Utils.getFormFieldValue("tbForm");
		if(query.newPassword != query.surePassword){
			UIUtils.alert("两次输入的密码不一致，请重新输入！", function(){
				$("#tbSurePassword").focus();
			});
			return;
		}
		UIUtils.loading('正在保存请稍候......');
		HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.MODIFY_PASSWORD, query, function (data, success) {
			UIUtils.loaded();
			if(success){
				UIUtils.alert("修改成功，请重新登录！", function(){
					Utils.exitSystem();
				});	
			}
		});
	}
};