<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FlatAccount :: Expense</title>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<center>
<form:form id="ownerForm" action="#" method="POST">
<table class="formtable" cellspacing="0" >
<tr>
<td colspan="2" bgcolor="#848484" align="center"><font color="white">Owner Detail</font></td>
</tr>
<tr>
<td>Owner Name</td>
<td><form:input path="owner_name" placeholder="Owner Name" id="owner_name" type="text" /><form:input id="owner_id" type="hidden" path="owner_id"/></td>
</tr>
<tr>
<td>Flat Number</td>
<td><form:input path="flat_number" placeholder="Flat #" id="flat_number" type="text" /></td>
</tr>
<tr>
<td>Floor</td>
<td><form:select path="floor" items="${floorList}" /></td>
</tr>
<tr>
<td>Active</td>
<td><form:checkbox path="is_active"></form:checkbox></td>
</tr>
<tr>
<td colspan="2" align="center"><input type="submit" id="submit_btn" value="Save"/></td>
</tr>
</table>
</form:form>
<br></br>

<div id="resultcontainer" class="ui-state-highlight ui-corner-all" style="display:none;margin-top: 20px; padding: 0 .7em;">
		<p><span id="resulticon" class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
		<span id="result"></span></p>
</div>
</center>
<script>
    
  $( "#ownerForm" ).submit(function( event ) {
	  var delay_time=2000;
	// Stop form from submitting normally
	event.preventDefault();
	// Get some values from elements on the page:
	var $form = $( this ),
	term = $form.find( "input[name='s']" ).val(),
	url = '${pageContext.request.contextPath}/saveowner';
	// Send the data using post
	var postData = $form.serialize();
	var posting = $.post( url, postData );
	// Put the results in a div
	posting.done(function( data ) {
		if(data.split('|')[0]=='true'){
			$('#resultcontainer').addClass('ui-state-highlight ui-corner-all');
			$('#resulticon').addClass('ui-icon ui-icon-info');
			$( "#result" ).empty().append('Successfully Created/Updated owner with ID : '+ data.split('|')[1]);
			$('#resultcontainer').show();
			if($('#owner_id').val()==-1){
				$('#ownerForm')[0].reset();
			}
		}else{
			$('#resultcontainer').addClass('ui-state-error ui-corner-all');
			$('#resulticon').addClass('ui-icon ui-icon-alert');
			$( "#result" ).empty().append('Could not create/update Owner ! Please check your inputs.');
			$('#resultcontainer').show();
		}
		$('#resultcontainer').delay(delay_time).fadeOut();
		//$('#resulticon').delay(delay_time).fadeOut();
		//$( "#result" ).delay(delay_time).fadeOut();
	
	});
	});
  </script>
</body>
</html>