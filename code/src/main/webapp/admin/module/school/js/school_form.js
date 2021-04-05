$(function () {
	var id = Utils.getUrlParam("id");
	if (id) {
		SchoolForm.Id = id;
	}
	 DataUtils.getDictItems(SchoolForm.DictKeys, function(items){
		 SchoolForm.DictItemList = items;
		 FormUtils.setFormFieldDict("tbForm", items);
		 SchoolForm.init();
    });
});

var SchoolForm = {
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
			SchoolForm.saveData();
		});

		if (SchoolForm.Id) {
			UIUtils.loading('正在查询请稍候......');
			HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.SCHOOL_INFO + SchoolForm.Id, {}, function (data) {
				UIUtils.loaded();
				if (data && data.rows) {
					FormUtils.setFormFieldValue("tbForm", data.rows);
					SchoolForm.FormData = data.rows;
				}
			});			
		}
	},
	"saveData": function () {
		if(!FormUtils.validateFormFieldValue("tbForm")){
			return;
		}
		var query = FormUtils.getFormFieldValue("tbForm", SchoolForm.FormData);
		UIUtils.loading('正在保存请稍候......');
		HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.SCHOOL_SAVE, query, function (data, success) {
			UIUtils.loaded();
			if(success){
				UIUtils.tip("保存成功", function(){
					if (parent && parent.SchoolManage) {
						parent.SchoolManage.search();
						parent.UIUtils.closeWindow();
					}
				});				
			}
		});
	}
};