$(function () {
	var id = Utils.getUrlParam("id");
	if (id) {
		PlayRewardAdd.Id = id;
	}
	PlayRewardAdd.init();
});

var PlayRewardAdd = {
	"Id": null,
	"init": function () {
		$("#btnClose").click(function () {
			if (parent && parent.UIUtils) {
				parent.UIUtils.closeWindow();
			}
		});
		$("#btnSave").click(function () {
			PlayRewardAdd.saveData();
		});

		if (PlayRewardAdd.Id) {
			UIUtils.loading('正在查询请稍候......');
			HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.PLAY_SEVEN_REWRAD + "/" + PlayRewardAdd.Id, {}, function (data) {
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
		if (PlayRewardAdd.Id) {
			query.id = PlayRewardAdd.Id;
		}
		UIUtils.loading('正在保存请稍候......');
		HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.PLAY_SEVEN_REWRAD, query, function (data, success) {
			UIUtils.loaded();
			if(success){
				if (parent && parent.PlayRewardManage) {
					parent.PlayRewardManage.search1();
					parent.UIUtils.closeWindow();
				}
			}
		});
	}
};