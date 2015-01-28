<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FlatAccount :: Deposit</title>
</head>
<body>
<center>
<form:form id="depositForm" action="#" method="POST">
<table class="formtable" cellspacing="0" >
<tr>
<td colspan="2" bgcolor="#848484" align="center"><font color="white">Deposit Entry</font></td>
</tr>
<tr>
<td>Account</td>
<td><input id="owner_name" type="text" /><form:input type="hidden" path="owner_id"/></td>
</tr>
<tr>
<td>Transaction Date</td>
<td><form:input id="trtx_date" path="trtx_date" type="text" placeholder="Transaction Date" /></td>
</tr>
<tr>
<td>Reason</td>
<td><form:select path="trtx_reason" items="${reasonList}" /></td>
</tr>
<tr>
<td>Amount</td>
<td><form:input id="trtx_amount" type="text" path="trtx_amount" placeholder="0.00"/></td>
</tr>
<tr>
<td>Transaction Comment</td>
<td><form:input id="trtx_comment" type="text" path="trtx_comment" placeholder="Your comment"/></td>
</tr>
<tr>
<td colspan="2" align="center"><input type="submit" id="submit_btn" value="Submit"/></td>
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
  $(document).ready(function() {
	  $('#trtx_date').datepicker({
		  dateFormat: 'dd-M-yy'  
	  });
	$('#owner_name').autocomplete({
		source: '${pageContext.request.contextPath}/getOwners',
		select: function( event, ui ) {
			$( "#owner_name" ).val(ui.item.owner_name);
			$("#owner_id").val(ui.item.owner_id);
			return false;
		}
	}) .autocomplete( "instance" )._renderItem = function( ul, item ) {
		return $( "<li>" )
		.append( item.owner_name + "(" + item.owner_id + ")" )
		.appendTo( ul );
		};
 	 
  });
  
  $( "#depositForm" ).submit(function( event ) {
	// Stop form from submitting normally
	event.preventDefault();
	// Get some values from elements on the page:
	var $form = $( this ),
	term = $form.find( "input[name='s']" ).val(),
	url = '${pageContext.request.contextPath}/submitdeposit';
	// Send the data using post
	var postData = $form.serialize();
	var posting = $.post( url, postData );
	// Put the results in a div
	posting.done(function( data ) {
		if(data.split('|')[0]=='true'){
			$('#resultcontainer').addClass('ui-state-highlight ui-corner-all');
			$('#resulticon').addClass('ui-icon ui-icon-info');
			$( "#result" ).empty().append('Successfully deposited with transaction ID : '+ data.split('|')[1]);
			$('#resultcontainer').show();
			$('#depositForm')[0].reset();
		}else{
			$('#resultcontainer').addClass('ui-state-error ui-corner-all');
			$('#resulticon').addClass('ui-icon ui-icon-alert');
			$( "#result" ).empty().append('Could not create deposit entry ! Please check your inputs.');
			$('#resultcontainer').show();
		}
		$('#resultcontainer').delay(2000).fadeOut();
	
	});
	});
  </script>
</body>
</html>