<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<!DOCTYPE html >
<html>
<head>
<link rel="shortcut icon" type="image/png" href="images/logo2.png" />
<meta charset="utf-8">

<title>Danh sách đơn hàng</title>
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
			<a href="home/index.htm" title="Trang chủ"><img alt="logo"
				src="images/logo2.png" width="45" height="32"></a>

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
				href="order/show.htm?language=en" data-lang="en"><img alt=""
					src="https://uis.ptithcm.edu.vn/App_Themes/Standard/Images/US.gif"></a></li>
			<li class="nav-item"><a class="nav-link"
				href="order/show.htm?language=vi" data-lang="vi"><img alt=""
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
	<div class="container">
		<br>
		<h3>
			<s:message code="global.order" />
		</h3>
		<h4 style="color: red;">${message}</h4>
		<c:choose>
			<c:when test="${listOrder == null}">
				<div class="alert alert-danger">
					<s:message code="global.listOrderEmpty" />
				</div>
			</c:when>
			<c:otherwise>
				<table class="table table-striped">


					<thead>
						<tr>
							<th><s:message code="global.IdOrder" /></th>
							<th style="text-align: center;"><s:message
									code="global.fullname" /></th>
							<th style="text-align: center;"><s:message
									code="global.address" /></th>
							<th style="text-align: center;"><s:message
									code="global.phone" /></th>
							<th style="text-align: center;"><s:message
									code="global.timeOrder" /></th>
							<th style="text-align: center;"><s:message
									code="global.total" /></th>
							<th style="text-align: center;"><s:message
									code="global.status" /></th>
							<th style="text-align: center;"><s:message
									code="global.optional" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="o" items="${listOrder}">
							<c:if test="${o.getStatusOrder().trim()=='2'}">
								<tr class="table-success">
							</c:if>
							<c:if test="${o.getStatusOrder().trim()=='0'}">
								<tr class="table-danger">
							</c:if>
							<c:if test="${o.getStatusOrder().trim()=='1'}">
								<tr class="table-warning">
							</c:if>
							<td>${o.getIdOrder()}</td>
							<td style="text-align: center;">${o.getLastName()}
								${o.getFirstName()}</td>
							<td style="text-align: center;">${o.getAddress()}</td>
							<td style="text-align: center;">${o.getPhone()}</td>
							<td style="text-align: center;">${o.getOrderTime()}</td>
							<td style="text-align: center;">${o.getGiaVN()}</td>
							<c:choose>
								<c:when test="${o.getStatusOrder().trim()=='1'}">
									<td style="text-align: center;"><s:message
											code="global.status1" /></td>
								</c:when>
								<c:when test="${o.getStatusOrder().trim()=='0'}">
									<td style="text-align: center;"><s:message
											code="global.status0" /></td>
								</c:when>
								<c:when test="${o.getStatusOrder().trim()=='3'}">
									<td style="text-align: center;"><s:message
											code="global.status3" /></td>
								</c:when>
								<c:otherwise>
									<td style="text-align: center;"><s:message
											code="global.status2" /></td>
								</c:otherwise>
							</c:choose>
							<td style="text-align: center;"><a
								href="order/showDetail/${o.getIdOrder()}.htm"><s:message
										code="global.show" /></a></td>
							</tr>
						</c:forEach>

						<%-- <c:if test="${Cart != null}">
					<c:forEach var="order" items="${Cart}">
						<tr>
							<td>${order.getSeed().getSeedName()}</td>
							<td style="text-align: center;">${order.getNumber()}</td>
							<td style="text-align: center;">${order.getGiaVN()}</td>
							<td style="text-align: center;">${order.getTongGiaVN()}</td>
							<td style="text-align: center;"><a
								href="cart/delete/${order.getSeed().getIdSeed()}.htm">Xoá</a></td>
						</tr>
					</c:forEach>
				</c:if>
				<tr>
					<td>Tổng</td>
					<td style="text-align: center;"></td>
					<td style="text-align: center;"></td>
					<td style="text-align: center;"></td>
					<td style="text-align: center;">${Tong}</td>
				</tr> --%>


					</tbody>
				</table>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>