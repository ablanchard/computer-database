<jsp:include page="include/header.jsp" />

<%@page import="java.util.List"%>
<%@page import="com.excilys.data.Company"%>
<%@page import="com.excilys.data.Computer"%>
<%@page import="java.text.SimpleDateFormat"%>
<% List<Company> companies = (List<Company>)request.getAttribute("companies"); 
Computer computer = (Computer) request.getAttribute("computer");
int companyId ;%>
<section id="main">

	<h1>Edit Computer</h1>
	
	<form action="editComputer" method="POST">
		<fieldset>
			<div class="clearfix">
				<label for="name">Computer name:</label>
				<div class="input">
					<input type="text" name="name" value="<%=computer.getName() %>"  id="name"/>
					<span class="help-inline">Required</span>
				</div>
				<input type="hidden" value="<%=computer.getId() %>" name="id"/>
			</div>
	
			<div class="clearfix">
				<label for="introduced">Introduced date:</label>
				<div class="input">
					<input type="date" name="introducedDate"  value="<% if(computer.getIntroduction() !=null) { %><%= new SimpleDateFormat("yyyy-MM-dd").format(computer.getIntroduction()) %><% }%>" id="introducedDate" pattern="YYYY-MM-dd"/>
					<span class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="discontinued">Discontinued date:</label>
				<div class="input">
					<input type="date" name="discontinuedDate" value="<% if(computer.getDiscontinued() !=null) { %><%= new SimpleDateFormat("yyyy-MM-dd").format(computer.getDiscontinued()) %><% } %>" id="discontinuedDate" pattern="YYYY-MM-dd"/>
					<span class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="company">Company Name:</label>
				<div class="input">
					<select name="company" id="company">
						<option value="0" <% if(computer.getCompany() == null) { companyId = 0;%> selected="selected"<% } else {companyId = computer.getCompany().getId(); } %>>--</option>
						<% for(Company c : companies){ %>
						<option value="<%=c.getId() %>" <%if(c.getId()==companyId){ %>selected="selected"<% } %>><%=c.getName() %></option>
						<% } %>
					</select>
				</div>
			</div>
		</fieldset>
		<div class="actions">
			<button type="submit"  class="btn primary">Edit</button>
			or <a href="index" class="btn">Cancel</a>
		</div>
	</form>
</section>

<jsp:include page="include/footer.jsp" />