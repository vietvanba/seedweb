<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<!DOCTYPE html >
<html>
<head>
<link rel="shortcut icon" type="image/png" href="images/logo2.png" />
<meta charset="utf-8">

<title>${Seed.seedName }</title>
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

*[id$=errors] {
	color: red;
	font-style: italic;
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

* {
	box-sizing: border-box;
}

/* Create three equal columns that floats next to each other */
.column {
	float: left;
	width: 20%;
	padding: 10px;
	height: 370px; /* Should be removed. Only for demonstration */
	margin-left: 30px;
}

/* Clear floats after the columns */
.row:after {
	content: "";
	display: table;
	clear: both;
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

img {
	border-radius: 5%;
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
p{
	font-size: 20px;
	font-family: serif;

}
</style>
</head>
<body >
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
				href="seed/${Seed.idSeed}.htm?language=en" data-lang="en"><img alt=""
					src="https://uis.ptithcm.edu.vn/App_Themes/Standard/Images/US.gif"></a></li>
			<li class="nav-item"><a class="nav-link"
				href="seed/${Seed.idSeed}.htm?language=vi" data-lang="vi"><img alt=""
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

	<div class="container" style="margin-top: 70px">

		<c:if test="${message==true}">
			<div class="alert alert-success">
				<strong><s:message code="global.cart" /> :</strong>
				<s:message code="global.success" />
			</div>
		</c:if>
		<c:if test="${message=='loginFalse'}">
			<div class="alert alert-danger">
				<strong><s:message code="global.error" /> :</strong>
				<s:message code="global.errLogin" />
			</div>
		</c:if>
		<c:if test="${message=='max'}">
			<div class="alert alert-danger">
				<strong><s:message code="global.error" /> :</strong>
				<s:message code="global.errNumber" />
			</div>
		</c:if>
		<img alt="Hoa Hướng Dương" src="${Seed.images}"
			style="width: 500px; height: 350px; float: left; object-fit: cover;">
		<div
			style="margin-left: 510px; font-family: Serif, Comic Sans, Comic Sans MS, cursive; font-size: 24px">
			<div style="margin-left: 50px">

				<p style="margin-right: 120px; float: left;">
					<s:message code="global.seedname" />
					:
				</p>
				<p style="margin-left: 220px;">${Seed.seedName }</p>
				<p style="margin-right: 120px; float: left;">
					<s:message code="global.classify" />
					:
				</p>
				<p style="margin-left: 220px;">${TypeSeedName}</p>
				<p style="margin-right: 120px; float: left;">
					<s:message code="global.price" />
					:
				</p>
				<p style="margin-left: 220px;">${Seed.getGiaVN()}</p>
				<p style="margin-right: 120px;">
					<s:message code="global.description" />
					:
				</p>
				<p style="margin-right: 120px; font-size: 22px">${Seed.information}</p>
				<p style="margin-right: 120px; float: left;">
					<s:message code="global.number" />
					:
				</p>

				<form:form action="seed/${Seed.idSeed}.htm"
					modelAttribute="orderDetail">

					<div class="buttons_added">
						<button class="minus">-</button>
						<form:input path="number" style="width: 100px"
							aria-label="quantity" max="500" min="1" step="1" type="text" pattern="[0-9]+([0-9]+)?"
							
							value="1" name="quantity" class="quantity-field" />
						<button class="plus">+</button>
						<form:errors path="number" />


					</div>
					<hr>
					<c:if test="${Seed.number>0}">
						<button class="btn btn-outline-warning" name="btnCart"
							style="margin-right: 100px">
							<s:message code="global.addcart" />
						</button>
						<button class="btn btn-outline-success" name="btnBuyNow"
							style="margin-left: 50px">
							<s:message code="global.buynow" />
						</button>

					</c:if>
					<c:if test="${Seed.number<=0}">
						<div>
							<s:message code="global.hethang" />
						</div>
					</c:if>
				</form:form>
				<br>
			</div>

		</div>
	</div>
	<script>
	$(document).ready(function() {
		$('.minus').click(function () {
			var $input = $(this).parent().find('input');
			var count = parseInt($input.val()) - 1;
			count = count < 1 ? 1 : count;
			$input.val(count);
			$input.change();
			return false;
		});
		$('.plus').click(function () {
			var $input = $(this).parent().find('input');
			$input.val(parseInt($input.val()) + 1);
			$input.change();
			return false;
		});
	});
	</script>
</body>
</html>