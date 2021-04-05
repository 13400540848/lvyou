$(function () {
	MyInfo.init(function(){
		$("#btnInfoSave").click(function () {
			MyInfo.saveInfo();
		});
		$("#btnPwdSave").click(function () {
			MyInfo.savePwd();
		});

	});
});

var zTreeRoleObj, zTreeMenuObj;
var zTreeSetting = {
	view: {
		selectedMulti: false
	},
	check: {
		enable: false
	},
	data: {
		simpleData: {
			enable: true
		}
	}
}
var MyInfo = {
	"UserInfo": null,
	"UserRoles":[],
	"MenuData":[],
	"init": function (callback) {
		Utils.getUserInfo(function (data) {
			FormUtils.setFormFieldValue("tbInfoForm", data);
			MyInfo.UserInfo = data;
			console.log("用户信息：",  data);
			if(callback){
				callback();
			}
			MyInfo.getUserRole(function (){
				var zNodes = MyInfo.UserRoles;
				zTreeRoleObj = $.fn.zTree.init($("#treeRole"), zTreeSetting, zNodes);
				zTreeRoleObj.expandAll(true);
			});
			DataUtils.getUserMenuList(function(data){
				MyInfo.MenuData = data;
				if(data!=null && data.length>0){
					$.each(data, function (i, item){
						if(item.children!=null){
							$.each(item.children, function (j, child){
								child.icon = null;
								child.url = null;
							});
						}
					});
				}
				console.log(data);
				var zNodes = data;
				zTreeMenuObj = $.fn.zTree.init($("#treeMenu"), zTreeSetting, zNodes);
				zTreeMenuObj.expandAll(true);
			});
		});
	},
	"getUserRole":function(callback){
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.USER_ROLE_INFO, {}, function (data) {
			UIUtils.loaded();
			if (data && data.rows) {
				MyInfo.UserRoles = data.rows;
			}
			if(callback){
				callback();
			}
		});
	},
	"saveInfo": function () {
		if(!FormUtils.validateFormFieldValue("tbInfoForm")){
			return;
		}
		var data = FormUtils.getFormFieldValue("tbInfoForm");
		if(!FormUtils.isMobilePhone(data.mobilePhone)){
			UIUtils.tip("手机号格式有误！");
			return ;
		}
		UIUtils.loading('保存中请稍候......');
		HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.USER_SAVE_INFO, data, function (data) {
			UIUtils.loaded();
			UIUtils.tip("保存成功", function(){
				location.reload();
			});
		});
	},
	"savePwd": function () {
		if(!FormUtils.validateFormFieldValue("tbPwdForm")){
			return;
		}
		var data = FormUtils.getFormFieldValue("tbPwdForm");
		if(data.newPassword != data.surePassword){
			UIUtils.tip("两次输入的密码不一致！");
			return;
		}else {
			UIUtils.loading('保存中请稍候......');
			HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.USER_MODIFY_PASSWORD, data, function (data) {
				UIUtils.loaded();
				UIUtils.tip("保存成功", function () {
					location.reload();
				});
			});
		}
	}
};