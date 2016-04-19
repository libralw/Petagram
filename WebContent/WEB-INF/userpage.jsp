<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="databeans.VisitHistory"%>
<%@page import="databeans.LikeHistory"%>
<%@page import="databeans.FollowerHistory"%>
<%@page import="databeans.CommentHistory"%>
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
<link
	href="http://fontawesome.io/assets/font-awesome/css/font-awesome.css"
	rel="stylesheet" media="screen">
<script src="js/vendor/modernizr-2.6.2.min.js"></script>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="https://www.google.com/jsapi?autoload={'modules':[{'name':'visualization','version':'1','packages':['corechart']}]}"></script>
<script type="text/javascript">
		$(document).ready(function() {
    		var $btnSets = $('#responsive'),
    		$btnLinks = $btnSets.find('a');
 
		    $btnLinks.click(function(e) {
		        e.preventDefault();
		        $(this).siblings('a.active').removeClass("active");
		        $(this).addClass("active");
		        var index = $(this).index();
		        $("div.user-menu>div.user-menu-content").removeClass("active");
		        $("div.user-menu>div.user-menu-content").eq(index).addClass("active");
		    });
		});

		$( document ).ready(function() {
		    $("[rel='tooltip']").tooltip();    
		 
		    $('.view').hover(
		        function(){
		            $(this).find('.caption').slideDown(250); //.fadeIn(250)
		        },
		        function(){
		            $(this).find('.caption').slideUp(250); //.fadeOut(205)
		        }
		    ); 
		});                  
        </script>
<script type="text/javascript">
        google.load('visualization', '1', {packages: ['corechart']});
        google.setOnLoadCallback(drawCharts);

        function drawCharts(){
        	drawVisitChart();
        	drawLikeChart();
        	drawFollowerHistory();
        	drawCommentHistory();
        }
        function drawVisitChart() {

			var rows = new Array();
			rows[rows.length]=['Date', 'Visits'];
			<%VisitHistory[] visitHistory = (VisitHistory[]) request.getAttribute("visitHistory");
			for (int i = 0; i < visitHistory.length; i++) {%>
				var row = new Array();
				row[0] = "<%=visitHistory[i].getDateString()%>";
				row[1] = <%=visitHistory[i].getVisits()%>;
				rows[rows.length] = row;
			<%}%>
			var data = google.visualization.arrayToDataTable(rows);
			
			var options = {
			'width':360,
			'height':300,
			'legend':'bottom',
			hAxis: {
			},
			vAxis: {
				maxValue:5,
				format:'#',
			    viewWindowMode:'explicit',
			    viewWindow: {
			        min:0,
			    }
			}
			};
			
			var chart = new google.visualization.LineChart(
			  document.getElementById('visit_trend'));
			
			chart.draw(data, options);

        }
        
        function drawLikeChart() {

			var rows = new Array();
			rows[rows.length]=['Date', 'Hugs'];
			<%LikeHistory[] likeHistory = (LikeHistory[]) request.getAttribute("likeHistory");
			for (int i = 0; i < likeHistory.length; i++) {%>
				var row = new Array();
				row[0] = "<%=likeHistory[i].getDateString()%>";
				row[1] = <%=likeHistory[i].getLikes()%>;
				rows[rows.length] = row;
			<%}%>
			var data = google.visualization.arrayToDataTable(rows);
			
			var options = {
					'width':360,
					'height':300,
					'legend':'bottom',
				hAxis: {
				},
				vAxis: {
					maxValue:5,
					format:'#',
				    viewWindowMode:'explicit',
				    viewWindow: {
				        min:0,
				    }
				}
			};
			
			var chart = new google.visualization.LineChart(
			  document.getElementById('like_trend'));
			
			chart.draw(data, options);

        }
        function drawFollowerHistory() {

			var rows = new Array();
			rows[rows.length]=['Date', 'Followers'];
			<%FollowerHistory[] followerHistory = (FollowerHistory[]) request.getAttribute("followerHistory");
			for (int i = 0; i < followerHistory.length; i++) {%>
				var row = new Array();
				row[0] = "<%=followerHistory[i].getDateString()%>";
				row[1] = <%=followerHistory[i].getFollowers()%>;
				rows[rows.length] = row;
			<%}%>
			var data = google.visualization.arrayToDataTable(rows);
			
			var options = {
					'width':360,
					'height':300,
					'legend':'bottom',
				hAxis: {
				},
				vAxis: {
					maxValue:5,
					minValue:-5,
					format:'#',
				    viewWindowMode:'explicit',
				}
			};
			
			var chart = new google.visualization.LineChart(
			  document.getElementById('follower_trend'));
			
			chart.draw(data, options);

        }        
        function drawCommentHistory() {

			var rows = new Array();
			rows[rows.length]=['Date', 'Comments'];
			<%CommentHistory[] commentHistory = (CommentHistory[]) request.getAttribute("commentHistory");
			for (int i = 0; i < commentHistory.length; i++) {%>
				var row = new Array();
				row[0] = "<%=commentHistory[i].getDateString()%>
	";
		row[1] =
<%=commentHistory[i].getComments()%>
	;
		rows[rows.length] = row;
<%}%>
	var data = google.visualization.arrayToDataTable(rows);

		var options = {
			'width' : 360,
			'height' : 300,
			'legend' : 'bottom',
			hAxis : {},
			vAxis : {
				maxValue : 5,
				format : '#',
				viewWindowMode : 'explicit',
				viewWindow : {
					min : 0,
				}
			}
		};

		var chart = new google.visualization.LineChart(document
				.getElementById('comment_trend'));

		chart.draw(data, options);

	}
</script>
<style>
.square, .btn {
	border-radius: 0px !important;
}

/* -- color classes -- */
.coralbg {
	background-color: #FA396F;
}

.coral {
	color: #FA396f;
}

.turqbg {
	background-color: #46D8D2;
}

.turq {
	color: #46D8D2;
}

.white {
	color: #fff !important;
}

/* -- The "User's Menu Container" specific elements. Custom container for the snippet -- */
div.user-menu-container {
	z-index: 10;
	background-color: #fff;
	margin-top: 20px;
	background-clip: padding-box;
	opacity: 0.97;
	filter: alpha(opacity = 97);
	-webkit-box-shadow: 0 1px 6px rgba(0, 0, 0, 0.175);
	box-shadow: 0 1px 6px rgba(0, 0, 0, 0.175);
}

div.user-menu-container .btn-lg {
	padding: 0px 12px;
}

div.user-menu-container h4 {
	font-weight: 300;
	color: #8b8b8b;
}

div.user-menu-container a, div.user-menu-container .btn {
	transition: 1s ease;
}

div.user-menu-container .thumbnail {
	width: 100%;
	min-height: 200px;
	border: 0px !important;
	padding: 0px;
	border-radius: 0;
	border: 0px !important;
}

/* -- Vertical Button Group -- */
div.user-menu-container .btn-group-vertical {
	display: block;
}

div.user-menu-container .btn-group-vertical>a {
	padding: 20px 25px;
	background-color: #46D8D2;
	color: white;
	border-color: #fff;
}

div.btn-group-vertical>a:hover {
	color: white;
	border-color: white;
}

div.btn-group-vertical>a.active {
	background: #FA396F;
	box-shadow: none;
	color: white;
}
/* -- Individual button styles of vertical btn group -- */
div.user-menu-btns {
	padding-right: 0;
	padding-left: 0;
	padding-bottom: 0;
}

div.user-menu-btns div.btn-group-vertical>a.active:after {
	content: '';
	position: absolute;
	left: 100%;
	top: 50%;
	margin-top: -13px;
	border-left: 0;
	border-bottom: 13px solid transparent;
	border-top: 13px solid transparent;
	border-left: 10px solid #46D8D2;
}
/* -- The main tab & content styling of the vertical buttons info-- */
div.user-menu-content {
	color: #323232;
}

ul.user-menu-list {
	list-style: none;
	margin-top: 20px;
	margin-bottom: 0;
	padding: 10px;
	border: 1px solid #eee;
}

ul.user-menu-list>li {
	padding-bottom: 8px;
	text-align: center;
}

div.user-menu div.user-menu-content:not (.active ){
	display: none;
}

/* -- The btn stylings for the btn icons -- */
.btn-label {
	position: relative;
	left: -12px;
	display: inline-block;
	padding: 6px 12px;
	background: rgba(0, 0, 0, 0.15);
	border-radius: 3px 0 0 3px;
}

.btn-labeled {
	padding-top: 0;
	padding-bottom: 0;
}

/* -- Custom classes for the snippet, won't effect any existing bootstrap classes of your site, but can be reused. -- */
.user-pad {
	padding: 20px 25px;
}

.no-pad {
	padding-right: 0;
	padding-left: 0;
	padding-bottom: 0;
}

.user-details {
	background: #eee;
	min-height: 333px;
}

.user-image {
	max-height: 200px;
	overflow: hidden;
}

.overview h3 {
	font-weight: 300;
	margin-top: 15px;
	margin: 10px 0 0 0;
}

.overview h4 {
	font-weight: bold !important;
	font-size: 40px;
	margin-top: 0;
}

.view {
	position: relative;
	overflow: hidden;
	margin-top: 10px;
}

.view p {
	margin-top: 20px;
	margin-bottom: 0;
}

.caption {
	position: absolute;
	top: 0;
	right: 0;
	background: rgba(70, 216, 210, 0.44);
	width: 100%;
	height: 100%;
	padding: 2%;
	display: none;
	text-align: center;
	color: #fff !important;
	z-index: 2;
}

.caption a {
	padding-right: 10px;
	color: #fff;
}

.info {
	display: block;
	padding: 10px;
	background: #eee;
	text-transform: uppercase;
	font-weight: 300;
	text-align: right;
}

.info p, .stats p {
	margin-bottom: 0;
}

.stats {
	display: block;
	padding: 10px;
	color: white;
}

.share-links {
	border: 1px solid #eee;
	padding: 15px;
	margin-top: 15px;
}

.square, .btn {
	border-radius: 0px !important;
}

/* -- media query for user profile image -- */
@media ( max-width : 767px) {
	.user-image {
		max-height: 400px;
	}
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
				<a href="home.do" class="site-brand pull-left"><strong>Petagram</strong></a>
				<div class="row">
					<div class="col-md-4 col-md-offset-6">
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
		<div class="site-banner">
			<div class="container">

				<div class="row user-menu-container square">
					<div class="col-md-7 user-details">
						<div class="row coralbg white">
							<div class="col-md-6 no-pad">
								<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
								<c:choose>
									<c:when test="${followable == null}">
										<div class="user-pad">
											<h2>Welcome back! ${viewUser.getUserName() }</h2>
										</div>
									</c:when>
									<c:when test="${followable.equals(\"followed\")}">
										<div class="user-pad">
											<h2>${viewUser.getUserName() }</h2>
											<a type="button" class="btn btn-labeled btn-info"
												href="connection.do?action=unfollow&id=${viewUser.getId() }">
												<span class="btn-label"><i class="fa fa-minus"></i></span>Following
											</a>
										</div>
									</c:when>
									<c:when test="${followable.equals(\"follow\")}">
										<div class="user-pad">
											<h2>${viewUser.getUserName() }</h2>
											<a type="button" class="btn btn-labeled btn-info"
												href="connection.do?action=follow&id=${viewUser.getId() }">
												<span class="btn-label"><i class="fa fa-plus"></i></span>Follow
											</a>
										</div>
									</c:when>
									<c:otherwise>
									</c:otherwise>
								</c:choose>
							</div>
							<div class="col-md-6 no-pad">
								<div class="user-image">
									<img
										src="https://secure.gravatar.com/avatar/de9b11d0f9c0569ba917393ed5e5b3ab?s=140&r=g&d=mm"
										class="img-responsive thumbnail">
								</div>
							</div>
						</div>
						<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"
							prefix="fn"%>
						<div class="row overview">
							<div class="col-md-4 user-pad text-center">
								<h3 style="color: #FF44AA">FOLLOWERS</h3>
								<a
									href="view-user.do?action=follower&userName=${viewUser.getUserName() }">
									<h4>${fn:length(followers)}</h4>
								</a>
							</div>
							<div class="col-md-4 user-pad text-center">
								<h3 style="color: #FF44AA">FOLLOWING</h3>
								<a
									href="view-user.do?action=followed&userName=${viewUser.getUserName() }">
									<h4>${fn:length(followeds)}</h4>
								</a>
							</div>
							<div class="col-md-4 user-pad text-center">
								<h3 style="color: #FF44AA">HUGS</h3>
								<h4>${likes }</h4>
							</div>
						</div>
					</div>
					<div class="col-md-1 user-menu-btns">
						<div class="btn-group-vertical square" id="responsive">
							<a href="#" class="btn btn-block btn-default active"> <i
								class="fa fa-eye fa-3x"></i>
							</a> <a href="#" class="btn btn-default"> <i
								class="fa fa-heart fa-3x"></i>
							</a> <a href="#" class="btn btn-default"> <i
								class="fa fa-user fa-3x"></i>
							</a> <a href="#" class="btn btn-default"> <i
								class="fa fa-edit fa-3x"></i>
							</a>
						</div>
					</div>
					<div class="col-md-4 user-menu">
						<div class="user-menu-content active">
							<div id="visit_trend"></div>
						</div>
						<div class="user-menu-content">
							<div id="like_trend"></div>
						</div>
						<div class="user-menu-content">
							<div id="follower_trend"></div>
						</div>
						<div class="user-menu-content">
							<div id="comment_trend"></div>
						</div>
					</div>
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
