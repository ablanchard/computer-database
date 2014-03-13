
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="companyId" value="${computer.company.id }"></c:set>
<script type="text/javascript" src="js/verif.js"></script>

<form action="${requestScope['action'] }" id="addComputer" method="POST">
		<fieldset>
			<div class="form-group" id="name-group">
				<label for="name">Computer name:</label>
				<div class="input">
					<input type="text" class="form-control" name="name"  id="name" value="${computer.name }"/>
					<span class="help-block">Required</span>
				</div>
			</div>
			<input type="hidden" value="${computer.id }" name="id"/>
			<div class="form-group" id="intro-group">
				<label for="introduced">Introduced date:</label>
				<div class="input">
					<fmt:formatDate value="${computer.introduction }" pattern="yyyy-MM-dd" var="inDate"/>
					<input class="form-control"type="date" name="introducedDate" value="${inDate }"  id="introducedDate" />
					<span class="help-block">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="form-group" id="discon-group">
				<label for="discontinued">Discontinued date:</label>
				<div class="input">
					<fmt:formatDate value="${computer.discontinued }" pattern="yyyy-MM-dd" var="disDate"/>
					<input class="form-control"type="date" name="discontinuedDate" value="${disDate }" id="discontinuedDate" />
					<span class="help-block">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="form-group">
				<label for="company">Company Name:</label>
				<div class="input">
					<select class="form-control"name="company" id="company">
						<c:forEach var="company" items="${requestScope['companies']}" >
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