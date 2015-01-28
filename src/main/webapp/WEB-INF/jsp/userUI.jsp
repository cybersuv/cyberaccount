<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FlatAccount :: User</title>
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<center>
		<form:form id="userForm" action="#" method="POST">
			<table class="formtable" cellspacing="0">
				<tr>
					<td colspan="2" bgcolor="#848484" align="center"><font
						color="white">User Detail</font></td>
				</tr>
				<tr>
					<td>User Name</td>
					<td><form:input path="user_name" placeholder="User Name"
							id="user_name" type="text" />
						<form:input id="user_id" type="hidden" path="userid" /></td>
				</tr>
				<tr>
					<td>User Login</td>
					<td><form:input path="user_login" placeholder="User Login ID"
							id="user_login" type="text" /></td>
				</tr>
				<tr>
					<td>User Password</td>
					<td><form:input path="password" placeholder="User Login ID"
							id="user_password" type="password" /></td>
				</tr>
				<tr>
					<td>User Role</td>
					<td><form:select path="user_role_id" items="${roleList}" /></td>
				</tr>
				<c:if test="${isadmin==true}">
					<tr>
						<td>Organisation</td>
						<td><form:select path="org_id" items="${orgList}" /></td>
					</tr>
				</c:if>
				<c:if test="${isadmin==false}">
					<form:hidden path="org_id" />
				</c:if>
				<tr>
					<td colspan="2" align="center"><input type="submit"
						id="submit_btn" value="Save" /></td>
				</tr>
			</table>
		</form:form>
		<br></br>

		<div id="resultcontainer" class="ui-state-highlight ui-corner-all"
			style="display: none; margin-top: 20px; padding: 0 .7em;">
			<p>
				<span id="resulticon" class="ui-icon ui-icon-info"
					style="float: left; margin-right: .3em;"></span> <span id="result"></span>
			</p>
		</div>
	</center>
	<script>
		$("#userForm")
				.submit(
						function(event) {
							var delay_time = 2000;
							// Stop form from submitting normally
							event.preventDefault();
							// Get some values from elements on the page:
							var $form = $(this), term = $form.find(
									"input[name='s']").val(), url = '${pageContext.request.contextPath}/saveuser';
							// Send the data using post
							var postData = $form.serialize();
							var posting = $.post(url, postData);
							// Put the results in a div
							posting
									.done(function(data) {
										$('#resultcontainer').removeClass();
										$('#resulticon').removeClass();
										if (data.split('|')[0] == 'true') {
											$('#resultcontainer')
													.addClass(
															'ui-state-highlight ui-corner-all');
											$('#resulticon').addClass(
													'ui-icon ui-icon-info');
											$("#result")
													.empty()
													.append(
															'Successfully Created/Updated user. ');
											$('#resultcontainer').show();
											if ($('#user_id').val() == -1) {
												$('#userForm')[0].reset();
											}
										} else {
											$('#resultcontainer')
													.addClass(
															'ui-state-error ui-corner-all');
											$('#resulticon').addClass(
													'ui-icon ui-icon-alert');
											$("#result").empty().append(
													data.split('|')[1]);
											$('#resultcontainer').show();
										}
										$('#resultcontainer').delay(delay_time)
												.fadeOut();
										//$('#resulticon').delay(delay_time).fadeOut();
										//$( "#result" ).delay(delay_time).fadeOut();
									});
						});
	</script>
</body>
</html>