<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<%@ page contentType="text/html;charset=UTF-8"%>
<!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Petagram</title>
<meta name="description" content="">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
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

ul.enlarge {
	list-style-type: none; /*remove the bullet point*/
	margin-left: 0;
}

ul.enlarge li {
	display: inline-block; /*places the images in a line*/
	position: relative;
	z-index: 0;
	/*resets the stack order of the list items - later we'll increase this*/
	margin: 10px 40px 0 20px;
}

ul.enlarge img {
	background-color: #eae9d4;
	padding: 6px;
	-webkit-box-shadow: 0 0 6px rgba(132, 132, 132, .75);
	-moz-box-shadow: 0 0 6px rgba(132, 132, 132, .75);
	box-shadow: 0 0 6px rgba(132, 132, 132, .75);
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	border-radius: 4px;
}

ul.enlarge span {
	position: absolute;
	left: -9999px;
	background-color: #eae9d4;
	padding: 10px;
	font-family: 'Droid Sans', sans-serif;
	font-size: .9em;
	text-align: center;
	color: #495a62;
	-webkit-box-shadow: 0 0 20px rgba(0, 0, 0, .75));
	-moz-box-shadow: 0 0 20px rgba(0, 0, 0, .75);
	box-shadow: 0 0 20px rgba(0, 0, 0, .75);
	-webkit-border-radius: 8px;
	-moz-border-radius: 8px;
	border-radius: 8px;
}

ul.enlarge li:hover {
	z-index: 50;
	cursor: pointer;
}

ul.enlarge span img {
	padding: 2px;
	background: #ccc;
}

ul.enlarge li:hover span {
	top: -80px;
	/*the distance from the bottom of the thumbnail to the top of the popup image*/
	left: -50px;
	/*distance from the left of the thumbnail to the left of the popup image*/
}

ul.enlarge li:hover:nth-child(2) span {
	left: -100px;
}

ul.enlarge li:hover:nth-child(3) span {
	left: -200px;
}
/**IE Hacks - see http://css3pie.com/ for more info on how to use CS3Pie and to download the latest version**/
ul.enlarge img, ul.enlarge span {
	behavior: url(pie/PIE.htc);
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
	<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
	<!-- SITE TOP -->
	<div class="site-top">
		<div class="site-header clearfix">
			<div class="container">
				<div class="site-brand pull-left">
					<ul class="nav navbar-nav">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"> ${user.getUserName() }</a>
							<ul class="dropdown-menu">
								<li><a href="view-user.do?userName=${user.getUserName() }">Account
										Info </a></li>
								<li><a href="logout.do">Sign Out </a></li>
							</ul></li>
					</ul>
				</div>
				<div class="row">
					<div class="col-md-4 col-md-offset-8">
						<form action="search-photo.do" class="search-form">
							<div class="form-group has-feedback">
								<label for="search" class="sr-only">Search</label> <input
									type="text" class="form-control" name="keyword" id="search"
									placeholder="search"> <span
									class="glyphicon glyphicon-search form-control-feedback"></span>
							</div>
						</form>
					</div>
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
					<div class="col-md-offset-2 col-md-8">
						<div class="subscribe-form">
							<div class="subscribe-form">
								<form accept-charset="UTF-8" method="POST"
									action="upload-photo.do" enctype="multipart/form-data">
									<textarea class="form-control counted" name="text"
										placeholder="What's happening..." rows="4"
										style="margin-bottom: 5px; background: transparent;"
										maxlength="150"></textarea>
									<input type="file" name="file" accept="image/*" />
									<div class="pull-right">
										<button class="btn btn-info" type="submit">Post</button>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- .site-banner -->
	</div>
	<!-- .site-top -->
	<!-- MAIN POSTS -->
	<div class="main-posts">
		<div class="container">
			<div class="row">
				<div class="blog-masonry masonry-true">
					<c:forEach var="photo" items="${photos}">
						<div class="post-masonry col-md-4 col-sm-6">
							<ul class="enlarge">
								<li><img src="${photo.getUrl()}" width="300px"
									height="200px" alt="Dechairs" /> <span> <a
										href="view-photo.do?id=${photo.getId() }"> <img
											src="${photo.getUrl()}" width="350px" height="250px"
											alt="Deckchairs" />
									</a><br /> ${photo.getText()}
								</span></li>
							</ul>
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
