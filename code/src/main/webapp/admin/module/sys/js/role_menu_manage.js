$(function () {	
	var id = Utils.getUrlParam("id");
	if (id) {
		RoleMenuManage.RoleId = id;
		RoleMenuManage.getRoleMenu(function(){
			RoleMenuManage.init();
		});
	}
});

var zTreeObj;
var RoleMenuManage = {
	"RoleId": null,
	"RoleMenus":[],
	"init": function (callback) {
		//ROLE_MENU_SEARCH
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
				},
				callback: {
					//beforeCheck: beforeCheck,
					onCheck: onCheck
				}
	   };
	   function beforeCheck(treeId, treeNode) {
			className = (className === "dark" ? "":"dark");
			console.log("[ "+getTime()+" beforeCheck ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name );
			return (treeNode.doCheck !== false);
		}
		function onCheck(e, treeId, treeNode) {
			console.log(treeNode);
		}
		
	   var zNodes = RoleMenuManage.RoleMenus;
	   $(document).ready(function(){
	      zTreeObj = $.fn.zTree.init($("#treeMenu"), setting, zNodes);
	      zTreeObj.expandAll(true);
	   });
		   
		$("#btnClose").click(function () {
			if (parent && parent.UIUtils) {
				parent.UIUtils.closeWindow();
			}
		});
		$("#btnSave").click(function () {
			RoleMenuManage.saveData();
		});
	},
	"getRoleMenu":function(callback){
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.ROLE_MENU_SET + RoleMenuManage.RoleId, {}, function (data) {
			UIUtils.loaded();
			if (data && data.rows) {
				RoleMenuManage.RoleMenus = data.rows;
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
				if(item.pId>0){
					if(item.checked){
						adds.push(item.id);
					}else{
						deletes.push(item.id);
					}
				}
			});
			UIUtils.loading('保存中请稍候......');
			var data = {roleId: RoleMenuManage.RoleId, addIds: adds, deleteIds: deletes};
			HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.ROLE_MENU_SAVE, data, function (data) {
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
		
//		var selectValue = $("#tbForm").find("input[type='radio']:checked").val();
//		if (parent && parent.selectIcon) {
//			parent.selectIcon(selectValue);
//			parent.UIUtils.closeWindow();
//		}
	}
};