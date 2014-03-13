<jsp:include page="include/header.jsp" />

<%@page import="java.util.List"%>
<%@page import="com.excilys.data.Company"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="addComputer" var="action" scope="request"/>
<c:set value="Add" var="actionName" scope="request"/>
<div class="container-fluid" id="main">
	<div class="row">
	<h1>Add Computer</h1>
	</div>
	<div class="row">
	<div class="col-md-4">
	<jsp:include page="include/computerForm.jsp" />
	</div>
	</div>
</div>

<jsp:include page="include/footer.jsp" />