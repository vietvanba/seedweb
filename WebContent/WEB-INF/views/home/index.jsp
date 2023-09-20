<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<!DOCTYPE html >
<html>
<head>
<link rel="shortcut icon" type="image/png" href="images/logo2.png" />
<meta charset="utf-8">

<title>Trang chủ</title>
<link rel="preconnect" href="https://fonts.gstatic.com">
<link
	href="https://fonts.googleapis.com/css2?family=Lora:ital,wght@0,400;0,500;0,600;0,700;1,400;1,500;1,600;1,700&display=swap"
	rel="stylesheet">
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
<link rel="stylesheet" media="screen"
	href="${pageContext.servletContext.contextPath}/css/News-Cards.css">

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

img.cangiua {
	display: block;
	margin-left: auto;
	margin-right: auto;
}

* {
	box-sizing: border-box;
}

/* Create two equal columns that floats next to each other */
.column {
	float: left;
	padding: 10px;
	width: 15rem;
}

/* Clear floats after the columns */
.row:after {
	content: "";
	display: table;
	clear: both;
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
				href="home/index.htm?language=en" data-lang="en"><img alt=""
					src="https://uis.ptithcm.edu.vn/App_Themes/Standard/Images/US.gif"></a></li>
			<li class="nav-item"><a class="nav-link"
				href="home/index.htm?language=vi" data-lang="vi"><img alt=""
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

	<div>
		<img alt="header" src="images/Header2.png" style="width: 1349px">
	</div>
	<div class="container" style="margin-left: 190px">
		<br>
		<h2 style="float: left;">
			<s:message code="global.seed" />
		</h2>
		<div class="dropdown" style="margin-left: 850px">
			<button type="button" class="btn btn-primary dropdown-toggle"
				data-toggle="dropdown">
				<s:message code="global.classify" />
			</button>
			<div class="dropdown-menu">
				<a class="dropdown-item" href="home/index.htm">Tất cả</a>
				<c:forEach var="type" items="${TypeSeeds}">
					<a class="dropdown-item"
						href="TypeSeed/${type.getIdTypeSeed()}.htm">${type.getTypeSeedName()}</a>
				</c:forEach>
			</div>
		</div>
		<br>
		<div style="margin-left: 20px">
			<c:forEach var="hoa" items="${Seed}">

				<figure class="snip1527" style="width: 250px; margin-right: 60px">
					<div class="image" style="width: 250px;">
						<img src="${hoa.images}" style="height: 300px; width: 250px;" />
					</div>
					<figcaption style="height: auto; width: 250px">
						<h3>${hoa.seedName}</h3>
						<p>${hoa.getGiaVN()}</p>
					</figcaption>
					<a href="seed/${hoa.getIdSeed()}.htm"></a>
				</figure>


			</c:forEach>
		</div>
	</div>
	<div>
		<img class="cangiua" alt="home.png" src="images/home.png"
			style="height: 720px; width: 1349px">
	</div>
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>