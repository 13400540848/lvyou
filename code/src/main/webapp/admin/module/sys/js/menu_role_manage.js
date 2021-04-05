$(function () {	
	var id = Utils.getUrlParam("id");
	if (id) {
		MenuRoleManage.MenuId = id;
		MenuRoleManage.getMenuRole(function(){
			MenuRoleManage.init();
		});
	}
});

var zTreeObj;
var MenuRoleManage = {
	"MenuId": null,
	"MenuRoles":[],
	"init": function () {
	   var setting = {
		   view: {
				selectedMulti: false
			},
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			}
	   };
		
	   var zNodes = MenuRoleManage.MenuRoles;
	   $(document).ready(function(){
	      zTreeObj = $.fn.zTree.init($("#treeRole"), setting, zNodes);
	      zTreeObj.expandAll(true);
	   });
		   
		$("#btnClose").click(function () {
			if (parent && parent.UIUtils) {
				parent.UIUtils.closeWindow();
			}
		});
		$("#btnSave").click(function () {
			MenuRoleManage.saveData();
		});
	},
	"getMenuRole":function(callback){
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.MENU_ROLE_SET + MenuRoleManage.MenuId, {}, function (data) {
			UIUtils.loaded();
			if (data && data.rows) {
				MenuRoleManage.MenuRoles = data.rows;
			}
			if(callback){
				callback();
			}
		});
	},
	"saveData": function () {
		var nodes = zTreeObj.getChangeCheckedNodes();
		if(nodes.length>0){
			var adds = [], deletes = [];
			$.each(nodes, function(i, item){
				if(item.checked){
					adds.push(item.id);
				}else{
					deletes.push(item.id);
				}
			});
			UIUtils.loading('保存中请稍候......');
			var data = {menuId: MenuRoleManage.MenuId, addIds: adds, deleteIds: deletes};
			HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.MENU_ROLE_SAVE, data, function (data) {
				UIUtils.loaded();
				UIUtils.tip("保存成功", function(){
					parent.UIUtils.closeWindow();
				});
			});
		}else{
			if (parent) {
				parent.UIUtils.closeWindow();
			}
		}
	}
};