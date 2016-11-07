<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="name" required="true" rtexprvalue="true" description="Name of corresponding property in bean object"%>
<%@ attribute name="label" required="true" rtexprvalue="true" description="Label appears in red color if input is considered as invalid after submission"%>
<%@ attribute name="type" required="false" rtexprvalue="true"%>
<%@ attribute name="css" required="false" rtexprvalue="true"%>
<%@ attribute name="labelCss" required="false" rtexprvalue="true"%>
	<%-- <c:set var="cssGroup" value="form-group ${status.error ? ' error' : '' }"/>
	<div class="${cssGroup}" id="${name}">
		<label class="${labelCss} control-label">${label}</label> 
		<div class="form-control ${css}">
			<form:input path="${name}" class="form-control"/> 
			<span class="help-inline">${status.errorMessage}</span>
		</div>
	</div> --%>
<div class="form-group">
  <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> ${label} </label>
  <div class="col-sm-9">
    <c:choose>
      <c:when test="${type=='password'}"><form:password path="${name}" id="${name}" showPassword="true" class="input-lg col-xs-10 col-sm-5" /></c:when>
      <c:otherwise><form:input path="${name}" id="${name}"  class="input-lg col-xs-10 col-sm-5" /></c:otherwise>
    </c:choose> 
    <span class="error">${status.errorMessage}</span>
  </div>
</div>
