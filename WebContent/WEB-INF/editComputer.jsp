<jsp:include page="../include/header.jsp" />

<%@page import="java.util.List"%>
<%@page import="com.excilys.data.Company"%>
<%@page import="com.excilys.data.Computer"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set value="editComputer" var="action" scope="request"/>
<c:set value="Edit" var="actionName" scope="request"/>
<div class="container-fluid" id="main">
	<div class="row">
	<c:if test="${not empty requestScope['error'] }">
		<div class="alert alert-danger"><p><c:out value="${requestScope['error'] }"></c:out></p></div>
	</c:if>
	<h1>Edit Computer</h1>
	</div>
	<div class="row">
	<div class="col-md-4">
	<jsp:include page="../include/computerForm.jsp" />
	</div>
	</div>
</div>

<jsp:include page="../include/footer.jsp" />