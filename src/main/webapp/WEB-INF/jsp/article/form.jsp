<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html/"%>
<%@ taglib prefix="ajax" tagdir="/WEB-INF/tags/ajax/"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<c:import url="../include/head.jsp">
  <c:param name="pageTitle" value="${article.actionDesc}产品" />
</c:import>
<link href="../resources/bootstrap-wysiwyg/bootstrap-combined.no-icons.min.css" rel="stylesheet">
<link href="../resources/bootstrap-wysiwyg/bootstrap-responsive.min.css" rel="stylesheet">
<!-- <link href="../resources/plugins/font-awesome.3.0.2.css" rel="stylesheet"> -->
<link href="http://netdna.bootstrapcdn.com/font-awesome/3.0.2/css/font-awesome.css" rel="stylesheet">
<link rel="stylesheet" href="../resources/bootstrap-wysiwyg/index.css" type="text/css">
<style>
.modal-dialog{
    position: relative;
    display: table; 
    overflow-y: auto;    
    overflow-x: auto;
    width: auto;
    min-width: 600px;   
}
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
        <h1>文章-${article.actionDesc}文章</h1>
        <ol class="breadcrumb">
          <li><a href="/"><i class="fa fa-dashboard"></i> 主页</a></li>
          <li class="active"><a href="/article/list">文章列表</a></li>
        </ol>
      </section>
      <!-- Main content -->
      <section class="content">
      	<%@ include file="../include/assetModal.html"%>
        <div class="box box-info">
          <div class="box-body">
            <form:form class="form-horizontal well" modelAttribute="article"  id="addOrEditForm" action="./${article.action}">
              <html:inputField name="title" label="文章标题" />
              <div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 缩略图 : </label>
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
              <html:inputField name="origin" label="来源" />
              <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 发布者 </label>
                <div class="col-sm-9 ">
                  <form:select path="author" class="form-control ">
                    <form:options class="span4 col-xs-10 col-sm-5" items="${authorList}" itemLabel="name" itemValue="id"/>
                  </form:select>
                </div>
              </div>
              				<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-tags">内容摘要：</label>
								<div class="col-sm-9">
									<form:textarea path="summary" id="summary"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right" for="form-field-tags">详细内容：</label>
								<div class="col-sm-9">
									<%-- <form:textarea path="description" class="wysi" cols="100" rows="5" /> --%>
									<form:hidden path="content" id="content"/>
								</div>
							</div>
  				<%@include file="../include/wysiwyg.jspf" %>	
              <div class="clearfix form-actions">
                <div class="col-md-offset-3 col-md-9">
                  <input type="hidden" name="id" value="${article.id }"/>
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
		
		$("#editor").bind('DOMNodeInserted', function(e) {  
		     $("#content").val($("#editor").html());
		     $.post("/api/getSummary", {content: $('#content').val(),${_csrf.parameterName}:"${_csrf.token}"}, function(json) {
		    	 $("#summary").val(json);
		 	 });
		});  
		
			
			$("#addOrEditForm").validate({
				rules : {
					title : "required",
					place : "required"
				}
			});
		});
		</script>
</body>
</html>
