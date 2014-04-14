<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="title" text="EPF Computer Database" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet" media="screen">
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" media="screen">
<script src="<c:url value="/resources/js/jquery-1.11.0.min.js" />" type="text/javascript" ></script>
</head>
<body>
	<nav class="navbar navbar-default" role="navigation">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      
      <a class="navbar-brand" href="reset"><spring:message code="computer.database" text="Application - Computer Database" /></a>
      
      <ul class="nav navbar-nav navbar-right">
        <li <c:if test="${pageContext.response.locale == 'fr'}">class="active"</c:if>><a href="?language=fr">Français</a></li>
        <li <c:if test="${pageContext.response.locale == 'en'}">class="active"</c:if>><a href="?language=en">English</a></li>
      </ul>
    </div>
</div>
</nav>
	