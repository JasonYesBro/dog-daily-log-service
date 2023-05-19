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
	        <div class="d-flex justify-content-start flex-wrap mt-3" id="logBoxListWrapper">
	        
				<c:forEach items="${trainingLogList}" var="trainingLog">
					<div class="type-log-box" data-log-id="${trainingLog.id}">
						<div class="log-img-box"><img src="${trainingLog.imagePath }" class="log-img" alt="훈련일지 이미지"></div>
						<div class="d-flex flex-column font-weight-bold" style="height:20%;">
							<div><span>훈련제목 : </span><span>${trainingLog.title}</span></div>
							<fmt:formatDate var="createdAt" value="${trainingLog.createdAt}" pattern="yyyy-MM-dd" />
							<div><span>일지작성날짜 : </span><span>${createdAt}</span></div>
						</div>
					</div>
				</c:forEach>
				
	    	</div>
			<div class='d-flex justify-content-center mt-3 pb-5'><button class="btn btn-secondary" id="moreBtn" data-type-id="${trainingLogList[0].typeId}">더보기</button></div>
	</div>
	
	<script>
		$(document).ready(function() {
			$('.type-log-box').on('click', function() {
				let logId = $(this).data('log-id');
				location.href = "/training/log_detail_view/" + logId;	
			});
			
			
			let cnt = 1;
			
			$('#moreBtn').on('click', function() {
				let typeId = $(this).data('type-id');
				cnt+=1;
				
				$.ajax({
					url:"/training/more_list_view?typeId="+typeId,
					data: {"cnt" : cnt},
					success : function(data) { // 해석된 html 전체가 내려온다. 조각페이지가 내려오겠지
						$('#logBoxListWrapper').html(data); // 이전내용을 지우고 새로운 내용을 넣는다.
					}
				});
				
			});
						
		})
	</script>
</div>