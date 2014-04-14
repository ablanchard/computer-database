<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="form" value="${requestScope['form_attrs'] }"></c:set>
<c:set var="form_errors" value="${requestScope['form_errors'] }"></c:set>
<c:set var="companies" value="${requestScope['companies']}" ></c:set>

<script type="text/javascript">
$(document).ready(function(){

	function isDate(txtDate,dateName)
	{
	    var currVal = txtDate;
	    if(currVal == '')
	        return false;

	    var rxDatePattern = /<spring:message code="date.regex.javascript"/>/; //Declare Regex
	    var dtArray = currVal.match(rxDatePattern); // is format OK?


	    if (dtArray == null) {
			$("#errorMessage").html(dateName + ": "+"<spring:message code="error.invalid"/>")
	    	return false;
	    }

	    return true;
	}


	$("#addComputer").submit(function(){
		if($("#name").val() == ""){
			$("#name-group").addClass("has-error");
			$("#errorDiv").removeClass("hidden");
			$("#errorMessage").text("<spring:message code="error.required"/>")
			return false;
		} else{
			$("#name-group").removeClass("has-error");
			$("#errorDiv").addClass("hidden");
			$("#name-group").addClass("has-success");

		}

		if($("#introducedDate").val() != ""){
			if(!isDate($("#introducedDate").val(),"<spring:message code="${form.intro.title}"/>")){
				$("#intro-group").addClass("has-error");
				$("#errorDiv").removeClass("hidden");
				return false;
			}else{
				$("#intro-group").removeClass("has-error");
				$("#intro-group").addClass("has-success");
				$("#errorDiv").addClass("hidden");
			}
		}

		if($("#discontinuedDate").val() != ""){
			if(!isDate($("#discontinuedDate").val(),"<spring:message code="${form.disc.title}"/>")){
				$("#discon-group").addClass("has-error");
				$("#errorDiv").removeClass("hidden");
				return false;
			}else{
				$("#discon-group").removeClass("has-error");
				$("#discon-group").addClass("has-success");
				$("#errorDiv").addClass("hidden");
			}
		}

		return true;
	});
});
</script>
<div class="alert alert-danger hidden" id="errorDiv">
	<p id="errorMessage"></p>
</div>