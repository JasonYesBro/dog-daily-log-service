<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="container" id="myPageWrapper">
    <div id="myPageTitleContainer">
        <h3 id="myPageTitle">마이페이지</h3>
        <img src="/Cute dog logo template.png" alt="" width="30">
    </div>
    <div id="infoContainer" class="d-flex align-items-center">
        <div>
            <img src="/static/img/basicPuppyProfile.png" alt="반려견 사진" width="150">
        </div>
        <div class="d-flex align-items-center ml-5">
            <span>반려견 이름</span>
        </div>
    </div>
    <div class="text-center" id="trainingSubTitle">
        <h4 id="trainingTitle">진행중인 훈련</h4>
        <div><img src="/static/img/greenBc.png" alt="" width="150"></div>
    </div>
    <div id="trainingContainer" class="d-flex flex-wrap justify-content-start">
        <div class="training-box d-flex flex-column">
            <h3 class="training-type display-5">기본 훈련</h3>
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
                <img src="/static/img/plusItem.png" alt="훈련일지 추가이미지" width="50" class="plus-training-img">
            </div>
        </div>
        <div class="training-box d-flex flex-column">
            <h3 class="training-type display-5">기본 훈련</h3>
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
                <img src="/static/img/plusItem.png" alt="훈련일지 추가이미지" width="50" class="plus-training-img">
            </div>
        </div>
        <div class="training-box d-flex flex-column">
            <h3 class="training-type display-5">기본 훈련</h3>
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
                <img src="/static/img/plusItem.png" alt="훈련일지 추가이미지" width="50" class="plus-training-img">
            </div>
        </div>
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
	                    <select class="custom-select form-control">
	                        <option value="" selected disabled>선택하세요</option>
	                        <option value="basicTraining">기본 훈련</option>
	                        <option value="basicTraining">배변 훈련</option>
	                        <option value="basicTraining">고급 훈련</option>
	                    </select>
	                </div>
	                <div class="form-group col-10">
	                    <input type="text" class="form-control" placeholder="훈련제목을 입력해주세요.">
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
	    })
	</script>
</div>