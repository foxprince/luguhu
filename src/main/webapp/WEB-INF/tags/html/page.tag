<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ attribute name="url" required="true" rtexprvalue="true"%>
<s:eval var="pageUrl" expression="T(cn.anthony.util.http.HTTPUtil).toGetStringExcludePage(url,param)"  />
<s:eval var="size" expression="T(cn.anthony.util.http.HTTPUtil).getValue('size' ,param)"  />
<c:url var="firstUrl" value="${pageUrl}&page=0" />
<c:url var="lastUrl" value="${pageUrl}&page=${totalPages-1}" />
<c:url var="prevUrl" value="${pageUrl}&page=${currentIndex - 2}" />
<c:url var="nextUrl" value="${pageUrl}&page=${currentIndex }" />

							<div class="row">
								<div class="col-sm-4">
								<form action="${pageUrl}"  method="GET" id="pageSizeForm">每页:
								<input type="text" name="size" value="${size==null||size.equals("")?10:size}" class="input-short"/>
								<input type="submit" value="条"/><form:errors path="size" cssclass="error"/>
								</form>
								</div>
								<div class="col-sm-4" >
									共${total}条，总${totalPages}页。
								</div>
								<div class="col-sm-4">
								<form action="${pageUrl}" method="GET" id="pageIndexForm">到第:
								<input type="hidden"  name="size" class="input-short" value="${size}"/>
                                <input type="text"  name="page" class="input-short" id="pageIndexInput" value="${currentIndex}"/>
								<input type="submit" value="页"/>
								</form>
								</div>
							</div>
							<div class="row text-center  pagination pagination-centered">
							<c:choose>
					            <c:when test="${hasPrevious}">
					                <li><a href="${firstUrl}">&lt;&lt;</a></li>
					                <li><a href="${prevUrl}">&lt;</a></li>
					            </c:when>
					        </c:choose>
					        <c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
					            <c:choose>
					                <c:when test="${i == currentIndex}">
					                    <li class="active"><a href="${pageUrl}&page=${i-1}"><c:out value="${i}" /></a></li>
					                </c:when>
					                <c:otherwise>
					                    <li><a href="${pageUrl}&page=${i-1}"><c:out value="${i}" /></a></li>
					                </c:otherwise>
					            </c:choose>
					        </c:forEach>
					        <c:choose>
					            <c:when test="${hasNext}">
					                <li><a href="${nextUrl}">&gt;</a></li>
					                <li><a href="${lastUrl}">&gt;&gt;</a></li>
					            </c:when>
					        </c:choose>			
							</div>
  <script src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
  <script src="/resources/plugins/jquery-validation/jquery.validate.min.js"></script>
  <script src="/resources/plugins/jquery-validation/messages_zh.js"></script>
  <script>
  $("#pageSizeForm").validate({
      rules : {
      	size: {
              required: true,
              number: true
          }
      }
    });
  	$("#pageIndexForm").validate({
        rules : {
        	page: {
                required: true,
                number: true
            }
        },
    	submitHandler: function() {
    		$("#pageIndexInput").val($("#pageIndexInput").val()-1);
    		form.submit();
		}
      });
    </script>