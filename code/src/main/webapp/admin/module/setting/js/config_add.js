$(function () {
	var id = Utils.getUrlParam("id");
	if (id) {
		ConfigAdd.Id = id;
	}
	ConfigAdd.init();
});

var ConfigAdd = {
	"PageSize": 15,
	"Id": null,
	"init": function () {
		$("#btnClose").click(function () {
			if (parent && parent.UIUtils) {
				parent.UIUtils.closeWindow();
			}
		});
		$("#btnSave").click(function () {
			ConfigAdd.saveData();
		});

		if (ConfigAdd.Id) {
			UIUtils.loading('正在查询请稍候......');
			HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.CONFIG_INFO + ConfigAdd.Id, {}, function (data) {
				UIUtils.loaded();
				if (data && data.rows) {
					Utils.setFormFieldValue("tbForm", data.rows);
				}
			});			
		}
	},
	"saveData": function () {
		if(!Utils.validateFormFieldValue("tbForm")){
			return;
		}
		var query = Utils.getFormFieldValue("tbForm");
		if (ConfigAdd.Id) {
			query.id = ConfigAdd.Id;
		}
		UIUtils.loading('正在保存请稍候......');
		HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.CONFIG_SAVE, query, function (data, success) {
			UIUtils.loaded();
			if(success){
				if (parent && parent.ConfigManage) {
					parent.ConfigManage.search();
					parent.UIUtils.closeWindow();
				}
			}
		});
	}
};