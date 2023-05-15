<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="container" id="pageWrapper">
    <div id="logWrapper">
        <h3>일지 작성</h3>
        <div class="log-header d-flex align-items-end justify-content-around">
            <!-- <div>
                <select name="" id="logType" class="custom-select form-control">
                    <option value="0">배변훈련</option>
                    <option value="1">사회화훈련</option>
                    <option value="2">기본훈련</option>
                    <option value="3">고급훈련</option>
                </select>
           </div> -->
           <input type="hidden" data-type-id="${typeId}" id="logTypeValue"/>
           <div>2023-02-02</div>
           <div>2023-04-10</div>
       </div>
       <hr class="log-hr">
       <div class="d-flex mt-5 align-items-center justify-content-between">
           <div class="log-title-box">
               <p class="log-title">제목</p>
               <div><img src="/static/img/bgPink1.png" alt="" width="110"></div>
               <textarea name="" id="logTitle" cols="50" rows="1" class="log-content" placeholder="제목을 입력해주세요."></textarea>
           </div>
           <div class="log-success-check-container">
               <p class="log-success-title">훈련성공 여부</p>
               <div class="text-center"><img src="/static/img/bgPink1.png" alt="" width="160"></div>
               <div class="log-success-check-box d-flex justify-content-around">
	               <label class="text-primary"><input type="radio" name="successCheck" value="1">성공</label>
	               <label class="text-danger"><input type="radio" name="successCheck" value="0">실패</label>

                   <!-- <div class="col-6 mx-auto text-center"><input type="checkbox" class="btn col-8">성공</div>
                   <div class="col-6 mx-auto text-center"><input type="checkbox" class="btn col-8">실패</div> -->
               </div>
           </div>
       </div>
       <div class="log-container mt-5">
           <div class="content-item">
               <p class="">문제 원인</p>
               <textarea name="" id="logProblem" cols="50" rows="4" class="log-content" placeholder="내용을 입력해주세요."></textarea>
           </div>
           <div class="content-item">
               <p class="">훈련 내용</p>
               <textarea name="" id="logContent" cols="50" rows="10" class="log-content" placeholder="내용을 입력해주세요."></textarea>
           </div>
           <div class="content-item">
               <p class="">사진 업로드</p>
               <div class="form-group d-flex flex-column justify-content-between">
                   <!-- <input type="text" id="imgUrl"/> -->
                   <div class="mb-5"><input type="file" class="file" id="file" name="file" accept=".jpg, .jpeg, .png, .gif"></div>
                   <div class="mx-auto">
                       <img id="imgPreview" style="border:0;">
                    </div>
                    
                </div>
            </div>
        </div>
        <div class="text-center mt-5">
            <button type="button" class="btn btn-lg" id="logCreateBtn">작성하기</button>
        </div>
    </div>
	<script>
	    $(document).ready(function() {
	        
	        // img 미리보기
	        $("#file").on("change", function(event) {
	            var file = event.target.files[0];
	            var reader = new FileReader(); 
	            reader.onload = function(e) {
	                $("#imgPreview").attr("src", e.target.result);
	            }
	            reader.readAsDataURL(file);
	        });
	
	        // ajax 통신
	        $("#logCreateBtn").on('click', function() {
	        	let typeId = $('#logTypeValue').data('type-id');
	            let title = $('#logTitle').val();
	            let successCheck = $('input:radio[name=successCheck]').is(':checked');
	            let problem = $('#logProblem').val();
	            let content = $('#logContent').val();
	            let file = $('#file').val();
	            alert(typeId);
	            
	            //validation
	        	if (!title) {
	        		alert("훈련제목을 입력해주세요.");
	        		return false;
	        	}
	        	if (!successCheck) {
	        		alert("훈련성공여부를 선택해주세요.");
	        		return false;
	        	}
	        	if (!content) {
	        		alert("내용을 입력해주세요.");
	        		return false;
	        	}
	        	if (!file) {
	        		alert("사진을 선택해주세요.");
	        		return false;
	        	}
	            
	            
	            let formData = new FormData();
	            formData.append("typeId", typeId);
	            formData.append("title", title);
	            formData.append("successCheck", successCheck);
	            formData.append("problem", problem);
	            formData.append("content", content);
				formData.append("file", $('#file')[0].files[0]);
	    
	            $.ajax({
	            	type: "post",
	            	url : "/training/log_create",
	            	data : formData,
					enctype : "multipart/form-data" // 파일 업로드를 위한 필수 설정
					, processData : false // 파일 업로드를 위한 필수 설정
					, contentType : false
	            	, success : function(data) {
	            		
	            		if (data.code == 1) {
	            			location.href="/main"; // 임시 : 메인페이지로 이동 완성되면 일지 리스트로 가기 
	            		} else {
	            			alert(data.errorMessage);
	            		}
	            	}
	            	, error : function(status, request, error) {
	            		alert("회원가입에 실패했습니다. 관리자에게 문의바랍니다.");
	            	}
	            	
	            });
	        })
	    })
	</script>
</div>