 angular.element(document).ready(function() {
      angular.module("AccountApp",[])
      .controller("AccountCtrl",["$scope","$http",function($scope,$http){
          $scope.groups = [];
          //当页面完成赋值，则需要加载用户信息
          $scope.$watch("userId",function(n,o) {
              if(n) {
                $http.get("/v0.1/user/"+$scope.userId)
                .then(function(resp){
                 //用户
                 $scope.user = resp.data.data;
                 $scope.queryData();
                })
              }
          });

          //查询
          $scope.queryData = function() {
               $http.get("/v0.1/article/byuser",{
                 params:{
                   offset:0,
                   limit:200,
                   orderby:'createTime',
                   direction:'DESC',
                   user_id: $scope.user.id
                 }
               })
               .then(function(respx){
                //大事记
                var gmap = {};
                var items = respx.data.data.content;
                for(var i=0;i<items.length;i++) {
                  var item = items[i];
                  var key = new Date(parseInt(item.create_time)).toLocaleDateString();
                  if(gmap[key]) {
                    gmap[key].push(item);
                  }else{
                    gmap[key] = [item];
                  }
                }
                for(g in gmap) {
                  $scope.groups.push({date:g,items:gmap[g]});
                }
               });
          };



      }]) ;
      angular.bootstrap(document,["AccountApp"]);
});