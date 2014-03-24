<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

	    var rxDatePattern = /^(\d{4})[\/\-](\d{1,2})[\/\-](\d{1,2})$/; //Declare Regex
	    var dtArray = currVal.match(rxDatePattern); // is format OK?


	    if (dtArray == null) {
			$("#errorMessage").html(dateName + ": ${form_errors['invalidFormat']}")
	    	return false;
	    }

	    //Checks for mm/dd/yyyy format.
	    dtMonth = dtArray[2];
	    dtDay= dtArray[3];
	    dtYear = dtArray[1];    

	    console.log(dtDay + " "+ dtMonth + " " + dtYear);    

	    if (dtMonth < 1 || dtMonth > 12){
			$("#errorMessage").html(dateName + ": ${form_errors['noMonth']}")
	        return false;
	    }
	    else if (dtDay < 1 || dtDay> 31) {
			$("#errorMessage").html(dateName + ": ${form_errors['noDay']}")
	        return false;
	    }
	    else if ((dtMonth==4 || dtMonth==6 || dtMonth==9 || dtMonth==11) && dtDay ==31) {

			$("#errorMessage").html(dateName + ": ${form_errors['noDayInMonth']}")	
	    
	        return false;
	    }
	    else if (dtMonth == 2) 
	    {
	        var isleap = (dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0));
	        if (dtDay> 29 || (dtDay ==29 && !isleap)) {

				$("#errorMessage").html(dateName + ": ${form_errors['noDayInFeb']}")	
	                return false;
	        }
	    }
	    return true;
	}


	$("#addComputer").submit(function(){
		if($("#name").val() == ""){
			$("#name-group").addClass("has-error");
			$("#errorDiv").removeClass("hidden");
			$("#errorMessage").text("${form_errors['emptyName']}")
			return false;
		} else{
			$("#name-group").removeClass("has-error");
			$("#errorDiv").addClass("hidden");
			$("#name-group").addClass("has-success");

		}

		if($("#introducedDate").val() != ""){
			if(!isDate($("#introducedDate").val(),"${form.intro.title}")){
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
			if(!isDate($("#discontinuedDate").val(),"${form.disc.title}")){
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