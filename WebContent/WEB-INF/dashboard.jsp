<jsp:include page="../include/header.jsp" />
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.excilys.data.Computer"%>
<%@page import="com.excilys.dto.ComputerDTO"%>
<%@page import="com.excilys.data.Company"%>
<%@page import="com.excilys.servlet.Header"%>
<%@page import="com.excilys.dao.ComputerDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="cl" uri="/WEB-INF/taglib/computerLib.tld" %>

<c:set var="nbElementsParPage" value="${requestScope['sw'].nbComputersPerPage }"></c:set>
<c:set var="page" value="${requestScope['sw'].page }"></c:set>
<c:set var="pageMax" value="${requestScope['sw'].pageMax }"></c:set>
<c:set var="orderCol" value="${requestScope['sw'].orderCol }"></c:set>
<c:set var="orderDirection" value="${requestScope['sw'].orderDirection }"></c:set>
<c:set var="query" value="${requestScope['sw'].query }"></c:set>
<c:set var="count" value="${requestScope['sw'].count }"></c:set>


<div class="container-fluid" id="main">
	<div class="row">
	<c:if test="${not empty requestScope['success'] }">
		<div class="alert alert-success"><p><c:out value="${requestScope['success'] }"></c:out></p></div>
	</c:if>
	<c:if test="${not empty requestScope['error'] }">
		<div class="alert alert-danger"><p><c:out value="${requestScope['error'] }"></c:out></p></div>
	</c:if>
	<div class="col-md-12">
	<h1 id="homeTitle"><c:out value="${count}"/> Computers found</h1>
	</div>
	</div>
	<div class="row" id="actions">
		<div class="col-md-12">
		<form role="form" class="form-inline" action="index" method="GET">
			<div class="form-group">
				<label class="sr-only" for="searchbox">Search</label>
				<input type="search" id="searchbox" name="search" value="" placeholder="Search name" />
			</div>	
			<button type="submit" id="searchsubmit"	class="btn btn-default">Filter by name</button>
		</form>
		<a href="addComputer" class="btn btn-success" id="add" >Add Computer</a>
		</div>
	</div>
	<c:if test="${count != 0 }">
		<div class="row">
			<div class="col-md-12">
				<cl:pagination page="${page }" pageMax="${pageMax }" query="${query}" orderCol="${orderCol }" orderDirection="${ orderDirection }"></cl:pagination>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<table class="computers table table-striped table-hover table-bordered">
					<thead>
						<tr>
							<c:forEach var="tableHeader" items="${requestScope['tableHeaders']}"  >
								<th>${tableHeader.name }
									<c:if test="${not empty tableHeader.orderName }">
										<cl:orderButton page="${page }" query="${query }" actualOrder="${orderCol }" actualDirection="${orderDirection }" colName="${tableHeader.orderName }"  ></cl:orderButton>
									</c:if>
								</th>
							</c:forEach>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="computer" items="${requestScope['sw'].items}"  >
							<tr>
								<td><a href="editComputer?id=${computer.id}" onclick="">${computer.name }</a></td>
								<td>${computer.introducedDate }</td>
								<td>${computer.discontinuedDate }</td>
								<td>${computer.companyName}</td>
								<td>
									<a href="editComputer?id=${computer.id}" onclick="">
										<button class="btn btn-primary">Edit</button>
									</a>
									<a href="deleteComputer?id=${computer.id}" >
										<button class="btn btn-danger">Delete</button>
									</a>
								</td>
							</tr>
						</c:forEach>				
					</tbody>
				</table>
			</div>
		</div>
	</c:if>
</div>

<jsp:include page="../include/footer.jsp" />
