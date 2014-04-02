
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page import="com.excilys.servlet.ComputerForm"%>
<%@page import="com.excilys.servlet.FormInput"%>
<c:set var="companyId" value="${dto.companyId }"></c:set>
<c:set var="form" value="${requestScope['form_attrs'] }"></c:set>
<c:set var="companies" value="${requestScope['companies']}" ></c:set>
<jsp:include page="verification.jsp" />
		<div class="col-md-12">
			<h1><c:out value="${requestScope['actionName'] }" default="Add"/> Computer</h1>
		</div>
	</div>
	<div class="row">
	<div class="col-md-8">
		<form:form method="post" action="insert" id="addComputer" modelAttribute="dto">
			<fieldset>
				<div class="row form-group" id="name-group">
					<div class="col-md-6">
						<form:label path="${form.name.name }" >${form.name.title }:</form:label>
						<div class="row input">
							<div class="col-md-6">
								<form:input type="text" class="form-control"  path="${form.name.name }"  id="name" value="${dto.name }"/>
								<span class="help-block">Required</span>
							</div>
							<div class="col-md-6">
								<div class="alert"><form:errors class="alert alert-danger" path="${form.name.name }"  /></div>
							</div>
						</div>
					</div>
				</div>
				<form:hidden  path="${form.id.name }" value="${dto.id}"/>
				<div class="row form-group" id="intro-group">
					<div class="col-md-6">
						<form:label path="${form.intro.name }">${form.intro.title }:</form:label>
						<div class="row input">
							<div class="col-md-6">
								<form:input class="form-control" type="date"  path="${form.intro.name }" value="${dto.introducedDate }"  id="introducedDate" />
								<span class="help-block">YYYY-MM-DD</span>
							</div>
							<div class="col-md-6">
								<div class="row alert" ><form:errors path="${form.intro.name }" class="alert alert-danger" /></div>
							</div>
						</div>
					</div>
				</div>
				<div class="row form-group" id="discon-group">
					<div class="col-md-6">
						<form:label path="${form.disc.name }">${form.disc.title }:</form:label>
						<div class="row input">
							<div class="col-md-6">
								<form:input class="form-control" type="date"  path="${form.disc.name }" value="${dto.discontinuedDate }" id="discontinuedDate" />
								<span class="help-block">YYYY-MM-DD</span>
							</div>
							<div class="col-md-6">
								<div class="row alert" ><form:errors path="${form.disc.name }" class="alert alert-danger" /></div>
							</div>
						</div>
					</div>
				</div>
				<div class="row form-group">
					<div class="col-md-6">
						<form:label path="${form.company.name }">${form.company.title }:</form:label>
						<div class="row input">
							<div class="col-md-6">
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
		<div class="actions">
			<button type="submit" id="submit" class="btn btn-primary"><c:out value="${requestScope['actionName'] }" default="Add"/></button>
			 or <a href="index" class="btn btn-danger">Cancel</a>
		</div>
</form:form>
</div>
	</div>