<jsp:include page="include/header.jsp" />
<%@page import="java.util.List"%>
<%@page import="com.excilys.data.Computer"%>
<%@page import="com.excilys.data.Company"%>
<% List<Computer> computers = (List<Computer>)request.getAttribute("computers"); %>
<section id="main">
	<h1 id="homeTitle">456 Computers found</h1>
	<div id="actions">
		<form action="" method="POST">
			<input type="search" id="searchbox" name="search"
				value="" placeholder="Search name">
			<input type="submit" id="searchsubmit"
				value="Filter by name"
				class="btn primary">
		</form>
		<a class="btn success" id="add" href="addComputer.jsp">Add Computer</a>
	</div>

		<table class="computers zebra-striped">
			<thead>
				<tr>
					<!-- Variable declarations for passing labels as parameters -->
					<!-- Table header for Computer Name -->
					<th>Computer Name</th>
					<th>Introduced Date</th>
					<!-- Table header for Discontinued Date -->
					<th>Discontinued Date</th>
					<!-- Table header for Company -->
					<th>Company</th>
				</tr>
			</thead>
			<tbody>
				<% for(Computer p : computers){ %>
				<tr>
					<td><a href="#" onclick=""><%= p.getName() %></a></td>
					<td><%= p.getIntroduction() %></td>
					<td><%= p.getDiscontinued() %></td>
					<td><% if(p.getCompany() != null) { %>
						<%= p.getCompany().getName() %>
						<% } else { %>
						No Company
						<% } %>					
					</td>
				</tr>
				<% } %>
			</tbody>
		</table>
</section>

<jsp:include page="include/footer.jsp" />
