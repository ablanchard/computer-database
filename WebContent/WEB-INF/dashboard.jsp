<jsp:include page="../include/header.jsp" />
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.excilys.data.Computer"%>
<%@page import="com.excilys.data.Company"%>
<%@page import="com.excilys.dao.ComputerDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="nbElementsParPage" value="${requestScope['sw'].nbComputersPerPage }"></c:set>
<c:set var="page" value="${requestScope['sw'].page }"></c:set>
<c:set var="pageMax" value="${requestScope['sw'].pageMax }"></c:set>
<c:if test="${empty requestScope['sw'].page}">
	<c:set var="page" value="1"></c:set>
</c:if>
<c:if test="${page < 1}">
	<c:set var="page" value="1"></c:set>
</c:if>
<c:if test="${page >= pageMax}">
	<c:set var="page" value="${pageMax }"></c:set>
</c:if>

<c:set var="newDirection" value="asc"></c:set>
<c:if test="${param['direction'] == newDirection}">
	<c:set var="newDirection" value="desc"></c:set>
</c:if>

<c:set var="orderName" value="name"></c:set>
<c:set var="orderIntro" value="intro"></c:set>
<c:set var="orderDisc" value="disc"></c:set>
<c:set var="orderCompany" value="company"></c:set>

<div class="container-fluid" id="main">
	<div class="row">
	<c:if test="${not empty requestScope['success'] }">
		<div class="alert alert-success"><p><c:out value="${requestScope['success'] }"></c:out></p></div>
	</c:if>
	<c:if test="${not empty requestScope['error'] }">
		<div class="alert alert-danger"><p><c:out value="${requestScope['error'] }"></c:out></p></div>
	</c:if>
	<div class="col-md-12">
	<h1 id="homeTitle"><c:out value="${requestScope['sw'].count}"/> Computers found</h1>
	</div>
	</div>
	<div class="row" id="actions">
		<div class="col-md-12">
		<form role="form" class="form-inline" action="index" method="PUT">
			<div class="form-group">
				<label class="sr-only" for="searchbox">Search</label>
				<input type="search" id="searchbox" name="search" value="" placeholder="Search name" />
			</div>	
			<button type="submit" id="searchsubmit"	class="btn btn-default">Filter by name</button>
		</form>
		<a href="addComputer" class="btn btn-success" id="add" >Add Computer</a>
		</div>
	</div>
	<c:if test="${requestScope['sw'].count != 0 }">
	<div class="row">
		
		<div class="col-md-12">
			<ul class="pagination">
				
				<c:url value="/index" var="variableURL">
						<c:param name="page" value="${page -1 }"/>
						<c:if test="${not empty requestScope['sw'].query }"><c:param name="search" value="${requestScope['sw'].query }"/></c:if>
						<c:if test="${not empty requestScope['sw'].orderCol }"><c:param name="order" value="${requestScope['sw'].orderCol }"/></c:if>
						<c:if test="${not empty requestScope['sw'].orderDirection }"><c:param name="direction" value="${requestScope['sw'].orderDirection }"/></c:if>
				</c:url>
			  <li <c:if test="${page -1 == 0 }">class="disabled"</c:if>><a href="${variableURL }">&laquo;</a></li>
			  <c:forEach var="lienPage" begin="1" end="${ pageMax}">
			  	<c:url value="/index" var="variableURL">
						<c:param name="page" value="${lienPage }"/>
						<c:if test="${not empty requestScope['sw'].query }"><c:param name="search" value="${requestScope['sw'].query }"/></c:if>
						<c:if test="${not empty requestScope['sw'].orderCol }"><c:param name="order" value="${requestScope['sw'].orderCol }"/></c:if>
						<c:if test="${not empty requestScope['sw'].orderDirection }"><c:param name="direction" value="${requestScope['sw'].orderDirection }"/></c:if>
				</c:url>
			  	<li <c:if test="${lienPage == page }">class="active"</c:if> ><a href="${variableURL}" >${ lienPage}</a></li>
			  </c:forEach>
			  <c:url value="/index" var="variableURL">
				  			<c:choose>
							<c:when test="${page >= pageMax }">
								<c:param name="page" value="${page}"/>
							</c:when>
							<c:when test="${page < pageMax }">
								<c:param name="page" value="${page + 1 }"/>
							</c:when>
						</c:choose>
						<c:if test="${not empty requestScope['sw'].query }"><c:param name="search" value="${requestScope['sw'].query }"/></c:if>
						<c:if test="${not empty requestScope['sw'].orderCol }"><c:param name="order" value="${requestScope['sw'].orderCol }"/></c:if>
						<c:if test="${not empty requestScope['sw'].orderDirection }"><c:param name="direction" value="${requestScope['sw'].orderDirection }"/></c:if>
				</c:url>
			  <li <c:if test="${page >= pageMax }">class="disabled"</c:if>><a href="${variableURL }">&raquo;</a></li>
			</ul>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
		<table class="computers table table-striped table-hover table-bordered">
			<thead>
				<tr>
					<!-- Variable declarations for passing labels as parameters -->
					<!-- Table header for Computer Name -->
					<c:url value="/index" var="variableURL">
						<c:param name="page" value="${page}"/>
						<c:if test="${not empty requestScope['sw'].query }"><c:param name="search" value="${requestScope['sw'].query }"/></c:if>
						<c:param name="order" value="${orderName }"/>
						<c:choose>
							<c:when test="${requestScope['sw'].orderCol == orderName}">
								<c:param name="direction" value="${newDirection}"/>
							</c:when>
							<c:when test="${requestScope['sw'].orderCol != orderName}">
								<c:param name="direction" value="asc"/>
							</c:when>
						</c:choose>
					</c:url>
					<th>Computer Name
						<a href="${variableURL }">
						<button type="button" class="btn btn-default btn-md">
 							 <span class="glyphicon glyphicon-sort"></span> Order
						</button></a>
					</th>
					
					<c:url value="/index" var="variableURL">
						<c:param name="page" value="${page}"/>
						<c:if test="${not empty requestScope['sw'].query }"><c:param name="search" value="${requestScope['sw'].query }"/></c:if>
						<c:param name="order" value="${orderIntro }"/>
						<c:choose>
							<c:when test="${requestScope['sw'].orderCol == orderIntro}">
								<c:param name="direction" value="${newDirection}"/>
							</c:when>
							<c:when test="${requestScope['sw'].orderCol != orderIntro}">
								<c:param name="direction" value="asc"/>
							</c:when>
						</c:choose>
					</c:url>
					<th>Introduced Date
						<a href="${variableURL }">
						<button type="button" class="btn btn-default btn-md">
 							 <span class="glyphicon glyphicon-sort"></span> Order
						</button></a></th>
					<!-- Table header for Discontinued Date -->
					
					<c:url value="/index" var="variableURL">
						<c:param name="page" value="${page}"/>
						<c:if test="${not empty requestScope['sw'].query }"><c:param name="search" value="${requestScope['sw'].query }"/></c:if>
						<c:param name="order" value="${orderDisc }"/>
						<c:choose>
							<c:when test="${requestScope['sw'].orderCol == orderDisc}">
								<c:param name="direction" value="${newDirection}"/>
							</c:when>
							<c:when test="${requestScope['sw'].orderCol != orderDisc}">
								<c:param name="direction" value="asc"/>
							</c:when>
						</c:choose>
					</c:url>
					<th>Discontinued Date
						<a href="${variableURL }">
						<button type="button" class="btn btn-default btn-md">
 							 <span class="glyphicon glyphicon-sort"></span> Order
						</button></a></th>
					<!-- Table header for Company -->
					
					<c:url value="/index" var="variableURL">
						<c:param name="page" value="${page}"/>
						<c:if test="${not empty requestScope['sw'].query }"><c:param name="search" value="${requestScope['sw'].query }"/></c:if>
						<c:param name="order" value="${orderCompany }"/>
						<c:choose>
							<c:when test="${requestScope['sw'].orderCol == orderCompany}">
								<c:param name="direction" value="${newDirection}"/>
							</c:when>
							<c:when test="${requestScope['sw'].orderCol != orderCompany}">
								<c:param name="direction" value="asc"/>
							</c:when>
						</c:choose>
					</c:url>
					<th>Company
						<a href="${variableURL }">
						<button type="button" class="btn btn-default btn-md">
 							 <span class="glyphicon glyphicon-sort"></span> Order
						</button></a></th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="computer" items="${requestScope['sw'].items}"  >
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
					<td>
					<a href="editComputer?id=${computer.id}" onclick=""><button class="btn btn-primary">Edit</button></a>
					<a href="deleteComputer?id=${computer.id}" ><button class="btn btn-danger">Delete</button></a></td>
				</tr>
				</c:forEach>				
			</tbody>
		</table>
	</div>
	</div>
	</c:if>
</div>

<jsp:include page="../include/footer.jsp" />
