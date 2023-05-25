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
	    	<div class="" style="width:100%; height:200px;" id="moreDiv">
	    		<div id="mouse"></div>
	    	</div>
			
	</div>
	
	<script>
			// 하단 div
			var footerDiv = document.getElementById('moreDiv');
			// items 감싼 div
			var logContainer = document.getElementById('logListContainer');
			// 하단 div의 부모요소
			var parent = footerDiv.parentElement;
			
			// item부르는 가중치
			let cnt = 1;
			
			// 스크롤 감지
			logContainer.onscroll = function(e) {
			    //추가
			    
			    // 하단의 박스의 높이에서 logListContainer의 높이를 뺌 -> 절대값
			    var distance = footerDiv.getBoundingClientRect().top - parent.getBoundingClientRect().top;
			    
				// 옵션
		    	let option = {
					    threshold: 1.0,
					};
		    	
		    	const io = new IntersectionObserver(entries => {
		    	
					  entries.forEach(entry => {
					    // 관찰 대상이 viewport 안에 들어온 경우
					    if (entry.isIntersecting) {
					    	
							let typeId = $('#logBoxListWrapper').data('type-id');
							
							// cnt 테스트 -> 12개까지는 되는데 18개까지 되는지 확인해야함
							timer = setTimeout(() => $.ajax({
								url:"/training/log/more?typeId="+typeId,
								data: {"cnt" : cnt},
								success : function(data) { // 해석된 html 전체가 내려온다. 조각페이지가 내려오겠지
									
									// 서버응답 성공 시
									if(data.code == 1) {
										$.ajax({
											url:"/training/more_list_view?typeId="+typeId,
											data: {"cnt" : cnt},
											success : function(data) { 
												cnt+=1;
												$('#logBoxListWrapper').append(data); // 이전내용을 지우고 새로운 내용을 넣는다.
												
											}, 
											error : function(request, error, status) {
												alert("더보기를 실패했습니다. 관리자에게 문의바랍니다.");
											}
										});
										
									} else { // 서버에 요청했을때 더이상 부를 Item이 없을때
										return;
									}
								}
							}), 450); // 불러지는 밀리세컨즈?설정
							
							io.unobserve(footerDiv);
							
							return;
					    }
					    // 화면에서 제외된경우
					    else {
					    	return;
					    }
					  })
					}, option);
			    
					// 실제 실행 위치 하단부와 listContainer를 뺀 절댓값이 435고정
					// console.log(distance); 자신의 영역의 값이 얼만지 알아야함
					if(distance == 435) {
					  	//실행할 로직 (콘텐츠 추가)
						io.observe(footerDiv);
					  	return;
					} else {
						return;
					}
			};

	
		$(document).ready(function() {
			// 동적 추가된 log 클릭할 수 있게
			$(document).on('click', '.type-log-box', function() {
				let logId = $(this).data('log-id');
				location.href = "/training/log_detail_view/" + logId;	
			});
		})

	</script>
</div>