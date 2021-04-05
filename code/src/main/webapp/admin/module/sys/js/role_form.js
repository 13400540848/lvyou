$(function () {
	var id = Utils.getUrlParam("id");
	if (id) {
		RoleForm.Id = id;
	}
	 DataUtils.getDictItems(RoleForm.DictKeys, function(items){
		 RoleForm.DictItemList = items;
		 FormUtils.setFormFieldDict("tbForm", items);
		 RoleForm.init();
    });
});

var RoleForm = {
	"PageSize": 15,
	"DictKeys":["NORMAL_STATUS"],
	"DictItemList":null,
	"FormData":null,
	"Id": null,
	"init": function (callback) {
		$("#btnClose").click(function () {
			if (parent && parent.UIUtils) {
				parent.UIUtils.closeWindow();
			}
		});
		$("#btnSave").click(function () {
			RoleForm.saveData();
		});

		if (RoleForm.Id) {
			UIUtils.loading('正在查询请稍候......');
			HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.ROLE_INFO + RoleForm.Id, {}, function (data) {
				UIUtils.loaded();
				if (data && data.rows) {
					FormUtils.setFormFieldValue("tbForm", data.rows);
					RoleForm.FormData = data.rows;
				}
			});			
		}
	},
	"saveData": function () {
		if(!FormUtils.validateFormFieldValue("tbForm")){
			return;
		}
		var query = FormUtils.getFormFieldValue("tbForm", RoleForm.FormData);
		UIUtils.loading('正在保存请稍候......');
		HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.ROLE_SAVE, query, function (data, success) {
			UIUtils.loaded();
			if(success){
				UIUtils.tip("保存成功", function(){
					if (parent && parent.RoleManage) {
						parent.RoleManage.search();
						parent.UIUtils.closeWindow();
					}
				});				
			}
		});
	}
};