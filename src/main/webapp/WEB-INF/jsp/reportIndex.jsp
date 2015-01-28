<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FlatAccount :: Reports</title>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<center>
<table class="formtable" cellspacing="0" >
<tr>
<td colspan="1" bgcolor="#848484" align="center"><font color="white">Reports</font></td>
</tr>
<tr>
<td><a href="javascript:getOwnersReport()">Accounts</a></td>
</tr>
<tr>
<td><a href="javascript:getUsersReport()">Users</a></td>
</tr>
<tr>
<td><a href="javascript:getReasonsReport()">Reasons</a></td>
</tr>
<tr>
<td><a href="javascript:getCreditReport()">Deposits</a></td>
</tr>
<tr>
<td><a href="javascript:getDebitReport()">Expenses</a></td>
</tr>
<tr>
<td><a href="javascript:getOwnerReport()">Account Statement</a></td>
</tr>
</table>
<br></br>

<div id="resultcontainer" class="ui-state-highlight ui-corner-all" style="display:none;margin-top: 20px; padding: 0 .7em;">
		<p><span id="resulticon" class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
		<span id="result"></span></p>
</div>
</center>
<script>
function getOwnersReport(){
	$.get( "${contextPath}/getAllOwners", function( data ) {
		$('#container').html(data);
		});
}

function getUsersReport(){
	$.get( "${contextPath}/getAllUsers", function( data ) {
		$('#container').html(data);
		});
}

function getReasonsReport(){
	$.get( "${contextPath}/getAllReasons", function( data ) {
		$('#container').html(data);
		});
}

function getCreditReport(){
	$.get( "${contextPath}/showcredittxui", function( data ) {
		$('#container').html(data);
		});
}

function getDebitReport(){
	$.get( "${contextPath}/showdebittxui", function( data ) {
		$('#container').html(data);
		});
}

function getOwnerReport(){
	$.get( "${contextPath}/showownertxui", function( data ) {
		$('#container').html(data);
		});
}

  </script>
</body>
</html>