<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cl" uri="/WEB-INF/taglib/computerLib.tld" %>
<%@ attribute name="page" required="true" type="java.lang.Integer"%>
<%@ attribute name="pageMax" required="true" type="java.lang.Integer"%>
<%@ attribute name="query" type="java.lang.String" %>
<%@ attribute name="orderCol" type="java.lang.String" %>
<%@ attribute name="orderDirection" type="java.lang.String" %>
<%@ attribute name="context" type="java.lang.String" %>

<c:if test="${empty page}">
	<c:set var="page" value="0"></c:set>
</c:if>
<c:if test="${page < 0}">
	<c:set var="page" value="0"></c:set>
</c:if>
<c:if test="${page >= pageMax}">
	<c:set var="page" value="${pageMax }"></c:set>
</c:if>
<c:set var="prev" value="${page-1}"/>
<c:set var="next" value="${page+1}"/>

<ul class="pagination">
  <li <c:if test="${page  == 0 }">
      <c:set var="prev" value="${page}"/>
      class="disabled"</c:if> >
  	<cl:link page="${prev }" query="${query }" orderCol="${orderCol }" orderDirection="${orderDirection }">
  		&laquo;
  	</cl:link>
  </li>
  <c:forEach var="lienPage" begin="1" end="${ pageMax}">
  	<li <c:if test="${lienPage -1 == page }">class="active"</c:if> >
	  	<cl:link  page="${lienPage-1 }" query="${query }" orderCol="${orderCol }" orderDirection="${orderDirection }">
	  		${ lienPage }
	  	</cl:link>
  	</li>
  </c:forEach>
	<li <c:if test="${page >= pageMax -1 }">
			<c:set var="next" value="${page}"></c:set>
			class="disabled"
		</c:if>>
	  <cl:link  page="${next  }" query="${query }" orderCol="${orderCol }" orderDirection="${orderDirection }">
		   &raquo;
	  </cl:link>
	</li>
</ul>