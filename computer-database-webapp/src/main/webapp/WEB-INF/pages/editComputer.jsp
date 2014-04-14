<jsp:include page="../include/header.jsp" />

<%@page import="java.util.List"%>
<%@page import="com.excilys.data.Company"%>
<%@page import="com.excilys.data.Computer"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="container-fluid" id="main">
	<div class="row">
		<c:if test="${not empty requestScope['error'] }">
			<div class="alert alert-danger"><p><c:out value="${requestScope['error'] }"></c:out></p></div>
		</c:if>
		<div class="col-md-12">
			<h1><spring:message code="edit.computer" /></h1>
		</div>
	</div>
	<div class="row">
		<div class="col-md-8">
			<form:form method="post" action="edit" id="addComputer" modelAttribute="dto">
			<jsp:include page="../include/computerForm.jsp" />
			<div class="actions">
				<button type="submit" id="submit" class="btn btn-primary"><spring:message code="edit" text="Edit" /></button>
				 <spring:message code="or" text="or" /> <a href="index" class="btn btn-danger"><spring:message code="cancel" text="Cancel" /></a>
			</div>
			</form:form>
		</div>
	</div>
</div>

<jsp:include page="../include/footer.jsp" />