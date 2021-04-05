$(function () {
	var id = Utils.getUrlParam("id");
	if (id) {
		UserForm.Id = id;
		$("#divPassword").remove();
		$("#divPassword1").remove();
	}
	 DataUtils.getDictItems(UserForm.DictKeys, function(items){
		 UserForm.DictItemList = items;
		 FormUtils.setFormFieldDict("tbForm", items);
		 UserForm.init();
    });
});

var UserForm = {
	"DictKeys":["USER_TYPE", "NORMAL_STATUS","SEX"],
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
			UserForm.saveData();
		});
		$("#btnIcon").click(function () {
			UserForm.openIcon();
		});
		

		if (UserForm.Id) {
			UIUtils.loading('正在查询请稍候......');
			HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.USER_INFO + UserForm.Id, {}, function (data) {
				UIUtils.loaded();
				if (data && data.rows) {
					FormUtils.setFormFieldValue("tbForm", data.rows);
					UserForm.FormData = data.rows;
				}
			});			
		}
	},
	"saveData": function () {
		if(!FormUtils.validateFormFieldValue("tbForm")){
			return;
		}
		var query = FormUtils.getFormFieldValue("tbForm", UserForm.FormData);
		if(query.password !== query.password1){
			UIUtils.tip("两次输入的密码不一致！");
			return;
		}
		if(query.mobilePhone){
			if(!FormUtils.isEmail(query.mobilePhone)){
				UIUtils.tip("手机号格式有误！");
				return ;
			}
		}
		if(query.mail){
			if(!FormUtils.isEmail(query.mail)){
				UIUtils.tip("邮箱格式有误！");
				return ;
			}
		}
		UIUtils.loading('正在保存请稍候......');
		HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.USER_SAVE, query, function (data, success) {
			UIUtils.loaded();
			if(success){
				UIUtils.tip("保存成功", function(){
					if (parent && parent.UserManage) {
						parent.UserManage.search();
						parent.UIUtils.closeWindow();
					}
				});
			}
		});
	},
	"openIcon" : function(){
		var t  = new Date().getTime();
		var url = "select_icon.html?t=" + t;
		if(UserForm.FormData){
			url += "&icon=" + UserForm.FormData.icon;
		}
		UIUtils.openWindow("选择图标", url, 500, 320);
	}
};

var selectIcon = function(value){
	$("#tbIcon").val(value);
	UserForm.FormData.icon = value;	
}