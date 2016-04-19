
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- <p style="font-size: medium">Positive feedback.</p> -->
<c:if test="${message !=null}">
	<p style="font-size: medium">${message}</p>
</c:if>
<!-- <p style="font-size: medium; color: red">Negative feedback.</p> -->
<div>
	<c:if test="${errors !=null && fn:length(errors) > 0}">
		<div class="alert alert-warning">
			<h4>
				<img src="img/Warning.png" style="width: 20px; height: 20px">
				There was a problem with your request
			</h4>

			<c:forEach var="error" items="${errors}">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${error}<br>
			</c:forEach>
		</div>
	</c:if>
</div>
