<jsp:include page="include/header.jsp" />
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.excilys.data.Computer"%>
<%@page import="com.excilys.data.Company"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<% List<Computer> computers = (List<Computer>)request.getAttribute("computers"); %>
<section id="main">
	<h1 id="homeTitle"><%=computers.size() %> Computers found</h1>
	<div id="actions">
		<form action="" method="POST">
			<input type="search" id="searchbox" name="search"
				value="" placeholder="Search name">
			<input type="submit" id="searchsubmit"
				value="Filter by name"
				class="btn primary">
		</form>
		<a class="btn success" id="add" href="addComputer">Add Computer</a>
	</div>

		<table class="computers zebra-striped">
			<thead>
				<tr>
					<!-- Variable declarations for passing labels as parameters -->
					<!-- Table header for Computer Name -->
					<th>Computer Name</th>
					<th>Introduced Date</th>
					<!-- Table header for Discontinued Date -->
					<th>Discontinued Date</th>
					<!-- Table header for Company -->
					<th>Company</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="computer" items="${requestScope['computers']}" >
			<c:set var="introduction" value="${computer.introduction }" />
				<tr>
					<td><a href="editComputer?id=${computer.id}" onclick="">${computer.name }</a></td>
					<td>
					
					<fmt:formatDate type="date" value="${computer.introduction }" pattern="yyyy-MM-dd"/>
					 
					</td>
					<td>
					<fmt:formatDate value="${computer.discontinued }" pattern="yyyy-MM-dd"/>
					</td>
					<td><c:out value="${computer.company.name}" default="No company"/></td>
					<td><a href="deleteComputer?id=${computer.id}" class="btn danger">Delete</a></td>
				</tr>
				</c:forEach>				
			</tbody>
		</table>
</section>

<jsp:include page="include/footer.jsp" />
