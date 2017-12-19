<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html/"%>
<%@ taglib prefix="ajax" tagdir="/WEB-INF/tags/ajax/"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<c:import url="../include/head.jsp">
  <c:param name="pageTitle" value="${product.actionDesc}产品" />
</c:import>

<body class="hold-transition skin-green-light sidebar-mini">
  <div class="wrapper">
    <!-- topbar -->
    <%@ include file="../include/topbar.jspf"%>
    <!-- left menu-->
    <%@ include file="../include/menu.jspf"%>
    <div class="content-wrapper">
      <!-- Content Header (Page header) -->
      <section class="content-header">
        <h1>产品-${product.actionDesc}单品</h1>
        <ol class="breadcrumb">
          <li><a href="/"><i class="fa fa-dashboard"></i> 主页</a></li>
          <li class="active"><a href="/product/list">单品列表</a></li>
        </ol>
      </section>
      <!-- Main content -->
      <section class="content">
        <div class="box box-info">
          <div class="box-body">
            <form:form class="form-horizontal well" modelAttribute="product"  id="addOrEditForm" action="./${product.action}">
              <html:inputField name="title" label="单品名称" />
              <html:inputField name="place" label="产地" />
              <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 生产者 </label>
                <div class="col-sm-9 ">
                  <form:select path="producer" class="form-control ">
                    <form:options class="span4 col-xs-10 col-sm-5" items="${producerList}" itemLabel="name" itemValue="id"/>
                  </form:select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="form-field-tags">详细说明</label>
                <div class="col-sm-9">
                  <form:textarea path="content" cols="50" rows="5"/>
                </div>
              </div>
              <div class="clearfix form-actions">
                <div class="col-md-offset-3 col-md-9">
                  <input type="hidden" name="id" value="${product.id }"/>
                  <button class="btn btn-lg btn-info" type="submit"> <i class="glyphicon glyphicon-ok bigger-110"></i> 提交 </button>
                  &nbsp; &nbsp; &nbsp;
                  <button class="btn btn-lg" type="reset"> <i class="glyphicon glyphicon-remove bigger-110"></i> 重置 </button>
                </div>
              </div>
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
    <%@ include file="../include/script.jspf"%>
  	<%@ include file="../include/footer.jspf"%>
    <%@ include file="../include/sidebar.jspf"%>
  </div>
  <!-- ./wrapper -->
  <script src="../plugins/jquery-validation/jquery.validate.min.js"></script>
  <script src="../plugins/jquery-validation/messages_zh.js"></script>
  <script>
			$(document).ready(function() {

			});
			$("#addOrEditForm").validate({
				rules : {
					title : "required",
					place : "required"
				}
			});
		</script>
</body>
</html>
