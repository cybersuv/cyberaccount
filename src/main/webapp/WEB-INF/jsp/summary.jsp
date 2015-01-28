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
<table class="formtable" cellspacing="1">
<tr>
<td colspan="2" bgcolor="#848484" align="center"><font color="white">Account Summary</font></td>
</tr>
<tr>
<td>Current Balance</td>
<td align="right"><span id="cur_bal">${summary.currentBalance}</span></td>
</tr>
<tr>
<td>Current Deposit</td>
<td align="right"><span id="cur_deposit">${summary.currentMonthDeposit}</span></td>
</tr>
<tr>
<td>Current Expense</td>
<td align="right"><span id="cur_expense">${summary.currentMonthExpense}</span></td>
</tr>
<tr>
<td>Previous Balance</td>
<td align="right"><span id="cur_bal">${summary.previousBalance}</span></td>
</tr>
</table>
<br></br>
<div id="resultcontainer" style="min-width: 700px" >
</div>
</center>
</body>
</html>