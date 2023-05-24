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
							<div><span>훈련제목 : </span><span>${trainingLog.title}</span></div>
							<fmt:formatDate var="createdAt" value="${trainingLog.createdAt}" pattern="yyyy-MM-dd" />
							<div><span>일지작성날짜 : </span><span>${createdAt}</span></div>
						</div>
					</div>
				</c:forEach>
	    	</div>
			<!-- <div class='d-flex justify-content-center mt-3 pb-5'><button class="btn btn-secondary" id="moreBtn">더보기</button></div> -->
	</div>
	
	<script>
		$(document).ready(function() {
			// 추가된 마지막 요소 -> 동적추가된 요소라 다시 잡아야 할 수도,,,?
			let boxes = document.querySelector(".type-log-box:last-child");
			
			let option = {
				    threshold: 1.0,
				}
			
			const io = new IntersectionObserver(entries => {
				  entries.forEach(entry => {
				    // 관찰 대상이 viewport 안에 들어온 경우
				    if (entry.isIntersecting) {
				    	console.log('화면에 나타남');
			            let cnt = 1;
						let typeId = $('#logBoxListWrapper').data('type-id');
						cnt+=1;
						
						// cnt 테스트 -> 12개까지는 되는데 18개까지 되는지 확인해야함
						timer = setTimeout(() => $.ajax({
							url:"/training/more_list_view?typeId="+typeId,
							data: {"cnt" : cnt},
							success : function(data) { // 해석된 html 전체가 내려온다. 조각페이지가 내려오겠지
								$('#logBoxListWrapper').html(data); // 이전내용을 지우고 새로운 내용을 넣는다.
							}
						}), 350)
						
				    }
				    // 그 외의 경우
				    else {
				    	console.log('화면에서 제외됨');
				    }
				  })
				}, option);

			io.observe(boxes);
			
			// 동적 추가된 log 클릭할 수 있게
			$(document).on('click', '.type-log-box', function() {
				let logId = $(this).data('log-id');
				location.href = "/training/log_detail_view/" + logId;	
			});
			
			/* let cnt = 1;			
			
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
				
			}); */
		})
	</script>
</div>