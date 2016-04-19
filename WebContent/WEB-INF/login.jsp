<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>Petagram</title>
<meta name="generator" content="Bootply" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<link href="css/bootstrap.min.css" rel="stylesheet">
<!--[if lt IE 9]>
			<script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
<link href="css/styles.css" rel="stylesheet">
</head>
<body>
	<!--login modal-->
	<div id="loginModal" class="modal show" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="text-center">Login to Petagram</h1>
				</div>
				<div class="modal-body">
					<form class="form col-md-12 center-block" method="post"
						action="instagram-login.do">
						<div class="form-group">
							<button class="btn btn-success btn-lg btn-block">
								<span class="fa fa-instagram"></span> Login with Instagram
							</button>
						</div>
					</form>
					<form class="form col-md-12 center-block" method="post"
						action="twitter-login.do">
						<div class="form-group">
							<button class="btn btn-danger btn-lg btn-block">
								<span class="fa fa-twitter"></span> Login with Twitter
							</button>
						</div>
					</form>
					<form class="form col-md-12 center-block" method="post"
						action="login.do">
						<div class="form-group">
							<input type="text" class="form-control input-lg"
								placeholder="User name" name="userName">
						</div>
						<div class="form-group">
							<input type="password" class="form-control input-lg"
								placeholder="Password" name="password">
						</div>
						<div class="form-group">
							<button class="btn btn-primary btn-lg btn-block">Log In</button>
							<span class="pull-right"><a href="register.do">Sign up
									now</a></span>
						</div>
					</form>
				</div>
				<div class="modal-footer"></div>
			</div>
		</div>
	</div>
	<!-- script references -->
	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>