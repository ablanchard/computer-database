<jsp:include page="include/header.jsp" />

<%@page import="java.util.List"%>
<%@page import="com.excilys.data.Company"%>
<%@page import="com.excilys.data.Computer"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<% List<Company> companies = (List<Company>)request.getAttribute("companies"); 
Computer computer = (Computer) request.getAttribute("computer"); %>
<c:set var="companyId" value="${computer.company.id }"></c:set>
<section id="main">

	<h1>Edit Computer</h1>
	
	<form action="editComputer" method="POST">
		<fieldset>
			<div class="clearfix">
				<label for="name">Computer name:</label>
				<div class="input">
					<input type="text" name="name" value="${computer.name }"  id="name"/>
					<span class="help-inline">Required</span>
				</div>
				<input type="hidden" value="${computer.id }" name="id"/>
			</div>
	
			<div class="clearfix">
				<label for="introduced">Introduced date:</label>
				<div class="input">
					<fmt:formatDate value="${computer.introduction }" pattern="yyyy-MM-dd" var="inDate"/>
					<input type="date" name="introducedDate" value="${inDate }"  id="introducedDate" pattern="YYYY-MM-dd"/>
					<span class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="discontinued">Discontinued date:</label>
				<div class="input">
					<fmt:formatDate value="${computer.discontinued }" pattern="yyyy-MM-dd" var="disDate"/>
					<input type="date" name="discontinuedDate" value="${disDate }" id="discontinuedDate" pattern="YYYY-MM-dd"/>
					<span class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="company">Company Name:</label>
				<div class="input">
					<select name="company" id="company">
						<c:forEach var="company" items="${requestScope['companies']}" >
							<option value="${company.id }" <c:if test="${company.id == companyId }">selected="selected"</c:if>>${company.name }</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</fieldset>
		<div class="actions">
			<button type="submit"  class="btn primary">Edit</button>
			or <a href="index" class="btn">Cancel</a>
		</div>
	</form>
</section>

<jsp:include page="include/footer.jsp" />