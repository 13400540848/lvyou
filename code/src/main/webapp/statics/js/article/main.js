 angular.element(document).ready(function() {
      angular.module("App",[]).controller("MainCtrl",["$scope","$http",function($scope,$http){

       layui.use("form",function(){
           var form = layui.form;
          form.on('submit(replay)', function(data){
             $scope.replay(data.field);
          });
        })

        $scope.login = function(){
                $("#login-name").val("");
                $("#login-pass").val("");
                $('#loginModal').modal('show');
        }

           $scope.article = {};
           $scope.recommend_articles = [];
           $scope.author_articles = [];
           $scope.user = window.User || layui.cache.user ||  {};
             //加载推荐文章，根据登录者
           $http.get("/v0.1/article/recommend",{params:{user_id:$scope.user.uid}}).then(function(resp){
             $scope.recommend_articles = resp.data.data || [];
           });
           $scope.comment = {cnt:1,comments:[{head:'',createTime:'',userName:'hyx',content:'dfgsdfgsdfgs'}]};
          //加载本文作者以及作者的相关文章列表
          $scope.author = {};
           $scope.$watch("article.user_id",function(n,o){
                if(n) {
                  $http.get("/v0.1/user/"+n).then(function(resp){
                     $scope.author = resp.data.data;
                  });
                  //加载作者文章
                  $http.get("/v0.1/article/author/"+n).then(function(resp){
                     $scope.author_articles = resp.data.data || [];
                  });
                }
            });
          $scope.reflashComment = function() {
              $http.get("/v0.1/article/"+$scope.article.id+"/comment").then(function(resp){
                 $scope.comment.cnt = resp.data.data.length;
                 $scope.comment.comments = resp.data.data;
              });
          }

          //文章ID出来了之后，触发请求相关的数据
          $scope.$watch("article.id",function(n,o){
              if(n) {
                $scope.reflashComment();
              }
          });
          //加载推荐文章
          $scope.replay = function(data) {
              if(!data.content) {
                return;
              }
              $http.post("/v0.1/article/"+$scope.article.id+"/comment",
                  {
                    content:data.content,
                    goods_name:$scope.article.title
                  }
              ).success(function(resp){
                  if(resp.code==='200') {
                     $scope.comment_content = "";
                      //刷新下列表
                     $scope.reflashComment();
                  }else{
                     var layer = layui.layer;
                     layer.msg(resp.message);
                  }
              })
          }
          //点赞
          $scope.thumbUp = function() {
           $http.post("/v0.1/article/"+$scope.article.id+"/thumbup").then(function(resp){
                 if(resp.data.code == '200') {
                   $scope.article.greats = $scope.article.greats + 1;
                 }else if(resp.data.code == '1002') {
//                   layui.layer.msg(resp.data.message);
                 }
           })
          }
      }]).filter('to_trusted', ['$sce', function ($sce) {
         return function (text) {
            return $sce.trustAsHtml(text);
         };
     }]);
      angular.bootstrap(document,["App"]);
    });