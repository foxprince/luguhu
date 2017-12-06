<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html/"%>
<%@ taglib prefix="ajax" tagdir="/WEB-INF/tags/ajax/"%>
<!DOCTYPE html>
<html>
<c:import url="../include/head.jsp">
	<c:param name="pageTitle" value="素材列表" />
</c:import>
<link href="../resources/plugins/lightbox2/css/lightbox.min.css" rel="stylesheet"/>
<body class="hold-transition skin-green-light sidebar-mini">
	<div class="wrapper">
		<!-- topbar -->
		<%@ include file="../include/topbar.jspf"%>
		<!-- left menu-->
		<%@ include file="../include/menu.jspf"%>
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>素材列表</h1>
				<ol class="breadcrumb">
					<li><a href="/"><i class="fa fa-dashboard"></i> 主页</a></li>
					<li class="active"><a href="/asset/list">素材列表</a></li>
				</ol>
			</section>
			<!-- Main content -->
			<section class="content">
				<div class="row">
					<div class="col-xs-12">
						<div class="box">
							<div class="box-header">
								<div class="box-content col-sm-12">
									<div class="row modal-body">
										<form:form id="uploadForm" class="form-horizontal well">
											<div class="form-group">
												<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 添加图片 *: </label>
												<div class="col-sm-9 ">
													<input type="file" id="file" name="file" onchange="imgUpload(this)" />
												</div>
											</div>
										</form:form>
									</div>
								</div>
							</div>
							<!-- /.box-header -->
							<div id="assetList" class="box-body">
								<c:forEach var="item" items="${itemList}">
									<div class="col-sm-6 col-md-3">
										<div class="thumbnail">
										<div id="previewImg">
											<a href="/asset/preview?fileName=${item.location}" data-lightbox="image-1" data-title="${item.title}"><img  style="max-width:200px;max-height:200px;" src="/asset/preview?size=small&fileName=${item.location} " /></a>
										</div>
										<div class="caption">
											<h6 id="imgTitle-${item.id}">${item.title}</h6>
											<p>
												<a href="#" assetId="${item.id}"  class="btn btn-primary" role="button" onclick="editTitle(this)">修改</a> <a href="#" class="btn btn-danger" role="button">删除</a>
											</p>
										</div></div>
									</div>
								</c:forEach>
							</div>
							<div class="box-footer"><html:page url="./listPage?1=1" /></div>
							<!-- /.box-body -->
						</div>
						<!-- /.box -->
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</section>
			<!-- <section class="content-header">
				<a class="btn btn-lg btn-info" href="./add">添加素材</a>
			</section> -->
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
		<%@ include file="../include/script.jspf"%>
		<%@ include file="../include/footer.jspf"%>
		<%@ include file="../include/sidebar.jspf"%>
		<script src="/resources/plugins/lightbox2/js/lightbox.min.js" ></script>
		
		<script>
			$(document).ready(function() {
				
			});
			function editTitle(sender) {
				var titleDom = $(sender).parent().parent().find('h6');
				titleDom.html('<input type="text" value="'+titleDom.html()+'" />');
				$(titleDom).find('input').focus();
				$(sender).text('提交');
				$(sender).attr('onclick','chgTitle(this)');
			}
			function chgTitle(sender) {
				$.post("/asset/edit", {
					"${_csrf.parameterName}":"${_csrf.token}",
					id: $(sender).attr("assetId"),
				    title: $(sender).parent().parent().find('h6 input').val()
				}, 
				function(data) {
					var titleDom = $(sender).parent().parent().find('h6');
					titleDom.html($(titleDom).find('input').val());
					$(sender).text('修改');
					$(sender).attr('onclick','editTitle(this)');
				});
			}
			function imgUpload(sender) {
				var uploadLayer = layer.msg('文件上传中...', {
					icon : 16,
					shade : 0.01,
					time : 999999
				});
				var fd = new FormData(document.getElementById("uploadForm"));
				$.ajax({
					type : 'post',
					url : "/asset/upload",
					data : fd,
					processData : false, // tell jQuery not to process the data
					contentType : false, // tell jQuery not to set contentType
					beforeSerialize : function() {
					},
					complete : function() {
						layer.close(uploadLayer);
					},
					success : function(json) {
						if (json.code == 0) {
							alert('文件上传成功。');
							var t = '<div class="col-sm-6 col-md-3"><div class="thumbnail">';
							t += '<div id="previewImg">';
							t += '	<img style="max-width:200px;max-height:200px;" src="/asset/preview?fileName=' + json.data.location + ' " />';
							t += '</div>';
							t += '<div class="caption">';
							t += '	<h6>' + json.data.title + '</h6>';
							t += '	<p>';
							t += '		<a href="#" class="btn btn-primary" role="button" onclick="editTitle(this)">修改</a> <a href="#" class="btn btn-danger" role="button">删除</a>';
							t += '	</p>';
							t += '</div>';
							t += '</div></div>';
							$("#assetList").prepend(t);
						} else
							alert('文件上传失败。');
					},
					error : function(XmlHttpRequest, textStatus, errorThrown) {
						console.log(XmlHttpRequest);
						console.log(textStatus);
						console.log(errorThrown);
					}
				});
			}
		</script>
	</div>
	<!-- ./wrapper -->
</body>
</html>
