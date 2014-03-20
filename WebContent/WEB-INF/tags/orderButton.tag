<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="page" required="true" type="java.lang.Integer"%>
<%@ attribute name="query" type="java.lang.String" %>
<%@ attribute name="colName" type="java.lang.String" %>
<%@ attribute name="actualDirection" type="java.lang.String" %>
<%@ attribute name="actualOrder" type="java.lang.String" %>

<c:set var="newDirection" value="asc"></c:set>
<c:if test="${actualDirection == newDirection}">
	<c:set var="newDirection" value="desc"></c:set>
</c:if>
<c:url value="/index" var="variableURL">
	<c:param name="page" value="${page}"/>
	<c:if test="${not empty query }">
		<c:param name="search" value="${query }"/>
	</c:if>
	<c:param name="order" value="${colName }"/>
	<c:choose>
		<c:when test="${colName == actualOrder}">
			<c:param name="direction" value="${newDirection}"/>
		</c:when>
		<c:when test="${colName != actualOrder}">
			<c:param name="direction" value="asc"/>
		</c:when>
	</c:choose>
</c:url>
<a href="${variableURL }">
	<button type="button" class="btn btn-default btn-md">
			 <span class="glyphicon glyphicon-sort"></span> Order
	</button>
</a>