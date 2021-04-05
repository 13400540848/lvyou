<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String basePath = request.getContextPath();
if(basePath.endsWith("/")){
	basePath = basePath.substring(0, basePath.length()-1);
}
String path = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+basePath;
%>
<!DOCTYPE HTML>
<html>
 <head>
 <script src="/statics/js/md5.min.js" type="text/javascript"></script>
 </head>
 <body>
  <div class="layui-container fly-marginTop">
   <div class="fly-panel fly-panel-user" pad20="">
    <div class="layui-tab layui-tab-brief" lay-filter="user">
     <ul class="layui-tab-title">
      <li><a href="/login">登入</a></li>
      <li class="layui-this">注册</li>
     </ul>
     <div class="layui-form layui-tab-content" id="LAY_ucm" style="padding: 20px 0;">
      <div class="layui-tab-item layui-show">
       <div class="layui-form layui-form-pane">
        <form method="post">
         <div class="layui-form-item">
          <label for="L_email" class="layui-form-label">邮箱</label>
          <div class="layui-input-inline">
           <input type="text" id="L_email" name="email" required="" lay-verify="email" autocomplete="off" class="layui-input" />
          </div>
          <div class="layui-form-mid layui-word-aux">
           将会成为您唯一的登入名
          </div>
         </div>
         <div class="layui-form-item">
          <label for="L_username" class="layui-form-label">昵称</label>
          <div class="layui-input-inline">
           <input type="text" id="L_username" name="username" required="" lay-verify="required" autocomplete="off" class="layui-input" />
          </div>
         </div>
         <div class="layui-form-item">
          <label for="L_pass" class="layui-form-label">密码</label>
          <div class="layui-input-inline">
           <input type="password" id="L_pass" name="pass" required="" lay-verify="required" autocomplete="off" class="layui-input" />
          </div>
          <div class="layui-form-mid layui-word-aux">
           6到16个字符
          </div>
         </div>
         <div class="layui-form-item">
          <label for="L_repass" class="layui-form-label">确认密码</label>
          <div class="layui-input-inline">
           <input type="password" id="L_repass" name="repass" required="" lay-verify="required" autocomplete="off" class="layui-input" />
          </div>
         </div>

         <div class="layui-form-item">
          <button class="layui-btn" lay-filter="go" lay-submit="">立即注册</button>
         </div>
         <div class="layui-form-item fly-form-app" style="display:none">
          <span>或者直接使用社交账号快捷注册</span>
          <a href="/app/qq" onclick="layer.msg('正在通过QQ登入', {icon:16, shade: 0.1, time:0})" class="iconfont icon-qq" title="QQ登入"></a>
          <a href="/app/weibo/" onclick="layer.msg('正在通过微博登入', {icon:16, shade: 0.1, time:0})" class="iconfont icon-weibo" title="微博登入"></a>
         </div>
        </form>
        <customscript>
          <script>
            layui.use(['form','fly'], function(){
              var form = layui.form;
              var fly = layui.fly;
                form.on('submit(go)', function(data){
                  // 去注册
                  var postData = data.field || {};
                  postData['pass'] = md5(postData.pass);
                  fly.json('/v0.1/user/register', postData ,function(res){
                    var redirect_url = fly.getQueryString("redirect_url");
                     if(null==redirect_url) {
                       redirect_url = '/';
                     }
                     window.location.href = redirect_url;
                  });
                  return false;
                });
            });
          </script>
         </customscript>
       </div>
      </div>
     </div>
    </div>
   </div>
  </div>
 </body>
</html>