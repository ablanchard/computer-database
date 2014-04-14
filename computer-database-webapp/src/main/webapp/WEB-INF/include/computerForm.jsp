
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page import="com.excilys.data.ComputerForm"%>
<%@page import="com.excilys.data.FormInput"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="companyId" value="${dto.companyId }"></c:set>
<c:set var="form" value="${requestScope['form_attrs'] }"></c:set>
<c:set var="companies" value="${requestScope['companies']}" ></c:set>
<jsp:include page="verification.jsp" />
	<fieldset>
	<div class="row form-group" id="name-group">
		<div class="col-md-12">
			<form:label path="${form.name.name }" ><spring:message code="${form.name.title }" text="Computer name" />:</form:label>
			<div class="row input">
				<div class="col-md-4">
					<form:input type="text" class="form-control"  path="${form.name.name }"  id="name" value="${dto.name }"/>
					<span class="help-block"><spring:message code="required" text="Required" /></span>
				</div>
				<div class="col-md-8">
					<div class="alert"><form:errors class="alert alert-danger" path="${form.name.name }"  /></div>
				</div>
			</div>
		</div>
	</div>
	<form:hidden  path="${form.id.name }" value="${dto.id}"/>
	<div class="row form-group" id="intro-group">
		<div class="col-md-12">
			<form:label path="${form.intro.name }"><spring:message code="${form.intro.title }" text="Introducted date" />:</form:label>
			<div class="row input">
				<div class="col-md-4">
					<form:input class="form-control" type="date"  path="${form.intro.name }" value="${dto.introducedDate }"  id="introducedDate" />
					<span class="help-block"><spring:message code="date.helper" text="YYYY-MM-DD" /></span>
				</div>
				<div class="col-md-8">
					<div class="row alert" ><form:errors path="${form.intro.name }" class="alert alert-danger" /></div>
				</div>
			</div>
		</div>
	</div>
	<div class="row form-group" id="discon-group">
		<div class="col-md-12">
			<form:label path="${form.disc.name }"><spring:message code="${form.disc.title }" text="Discontinued date" />:</form:label>
			<div class="row input">
				<div class="col-md-4">
					<form:input class="form-control" type="date"  path="${form.disc.name }" value="${dto.discontinuedDate }" id="discontinuedDate" />
					<span class="help-block"><spring:message code="date.helper" text="YYYY-MM-DD" /></span>
				</div>
				<div class="col-md-8">
					<div class="row alert" ><form:errors path="${form.disc.name }" class="alert alert-danger" /></div>
				</div>
			</div>
		</div>
	</div>
	<div class="row form-group">
		<div class="col-md-12">
			<form:label path="${form.company.name }"><spring:message code="${form.company.title }" text="Company name" />:</form:label>
			<div class="row input">
				<div class="col-md-4">
					<form:select class="form-control" path="${form.company.name }" id="company">
						<c:forEach var="company" items="${companies}" >
							<option value="${company.id }" <c:if test="${company.id == companyId }">selected="selected"</c:if>>${company.name }</option>
						</c:forEach>
					</form:select>
				</div>
			</div>
		</div>
	</div>
</fieldset>