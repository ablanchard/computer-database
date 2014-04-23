<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cl" uri="/WEB-INF/taglib/computerLib.tld" %>
<%@ attribute name="page" required="true" type="java.lang.Integer"%>
<%@ attribute name="query" type="java.lang.String" %>
<%@ attribute name="colName" type="java.lang.String" %>
<%@ attribute name="actualDirection" type="java.lang.String" %>
<%@ attribute name="actualOrder" type="java.lang.String" %>
<%@ attribute name="context" type="java.lang.String" %>
<%@ attribute name="icon" type="java.lang.String" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="newDirection" value="ASC"></c:set>
<c:set var="cssClass" value="btn btn-default btn-md" />
<c:set var="buttonAlpha" value="glyphicon-sort-by-alphabet"/>
<c:set var="buttonNum" value="glyphicon-sort-by-order"/>
<c:set var="buttonIcon" value="${buttonAlpha }"/>
<c:if test="${icon == 'num' }">
	<c:set var="buttonIcon" value="${buttonNum }"/>
</c:if>

<c:if test="${actualDirection == newDirection}">
	<c:set var="newDirection" value="DESC"/>
	<c:if test="${colName == actualOrder}">
		<c:set var="buttonIcon" value="${buttonIcon }-alt"/>
	</c:if>
</c:if>
<c:choose>
	<c:when test="${colName == actualOrder}">
		<c:set var="direction" value="${newDirection}"/>
		<c:set var="cssClass" value="${cssClass } active" />
	</c:when>
	<c:when test="${colName != actualOrder}">
		<c:set var="direction" value="ASC"/>
	</c:when>
</c:choose>
<cl:link page="${page }" query="${query }" orderCol="${colName }" orderDirection="${direction }"  >

	<button type="button" class="${cssClass}">
			 <span class="glyphicon ${buttonIcon }"></span> <spring:message code="order" text="Order" />
	</button></cl:link>
