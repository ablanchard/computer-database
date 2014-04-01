<jsp:include page="../include/header.jsp" />

<%@page import="java.util.List"%>
<%@page import="com.excilys.data.Company"%>
<%@page import="com.excilys.data.Computer"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="container-fluid" id="main">
	<div class="row">
	<c:if test="${not empty requestScope['error'] }">
		<div class="alert alert-danger"><p><c:out value="${requestScope['error'] }"></c:out></p></div>
	</c:if>
	
	<jsp:include page="../include/computerForm.jsp" />
	
</div>

<jsp:include page="../include/footer.jsp" />