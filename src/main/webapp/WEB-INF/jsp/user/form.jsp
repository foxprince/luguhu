<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html/"%>
<%@ taglib prefix="ajax" tagdir="/WEB-INF/tags/ajax/"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<c:import url="../include/head.jsp">
  <c:param name="pageTitle" value="${item.actionDesc}用户" />
</c:import>
<link rel="stylesheet" href="../resources/ace/ace-rtl.min.css" />

<body class="hold-transition skin-green-light sidebar-mini">
  <div class="wrapper">
    <!-- topbar -->
    <%@ include file="../include/topbar.jspf"%>
    <!-- left menu-->
    <%@ include file="../include/menu.jspf"%>
    <div class="content-wrapper">
      <!-- Content Header (Page header) -->
      <section class="content-header">
        <h1>${item.actionDesc}用户</h1>
        <ol class="breadcrumb">
          <li><a href="/"><i class="fa fa-dashboard"></i> 主页</a></li>
          <li class="active"><a href="./list">用户列表</a></li>
        </ol>
      </section>
      <!-- Main content -->
      <section class="content">
        <div class="box box-info">
          <div class="box-body">
            <form:form class="form-horizontal well" modelAttribute="item" id="addOrEditForm" action="/user/add" method="POST">
              <fieldset>
              <html:inputField name="email" label="邮箱地址：" />
              <html:inputField name="password" type="password" label="密码：" />
              <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="form-field-1">确认密码：</label>
                <div class="col-sm-9">
                  <input name="repassword" type="password" class="form-control" placeholder="确认密码"  value="<c:out value="${item.action=='edit'?item.password:'' }"/>" /> 
                </div>
              </div>
              <html:inputField name="name" label="姓名" />
              <html:inputField name="phone" label="手机号码" />
              <div class="form-group"> 
                <label class="col-sm-3 control-label no-padding-right" for="form-field-1">用户类型：</label>
                <div class="col-sm-9">
                  <span class="block input-icon input-icon-right">
                    <form:radiobutton path="level" value="0" /> 散户 
                    <form:radiobutton path="level" value="1" /> 份额用户 
                    <form:radiobutton path="level" value="2" /> 股东用户 
                    <form:radiobutton path="level" value="3" /> 生产者 
                    <span class="help-inline"></span>
                  </span>
                  </div>
              </div> 
              <div class="form-group"> 
                <label class="col-sm-3 control-label no-padding-right" for="form-field-1">审核状态：</label>
                <div class="col-sm-9">
                  <span class="block input-icon input-icon-right">
                    <form:radiobutton path="verified" value="true" /> 审核通过 
                    <form:radiobutton path="verified" value="false" /> 待审核 
                    <span class="help-inline"></span>
                  </span>
                </div>
              </div>
                <div class="space-24"></div>
                <div class="form-group clearfix">
                  <button type="submit" class="width-65 btn btn-sm btn-success">${item.actionDesc}</button>
                  <button type="reset" class="width-30 btn btn-sm"> 重置 </button>
                  <form:hidden path="id"/>
                </div>
              </fieldset>
            </form:form>
          </div>
          <!-- /.box-body -->
          <div class="box-footer">
            <button type="button" class="btn btn-default">Someting else...</button>
          </div>
          <!-- /.box-footer -->
        </div>
        <!-- /.box -->
      </section>
      <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <%@ include file="../include/footer.jspf"%>
    <%@ include file="../include/sidebar.jspf"%>
  </div>
  <!-- ./wrapper -->
  <%@ include file="../include/script.jspf"%>
  <script src="../resources/plugins/jquery-validation/jquery.validate.min.js"></script>
  <script src="../resources/plugins/jquery-validation/messages_zh.js"></script>
  <script>
			$(document).ready(function() {

			});
			$("#addOrEditForm").validate({
				rules: {
		    		password: {
		    			required: true,
		    			minlength: 6
		    		},
		    		repassword: {
		    			required: true,
		    			minlength: 6,
		    			equalTo: "#password"
		    		},
		    		email: {
		    			required: true,
		    			${item.action=='add'?'remote: "emailCanRegisted",':'' }
		    			email: true
		    		}
		    	},
		    	messages: {
		    		repassword: {
		    			required: "再输一遍密码",
		    			equalTo: "两次输入的密码必须一致"
		    		},
		    		email: {
		    			remote: jQuery.validator.format("邮箱地址已注册。")
		    		}
		    	}
			});
		</script>
</body>
</html>
