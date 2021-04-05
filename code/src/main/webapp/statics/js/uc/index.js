 angular.element(document).ready(function() {
      angular.module("UserApp",["ngRoute"])
      .config(function($routeProvider, $locationProvider) {
            $routeProvider
            .when('/setting',{
               templateUrl:'/statics/js/uc/setting.html?_t=123',
               controller:'SettingCtrl'

            })
            .when('/',{
                 templateUrl:'/statics/js/uc/uc.html?_t=123',
                controller:'UcCtrl'
            })
            .when('/collect',{
                 templateUrl:'/statics/js/uc/collect.html?_t=123',
                 controller:'CollectCtrl'
            })
            .when('/message',{
                 templateUrl:'/statics/js/uc/message.html?_t=123',
                 controller:'MessageCtrl'
            })
            .otherwise({
               redirectTo: '/setting'
            })
       })
      .controller("UserCtrl",["$scope","$http","$window",function($scope, $http,$window){
          //layui-this
          $scope.$on('$routeChangeSuccess',function (event,current,previous) {
              if(current["$$route"] && current["$$route"]["originalPath"]) {
                 $scope.currentPath = current["$$route"]["originalPath"];
              }else if(current["redirectTo"]){
                 $scope.currentPath = current["redirectTo"];
              }
          });
          var href =  $window.location.href;
          var indx = href.indexOf("#");
          if(indx > -1) {
             $scope.currentPath = href.substring(indx+1);
          }
      }])
      .controller("SettingCtrl",["$scope","$http","$window",function($scope, $http,$window){
                $scope.user = {   };

                layui.use(["fly","upload","form","jquery","layer"],function(fly,upload,form,$,layer){
                   // 获取用户详细信息
                   var user_id = layui.cache.user.uid;
                   $http.get("/v0.1/user/"+user_id)
                   .then(function(resp){
                       if(resp.data.code==='200') {
                          var u = resp.data.data;
                          $scope.user = u;
                       }else{
                          layer.msg(resp.data.message);
                       }
                   }) ;
                    //基本信息
                   form.on('submit(user)', function(data){
                       data.elem.disabled = true;
                       $http.post('/v0.1/user/'+user_id+'/info',$scope.user ).then(function(resp){
                          $scope.user = resp.data.data;
                          data.elem.disabled = false;
                       },function(error){
                         data.elem.disabled = false;
                       });
                   });

                     //密码
                  form.on('submit(password)', function(data){
                     if(data.field.password != data.field.repassword) {
                          layer.msg("两次密码不一致，请重新确认",{shift:6});
                          return;
                     }else{
                        $http.post('/v0.1/user/'+user_id+'/pw' ,{},{params:{password: md5(data.field.password)}}).then(function(resp){
                          $scope.user = resp.data.data;
                        });
                     }
                  });
                    //头像
                    var avatarAdd = $(".avatar-add");
                    upload.render({
                       elem: '.upload-img'
                       ,url: '/v0.1/upload'
                       ,size: 50
                       ,before: function(){
                          avatarAdd.find('.loading').show();
                       }
                       ,done: function(res){
                         if(res.state == 'SUCCESS'){
                           $http.post('/v0.1/user/'+user_id+'/head' ,{},{params:{head_image:res.url}} ).then(function(resp){
                             $scope.user = resp.data.data;
                           });
                         } else {
                           layer.msg(res.msg, {icon: 5});
                         }
                         avatarAdd.find('.loading').hide();
                       }
                       ,error: function(){
                         avatarAdd.find('.loading').hide();
                       }
                     });
                });
     }])
    .controller("UcCtrl",["$scope","$http","$window",function($scope, $http,$window){
             $scope.user = {
                 user_name:'huangyx',
                 sex:'1',
                 city:'福州',
                 summary:'nothing',
                 email:'331068369@qq.com'
               };
                console.log("UcCtrl");
    }])
    .controller("CollectCtrl",["$scope","$http","$window",function($scope, $http,$window){
              $scope.user = {
                  user_name:'huangyx',
                  sex:'1',
                  city:'福州',
                  summary:'nothing',
                  email:'331068369@qq.com'
                };
                 console.log("CollectCtrl");
    }])
    .controller("MessageCtrl",["$scope","$http","$window",function($scope, $http,$window){
                   $scope.user = {
                       user_name:'huangyx',
                       sex:'1',
                       city:'福州',
                       summary:'nothing',
                       email:'331068369@qq.com'
                     };
                   console.log("MessageCtrl");
    }]) ;
      angular.bootstrap(document,["UserApp"]);
    });