<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<c:import url="../include/head.jsp">
	<c:param name="pageTitle" value="登录"/>
</c:import>

  <body class="hold-transition login-page">
    <div class="login-box">
      <div class="login-logo">
        <b><spring:message code="website.name"/></b><spring:message code="website.title"/>
      </div><!-- /.login-logo -->
      <div class="login-box-body">
        <p class="login-box-msg">请登录</p>
        <form action="/user/login" method="post">
          <div class="form-group has-feedback">
            <input type="text" name="username"class="form-control" placeholder="手机号|邮箱|用户名">
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
          </div>
          <div class="form-group has-feedback">
            <input type="password" class="form-control" placeholder="密码">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
          </div>
          <div class="row">
            <div class="col-xs-8">
              <div >
                <label>
                  <input type="checkbox"> 记住我
                </label>
              </div>
            </div><!-- /.col -->
            <div class="col-xs-4">
              <button type="submit" class="btn btn-primary btn-block btn-flat">登录</button>
            </div><!-- /.col -->
          </div>
        </form>

        

        <a href="#">忘记密码</a><br>
        <a href="/user/add" class="text-center">注册</a>

      </div><!-- /.login-box-body -->
    </div><!-- /.login-box -->

    <!-- jQuery 2.1.4 -->
    <script src="../plugins/jQuery/jQuery-2.1.4.min.js"></script>
    <!-- Bootstrap 3.3.5 -->
    <script src="../bootstrap/js/bootstrap.min.js"></script>
  </body>
</html>
