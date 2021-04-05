$(function () {
	var pid = Utils.getUrlParam("pid");
	if (pid) {
		ProjectMoneyAdd.ProjectId = pid;
	}
	var id = Utils.getUrlParam("id");
	if (id) {
		ProjectMoneyAdd.Id = id;
	}
	ProjectMoneyAdd.init();
});

var ProjectMoneyAdd = {
	"PageSize": 15,
	"Id": null,
	"ProjectId": null,
	"EditData": null,
	"init": function () {
		$("#btnClose").click(function () {
			if (parent && parent.UIUtils) {
				parent.UIUtils.closeWindow();
			}
		});
		$("#btnSave").click(function () {
			ProjectMoneyAdd.saveData();
		});

		this.initType(function(){
			if (ProjectMoneyAdd.Id) {
				UIUtils.loading('正在查询请稍候......');
				HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.PROJECT_MONEY_INFO + ProjectMoneyAdd.Id, {}, function (data) {
					UIUtils.loaded();
					if (data && data.rows) {
						ProjectMoneyAdd.EditData = data.rows;
						Utils.setFormFieldValue("tbForm", data.rows);
					}
				});
				
			} else {
				
			}
		});
		
	},
	"initType": function (callback) {
		DataUtils.getMoneyTypeList("0", function(data){
			if (data) {
				$.each(data, function(i, item){
					$("#tbMoneyType").append("<option value='"+item.typeId+"'>"+item.typeName+"</option>");
				});	
			}
			if(callback){
				callback();
			}
		});
	},
	"saveData": function () {
		if(!Utils.validateFormFieldValue("tbForm")){
			return;
		}
		var query = Utils.getFormFieldValue("tbForm");
		if (ProjectMoneyAdd.Id) {
			query.id = ProjectMoneyAdd.Id;
			query.projectId = ProjectMoneyAdd.EditData.projectId;
		}
		if (ProjectMoneyAdd.ProjectId) {
			query.projectId = ProjectMoneyAdd.ProjectId;
		}
		UIUtils.loading('正在保存请稍候......');
		HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.PROJECT_MONEY_SAVE, query, function (data, success) {
			UIUtils.loaded();
			if(success){
				if (parent && parent.ProjectAdd) {
					parent.ProjectAdd.search();
					parent.UIUtils.closeWindow();
				}
			}
		});
	}
};