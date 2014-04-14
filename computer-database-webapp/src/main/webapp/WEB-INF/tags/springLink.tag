<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="page" required="true" type="java.lang.Integer"%>
<%@ attribute name="value" type="java.lang.String"%>
<%@ attribute name="query" type="java.lang.String" %>
<%@ attribute name="orderCol" type="java.lang.String" %>
<%@ attribute name="orderDirection" type="java.lang.String" %>
<%@ attribute name="context" type="java.lang.String" %>


<c:if test="${empty value }"><c:set var="value" value="${context }/index/"/></c:if>
<c:set var="url" value="${value}${page }"/>

<c:if test="${not empty query }"><c:set var="url" value="${url};search=${query }"></c:set></c:if>
<c:if test="${not empty orderCol }">
	<c:set var="url" value="${url};order=${orderCol }"/>
</c:if>

<c:if test="${not empty orderDirection }">
	<c:set var="url" value="${url};direction=${orderDirection }"></c:set>
</c:if>


<a href="${url }"><jsp:doBody/></a>