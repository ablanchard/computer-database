<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="page" required="true" type="java.lang.Integer"%>
<%@ attribute name="value" type="java.lang.String"%>
<%@ attribute name="query" type="java.lang.String" %>
<%@ attribute name="orderCol" type="java.lang.String" %>
<%@ attribute name="orderDirection" type="java.lang.String" %>
<%@ attribute name="context" type="java.lang.String" %>

<c:if test="${empty value }"><c:set var="value" value="${context }/index"></c:set></c:if>
<c:url value="${value }" var="variableURL">
		<c:if test="${not empty page }"><c:param name="page" value="${page}"/></c:if>
		<c:if test="${not empty query }"><c:param name="search" value="${query}"/></c:if>
		<c:if test="${not empty orderCol }"><c:param name="order" value="${orderCol }"/></c:if>
		<c:if test="${not empty orderDirection }"><c:param name="direction" value="${orderDirection }"/></c:if>
</c:url>
<a href="${variableURL }"><jsp:doBody/></a>