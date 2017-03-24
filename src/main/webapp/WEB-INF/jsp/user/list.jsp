<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html/"%>
<%@ taglib prefix="ajax" tagdir="/WEB-INF/tags/ajax/"%>
<!DOCTYPE html>
<html>
<c:import url="../include/head.jsp">
  <c:param name="pageTitle" value="用户列表" />
</c:import>
<style>
      
    </style>
<body class="hold-transition skin-green-light sidebar-mini">
  <div class="wrapper">
    <!-- topbar -->
    <%@ include file="../include/topbar.jspf"%>
    <!-- left menu-->
    <%@ include file="../include/menu.jspf"%>
    <div class="content-wrapper">
      <!-- Content Header (Page header) -->
      <section class="content-header">
        <h1>用户列表-${levelName}</h1>
        <ol class="breadcrumb">
          <li><a href="/"><i class="fa fa-dashboard"></i> 主页</a></li>
          <li class="active"><a href="./list">用户列表</a></li>
        </ol>
      </section>
      <!-- Main content -->
      <section class="content">
        <div class="row">
          <div class="col-xs-12">
            <div class="box">
              <div class="box-header">
                <div class="box-content col-sm-12 bg-green">
                  <div class="row modal-body">
                     <form:form class="form-inline form-search" modelAttribute="search" id="searchForm" action="/user/list"  method="post">
                      <div class="form-group">  
                      <form:select path="level" class="form-control" onchange="window.location.href='/user/list?level='+(this.options[this.selectedIndex].value)">
                        <form:option value="" label="-- 用户分类 --"/>
                        <form:options items="${userLevelMap}"/>
                      </form:select>&nbsp;
                      <label class="">姓名、手机号码：</label>
                      <form:input class="form-control" path="name" placeholder="姓名、手机号码"/>
                      <button type="submit" class="btn btn-outline btn-primary btn-lg">搜</button>
                      </div>
                    </form:form>
                  </div>
                </div>
              </div>
              <!-- /.box-header -->
              <div class="box-body ">
                <table id="example11" class="table table-bordered table-striped table-hover ">
                  <thead>
                    <tr class="bg-blue">
                      <th>编号</th>
                      <th>姓名</th>
                      <th>类型</th>
                      <th>邮箱</th>
                      <th>性别</th>
                      <th>电话</th>
                      <th>注册时间</th>
                      <th>注册方式</th>
                      <th>状态</th>
                      <th>操作</th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach var="item" items="${itemList}">
                      <tr>
                        <td>${item.id}</td>
                        <td>${item.name}</td>
                        <td>${item.levelDesc}</td>
                        <td>${item.email}</td>
                        <td>${item.sexDesc}</td>
                        <td>${item.phone}</td>
                        <td>${item.ctime}</td>
                        <td>${item.originDesc}</td>
                        <td id="td_verified_${item.id}">${item.verifiedDesc}
                        <span id="span_verified_${item.id}"><c:if test="${item.verified==false}"><a href="#" id="${item.id}" class="btn btn-success verified">审核通过</a></c:if></span>
                        </td>
                        <td class="row-fluid span12">
                        <span class="btn-group">
                          <a href="./edit?id=${item.id}" class="btn btn-info span6">修改</a>
                          <a id="${item.id}" href="./delete?id=${item.id}" class="btn btn-danger span6" onclick='${fn:length(item.products)>0?"alert(\"存在此生产者提供的产品，不能删除\"); return false;":"return confirm(\"您确认删除吗？\");"}'>删除</a>
                        </span>
                        </td>
                      </tr>
                    </c:forEach>
                  </tbody>
                  <tfoot>
                    <tr>
                      <th colspan="12"></th>
                    </tr>
                  </tfoot>
                </table>
                <html:page url="./listPage?1=1" />
              </div>
              <!-- /.box-body -->
            </div>
            <!-- /.box -->
          </div>
          <!-- /.col -->
        </div>
        <!-- /.row -->
      </section>
      <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <%@ include file="../include/script.jspf"%>
    <%@ include file="../include/footer.jspf"%>
    <%@ include file="../include/sidebar.jspf"%>
  </div>
  <!-- ./wrapper -->
  <script>
  	$(document).ready(function(){
		$("a.verified").click(function() {
			var btnId=$(this).attr('id');
			$('span[id=td_verified_'+btnId+']').html('<div class="overlay"> <i class="fa fa-refresh fa-spin"></i> </div>');
			$.post("/user/verified",
				{id: btnId},
				function(data){
					if($.trim(data)=='true'){
						$('td[id=td_verified_'+btnId+']').html('<span class="success">审核成功</span>');
					}else{
						alert('Server is busy, please try later!');
					}
				}
			);
		});
	});
    
  </script>
</body>
</html>
