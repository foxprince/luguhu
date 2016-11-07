<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ attribute name="modelAttribute" required="true" rtexprvalue="true" %>
<%@ attribute name="id" required="true" rtexprvalue="true" %>
<%@ attribute name="action" required="true" rtexprvalue="true" %>
<%@ attribute name="method" required="true" rtexprvalue="true" %>

<form:form modelAttribute="${modelAttribute}" id="${id}" action="${action}"  method="${method}">
		<fieldset>
	       <jsp:doBody />
	    </fieldset>
</form:form>