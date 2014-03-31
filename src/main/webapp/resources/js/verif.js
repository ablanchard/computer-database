$(document).ready(function(){

	function isDate(txtDate)
	{
	    var currVal = txtDate;
	    if(currVal == '')
	        return false;

	    var rxDatePattern = /^(\d{4})[\/\-](\d{1,2})[\/\-](\d{1,2})$/; //Declare Regex
	    var dtArray = currVal.match(rxDatePattern); // is format OK?


	    if (dtArray == null) 
	        return false;

	    //Checks for mm/dd/yyyy format.
	    dtMonth = dtArray[2];
	    dtDay= dtArray[3];
	    dtYear = dtArray[1];    

	    console.log(dtDay + " "+ dtMonth + " " + dtYear)    

	    if (dtMonth < 1 || dtMonth > 12) 
	        return false;
	    else if (dtDay < 1 || dtDay> 31) 
	        return false;
	    else if ((dtMonth==4 || dtMonth==6 || dtMonth==9 || dtMonth==11) && dtDay ==31) 
	        return false;
	    else if (dtMonth == 2) 
	    {
	        var isleap = (dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0));
	        if (dtDay> 29 || (dtDay ==29 && !isleap)) 
	                return false;
	    }
	    return true;
	}


	$("#addComputer").submit(function(){
		if($("#name").val() == ""){
			$("#name-group").addClass("has-error");
			return false;
		} else{
			$("#name-group").removeClass("has-error");
			$("#name-group").addClass("has-success");

		}

		if($("#introducedDate").val() != ""){
			if(!isDate($("#introducedDate").val())){
				$("#intro-group").addClass("has-error");
				return false;
			}else{
				$("#intro-group").removeClass("has-error");
				$("#intro-group").addClass("has-success");
			}
		}

		if($("#discontinuedDate").val() != ""){
			if(!isDate($("#discontinuedDate").val())){
				$("#discon-group").addClass("has-error");
				return false;
			}else{
				$("#discon-group").removeClass("has-error");
				$("#discon-group").addClass("has-success");
			}
		}

		return true;
	});
});