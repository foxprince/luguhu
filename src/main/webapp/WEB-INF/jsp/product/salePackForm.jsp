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
<link href="../resources/bootstrap-wysiwyg/bootstrap-combined.no-icons.min.css" rel="stylesheet">
<link href="../resources/bootstrap-wysiwyg/bootstrap-responsive.min.css" rel="stylesheet">
<!-- <link href="../resources/plugins/font-awesome.3.0.2.css" rel="stylesheet"> -->
<link href="http://netdna.bootstrapcdn.com/font-awesome/3.0.2/css/font-awesome.css" rel="stylesheet">
<link rel="stylesheet" href="../resources/bootstrap-wysiwyg/index.css" type="text/css">
   
<body class="wysihtml5-supported hold-transition skin-green-light sidebar-mini">
	<%@ include file="../include/assetModal.html"%>
	<!-- /.modal -->
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
					<li class="active"><a href="/productSalePack/list">产品销售包列表</a></li>
				</ol>
			</section>
			<!-- Main content -->
			<section class="content">
				<div class="box box-info">
					<div class="box-body">
						<form:form id="uploadForm" class="form-horizontal well">
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 产品图片 *: </label>
								<div class="col-sm-9 ">
									<input type="file" id="file" name="file" onchange="imgUpload(this)" style="width: 380px; padding: 0px;" />
									<div id="previewImg">
										<img class="preview" src="/asset/preview?fileName=${item.asset.location} " />
									</div>
								</div>
								<div class="col-sm-4">
									<a class="btn btn-success" id="assetSelect" data-toggle="modal" data-target="#assetModal">从素材库选择 </a>
								</div>
							</div>
						</form:form>
						<form:form class="form-horizontal well" modelAttribute="item" id="addOrEditForm" action="./${item.action}">
							<html:inputField name="title" label="销售包名称 *:" />
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 产品规格： </label>
								<div class="col-sm-9 panel panel-success">
									<c:forEach var="product" items="${productList}">
										<div class="panel-group" id="accordion">
											<div class="panel panel-default">
												<div class="panel-heading">
													<h4 class="panel-title">
														<a data-toggle="collapse" href="#collapse${product.id }"> ${product.title} </a>
													</h4>
												</div>
												<div id="collapse${product.id }" class="panel-collapse collapse in">
													<div class="panel-body">
														<form:checkboxes path="saleUnits" items="${product.saleUnits}" itemLabel="title" />
													</div>
												</div>
											</div>
										</div>
									</c:forEach>
								</div>
							</div>
							<html:inputField name="minBatch" label="起售斤数：" />
							<html:inputField name="price" label="价格 *：" />
							<html:inputField name="amount" label="库存总数：" />
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-tags">销售起止时间：</label>
								<div class="col-sm-9">
									<form:input class="input-lg col-xs-10 col-sm-5" path="saleBegin" label="销售开始时间" id="datetimepicker"
										data-date-format="yyyy-mm-dd hh:ii" />
									-
									<form:input class="input-lg col-xs-10 col-sm-5" path="saleEnd" label="销售结束时间" data-date-format="yyyy-mm-dd hh:ii" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-tags">内容摘要：</label>
								<div class="col-sm-9">
									<form:textarea path="intro" id="intro"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-tags">详细说明：</label>
								<div class="col-sm-9">
									<%-- <form:textarea path="description" class="wysi" cols="100" rows="5" /> --%>
									<form:hidden path="description" id="description"/>
								</div>
							</div>
							<%@include file="../include/wysiwyg.jspf" %>
							<div class="clearfix form-actions">
								<div class="col-md-offset-3 col-md-9">
									<form:hidden path="id" />
									<button class="btn btn-lg btn-info" type="submit">
										<i class="glyphicon glyphicon-ok bigger-110"></i> 提交
									</button>
									&nbsp; &nbsp; &nbsp;
									<button class="btn btn-lg" type="reset">
										<i class="glyphicon glyphicon-remove bigger-110"></i> 重置
									</button>
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
	<script src="../resources/plugins/jquery-validation/jquery.validate.min.js"></script>
	<script src="../resources/plugins/jquery-validation/messages_zh.js"></script>
	
	<script src="../resources/bootstrap-wysiwyg/bootstrap-wysiwyg.js" type="text/javascript"></script>
	<script src="../resources/bootstrap-wysiwyg/external/jquery.hotkeys.js" type="text/javascript"></script>
	<script>
		$(document).ready(function() {
			function initToolbarBootstrapBindings() {
			      var fonts = ['Serif', 'Sans', 'Arial', 'Arial Black', 'Courier', 
			            'Courier New', 'Comic Sans MS', 'Helvetica', 'Impact', 'Lucida Grande', 'Lucida Sans', 'Tahoma', 'Times',
			            'Times New Roman', 'Verdana'],
			            fontTarget = $('[title=Font]').siblings('.dropdown-menu');
			      $.each(fonts, function (idx, fontName) {
			          fontTarget.append($('<li><a data-edit="fontName ' + fontName +'" style="font-family:\''+ fontName +'\'">'+fontName + '</a></li>'));
			      });
			      $('a[title]').tooltip({container:'body'});
			    	$('.dropdown-menu input').click(function() {return false;})
					    .change(function () {$(this).parent('.dropdown-menu').siblings('.dropdown-toggle').dropdown('toggle');})
			        .keydown('esc', function () {this.value='';$(this).change();});

			      $('[data-role=magic-overlay]').each(function () { 
			        var overlay = $(this), target = $(overlay.data('target')); 
			        overlay.css('opacity', 0).css('position', 'absolute').offset(target.offset()).width(target.outerWidth()).height(target.outerHeight());
			      });
			      if ("onwebkitspeechchange"  in document.createElement("input")) {
			        var editorOffset = $('#editor').offset();
			        $('#voiceBtn').css('position','absolute').offset({top: editorOffset.top, left: editorOffset.left+$('#editor').innerWidth()-35});
			      } else {
			        $('#voiceBtn').hide();
			      }
				};
				function showErrorAlert (reason, detail) {
					var msg='';
					if (reason==='unsupported-file-type') { msg = "Unsupported format " +detail; }
					else {
						console.log("error uploading file", reason, detail);
					}
					$('<div class="alert"> <button type="button" class="close" data-dismiss="alert">&times;</button>'+ 
					 '<strong>File upload error</strong> '+msg+' </div>').prependTo('#alerts');
				};
			    initToolbarBootstrapBindings(); 
				$('#editor').wysiwyg({ fileUploadError: showErrorAlert} );
		});
		$("#editor").bind('DOMNodeInserted', function(e) {  
		     $("#description").val($("#editor").html());
		     $.get("/api/getSummary?content="+ $("#description").val(), function(json) {
		    	 $("#intro").val(json);
		 	 });
		});  
		$('#datetimepicker').daterangepicker({
			startDate : moment(),
			maxDate : '2020-12-31 23:59:59',
			showDropdowns : true,
			showWeekNumbers : false, //是否显示第几周
			timePicker : true, //是否显示小时和分钟
			timePickerIncrement : 60, //时间的增量，单位为分钟
			timePicker12Hour : false, //是否使用12小时制来显示时间
			format : 'YYYY-MM-DD HH:mm:ss', //控件中from和to 显示的日期格式
			separator : ' - ',
			locale : {
				applyLabel : '确定',
				cancelLabel : '取消',
				fromLabel : '起始时间',
				toLabel : '结束时间',
				customRangeLabel : '自定义',
				daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],
				monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月' ],
				firstDay : 1
			}
		}, function(start, end, label) {//格式化日期显示框
			$('input[name=saleBegin]').val(start.format('YYYY-MM-DD HH:mm:ss'));
			$('input[name=saleEnd]').val(end.format('YYYY-MM-DD HH:mm:ss'));
		});
		function toggle() {
			if($("#toggUrl").css("display")=="none")
				$("#toggUrl").css("display","block");
			else
				$("#toggUrl").css("display","none");
		}
		$("#addOrEditForm").validate({
			rules : {
				title : "required",
				price : {
					required : false,
					number : true
				},
				amount : {
					required : false,
					digits : true
				},
				saleBegin : {
					required : false,
					date : true
				},
				saleEnd : {
					required : false,
					date : true
				}
			}
		});
	</script>
</body>
</html>
