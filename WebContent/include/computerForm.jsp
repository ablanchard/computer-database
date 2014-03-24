
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="com.excilys.servlet.ComputerForm"%>
<%@page import="com.excilys.servlet.FormInput"%>
<c:set var="companyId" value="${dto.companyId }"></c:set>
<c:set var="form" value="${requestScope['form_attrs'] }"></c:set>
<c:set var="companies" value="${requestScope['companies']}" ></c:set>
<script type="text/javascript" src="js/verif.js"></script>

<form action="${requestScope['action'] }" id="addComputer" method="POST">
		<fieldset>
			<div class="form-group" id="name-group">
				<label for="name">${form.name.title }:</label>
				<div class="input">
					<input type="text" class="form-control" name="${form.name.name }"  id="name" value="${dto.name }"/>
					<span class="help-block">Required</span>
				</div>
			</div>
			<input type="hidden" value="${dto.id }" name="id"/>
			<div class="form-group" id="intro-group">
				<label for="introduced">${form.intro.title }:</label>
				<div class="input">
					<input class="form-control"type="date" name="${form.intro.name }" value="${dto.introducedDate }"  id="introducedDate" />
					<span class="help-block">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="form-group" id="discon-group">
				<label for="discontinued">${form.disc.title }:</label>
				<div class="input">
					<input class="form-control"type="date" name="${form.disc.name }" value="${dto.discontinuedDate }" id="discontinuedDate" />
					<span class="help-block">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="form-group">
				<label for="company">${form.company.title }:</label>
				<div class="input">
					<select class="form-control"name="${form.company.name }" id="company">
						<c:forEach var="company" items="${companies}" >
							<option value="${company.id }" <c:if test="${company.id == companyId }">selected="selected"</c:if>>${company.name }</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</fieldset>
		<div class="actions">
			<button type="submit" id="submit" class="btn btn-primary">${requestScope['actionName'] }</button>
			 or <a href="index" class="btn btn-danger">Cancel</a>
		</div>
	</form>