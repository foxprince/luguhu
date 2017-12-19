<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html/"%>
<%@ taglib prefix="ajax" tagdir="/WEB-INF/tags/ajax/"%>
<!DOCTYPE html>
<html>
<c:import url="../include/head.jsp">
  <c:param name="pageTitle" value="微信内容列表" />
</c:import>
<link href="../plugins/lightbox2/css/lightbox.min.css" rel="stylesheet"/>

<body class="hold-transition skin-green-light sidebar-mini">
  <div class="wrapper">
    <!-- topbar -->
    <%@ include file="../include/topbar.jspf"%>
    <!-- left menu-->
    <%@ include file="../include/menu.jspf"%>
    <div class="content-wrapper">
      <!-- Content Header (Page header) -->
      <section class="content-header">
        <h1>微信内容收集</h1>
        <ol class="breadcrumb">
          <li><a href="/"><i class="fa fa-dashboard"></i> 主页</a></li>
          <li class="active"><a href="/asset/list?createFrom=WECHAT">微信内容列表</a></li>
        </ol>
      </section>
      <!-- Main content -->
      <section class="content">
        <div class="row">
          <div class="col-xs-12">
            <div class="box">
              <div class="box-header">
                <div class="box-content col-sm-12 bg-gray color-palette">
                  <div class="row modal-body">
                     <ul class="list-inline">
                     <c:forEach var="item" items="${wechatUsers}">
                     	<li class="user-header text-center <c:if test="${item.id==wxUser.id }">table-bordered</c:if>"><a href="list?createFrom=WECHAT&wxUser=${item.id}"><img src="${item.headImgUrl }" style="width:45px;height: 45px;" class="img-circle" alt="${item.nickname}"></a><p>${item.nickname}</p></li>
                     </c:forEach>
                     </ul>
                     <ul class="list-inline">
                     <c:forEach var="item" items="${tags}">
                     	<li class="user-header text-center <c:if test="${item.id==tag.id }">table-bordered</c:if>"><a class="btn btn-default"href="list?createFrom=WECHAT&tags=${item.id}">${item.label}</a></li>
                     </c:forEach>
                     </ul>
                     <form:form class="form-inline form-search" modelAttribute="search" id="searchForm" action="/asset/list?createFrom=WECHAT"  method="post">
                      <div class="form-group">  
                      <form:input class="form-control" path="sourceName" placeholder="关键词"/>
                      <button type="submit" class="btn btn-outline btn-primary btn-lg">搜</button>
                      <a href="/asset/list?createFrom=WECHAT" class="btn btn-outline btn-primary btn-lg">重置</a>
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
                      <th>发布者</th>
                      <th>添加时间</th>
                      <th>内容</th>
                      <th>标签</th>
                      <th>操作</th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach var="item" items="${itemList}">
                      <tr>
                        <td>${item.id}</td>
                        <td>${item.wxUser.nickname}</td>
                        <td>${item.formatCtime}</td>
                        <td width="50%">
                        <c:choose>
                        	<c:when test="${item.type =='image'}">
                        	<a href="/asset/preview?fileName=${item.location}" data-lightbox="image-1" data-title="${item.title}"><img  style="max-width:200px;max-height:200px;" src="/asset/preview?size=small&fileName=${item.location} " /></a>
                        	</c:when>
                        	<c:otherwise>
                        		${item.sourceName}
   							</c:otherwise>
                        </c:choose>
                        </td>
                        <td>${item.tagLables}</td>
                        <td><a href="./delete?createFrom=WECHAT&id=${item.id}" class="btn btn-danger" onclick='return confirm("您确认忽略吗？");'>忽略</a><br /> </td>
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
      <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <%@ include file="../include/script.jspf"%>
    <%@ include file="../include/footer.jspf"%>
    <%@ include file="../include/sidebar.jspf"%>
    <script src="../plugins/lightbox2/js/lightbox.min.js" ></script>
    
  </div>
  <!-- ./wrapper -->
</body>
</html>
