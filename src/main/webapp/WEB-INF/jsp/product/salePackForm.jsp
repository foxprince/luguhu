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
	<div class="modal fade" id="assetModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">素材库</h4>
				</div>
				<div class="modal-body">
					<div id="assetList" class="box-body"></div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default pull-left" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary">Save changes</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
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
									<a class="btn btn-success" data-toggle="modal" data-target="#assetModal">从素材库选择 </a>
								</div>

							</div>
						</form:form>
						<form:form class="form-horizontal well" modelAttribute="item" id="addOrEditForm" action="./${item.action}">
							<html:inputField name="title" label="销售包名称 *:" />
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 产品规格 </label>
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
									<%-- <c:forEach var="product" items="${productList}">
              <div class="box box-default box-solid collapsed-box">  
                <div class="box-header with-border">
                  <h3 class="box-title">${product.title}</h3>
                  <div class="box-tools pull-right">
                    <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-plus"></i></button>
                  </div><!-- /.box-tools -->
                </div>
                <div class="box-body" class="panel-collapse collapse in">
                <c:forEach var="unit" items="${product.saleUnits}">
                  <form:checkbox path="saleUnits" value="${unit.id }"/>${unit.title}
                </c:forEach>
                </div>
              </div>
              </c:forEach> --%>
								</div>
							</div>
							<%-- <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 产品规格 </label>
                <div class="col-sm-9 ">
                  <form:select path="saleUnits" class="form-control ">
                    <form:options class="span4 col-xs-10 col-sm-5" items="${saleUnitList}"  itemLabel="title" itemValue="id"/>
                  </form:select>
                </div>
              </div> --%>
							<html:inputField name="minBatch" label="起售斤数" />
							<html:inputField name="price" label="价格 *" />
							<html:inputField name="amount" label="库存总数" />
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-tags">销售起止时间</label>
								<div class="col-sm-9">
									<form:input class="input-lg col-xs-10 col-sm-5" path="saleBegin" label="销售开始时间" id="datetimepicker"
										data-date-format="yyyy-mm-dd hh:ii" />
									-
									<form:input class="input-lg col-xs-10 col-sm-5" path="saleEnd" label="销售结束时间" data-date-format="yyyy-mm-dd hh:ii" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-tags">详细说明</label>
								<div class="col-sm-9">
									<form:textarea path="description" cols="100" rows="5" />
								</div>
							</div>
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
	<script>
		$(document).ready(function() {
		});
		$('#assetModal').on(
				'show.bs.modal',
				function(event) {
					var modal = $(this);
					$.get("/asset/list.json", function(json) {
						if (json.code == 0) {
							for (var i = 0; i < json.data.numberOfElements; i++) {
								var j = json.data.content[i];
								var item = '<div class="col-sm-6 col-md-3">';
								item += '	<div class="thumbnail">';
								item += '	<div>';
								item += '		<a href="#" onclick="selectAsset(\'' + j.id + '\',\'' + j.location
										+ '\')"><img  style="max-width:200px;max-height:200px;" src="/asset/preview?fileName=' + j.location + '" /></a>';
								item += '	</div>';
								item += '	<div class="caption">';
								item += '		<h6 id="imgTitle-${item.id}">' + j.title + '</h6>';
								item += '	</div></div>';
								item += '</div>';
								$("#assetList").append(item);
							}
						} else
							alert('载入素材库失败。');
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
		function selectAsset(id, location) {
			$("#assetList").html(" ");
			$('#assetModal').modal('hide');
			$("#previewImg").html('<img class="preview" src="/asset/preview?fileName=' + location + '"/>');
			$("#addOrEditForm").append('<input type="hidden" name="asset"  value="'+id+'"name="img"/>');
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
						$("#previewImg").html('<img class="preview" src="/asset/preview?fileName=' + json.data.location + '"/>');
						$("#addOrEditForm").append('<input type="hidden" id="packImg" value="'+json.data+'"name="img"/>');
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
