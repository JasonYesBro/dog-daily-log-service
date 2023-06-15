<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="container" id="pageWrapper">
    <div id="logWrapper">

       <div class="d-flex mt-5 align-items-center justify-content-between">
           <div class="log-title-box">
               <p class="log-title">제목</p>
               <div><img src="/static/img/bgPink1.png" alt="" width="110"></div>
               <textarea name="" id="logTitle" cols="50" rows="1" class="log-content" placeholder="제목을 입력해주세요.">${trainingLog.title}</textarea>
           </div>
           <div class="log-success-check-container">
               <p class="log-success-title">훈련성공 여부</p>
               <div class="text-center"><img src="/static/img/bgPink1.png" alt="" width="160"></div>
               <div class="log-success-check-box d-flex justify-content-around">
               
	               <c:if test="${trainingLog.successCheck eq 1}">
		               <label class="text-primary"><input type="radio" name="successCheck" value=1 checked>성공</label>               
		               <label class="text-danger"><input type="radio" name="successCheck" value=0>실패</label>
	               </c:if>
	                <c:if test="${trainingLog.successCheck eq 0}">
		               <label class="text-primary"><input type="radio" name="successCheck" value="1">성공</label>               
		               <label class="text-danger"><input type="radio" name="successCheck" value="0" checked>실패</label>
	               </c:if>
               
               </div>
           </div>
       </div>
       <div class="log-container mt-5">
           <div class="content-item">
               <p class="">문제 원인</p>
               <textarea name="" id="logProblem" cols="50" rows="4" class="log-content" placeholder="내용을 입력해주세요.">${trainingLog.problem}</textarea>
           </div>
           <div class="content-item">
               <p class="">훈련 내용</p>
               <textarea name="" id="logContent" cols="50" rows="10" class="log-content" placeholder="내용을 입력해주세요.">${trainingLog.content}</textarea>
           </div>
           <div class="content-item">
               <p class="">사진 업로드</p>
               <div class="form-group d-flex flex-column justify-content-between">
                   <!-- <input type="text" id="imgUrl"/> -->
                   <div class="mb-5"><input type="file" class="file" id="file" name="file" accept=".jpg, .jpeg, .png, .gif"></div>
                   <div class="mx-auto">
                       <img id="imgPreview" style="border:0;" src="${trainingLog.imagePath}">
                    </div>
                    
                </div>
            </div>
        </div>
        <div class="d-flex justify-content-between mt-5" style="width:55%">
	            <button type="button" class="btn btn-lg" data-log-id="${trainingLog.id}" id="logDeleteBtn">삭제하기</button>
	            <button type="button" class="btn btn-lg" data-log-id="${trainingLog.id}" id="logUpdateBtn">수정하기</button>
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
    		
    		// 수정하기버튼 클릭 시
    		$("#logUpdateBtn").on('click', function() {
    			let logId = $(this).data('log-id');
	            let title = $('#logTitle').val();
	            let successCheck = $('input:radio[name=successCheck]:checked').val();
	            let problem = $('#logProblem').val();
	            let content = $('#logContent').val();
	            let file = $('#file').val();
	            
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
	        	
	        	let formData = new FormData();
	            formData.append("logId", logId);
	            formData.append("title", title);
	            formData.append("successCheck", successCheck);
	            formData.append("problem", problem);
	            formData.append("content", content);
				formData.append("file", $('#file')[0].files[0]);
	    		
				// 일지 수정 ajax 통신
	           	$.ajax({
	            	type: "put",
	            	url : "/training/log/update",
	            	data : formData,
					enctype : "multipart/form-data" // 파일 업로드를 위한 필수 설정
					, processData : false // 파일 업로드를 위한 필수 설정
					, contentType : false
	            	, success : function(data) {
            			location.reload(true); // 새로고침 
	            	}
	            	, error : function(status, request, error) {
	            		alert("일지 수정에 실패했습니다. 관리자에게 문의바랍니다.");
	            	}
	            	
	            });
    		});
    		
    		// 일지 삭제 버튼 클릭 시
    		$('#logDeleteBtn').on('click', function() {
    			let logId = $(this).data('log-id');
    			
    			$.ajax({
    				type : "delete",
    				url : "/training/log/delete",
    				data : {"logId" : logId},
    				
    				success : function(data) {
    					if(data.code == 1) {
    						alert("일지가 삭제되었습니다.");
    						location.href="/main"; // 임시 처리
    					} else {
    						alert(data.errorMessage);
    					}
    				},
    				error : function(status, error, request) {
    					alert("관리자에게 문의바랍니다.");
    				}
    			});
    		});
    	})
    </script>
</div>