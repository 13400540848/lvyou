 angular.element(document).ready(function() {
  angular.module("App",['angularFileUpload']).controller("MainCtrl",["$scope","$http",'$window', '$interval','FileUploader',function($scope,$http,$window,$interval,FileUploader){
     $scope.types = [{name:'随笔',value:"1"},
                     {name:'诗词',value:"2"},
                     {name:'散文',value:"3"},
                     {name:'小说',value:"4"}];
     $scope.article = {
        type: $scope.types[0].value,
        tags: '',
        summary:'',
        title:'',
        image:''
     }
     var _Edit = UM.getEditor('myEditor');

     if($window.User.user_id!=='') {
        $interval(function(){
          $http.get("/v0.1/user/valid_login");
        },5*60*1000) ;
     }

     var uploader = $scope.uploader = new FileUploader({
                 url: $FileServer+'/v0.1/upload?folder=skeletonize'
     });
     uploader.success_list = [];
     uploader.onAfterAddingAll = function(addedFileItems) {
         //上传
         uploader.uploadAll();
     };
     uploader.onCompleteItem = function(fileItem, response, status, headers) {
         //设置
         var json = response;
         var result = angular.fromJson(json);
         var item =  {};
         $scope.article.image = item.remote_url = $FileServer+ result.url;
         uploader.success_list=[item];
     };
     uploader.onCompleteAll = function() {
         uploader.clearQueue();
     };

     $scope.toggleUploader = function() {
        var st = setTimeout(function(){
            angular.element(".uploader").trigger("click");
            clearTimeout(st);
         },10)
     }

     $scope.publish = function(){
          if(! _Edit.hasContents()) {
              $scope.$error = "无内容需要保存~";
              return;
          }

           if(! $scope.article.title) {
                $scope.$error = "文章标题不能为空~";
                return;
           }

          var content = _Edit.getContent();
          $scope.article.content  = content;
          $http.post('/v0.1/article',$scope.article).then(function(resp){
              if(resp.data.code=='200') {
                 $scope.article = resp.data.data;
                 window.location.href = '/article?id='+$scope.article.id;
              }else{
                 $scope.$error =resp.data.message;
              }
           },function(error){
              $scope.$error = "保存失败~";
           });

     };

     function getQuery(name) {
     　　var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     　　var r = $window.location.search.substr(1).match(reg);
     　　if (r!=null) return unescape(r[2]); return null;
 　　}

     var articleId = getQuery("id");
     if(articleId) {
      //加载文章
      $http.get("/v0.1/article/"+articleId).then(function(resp){
         console.log(resp);
         var article = resp.data.data;
         $scope.article = article;
         $scope.uploader.success_list.push({remote_url:$scope.article.image});
         _Edit.setContent($scope.article.content);
      },function(error){
         console.log(error);
      });
     }

  }]);
  angular.bootstrap(document,["App"]);
});