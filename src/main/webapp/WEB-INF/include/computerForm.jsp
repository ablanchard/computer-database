
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page import="com.excilys.servlet.ComputerForm"%>
<%@page import="com.excilys.servlet.FormInput"%>
<c:set var="companyId" value="${dto.companyId }"></c:set>
<c:set var="form" value="${requestScope['form_attrs'] }"></c:set>
<c:set var="companies" value="${requestScope['companies']}" ></c:set>
<jsp:include page="verification.jsp" />
<h1><c:out value="${requestScope['actionName'] }" default="Add"/> Computer</h1>
	</div>
	<div class="row">
	<div class="col-md-4">
<form:form method="post" action="insert" id="addComputer" modelAttribute="dto">

		<fieldset>
			<div class="form-group" id="name-group">
				<form:label path="${form.name.name }" >${form.name.title }:</form:label>
				<div class="input">
					<form:input type="text" class="form-control"  path="${form.name.name }"  id="name" value="${dto.name }"/>
					<span class="help-block">Required</span>
				</div>
			</div>
			<form:input type="hidden"  path="${form.id.name }" value="${dto.id}"/>
			<div class="form-group" id="intro-group">
				<form:label path="${form.intro.name }">${form.intro.title }:</form:label>
				<div class="input">
					<form:input class="form-control" type="date"  path="${form.intro.name }" value="${dto.introducedDate }"  id="introducedDate" />
					<span class="help-block">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="form-group" id="discon-group">
				<form:label path="${form.disc.name }">${form.disc.title }:</form:label>
				<div class="input">
					<form:input class="form-control" type="date"  path="${form.disc.name }" value="${dto.discontinuedDate }" id="discontinuedDate" />
					<span class="help-block">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="form-group">
				<form:label path="${form.company.name }">${form.company.title }:</form:label>
				<div class="input">
					<form:select class="form-control" path="${form.company.name }" id="company">
						<c:forEach var="company" items="${companies}" >
							<option value="${company.id }" <c:if test="${company.id == companyId }">selected="selected"</c:if>>${company.name }</option>
						</c:forEach>
					</form:select>
				</div>
			</div>
		</fieldset>
		<div class="actions">
			<button type="submit" id="submit" class="btn btn-primary"><c:out value="${requestScope['actionName'] }" default="Add"/></button>
			 or <a href="index" class="btn btn-danger">Cancel</a>
		</div>
</form:form>
</div>
	</div>