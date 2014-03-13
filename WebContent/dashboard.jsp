<jsp:include page="include/header.jsp" />
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.excilys.data.Computer"%>
<%@page import="com.excilys.data.Company"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<% List<Computer> computers = (List<Computer>)request.getAttribute("computers"); %>
<div class="container-fluid" id="main">
	<div class="row">
	<div class="col-md-12">
	<h1 id="homeTitle"><%=computers.size() %> Computers found</h1>
	</div>
	</div>
	<div class="row" id="actions">
		<div class="col-md-10">
		<form role="form" class="form-inline" action="" method="POST">
			<div class="form-group">
				<label class="sr-only" for="searchbox">Search</label>
				<input type="search" id="searchbox" name="search" value="" placeholder="Search name" />
			</div>	
			<button type="submit" id="searchsubmit"	class="btn btn-default">Filter by name</button>
		</form>
		</div>
		<div class="col-md-2">
		<a href="addComputer" class="btn btn-success" id="add" >Add Computer</a>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
		<table class="computers table table-striped table-hover table-bordered">
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
					<td><a href="deleteComputer?id=${computer.id}" ><button class="btn btn-danger">Delete</button></a></td>
				</tr>
				</c:forEach>				
			</tbody>
		</table>
	</div>
	</div>
</div>

<jsp:include page="include/footer.jsp" />
