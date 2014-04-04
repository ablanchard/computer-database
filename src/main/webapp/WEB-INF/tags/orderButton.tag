<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cl" uri="/WEB-INF/taglib/computerLib.tld" %>
<%@ attribute name="page" required="true" type="java.lang.Integer"%>
<%@ attribute name="query" type="java.lang.String" %>
<%@ attribute name="colName" type="java.lang.String" %>
<%@ attribute name="actualDirection" type="java.lang.String" %>
<%@ attribute name="actualOrder" type="java.lang.String" %>
<%@ attribute name="context" type="java.lang.String" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="newDirection" value="asc"></c:set>
<c:if test="${actualDirection == newDirection}">
	<c:set var="newDirection" value="desc"></c:set>
</c:if>
<c:choose>
	<c:when test="${colName == actualOrder}">
		<c:set var="direction" value="${newDirection}"/>
	</c:when>
	<c:when test="${colName != actualOrder}">
		<c:set var="direction" value="asc"/>
	</c:when>
</c:choose>
<cl:link page="${page }" query="${query }" orderCol="${colName }" orderDirection="${direction }"  >

	<button type="button" class="btn btn-default btn-md">
			 <span class="glyphicon glyphicon-sort"></span> <spring:message code="order" text="Order" />
	</button></cl:link>
