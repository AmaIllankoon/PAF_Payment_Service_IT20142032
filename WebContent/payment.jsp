<%@page import="com.Payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Service</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/payment.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Payment Service</h1>

				<form id="formPayment" name="formPayment" method="post" action="payment.jsp">


					Card Holder: <input id="Card_Holder" name="Card_Holder" type="text"
						class="form-control form-control-sm"> 
						
						<br>Card Number: <input id="Card_Number" name="Card_Number" type="text"
						class="form-control form-control-sm"> 
						
						<br> Expire Date: <input id="Expire_Date" name="Expire_Date" type="text"
						class="form-control form-control-sm"> 
						
						<br> CVV: <input id="CVV" name="CVV" type="text"
						class="form-control form-control-sm"> 
						
						<br> <input
						id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidPaymentIDSave" name="hidPaymentIDSave" value="">
				</form>

				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>

				<br>
				<div id="divPaymentGrid">
					<%
						Payment PayObj = new Payment();
						out.print(PayObj.readCard());
					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
