<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<!DOCTYPE html >
<html>
<head>
<link rel="shortcut icon" type="image/png" href="images/logo2.png" />
<meta charset="utf-8">

<title>Trang chủ</title>
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
	font-family: 'Lora', serif,Arial, Helvetica, sans-serif;

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

			<img alt="logo" src="images/logo2.png" width="45" height="32">
			<li class="nav-item active"><a class="nav-link"
				href="admin/index.htm" title="Trang chủ">ADMIN </a></li>
			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" id="navbardrop"
				data-toggle="dropdown">Hạt giống</a>
				<div class="dropdown-menu">

					<a class="dropdown-item" href="admin/typeseed.htm">Loại hạt</a> <a
						class="dropdown-item" href="admin/seed.htm">Hạt giống</a>
				</div></li>
			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" id="navbardrop"
				data-toggle="dropdown">Đơn hàng</a>
				<div class="dropdown-menu">

					<a class="dropdown-item" href="admin/theorder.htm">Đơn hàng
						chưa xử lý</a> <a class="dropdown-item"
						href="admin/theorderCommit.htm">Đơn hàng đã hoàn thành</a>
				</div></li>
			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" id="navbardrop"
				data-toggle="dropdown">Liên hệ</a>
				<div class="dropdown-menu">
					<div class="dropdown-item">Số điện thoại: 0332796818</div>
					<div class="dropdown-item">Gmail: Baviet19@gmail.com</div>
				</div></li>
			<c:if test="${sessionScope.myLogin== null }">
				<li class="nav-item"><a class="nav-link" href="home/login.htm"
					style="text-align: right; margin-left: 750px">Đăng nhập</a></li>
			</c:if>
			<c:if test="${sessionScope.myLogin!=null }">

				<li class="nav-item active"><div class="nav-link"
						style="text-align: right; margin-left: 650px">Xin chào</div></li>
				<li class="nav-item active"><div class="nav-link">${sessionScope.myLogin.getFirstName()}</div></li>
				<li class="nav-item"><a class="nav-link" href="home/logout.htm">Đăng
						xuất</a></li>
			</c:if>

		</ul>


	</nav>
	<div class="container">
		<br>
		<h3>Đơn Hàng đã xử lý</h3>
		<h4 style="color: red;">${message}</h4>
		<c:choose>
			<c:when test="${TheOrderNew == null}">
				<div class="alert alert-danger">Bạn chưa có đơn hàng mới</div>
			</c:when>
			<c:otherwise>
				<table class="table table-striped">


					<thead class="thead-light">
						<tr>
							<th>Mã số đơn hàng</th>
							<th style="text-align: center;">Họ và tên</th>
							<th style="text-align: center;">Địa chỉ</th>
							<th style="text-align: center;">Số điện thoại</th>
							<th style="text-align: center;">Ngày giờ đặt</th>
							<th style="text-align: center;">Tổng</th>
							<th style="text-align: center;">Trạng thái đơn hàng</th>
							<th style="text-align: center;">Xem</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="o" items="${TheOrderNew}">
						<c:if test="${o.getStatusOrder().trim()=='2'}">
							<tr class="table-success">
							</c:if>
						<c:if test="${o.getStatusOrder().trim()=='0'}">
							<tr class="table-danger">
						</c:if>
								<td>${o.getIdOrder()}</td>
								<td style="text-align: center;">${o.getLastName()}
									${o.getFirstName()}</td>
								<td style="text-align: center;">${o.getAddress()}</td>
								<td style="text-align: center;">${o.getPhone()}</td>
								<td style="text-align: center;">${o.getOrderTime()}</td>
								<td style="text-align: center;">${o.getPrice()}</td>
								<c:choose>
									<c:when test="${o.getStatusOrder().trim()=='1'}">
										<td style="text-align: center;">Đơn hàng đã đặt</td>
									</c:when>
									<c:when test="${o.getStatusOrder().trim()=='0'}">
										<td class="" style="text-align: center;">Đơn hàng đã Huỷ</td>
									</c:when>

									<c:otherwise>
										<td style="text-align: center;">Đơn hàng đã được tiếp
											nhận</td>
									</c:otherwise>
								</c:choose>


								<td style="text-align: center;"><a
									href="admin/showDetail/${o.getIdOrder()}.htm">Xem</a></td>
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