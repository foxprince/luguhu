<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html/"%>
<%@ taglib prefix="ajax" tagdir="/WEB-INF/tags/ajax/"%>
<!DOCTYPE html>
<html>
<c:import url="../include/head.jsp">
  <c:param name="pageTitle" value="产品规格" />
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
        <h1>${product.title}产品规格列表</h1>
        <ol class="breadcrumb">
          <li><a href="/"><i class="fa fa-dashboard"></i> 主页</a></li>
          <li class="active"><a href="/saleUnit/listAll?relateId=${product.id }">${product.title}产品规格列表</a></li>
        </ol>
      </section>
      <!-- Main content -->
      <section class="content">
        <div class="row">
          <div class="col-xs-12">
            <div class="box">
              <%-- <div class="box-header">
                <div class="box-content col-sm-12 bg-green">
                  <div class="row modal-body">
                     <form:form class="form-inline form-search" modelAttribute="search" id="searchForm" action="/saleUnit/list"  method="post">
                      <div class="form-group">  
                      <form:input class="form-control" path="title" placeholder="名称"/>
                      <button type="submit" class="btn btn-outline btn-primary btn-lg">搜</button>
                      </div>
                    </form:form>
                  </div>
                </div>
              </div> --%>
              <!-- /.box-header -->
              <div class="box-body">
                <table id="example11" class="table table-bordered table-striped">
                  <thead>
                    <tr>
                      <th>编号</th>
                      <th>规格</th>
                      <th>所属单品</th>
                      <th>单位</th>
                      <th>单价</th>
                      <th>可单独销售</th>
                      <th>起售数</th>
                      <th>库存总数</th>
                      <th>添加时间</th>
                      <th>说明</th>
                      <th>修改</th>
                      <th>删除</th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach var="item" items="${itemList}">
                      <tr>
                        <td>${item.id}</td>
                        <td>${item.title}</td>
                        <td>${item.product.title}</td>
                        <td>${item.unit}</td>
                        <td>${item.price}</td>
                        <td>${item.saleableDesc}</td>
                        <td>${item.minBatch}</td>
                        <td>${item.total}</td>
                        <td>${item.formatCtime}</td>
                        <td>${item.content}</td>
                        <td><a href="./edit?id=${item.id}" class="btn btn-info">修改</a></td>
                        <td><a href="./delete?id=${item.id}" class="btn btn-danger" onclick='return confirm("您确认删除吗？");'>删除</a><br /> </td>
                      </tr>
                    </c:forEach>
                  </tbody>
                  <tfoot>
                    <tr>
                      <th colspan="12"></th>
                    </tr>
                  </tfoot>
                </table>
              </div>
              <!-- /.box-body -->
            </div>
            <!-- /.box -->
          </div>
          <!-- /.col -->
        </div>
        <!-- /.row -->
      </section>
      <section class="content-header">
        <a class="btn btn-lg btn-info" href="./add?relateId=${product.id}">添加产品规格</a>
      </section>
      <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <%@ include file="../include/script.jspf"%>
    <%@ include file="../include/footer.jspf"%>
    <%@ include file="../include/sidebar.jspf"%>
  </div>
  <script>
  $(document).ready(function(){

  });
  </script>
</body>
</html>
