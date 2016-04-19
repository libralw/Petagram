<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<%@ page contentType="text/html;charset=UTF-8"%>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Petagram</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="css/normalize.css">
<link rel="stylesheet" href="css/font-awesome.css">
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/templatemo-style.css">
<script src="js/vendor/modernizr-2.6.2.min.js"></script>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>

<style>
.dropdown-menu>li>a:hover {
	background: none;
	color: #fff;
}

.nav .open>a {
	background: none !important;
	color: #fff !important;
}

.nav>li>a:hover {
	background: none;
}

.dropdown ul.dropdown-menu {
	margin-top: -10px;
}

.navbar-nav>li>a {
	text-align: left !important;
}
</style>
</head>
<body>
	<!--[if lt IE 7]>
            <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->

	<div id="loader-wrapper">
		<div id="loader"></div>
	</div>

	<div class="content-bg"></div>
	<div class="bg-overlay"></div>

	<!-- SITE TOP -->

	<div class="site-top">
		<div class="site-header clearfix">
			<div class="container">
				<div class="site-brand">
					<ul class="nav navbar-nav">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"> ${user.getUserName() }</a>
							<ul class="dropdown-menu">
								<li><a href="view-user.do?userName=${user.getUserName()}">Account
										Info </a></li>
								<li><a href="logout.do">Sign Out </a></li>
							</ul></li>
					</ul>
				</div>
				<div class="col-md-4 col-md-offset-8">
					<form action="" method="post" class="pull-right">
						<a href="home.do" class="pull-right"><span
							class="glyphicon glyphicon-pencil"></span> Post new picture</a>
					</form>

				</div>
			</div>
		</div>
		<!-- .site-header -->
		<div class="site-banner">
			<div class="container">
				<div class="row">
					<div class="col-md-offset-2 col-md-8 text-center">
						<h2>
							PETAGRAM <span class="blue">colorful</span><span class="green">life</span>
						</h2>
					</div>
					<form action="search-photo.do" method="post" class="subscribe-form">
						<fieldset class="col-md-offset-2 col-md-7 col-sm-1">
							<input type="text" id="subscribe-email"
								placeholder="Find interesting things..." name="keyword">
						</fieldset>
						<fieldset class="col-md-1 col-sm-1">
							<input type="submit" id="subscribe-submit" class="button white"
								value="Search!">
						</fieldset>
					</form>
				</div>
			</div>
		</div>
		<!-- .site-banner -->
	</div>
	<!-- .site-top -->
	<!-- MAIN POSTS -->
	<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
	<div class="main-posts">
		<div class="container">
			<div class="row">
				<div class="blog-masonry masonry-true">
					<c:forEach var="photo" items="${photos}">
						<div class="post-masonry col-md-4 col-sm-6">
							<div class="post-thumb">
								<a href="view-photo.do?id=${photo.getId() }"><img
									src="${photo.getUrl()}" alt=""></a>
								<div class="title-over">
									<h4>${photo.getText()}</h4>
								</div>
							</div>
						</div>
						<!-- /.post-masonry -->
					</c:forEach>
				</div>
			</div>
		</div>
		<div class="container">
			<div class="row">
				<div class="col-md-offset-4 col-md-7 text-center"
					style="font-size: 18px;">
					<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
					<c:if test="${hasPrev}">
						<div class="col-md-3" style="text-decoration: underline">
							<a style="color: #fff" href="${prevPage }"><span
								class="glyphicon glyphicon-backward"></span> Previous</a>
						</div>
					</c:if>
					<c:if test="${hasNext}">
						<div class="col-md-4" style="text-decoration: underline">
							<a style="color: #fff" href="${nextPage }"> Next <span
								class="glyphicon glyphicon-forward"></span></a>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>

	<!-- FOOTER -->
	<footer class="site-footer">
		<div class="container">
			<div class="row">
				<div class="col-md-12 text-center">
					<p class="copyright-text">Copyright &copy; 2015 Blossom |
						eBusiness Technology</p>
				</div>
			</div>
		</div>
	</footer>

	<script src="js/jquery.min.js"></script>
	<script>
		window.jQuery
				|| document
						.write('<script src="js/vendor/jquery-1.10.2.min.js"><\/script>')
	</script>
	<script src="js/min/plugins.min.js"></script>
	<script src="js/min/main.min.js"></script>

	<!-- Preloader -->
	<script type="text/javascript">
		//<![CDATA[
		$(window).load(function() { // makes sure the whole site is loaded
			$('#loader').fadeOut(); // will first fade out the loading animation
			$('#loader-wrapper').delay(350).fadeOut('slow'); // will fade out the white DIV that covers the website.
			$('body').delay(350).css({
				'overflow-y' : 'visible'
			});
		})
		//]]>
	</script>
	<!-- templatemo 434 masonry -->
</body>
</html>
