<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="typeLogGroup" class="container">
	<h4 class="text-center">훈련일지 목록</h4>
	<div id="logListContainer">
	        <div class="d-flex justify-content-start flex-wrap mt-3">
		    <c:if test="${empty trainingLogList eq true}"> 
		    	<h3 class="text-center text-primary mt-5">아직 훈련일지를 작성하지 않으셨네요.</h3>
		    	<p class="text-center text-primary mt-5"><a href="/training/log_create_view">훈련일지 작성하러 GO!</a></p>
		    </c:if>
	        
			<c:forEach items="${trainingLogList}" var="trainingLog">
				<div class="type-log-box" data-log-id="${trainingLog.id}">
					<img src="${trainingLog.imagePath }" class="log-img" alt="훈련일지 이미지">
					<div class="d-flex flex-column">
						<span>${trainingLog.title}</span>
						 <fmt:formatDate var="createdAt" value="${trainingLog.createdAt}" pattern="yyyy-MM-dd" />
						<span>${createdAt}</span>
					</div>
				</div>
			</c:forEach>
	    	</div>
	</div>
	
	<script>
		$(document).ready(function() {
			$('.type-log-box').on('click', function() {
				let logId = $(this).data('log-id');
				alert(logId);
			});
		})
	</script>
</div>