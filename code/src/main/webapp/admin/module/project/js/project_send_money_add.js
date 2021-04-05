$(function () {
	var id = Utils.getUrlParam("id");
	if (id) {
		ProjectSendMoneyAdd.Id = id;
		ProjectSendMoneyAdd.init();
	}	
});

var ProjectSendMoneyAdd = {
	"PageSize": 15,
	"Id": null,
	"MaxMoney": 0,
	"EditData": null,
	"init": function () {
		$("#btnClose").click(function () {
			if (parent && parent.UIUtils) {
				parent.UIUtils.closeWindow();
			}
		});
		$("#btnSave").click(function () {
			ProjectSendMoneyAdd.saveData();
		});
		if (ProjectSendMoneyAdd.Id) {
			UIUtils.loading('正在查询请稍候......');
			HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.PROJECT_INFO + ProjectSendMoneyAdd.Id, {}, function (data) {
				UIUtils.loaded();
				if (data && data.rows) {
					ProjectSendMoneyAdd.EditData = data.rows;
					ProjectSendMoneyAdd.MaxMoney = ProjectSendMoneyAdd.getAllMoney() - ProjectSendMoneyAdd.getSendMoney();
					$("#tbLeaveMoney").text(ProjectSendMoneyAdd.MaxMoney + " " + data.rows.moneyTypeName);
					$("#tbMoneyAll").val(ProjectSendMoneyAdd.MaxMoney);					
				}
			});
			
		}	
	},
	"getAllMoney":function(){
		var result = 0;
		var project = ProjectSendMoneyAdd.EditData;
        if(project.projectUsers!=null && project.projectUsers.length>0){
            for(var i=0;i<project.projectUsers.length;i++){
                result += project.projectUsers[i].projectMoney;
            }
		}
		return result;
	},
	"getSendMoney":function(){
		var result = 0;
		var project = ProjectSendMoneyAdd.EditData;
        if(project.projectUsers!=null && project.projectUsers.length>0){
            for(var i=0;i<project.projectUsers.length;i++){
                result += project.projectUsers[i].sendMoney;
            }
		}
		return result;
	},
	"saveData": function () {
		if(!Utils.validateFormFieldValue("tbForm")){
			return;
		}
		var query = Utils.getFormFieldValue("tbForm");
		query.projectId = ProjectSendMoneyAdd.Id;
		UIUtils.loading('正在保存请稍候......');
		HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.PROJECT_MONEY_SEND_SAVE, query, function (data, success) {
			UIUtils.loaded();
			if(success){
				if (parent && parent.ProjectSendAdd) {
					parent.ProjectSendAdd.search();
					parent.UIUtils.closeWindow();
				}
			}
		});
	}
};