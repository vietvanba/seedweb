<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<!DOCTYPE html >
<html>
<head>
<link rel="shortcut icon" type="image/png" href="images/logo2.png" />
<meta charset="utf-8">

<title>Thông tin đơn hàng</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
	integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
	crossorigin="anonymous"></script>
<base href="${pageContext.servletContext.contextPath}/">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link
	href="https://fonts.googleapis.com/css2?family=Lora:ital,wght@0,400;0,500;0,600;0,700;1,400;1,500;1,600;1,700&display=swap"
	rel="stylesheet">
<style>
body {
	font-family: 'Lora', serif, Arial, Helvetica, sans-serif;
}

#mySidenav a {
	position: absolute;
	left: -80px;
	transition: 0.3s;
	padding: 15px;
	width: 100px;
	text-decoration: none;
	font-size: 14px;
	color: white;
	border-radius: 0 5px 5px 0;
}

#mySidenav a:hover {
	left: 0;
}

#blog {
	top: 482px;
	background-color: #2196F3;
}

#projects {
	top: 534px;
	background-color: #f44336;
}

.notification {
	background-color: #555;
	color: white;
	text-decoration: none;
	padding: 15px 26px;
	position: relative;
	display: inline-block;
	border-radius: 2px;
}

.notification:hover {
	background: red;
}

.notification .badge {
	position: absolute;
	top: -10px;
	right: -10px;
	padding: 5px 10px;
	border-radius: 50%;
	background-color: red;
	color: white;
}
</style>
</head>
<body>
	<nav class="navbar navbar-expand-sm bg-secondary navbar-dark">
		<ul class="navbar-nav">
			<a href="home/index.htm" title="Trang chủ"><img
				alt="logo" src="images/logo2.png" width="45" height="32"></a>

			<li class="nav-item active"><a class="nav-link"
				href="home/index.htm" title="Trang chủ"><s:message
						code="global.home" /></a></li>
			<li class="nav-item"><a class="nav-link" href="home/about.htm"
				title="Giới thiệu"><s:message code="global.aboutus" /></a></li>
			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" id="navbardrop"
				data-toggle="dropdown"><s:message code="global.seed" /></a>
				<div class="dropdown-menu">
					<c:forEach var="type" items="${TypeSeeds}">
						<a class="dropdown-item"
							href="TypeSeed/${type.getIdTypeSeed()}.htm">${type.getTypeSeedName()}</a>
					</c:forEach>

				</div></li>
			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" id="navbardrop"
				data-toggle="dropdown"><s:message code="global.contact" /></a>
				<div class="dropdown-menu">
					<div class="dropdown-item">Phone: 0332796818</div>
					<div class="dropdown-item">Gmail: Baviet19@gmail.com</div>
				</div></li>
			<li class="nav-item"><a class="nav-link"
				href="cart/theOrder.htm?language=en" data-lang="en"><img alt=""
					src="https://uis.ptithcm.edu.vn/App_Themes/Standard/Images/US.gif"></a></li>
			<li class="nav-item"><a class="nav-link"
				href="cart/theOrder.htm?language=vi" data-lang="vi"><img alt=""
					src="https://uis.ptithcm.edu.vn/App_Themes/Standard/Images/VI.gif"></a></li>
			<c:if test="${sessionScope.myLogin== null }">
				<li class="nav-item"><a class="nav-link" href="home/login.htm"
					style="text-align: right; margin-left: 590px"><s:message
							code="global.login" /></a></li>
				<li class="nav-item"><a class="nav-link"
					href="home/register.htm"><s:message code="global.signup" /></a></li>
			</c:if>
			<c:if test="${sessionScope.myLogin!=null }">

				<a href="cart/cart.htm" class="btn btn-secondary"
					style="margin-left: 550px"><s:message code="global.cart" /><span
					class="badge badge-light">${sessionScope.myOrder.size()}</span> </a>


				<li class="nav-item active"><div class="nav-link"
						style="text-align: right;">
						<s:message code="global.hello" />
					</div></li>
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" id="navbardrop"
					data-toggle="dropdown">${sessionScope.myLogin.getFirstName()}</a>
					<div class="dropdown-menu">

						<a class="dropdown-item" href="order/show.htm"> <s:message
								code="global.theOrder" /> <a class="dropdown-item"
							href="home/logout.htm"><s:message code="global.logout" /></a>
						</a>
					</div></li>

			</c:if>

		</ul>


	</nav>
	<br>
	<div class="container" style="width: 700px">
		<h2>
			<s:message code="global.infoOrder" />
			:
		</h2>
		<label style="color: red;">${message}</label>
		<c:if test="${sessionScope.myOrder!=null}">
			<form:form action="cart/theOrder.htm" modelAttribute="theOrder">

				<div class="form-group">
					<label><s:message code="global.lastname" />:</label>
					<form:input cssClass="form-control" path="lastName" />
					<form:errors path="lastName" />
				</div>
				<div class="form-group">
					<label><s:message code="global.firstname" />:</label>
					<form:input cssClass="form-control" path="firstName" />
					<form:errors path="firstName" />
				</div>

				<div class="form-group">
					<label><s:message code="global.address" />:</label>
					<form:input cssClass="form-control" path="address" />
					<form:errors path="address" />
				</div>
				<div class="form-group">
					<label><s:message code="global.phone" />:</label>
					<form:input cssClass="form-control" path="phone"
						pattern="0\\d{9}" />
					<form:errors path="phone" />
				</div>
				<div>
					<button class="btn btn-success">
						<s:message code="global.order" />
					</button>
				</div>


			</form:form>
		</c:if>
	</div>
	<div id="mySidenav" class="sidenav">
		<a href="https://www.facebook.com/baviet.19/" id="blog">Facebook</a> <a
			href="https://youtu.be/2WU38C1JqQA" id="projects">Youtube</a>
	</div>

</body>
</html>