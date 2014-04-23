<jsp:include page="../include/header.jsp" />
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.excilys.data.Computer"%>
<%@page import="com.excilys.dto.ComputerDTO"%>
<%@page import="com.excilys.data.Company"%>
<%@page import="com.excilys.util.Header"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="cl" uri="/WEB-INF/taglib/computerLib.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<c:set var="nbElementsParPage" value="${computerPage.page.size }"></c:set>
<c:set var="page" value="${computerPage.page.number }"></c:set>
<c:set var="pageMax" value="${computerPage.page.totalPages }"></c:set>
<c:set var="sort" value="${computerPage.page.sort }"></c:set>
<c:set var="count" value="${computerPage.page.totalElements }"></c:set>
<c:set var="orderCol" value="${fn:substringBefore(sort,':')}"></c:set>
<c:set var="orderDirection" value="${fn:substringAfter(sort,': ' )}"></c:set>
<c:set var="placeholder"><spring:message code="filter.placeholder" text="Search name" /></c:set>
<c:set var="computerFound" value="computers.found.plurial"/>
<c:if test="${count <= 1 }"><c:set var="computerFound" value="computers.found.singular"/></c:if>

<div class="container-fluid" id="main">
	<div class="row">
	<c:if test="${not empty param.success }">
		<div class="alert alert-success"><p><spring:message code="${param.success }" text="Success" /></p></div>
	</c:if>
	<c:if test="${not empty param.error }">
		<div class="alert alert-danger"><p><spring:message code="${param.error }" text="Errors" /></p></div>
	</c:if>
	<div class="col-md-12">
	<h1 id="homeTitle"><c:out value="${count}"/> <spring:message code="${computerFound }" text="Computers found" /></h1>
	</div>
	</div>
	<div class="row" id="actions">
		<div class="col-md-10">
		<form:form method="get" action="" class="form-inline" modelAttribute="computerPage">
			<div class="form-group">
                <form:input type="search" class="form-control"  path="search"   value="${computerPage.search}" placeholder="${placeholder }" />
			</div>
			<button type="submit" id="searchsubmit"	class="btn btn-default"><spring:message code="filter" text="Filter by name" /></button>
		</form:form>
		</div>
		<div class="col-md-2">
			<a href="initAdd" class="btn btn-success pull-right" id="add" ><spring:message code="add.computer" text="Add Computer" /></a>
		</div>
	</div>
	<c:if test="${count != 0 }">
		<div class="row">
			<div class="col-md-12">
				<cl:pagination  page="${page }" pageMax="${pageMax }" query="${computerPage.search}" orderCol="${orderCol }" orderDirection="${ orderDirection }"></cl:pagination>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<table class="computers table table-striped table-hover table-bordered">
					<thead>
						<tr>
							<c:forEach var="tableHeader" items="${requestScope['tableHeaders']}"  >
								<th><spring:message code="${tableHeader.name }" text="Header" />
									<c:if test="${not empty tableHeader.orderName }">
										<cl:orderButton  icon="${tableHeader.icon }" page="${page }" query="${computerPage.search }" actualOrder="${orderCol }" actualDirection="${orderDirection }" colName="${tableHeader.orderName }"  ></cl:orderButton>
									</c:if>
								</th>
							</c:forEach>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="computer" items="${computerPage.page.content}"  >
							<tr>
								<td><a href="initEdit?id=${computer.id}" onclick="">${computer.name }</a></td>
								<td>${computer.introducedDate }</td>
								<td>${computer.discontinuedDate }</td>
								<td>${computer.companyName}</td>
								<td>
									<a href="initEdit?id=${computer.id}" onclick="">
										<button class="btn btn-primary"><spring:message code="edit" text="Edit" /></button>
									</a>
									<a href="delete?id=${computer.id}" >
										<button class="btn btn-danger"><spring:message code="delete" text="Delete" /></button>
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
