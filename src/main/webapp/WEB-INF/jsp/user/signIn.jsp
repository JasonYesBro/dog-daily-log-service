<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="signUpWrpper" class="container d-flex flex-column align-items-center font-weight-bold">
	<form action="/user/sign_in" method="post" id="signInForm">
	    <div class="form-group input-box">
	        <label for="email">이메일</label>
	        <input type="text" name="email" class="form-control col-12" id="email" placeholder="이메일을 입력해주세요">
	    </div>
	    <div class="form-group input-box">
	        <div class="d-flex justify-content-between align-items-center">
	            <label for="password">비밀번호</label><a href="/user/reset_pwd_view" class="reset-pwd">비밀번호 재설정</a>
	        </div>
	        <input type="password" name="password" class="form-control col-12" id="password" placeholder="비밀번호를 입력해주세요">
	    </div>
	    <button type="submit" class="btn col-12 sign-up-btn" id="signInBtn">로그인</button>
	
	    <!-- signIn page로 -->
	    <div class="mt-2">
	        <a href="/kakao/oauth" id="goSignIn">카카오 계정으로 로그인하기</a>
	    </div>
	</form>
    
    <script>
    	$(document).ready(function(){
    		
    		$("#signInForm").on("submit", function(e) {
    			e.preventDefault();
    			
    			let email = $("#email").val().trim();
    			let password = $("#password").val();
    			
    			if (!email) {
    				alert('이메일을 입력하세요.');
    				return false;
    			}
    			if (!password) {
    				alert('비밀번호를 입력하세요.');
    				return false;
    			}
    			
    			let url = $('#signInForm').attr('action');
    			let params = $(this).serialize();
    			
    			
    			$.post(url, params)
    			.done(function(data) {
    				if (data.code == 1) {
    					alert("로그인에 성공하였습니다.");
    					location.href = "/main";
    				} else {
    					alert(data.errorMessage);
    				}
    			});
    			
    		});
    		
    	})
 	</script>
</div>