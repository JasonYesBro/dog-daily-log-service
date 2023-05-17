<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="typeLogGroup" class="container">
	<h4 class="text-center">훈련일지 목록</h4>
	<div id="logListContainer">
		    <c:if test="${empty trainingLogList eq true}"> 
			    <div class="d-flex flex-column align-items-center mt-5">
			    	<h3>아직 훈련일지를 작성하지 않으셨네요.</h3>
			    	<p><a href="/training/log_create_view" class="text-primary" style="font-size:30px;">훈련일지 작성하러 GO!</a></p>
			    </div>
		    </c:if>
	        <div class="d-flex justify-content-start flex-wrap mt-3">
	        
			<c:forEach items="${trainingLogList}" var="trainingLog">
				<div class="type-log-box" data-log-id="${trainingLog.id}">
					<img src="${trainingLog.imagePath }" class="log-img" alt="훈련일지 이미지">
					<div class="d-flex flex-column font-weight-bold ">
						<div><span>제목 : </span><span>${trainingLog.title}</span></div>
						 <fmt:formatDate var="createdAt" value="${trainingLog.createdAt}" pattern="yyyy-MM-dd" />
						<div><span>작성날짜 : </span><span>${createdAt}</span></div>
					</div>
				</div>
			</c:forEach>
	    	</div>
	</div>
	
	<script>
		$(document).ready(function() {
			$('.type-log-box').on('click', function() {
				let logId = $(this).data('log-id');
				location.href = "/training/log_detail_view/" + logId;	
			});
		})
	</script>
</div>