<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="signUpWrpper" class="container d-flex flex-column align-items-center font-weight-bold">
	<form action="/user/sign_up" method="post" class="" id="signUpForm">
		<div class="form-group input-box">
		    <label for="puppyName">강아지 이름</label>
		    <div class="d-flex justify-content-between">
			    <input type="text" name="puppyName" class="form-control col-8" id="puppyName" placeholder="반려견 이름을 입력해주세요">
			    <img id ="targetImg" src="/static/img/fileImg.png" width=40>
		    </div>
		    <div class="form-group d-flex">
				<input type="text" id="imgUrl"/>
				<input type="file" class="d-none" id="file" name="file" accept=".jpg, .jpeg, .png, .gif">
			</div>
		</div>
		<div class="form-group input-box">
		    <label for="datepicker">입양날짜</label>
		    <input type="text" name="adoptionDate" class="adoptionDate form-control col-5" id="datepicker" placeholder="">
		</div>
		<div class="form-group input-box">
		    <button type="submit" class="btn col-12 sign-up-btn" id="signUpBtn">회원가입하기</button>
		</div>
	</form>
	<!-- signIn page로 -->
	<div class="mt-2">
	    <a href="/user/sign_up_view" id="goSignIn">다른 계정으로 로그인하기</a>
	</div>
	
	<script>
		$(document).ready(function() {
			$.datepicker.setDefaults({
                dateFormat: 'yy-mm-dd'
            });
    
            $( "#datepicker" ).datepicker();
			$('#targetImg').click(function (e) {
                e.preventDefault();
				$('#file').click();
            });
			
			$('#file').on('change', function() {
	            let file = $('#file').val();
	          	$('#imgUrl').val(file);
            });
			// 회원가입
            $('#signUpForm').on('submit', function(e) {
            	e.preventDefault();
            	
	            let name = '${userName}';
	            let puppyName = $("#puppyName").val().trim();
				let email = '${userEmail}';
				/* let userId = '${userId}'; */
	            let adoptionDate = $(".adoptionDate").val();
	            let file = $("#imgUrl").val();
	            
	            // validation
	            if (!puppyName) {
	            	alert("반려견이름을 입력해주세요.");
	            	return false;
	            }
	            if (!adoptionDate) {
	            	alert("입양날짜를 입력해주세요.");
	            	return false;
	            }
	            
	            let url = $(this).attr('action');
	            let params = $(this).serialize();
	            
	            let formData = new FormData();
	            formData.append("name", name);
	            formData.append("puppyName", puppyName);
	            formData.append("email", email);
	            formData.append("adoptionDate", adoptionDate);
				formData.append("file", $('#file')[0].files[0]);
	            formData.append("signUpType", 1);
				
	            $.ajax({
	            	type: "post",
	            	url : url,
	            	data : formData,
					enctype : "multipart/form-data" // 파일 업로드를 위한 필수 설정
					, processData : false // 파일 업로드를 위한 필수 설정
					, contentType : false
	            	, success : function(data) {
	            		if (data.code == 1) {
	            			alert("가입을 환영합니다. 바로 일지를 작성해보세요!");
	            			location.href="/main"; // 메인페이지로 이동 
	            		} else {
	            			alert(data.errorMessage);
	            		}
	            	}
	            	, error : function(status, request, error) {
	            		alert("회원가입에 실패했습니다. 관리자에게 문의바랍니다.");
	            	}
	            	
	            });
	            
            });	
		})
	</script>
</div>