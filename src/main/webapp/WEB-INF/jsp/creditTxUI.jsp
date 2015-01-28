<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FlatAccount :: Tx</title>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<center>
<form id="txForm" action="#" method="POST">
<input name="trtxType" id="trtxType" type="hidden" value="${trtx_type}"/>
<input name="ownerId" id="ownerId" type="hidden" value="-1"/>
<table class="formtable" cellspacing="0">
<tr>
<td colspan="2" bgcolor="#848484" align="center"><font color="white">Transactions Parameters</font></td>
</tr>
<tr>
<td>Start Date</td>
<td><input name="startDate" id="startDate" type="text" /></td>
</tr>
<tr>
<td>End Date</td>
<td><input name="endDate" id="endDate" type="text" /></td>
</tr>
<tr>
<td colspan="2" align="center"><input type="submit" id="submit_btn" value="Get"/></td>
</tr>
</table>
</form>
<br></br>

<div id="resultcontainer" style="min-width: 700px" >
</div>
</center>
<script>
$(document).ready(function() {
	  $('#startDate').datepicker({
		  dateFormat: 'dd-M-yy'  
	  });
	  $('#endDate').datepicker({
		  dateFormat: 'dd-M-yy'  
	  });
	  $('#owner_name').keyup(function(){
		  if($('#owner_name').val().length==0){
			  $('#owner_id').val(-1);
		  }
	  });
});
  $( "#txForm" ).submit(function( event ) {
	  var delay_time=2000;
	// Stop form from submitting normally
	event.preventDefault();
	// Get some values from elements on the page:
	var $form = $( this ),
	term = $form.find( "input[name='s']" ).val(),
	url = '${pageContext.request.contextPath}/getAllTransactions';
	// Send the data using post
	var postData = $form.serialize();
	var posting = $.post( url, postData );
	// Put the results in a div
	posting.done(function( data ) {
		$( "#resultcontainer" ).empty().append(data);
	});
	});
  </script>
</body>
</html>