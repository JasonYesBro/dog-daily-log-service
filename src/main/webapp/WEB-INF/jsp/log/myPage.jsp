<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="container" id="myPageWrapper">
    <div id="myPageTitleContainer">
        <h3 id="myPageTitle">마이페이지</h3>
        <img src="/Cute dog logo template.png" alt="" width="30">
    </div>
    <div id="infoContainer" class="d-flex align-items-center">
        <div>
            <img src="/images/tmdgus5611@gmail.com_1683701297206/profileImg.png" alt="반려견 사진" width="150">
        </div>
        <div class="d-flex align-items-center ml-5">
            <span>${userInfo.puppyName}</span>
        </div>
    </div>
    <div class="text-center" id="trainingSubTitle">
        <h4 id="trainingTitle">진행중인 훈련</h4>
        <div><img src="/static/img/greenBc.png" alt="" width="150"></div>
    </div>
    <div id="trainingContainer" class="d-flex flex-wrap justify-content-start">
    	
    	<c:forEach items="${trainingTypeList}" var="trainingType">
	        <div class="training-box d-flex flex-column" data-type-id="${trainingType.id}">
	        	<div class="d-flex align-items-center">
		        <c:choose>
		        	<c:when test="${trainingType.trainingType == 0}">
		        	
			            <h3 class="training-type display-5">배변 훈련</h3>	        	
		        	</c:when>
		        	<c:when test="${trainingType.trainingType == 1}">
		        	
		        		<h3 class="training-type display-5">사회화 훈련</h3>	
		        	</c:when>
		        	<c:when test="${trainingType.trainingType == 2}">
		        	
		        		<h3 class="training-type display-5">기본 훈련</h3>	
		        	</c:when>
		        	<c:when test="${trainingType.trainingType == 3}">
		        		<h3 class="training-type display-5">고급 훈련</h3>	
		        	</c:when>
		        </c:choose>
	        		<span class="ml-3"><${trainingType.trainingTitle}></span>
	        	</div>
	        	
	        	<div class="text-muted">
	        		<fmt:formatDate var="startedAt" value="${trainingType.startedAt}" pattern="yyyy-MM-dd" />
          			<fmt:formatDate var="finishedAt" value="${trainingType.finishedAt}" pattern="yyyy-MM-dd" />
	        		<span>${startedAt} ~ ${finishedAt} </span>
	        	</div>
	        	
	            <div class="d-flex justify-content-end">
	                <span>2</span>
	                <span>/</span>
	                <span>10</span>
	            </div>
	            
	            <div id="myProgress">
	                <div id="myBar"></div>
	            </div>
	            <div class="cheerUpContainer">
	                <!-- 진행상황 기준에 따른 응원의 말 동적 변화 -->
	                <p class="cheerUpStatus d-none">시작이 반이에요!</p>
	                <p class="cheerUpStatus ">조금만 더 화이팅해요!</p>
	                <p class="cheerUpStatus d-none">아주 잘하고 있어요!</p>
	                <p class="cheerUpStatus d-none">대회에 나가도 되겠어요!</p>
	            </div>
	            <div class="plusContainer d-flex justify-content-end align-items-end">
	                <a href="/training/log_create_view?typeId=${trainingType.id}"><img src="/static/img/plusItem.png" alt="훈련일지 추가이미지" width="50" class="plus-training-img" data-type-id="${trainingType.id}"></a>
	        	</div>
       		</div>
        </c:forEach>
        
        <!-- 빈 상자 : 훈련타입 생성 상자 -->
        <!-- 훈련타입 생성 상자가 무조건 1개는 존재해야함-->
        <!-- 만약 사용자가 상자를 클릭해서 타입을 하나 만들었다면 동적으로 하나더 빈상자를 생성시켜야 함-->
        <div class="training-empty-box d-flex flex-column">
            <div class="plusTypeContainer">
                <img src="/static/img/plusItem.png" alt="훈련타입 추가이미지" width="150" class="plus-type-img" data-toggle="modal" data-target="#modal" data-post-id="${card.post.id}">
            </div>
        </div>
    </div>
    <div class="modal fade" id="modal">
	    <!-- modal-dialog-centered 수직가운데 정렬 -->
	    <div class="modal-dialog modal-dialog-centered modal-lg">
	        <div class="modal-content text-center" id="typeModal">
	            <div class="modal-body">
	                <h3 class="mb-4">훈련 타입을 생성해주세요.</h3>
	                <div class="form-group col-5">
	                    <select class="custom-select form-control" id="trainingType">
	                        <option value="" selected disabled>선택하세요</option>
	                        <option value="0">배변 훈련</option>
	                        <option value="1">사회화 훈련</option>
	                        <option value="2">기본 훈련</option>
	                        <option value="3">고급 훈련</option>
	                    </select>
	                </div>
	                <div class="form-group col-10">
	                    <input type="text" id="trainingTypeTitle" class="form-control" placeholder="훈련제목을 입력해주세요.">
	                </div>
	                <div class="form-group d-flex align-items-center justify-content-between col-11">
	                    <input type="text" class="form-control col-5" placeholder="훈련시작 날짜" id="trainingStartDate">
	                    <span>~</span>
	                    <input type="text" class="form-control col-5" placeholder="훈련종료 날짜" id="traingingFinishDate">
	                </div>
	            </div>
	            <div class="modal-footer">
	                <!-- data-dismiss="modal" 모달닫기 -->
	                <div class="py-2 btn" data-dismiss="modal">
	                    <a href="#" class="text-danger">취소하기</a>
	                </div>
	                <!-- 훈련 타입 생성버튼으로 타입 생성 API 연결 -->
	                <button type="button" class="btn type-create-btn">훈련타입 생성하기</button>
	            </div>
	        </div>
	    </div>
	</div>
	<script>
	    $(document).ready(function() {
	        $.datepicker.setDefaults({
	            dateFormat: 'yy-mm-dd'
	        });
	        
	        $( "#trainingStartDate" ).datepicker();
	        $( "#traingingFinishDate" ).datepicker();
	        
	        $(".type-create-btn").on('click', function() {
	        	let trainingType = $("#trainingType option:selected").val();
	        	let trainingTitle = $("#trainingTypeTitle").val();
	        	let startedAt = $("#trainingStartDate").val();
	        	let finishedAt = $("#traingingFinishDate").val();
	        	
	        	// validation
	        	if (!trainingType) {
	        		alert("훈련타입을 선택해주세요.");
	        		return false;
	        	}
	        	if (!trainingTitle) {
	        		alert("훈련제목을 입력해주세요.");
	        		return false;
	        	}
	        	if (!startedAt) {
	        		alert("훈련시작날짜를 선택해주세요.");
	        		return false;
	        	}
	        	if (!finishedAt) {
	        		alert("훈련종료날짜를 선택해주세요.");
	        		return false;
	        	}
	        	
	        	$.ajax({
	        		type: "post",
	        		url : "/training/type/create",
	        		data : {
	        			"trainingType" : trainingType
	        			, "trainingTitle" : trainingTitle
	        			, "startedAt" : startedAt
	        			, "finishedAt" : finishedAt
	        		},
	        		
	        		success : function(data) {
	        			if(data.code == 1){
	        				alert("훈련타입이 추가되었습니다.");
	        				location.reload(true);
	        			} else {
	        				alert(data.errorMessage)
	        			}
	        		},
	        		error : function(status, error, request) {
	        			alert("관리자에게 문의바랍니다.");
	        		}
	        	});
	        });
	        
	        /* $('.plus-training-img').on('click', function() {
	        	
	        	// typeId 넘겨주기
	        	let typeId = $(this).data('type-id');
	        
	        }); */
	        
	        // type 클릭시 -> log List 화면
	        $('.training-box').on('click', function() {
	        	let typeId = $(this).data('type-id');
	        	
	        	location.href = "/training/log_list_view?typeId="+ typeId;
	        	
	        })
	    })
	</script>
</div>