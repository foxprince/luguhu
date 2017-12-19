<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html/"%>
<%@ taglib prefix="ajax" tagdir="/WEB-INF/tags/ajax/"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>${title}_<spring:message code="website.name" /></title>
<!-- Tell the browser to be responsive to screen width -->
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<!-- Bootstrap 3.3.5 -->
<link rel="stylesheet" href="../bootstrap/css/bootstrap.min.css">
<!-- Font Awesome -->
<link rel="stylesheet" href="../dist/css/font-awesome.min.css">
<!-- Ionicons -->
<link rel="stylesheet" href="../dist/css/ionicons.min.css">
<link rel="stylesheet" href="../ace/ace.min.css" />
<link rel="stylesheet" href="../ace/ace-rtl.min.css" />
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="../plugins/html5shiv.min.js"></script>
        <script src="../plugins/respond.min.js"></script>
    <![endif]-->
</head>
<style type="text/css">
.help-inline {
	color: red;
}
.error{
  color:red;
}

</style>
<body class="login-layout">
  <div class="main-container">
    <div class="main-content">
      <div class="row">
        <div class="col-sm-10 col-sm-offset-1">
          <div class="login-container">
            <div class="center">
              <h1>
                <i class="icon-leaf green"></i> <span class="red"><spring:message code="website.name" /></span> <span
                  class="white"><spring:message code="website.title" /></span>
              </h1>
              <h4 class="blue">
                &copy;
                <spring:message code="website.corpName" />
              </h4>
            </div>
            <div class="space-6"></div>
            <div class="position-relative">
              <div id="login-box" class="login-box visible widget-box no-border">
                <div class="widget-body">
                  <div class="widget-main">
                    <h4 class="header blue lighter bigger">
                      <i class="icon-coffee green"></i> 登录
                    </h4>
                    <div class="space-6"></div>
                    <form id="login-form" action="/user/login" method="post">
                      <fieldset>
                        <label class="block clearfix"> <span class="block input-icon input-icon-right"> <input
                            type="email" id="loginEmail" name="email" class="form-control" placeholder="邮箱地址" /> <i class="icon-user"></i>
                        </span>
                        </label> <label class="block clearfix"> <span class="block input-icon input-icon-right">
                            <input type="password" id="loginPasswd" name="password" class="form-control" placeholder="密码" /> <i class="icon-lock"></i>
                        </span>
                        </label>
                        <div class="space"></div>
                        <div class="clearfix">
                          <label class="inline"> <input type="checkbox" class="ace" /> <span class="lbl"> 保存登录状态</span> </label>
                          <button type="submit" class="width-35 pull-right btn btn-sm btn-primary">
                            <i class="icon-key"></i> 登录
                          </button>
                        </div>
                        <div class="space-4"></div>
                      </fieldset>
                    </form>
                    <div class="social-or-login center">
                      <span class="bigger-110">Or Login Using</span>
                    </div>
                    <div class="social-login center">
                      <a class="btn btn-primary"> <i class="icon-facebook"></i>
                      </a> <a class="btn btn-info"> <i class="icon-twitter"></i>
                      </a> <a class="btn btn-danger"> <i class="icon-google-plus"></i>
                      </a>
                    </div>
                  </div>
                  <!-- /widget-main -->
                  <div class="toolbar clearfix">
                    <div>
                      <a href="#" onclick="show_box('forgot-box'); return false;" class="forgot-password-link"> <i
                        class="icon-arrow-left"></i> 忘记密码
                      </a>
                    </div>
                    <div>
                      <a href="#" onclick="show_box('signup-box'); return false;" class="user-signup-link"> 注册
                       <i class="icon-arrow-right"></i>
                      </a>
                    </div>
                  </div>
                </div>
                <!-- /widget-body -->
              </div>
              <!-- /login-box -->
              <div id="forgot-box" class="forgot-box widget-box no-border">
                <div class="widget-body">
                  <div class="widget-main">
                    <h4 class="header red lighter bigger">
                      <i class="icon-key"></i> 取回密码
                    </h4>
                    <div class="space-6"></div>
                    <p>请输入您的注册邮箱</p>
                    <form>
                      <fieldset>
                        <label class="block clearfix"> <span class="block input-icon input-icon-right"> <input
                            type="email" class="form-control" placeholder="Email" /> <i class="icon-envelope"></i>
                        </span>
                        </label>
                        <div class="clearfix">
                          <button type="button" class="width-35 pull-right btn btn-sm btn-danger">
                            <i class="icon-lightbulb"></i> 重新发送密码!
                          </button>
                        </div>
                      </fieldset>
                    </form>
                  </div>
                  <!-- /widget-main -->
                  <div class="toolbar center">
                    <a href="#" onclick="show_box('login-box'); return false;" class="back-to-login-link"> 返回登录 <i class="icon-arrow-right"></i>
                    </a>
                  </div>
                </div>
                <!-- /widget-body -->
              </div>
              <div id="regist-success-box" class="forgot-box widget-box no-border">
                <div class="widget-body">
                  <div class="widget-main">
                    <h4 class="header red lighter bigger">
                      <i class="icon-key"></i> 注册成功！
                    </h4>
                    <div class="space-6"></div>
                    <p><span id="autoLogin">5</span>秒后自动登录</p>
                    <p>
                    <a class="btn btn-large btn-success"href="/index">直接登录</a>
                    <a class="btn btn-large btn-warning"href="/user/detail">编辑我的资料</a>
                    </p>
                  </div>
                  <!-- /widget-main -->
                  <div class="toolbar center">
                    <a href="#" onclick="show_box('login-box'); return false;" class="back-to-login-link"> 返回登录 <i class="icon-arrow-right"></i>
                    </a>
                  </div>
                </div>
                <!-- /widget-body -->
              </div>
              <!-- /forgot-box -->
              <div id="signup-box" class="signup-box widget-box no-border visible">
                <div class="widget-body">
                  <div class="widget-main">
                    <h4 class="header green lighter bigger">
                      <i class="icon-group blue"></i> 注册
                    </h4>
                    <div class="space-6"></div>
                    <p>请填写如下信息（必填）:</p>
                    <form:form modelAttribute="user" id="add-user-form" action="/user/regist" method="POST">
                      <fieldset>
                        <label class="block clearfix" id="email"> <span
                          class="block input-icon input-icon-right"> <form:input path="email" type="email"
                              class="form-control" placeholder="邮箱地址" /> <i class="icon-envelope"></i> <span
                            class="glyphicon glyphicon-envelope form-control-feedback"></span> <span id="emailHint"
                            class="help-inline"></span>
                        </span>
                        </label> <label class="block clearfix" > <span
                          class="block input-icon input-icon-right"> <form:input path="password" type="password"
                              class="form-control" placeholder="密码" /> <span
                            class="glyphicon glyphicon-lock form-control-feedback"></span> <span class="help-inline"></span>
                        </span>
                        </label> <label class="block clearfix" id="repassword"> <span
                          class="block input-icon input-icon-right"> <input name="repassword"
                              type="password" class="form-control" placeholder="确认密码" /> <i class="icon-retweet"></i> <span
                            class="glyphicon glyphicon-lock form-control-feedback"></span> <span class="help-inline"></span>
                        </span>
                        </label> <label class="block clearfix" id="name"> <span
                          class="block input-icon input-icon-right"> <form:input path="name" class="form-control"
                              placeholder="姓名" /> <span class="glyphicon glyphicon-user form-control-feedback"></span>
                            <span class="help-inline"></span>
                        </span>
                        </label> 
                        <label class="block clearfix" id="name"> <span
                          class="block input-icon input-icon-right"> <form:input path="phone" class="form-control"
                              placeholder="手机号码" /> <span class="glyphicon glyphicon-phone form-control-feedback"></span>
                            <span class="help-inline"></span>
                        </span>
                        </label>
                        <label class="block clearfix" id="level"> <span
                          class="block input-icon input-icon-right"> <form:radiobutton path="level" value="0" />
                            散户 <form:radiobutton path="level" value="1" /> 份额用户 <form:radiobutton path="level"
                              value="2" /> 股东用户 <span class="help-inline"></span>
                        </span>
                        </label> <label class="block" id="agree">  <span
                          class="lbl"> 我接受 <a href="#">注册条款</a>
                        </span><input type="checkbox" name="terms"  />
                        </label>
                        <div class="space-24"></div>
                        <div class="clearfix">
                          <button type="reset" class="width-30 pull-left btn btn-sm">
                            <i class="icon-refresh"></i> 重置
                          </button>
                          <button type="submit" class="width-65 pull-right btn btn-sm btn-success">
                            注册 <i class="icon-arrow-right icon-on-right"></i>
                          </button>
                        </div>
                      </fieldset>
                    </form:form>
                  </div>
                  <div class="toolbar center">
                    <a href="#" onclick="show_box('login-box'); return false;" class="back-to-login-link"> <i
                      class="icon-arrow-left"></i> 返回登录
                    </a>
                  </div>
                </div>
                <!-- /widget-body -->
              </div>
              <!-- /signup-box -->
            </div>
            <!-- /position-relative -->
          </div>
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
    </div>
  </div>
  <!-- /.main-container -->
  <!-- /.register-box -->
  <!-- jQuery 2.1.4 -->
  <script src="../plugins/jQuery/jQuery-2.1.4.min.js"></script>
  <!-- Bootstrap 3.3.5 -->
  <script src="../bootstrap/js/bootstrap.min.js"></script>
  <script src="../plugins/jquery-validation/jquery.validate.min.js"></script>
  <script src="../plugins/jquery-validation/messages_zh.js"></script>
  <script type="text/javascript">
  	var showBox = "${s}";//getUrlParam("s");
  	if(showBox!=null&&showBox!="")
  		show_box(showBox);
  	if($('#regist-success-box').hasClass('visible')) {
  		autoLogin();
  	}
 	// 手机号码验证
	jQuery.validator.addMethod("isMobile", function(value, element) {
		var length = value.length;
		var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
		return this.optional(element) || (length == 11 && mobile.test(value));
	}, "请正确填写您的手机号码");
  	$("#login-form").validate({
  		rules: {
    		password: {
    			remote: {
    			    url: "/user/checkEmailAndPasswd",     //后台处理程序
    			    type: "post",               //数据发送方式
    			    data:{
    			    	email:function(){ return $("#loginEmail").val(); },
    			         password:function(){ return $("#loginPasswd").val(); }
    			    }
    			}
    		},
    		email: {
    			required: true,
    			email: true,
    			remote: "emailExists"
    		}
    	},
    	messages: {
    		password: {
    			remote: jQuery.validator.format("密码错误。")
    		},
    		email: {
    			remote: jQuery.validator.format("邮箱地址不存在。")
    		}
    	},
    	submitHandler: function() {
			form.submit();
		}
  	});  
  	$("#add-user-form").validate({
  		rules: {
    		password: {
    			required: true,
    			minlength: 6
    		},
    		repassword: {
    			required: true,
    			minlength: 6,
    			equalTo: "#password"
    		},
    		email: {
    			required: true,
    			email: true,
    			remote: "emailCanRegisted"
    		},
    		phone : {
				required : true,
				minlength : 11,
				isMobile : true
			},
    		terms: "required"
    	},
    	messages: {
    		repassword: {
    			required: "再输一遍密码",
    			equalTo: "两次输入的密码必须一致"
    		},
    		phone : {
				required : "请输入手机号码",
				minlength : "手机号码不能小于11个字符",
				isMobile : "请正确填写您的手机号码"
			},
    		email: {
    			remote: jQuery.validator.format("邮箱地址已注册。")
    		},
    		terms: "您必须接收我们的条款。"
    	}
    	// the errorPlacement has to take the table layout into account
    	/* errorPlacement: function(error, element) {
    		if (element.is(":radio"))
    			error.appendTo(element.parent().next().next());
    		else if (element.is(":checkbox"))
    			error.appendTo(element.next());
    		else
    			error.appendTo(element.parent().next());
    	}, */
  	});
  //});
  function getUrlParam(name) {
      var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
      var r = window.location.search.substr(1).match(reg);  //匹配目标参数
      if (r != null) return unescape(r[2]); return null; //返回参数值
  }    
  function show_box(id) {
      jQuery('.widget-box.visible').removeClass('visible');
      jQuery('#'+id).addClass('visible');
  }
  function autoLogin() {
	var i = 5;
	setInterval(function(){               
		if(i == 1) {
	        location.href = "/index";
	    }
	    $("#autoLogin").innerHTML = i--;
	
	},1000);
  };
  </script>
  <%-- <ajax:formPartialRefresh validateUrl="/user/add.json" formName="add-user-form" /> --%>
</body>
</html>
