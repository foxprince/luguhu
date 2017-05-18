<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html/"%>
<%@ taglib prefix="ajax" tagdir="/WEB-INF/tags/ajax/"%>
<!DOCTYPE html>
<html>
<c:import url="../include/head.jsp">
  <c:param name="pageTitle" value="文章列表" />
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
        <h1>文章列表</h1>
        <ol class="breadcrumb">
          <li><a href="/"><i class="fa fa-dashboard"></i> 主页</a></li>
          <li class="active"><a href="/article/list">文章列表</a></li>
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
                     <form:form class="form-inline form-search" modelAttribute="search" id="searchForm" action="/article/list"  method="post">
                      <div class="form-group">  
                      <form:input class="form-control" path="title" placeholder="名称"/>
                      <button type="submit" class="btn btn-outline btn-primary btn-lg">搜</button>
                      </div>
                    </form:form>
                  </div>
                </div>
              </div>
              <!-- /.box-header -->
              <div class="box-body">
                <table id="example11" class="table table-bordered table-striped">
                  <thead>
                    <tr>
                      <th>编号</th>
                      <th>标题</th>
                      <th>发布者</th>
                      <th>来源</th>
                      <th>添加时间</th>
                      <th>摘要</th>
                      <th>修改</th>
                      <th>删除</th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach var="item" items="${itemList}">
                      <tr>
                        <td>${item.id}</td>
                        <td>${item.title}</td>
                        <td>${item.author.name}</td>
                        <td>${item.origin}</td>
                        <td>${item.formatCtime}</td>
                        <td>${item.summary}</td>
                        <td><a href="./edit?id=${item.id}" class="btn btn-info">修改</a></td>
                        <td><a href="./delete?id=${item.id}" class="btn btn-danger" onclick='return confirm("您确认删除吗？");'>删除</a><br /> </td>
                      </tr>
                    </c:forEach>
                  </tbody>
                  <tfoot>
                    <tr>
                      <th colspan="9"></th>
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
      <section class="content-header">
        <a class="btn btn-lg btn-info" href="./add">添加文章</a>
      </section>
      <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <%@ include file="../include/script.jspf"%>
    <%@ include file="../include/footer.jspf"%>
    <%@ include file="../include/sidebar.jspf"%>
  </div>
  <!-- ./wrapper -->
</body>
</html>
