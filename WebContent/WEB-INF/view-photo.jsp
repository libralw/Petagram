<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<%@ page contentType="text/html;charset=UTF-8"%>
<html class="no-js">
<!--<![endif]-->
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
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/foundation.css" />
<link rel="stylesheet" href="css/foundation-icons.css" />

<script src="js/vendor/modernizr-2.6.2.min.js"></script>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/foundation.min.js"></script>
<script src="js/foundation.js"></script>

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
								<li><a href="view-user.do?userName=${user.getUserName()}">Account
										Info </a></li>
								<li><a href="logout.do">Sign Out </a></li>
							</ul></li>
					</ul>
				</div>




				<div class="row">
					<div class="col-md-4 col-md-offset-8">
						<form action="search-photo.do" class="search-form" method="post">
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



		<!-- .site-header -->
		<div class="site-banner">
			<div class="container">
				<div class="row">
					<div class="col-md-offset-2 col-md-8 text-center">
						<h2>
							<a href="home.do">PETAGRAM</a> <span class="green">colorful</span><span
								class="blue">life</span>
						</h2>
					</div>
				</div>
			</div>
		</div>
		<!-- .site-banner -->
	</div>
	<div class="main-posts">
		<div class="row">
			<div class="container">
				<div class="col-md-12" style="text-align: center">
					<img src="" />
				</div>
			</div>
		</div>
		<div>
			<br />
		</div>
		<div class="container">
			<div class="row">
				<div class="col-md-6">
					<div class="testimonial-wrap">
						<div class="row">
							<div class="col-md-12 testimonial">
								<div class="post-masonry col-md-12">
									<div class="post-thumb">
										<img src="${photo.getUrl()}" alt="">
									</div>
								</div>
							</div>
						</div>
					</div>
					<hr>
					<div class="hug">
						<div class="col-md-6">
							<p>
								Posted by <a href="view-user.do?userName=${owner.getUserName()}">${owner.getUserName()}</a>
							</p>
						</div>

						<div class="col-md-2">
							<a href="https://twitter.com/share" class="twitter-share-button"
								data-text="I have found very funny pictures here in Petagram: "
								data-url="http://chitoo.sinaapp.com:8080/task8/view-photo.do?id=${photo.getId()}"
								data-size="large" data-count="none">Tweet</a>
							<script>
								!function(d, s, id) {
									var js, fjs = d.getElementsByTagName(s)[0], p = /^http:/
											.test(d.location) ? 'http'
											: 'https';
									if (!d.getElementById(id)) {
										js = d.createElement(s);
										js.id = id;
										js.src = p
												+ '://platform.twitter.com/widgets.js';
										fjs.parentNode.insertBefore(js, fjs);
									}
								}(document, 'script', 'twitter-wjs');
							</script>
						</div>

						<div class="col-md-4">
							<span class="glyphicon glyphicon-heart"></span><span>${photo.getLikes()}</span>
							<a type="button" class="btn btn-labeled btn-info"
								href="like.do?id=${photo.getId()}"> Give it a Hug!</a>
						</div>
					</div>
				</div>
				<div class="col-md-1"></div>
				<div class="col-md-5">
					<h4>Comments:</h4>
					<div class="row">
						<div class="col-md-12">
							<div class="subscribe-form">
								<div class="subscribe-form">
									<form accept-charset="UTF-8" method="POST" action="comment.do"
										enctype="multipart/form-data">
										<textarea class="form-control counted" name="comment"
											placeholder="Your comments: ..." rows="4"
											style="margin-bottom: 5px; background: transparent;"
											maxlength="150"></textarea>
										<input type="hidden" name="id" value="${photo.getId() }">
										<div class="pull-right">
											<button class="btn btn-info" type="submit">Comment</button>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
					<div class="comment">
						<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"
							prefix="fn"%>
						<c:if test="${comments !=null && fn:length(comments) > 0}">
							<c:forEach var="comment" items="${comments}">
								<section class="viewer">
									<h6 class="byline">
										<a href="view-user.do?userName=${comment.getUserName() }"><i
											class="icon"></i>${comment.getUserName() }</a> <small>said
											at <span class="data">${comment.getTimeString() } </span>
										</small>
									</h6>
								</section>
								<section class="content">
									<p>${comment.getComment() }</p>
								</section>
							</c:forEach>
						</c:if>
					</div>
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

