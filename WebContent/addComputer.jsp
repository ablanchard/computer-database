<jsp:include page="include/header.jsp" />

<%@page import="java.util.List"%>
<%@page import="com.excilys.data.Company"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% List<Company> companies = (List<Company>)request.getAttribute("companies"); %>
<div class="container-fluid" id="main">
	<div class="row">
	<h1>Add Computer</h1>
	</div>
	<div class="row">
	<div class="col-md-4">
	<form action="addComputer" method="POST">
		<fieldset>
			<div class="form-group">
				<label for="name">Computer name:</label>
				<div class="input">
					<input type="text" class="form-control" name="name"  id="name"/>
					<span class="help-block">Required</span>
				</div>
			</div>
	
			<div class="form-group">
				<label for="introduced">Introduced date:</label>
				<div class="input">
					<input type="date"  class="form-control"name="introducedDate"  id="introducedDate" pattern="YY-MM-dd"/>
					<span class="help-block">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="form-group">
				<label for="discontinued">Discontinued date:</label>
				<div class="input">
					<input type="date"  class="form-control"name="discontinuedDate" id="discontinuedDate" pattern="YY-MM-dd"/>
					<span class="help-block">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="form-group">
				<label for="company">Company Name:</label>
				<div class="input">
					<select name="company" class="form-control" id="company">
						<c:forEach var="company" items="${requestScope['companies']}" >
							<option value="${company.id }">${company.name }</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</fieldset>
		<div class="actions">
			<button type="submit" class="btn btn-primary">Add</button>
			 or <a href="index"><button class="btn btn-danger">Cancel</button></a>
		</div>
	</form>
	</div>
	</div>
</div>

<jsp:include page="include/footer.jsp" />