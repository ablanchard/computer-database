<jsp:include page="include/header.jsp" />
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.excilys.data.Computer"%>
<%@page import="com.excilys.data.Company"%>
<% List<Computer> computers = (List<Computer>)request.getAttribute("computers"); %>
<section id="main">
	<h1 id="homeTitle"><%=computers.size() %> Computers found</h1>
	<div id="actions">
		<form action="" method="POST">
			<input type="search" id="searchbox" name="search"
				value="" placeholder="Search name">
			<input type="submit" id="searchsubmit"
				value="Filter by name"
				class="btn primary">
		</form>
		<a class="btn success" id="add" href="addComputer">Add Computer</a>
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
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<% for(Computer p : computers){ %>
				<tr>
					<td><a href="editComputer?id=<%=p.getId() %>" onclick=""><%= p.getName() %></a></td>
					<td>
					<% if(p.getIntroduction() != null) { %>
					<%= new SimpleDateFormat("yyyy-MM-dd").format(p.getIntroduction()) %>
					<% } else { %>
					null
					<% } %>
					</td>
					<td>
					<% if(p.getDiscontinued() != null) { %>
					<%=new SimpleDateFormat("yyyy-MM-dd").format(p.getDiscontinued()) %>
					<% } else { %>
					null
					<% } %>
					</td>
					<td><% if(p.getCompany() != null) { %>
						<%= p.getCompany().getName() %>
						<% } else { %>
						No Company
						<% } %>					
					</td>
					<td><a href="deleteComputer?id=<%=p.getId() %>" class="btn danger">Delete</a></td>
				</tr>
				<% } %>
			</tbody>
		</table>
</section>

<jsp:include page="include/footer.jsp" />
