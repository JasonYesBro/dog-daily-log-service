<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="typeLogGroup" class="container">
	<h4 class="text-center">내가 작성한 훈련일지</h4>
	<div id="logListContainer">
			<%-- type의 기간이 종료되었다면 훈련일지 작성 또한 안되게 해야함 --%>
		    <c:if test="${empty trainingLogList eq true}"> 
			    <div class="d-flex flex-column align-items-center mt-5">
			    	<h3>아직 훈련일지를 작성하지 않으셨네요.</h3>
			    	<p><a href="/training/log_create_view" class="text-primary" style="font-size:30px;">훈련일지 작성하러 GO!</a></p>
			    </div>
		    </c:if>
	        <div class="d-flex justify-content-start flex-wrap mt-3" id="logBoxListWrapper" data-type-id="${trainingLogList[0].typeId}">
	        
				<c:forEach items="${trainingLogList}" var="trainingLog">
					<div class="type-log-box" data-log-id="${trainingLog.id}">
						<div class="log-img-box"><img src="${trainingLog.imagePath }" class="log-img" alt="훈련일지 이미지"></div>
						<div class="d-flex flex-column font-weight-bold" style="height:20%;">
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
			
			
			$("#logBoxListWrapper").on("mousewheel", function (e) {
				let wheel = e.originalEvent.wheelDelta;
				
				let cnt = 0;
				// 적정 수준이상 스크롤 내릴때
				if (wheel < -100) {
					cnt+=1;
					
					console.log("적정"+ wheel);
					console.log(cnt);
					
					let typeId = $(this).data('type-id');
					
					/* $.ajax({
						url:"/training/more_list_view?typeId="+typeId,
						success : function(data) { // 해석된 html 전체가 내려온다. 조각페이지가 내려오겠지
							$('#logBoxListWrapper').html(data); // 이전내용을 지우고 새로운 내용을 넣는다.
						}
					}); */
					
				 	return false;
				}
			});
		})
	</script>
</div>