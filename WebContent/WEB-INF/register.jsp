<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Petagram</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="css/normalize.css">
<link rel="stylesheet" href="css/font-awesome.css">
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/templatemo-style.css">
<script src="js/vendor/modernizr-2.6.2.min.js"></script>
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
	<div class="site-top" style="margin-bottom: 0px;">
		<div class="site-banner1">
			<div class="container">
				<div class="row">
					<div class="row">

						<div class="col-lg-12 text-center v-center">

							<a href="#" class="site-brand text-center"><strong>Petagram</strong></a>
							<p class="lead">They use Petagram to communicate with the
								world</p>

							<br>
							<br>
							<br>

							<form class="form col-md-12 center-block" method="post"
								action="instagram-login.do">
								<div class="form-group"
									style="width: 440px; height: 80px; text-align: center; margin: 0 auto;">
									<button class="btn btn-primary btn-lg btn-block">
										<span class="fa fa-instagram"></span> Continued with Instagram
									</button>
								</div>
							</form>
							<form class="form col-md-12 center-block" method="post"
								action="twitter-login.do">
								<div class="form-group"
									style="width: 440px; height: 150px; text-align: center; margin: 0 auto;">
									<button class="btn btn-primary btn-lg btn-block">
										<span class="fa fa-twitter"></span> Continued with Twitter
									</button>
								</div>
							</form>
							<form class="form col-md-12 center-block" method="post"
								action="register.do">
								<div class="form-group"
									style="width: 440px; height: 80px; text-align: center; margin: 0 auto;">
									<input type="text" class="form-control input-lg"
										placeholder="User Name" name="userName">
								</div>
								<div class="form-group"
									style="width: 440px; height: 80px; text-align: center; margin: 0 auto;">
									<input type="password" class="form-control input-lg"
										placeholder="Password" name="password">
								</div>
								<div class="form-group"
									style="width: 440px; height: 80px; text-align: center; margin: 0 auto;">
									<button class="btn btn-success btn-lg btn-block">Sign
										Up</button>
									<span class="pull-right"><a href="login.do">Login</a></span>
								</div>
							</form>
							<div class="row"></div>
							<br>
							<br>
							<br>
						</div>
						<div class="col-md-12 text-center">
							<p class="copyright-text">Copyright &copy; 2015 Blossom |
								eBusiness Technology</p>
						</div>
					</div>
				</div>
				<!-- .site-banner -->

			</div>
		</div>
		<!-- .site-top -->
	</div>
	<!-- MAIN POSTS -->



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