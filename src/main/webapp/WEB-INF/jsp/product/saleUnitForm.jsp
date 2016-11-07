<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html/"%>
<%@ taglib prefix="ajax" tagdir="/WEB-INF/tags/ajax/"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<c:import url="../include/head.jsp">
  <c:param name="pageTitle" value="${item.actionDesc.concat(item.selfIntro)}" />
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
        <h1>${item.actionDesc.concat(item.selfIntro)}</h1>
        <ol class="breadcrumb">
          <li><a href="/"><i class="fa fa-dashboard"></i> 主页</a></li>
          <li class="active"><a href="/saleUnit/list?relateId=${product.id }">${product.title}产品规格列表</a></li>
        </ol>
      </section>
      <!-- Main content -->
      <section class="content">
        <div class="box box-info">
          <div class="box-body">
            <form:form class="form-horizontal well" modelAttribute="item"  id="addOrEditForm" action="./${item.action}ByRelate">
              <html:inputField name="title" label="产品规格" /> *
              
              <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 计量单位 </label>
                <div class="col-sm-9 ">
                  <form:select path="unit" class="form-control ">
                    <form:options class="span4 col-xs-10 col-sm-5" items="${unitList}" />
                  </form:select>
                </div>
              </div>
              <html:inputField name="price" label="单价" />
              <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 是否可以单独销售 </label>
                <div class="col-sm-9 ">
                  <form:select path="saleable" class="form-control ">
                    <form:option class="span4 col-xs-10 col-sm-5" value="true">可以</form:option>
                    <form:option class="span4 col-xs-10 col-sm-5" value="false">不可以</form:option>
                  </form:select>
                </div>
              </div>
              <html:inputField name="minBatch" label="最低起售数" />
              <html:inputField name="amount" label="库存总数" />
              <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="form-field-tags">详细说明</label>
                <div class="col-sm-9">
                  <form:textarea path="description" cols="50" rows="5"/>
                </div>
              </div>
              <div class="clearfix form-actions">
                <div class="col-md-offset-3 col-md-9">
                  <form:hidden path="product" value="${product.id}"/>
                  <form:hidden path="id" />
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
				rules : {
					title : "required",
					price: {
				    	required: false,
				        number: true
				    },
				    amount: {
				    	required: false,
				    	digits:true	
				    },
				    minBatch: {
				    	required: false,
				    	digits:true	
				    }
				}
			});
		</script>
</body>
</html>
