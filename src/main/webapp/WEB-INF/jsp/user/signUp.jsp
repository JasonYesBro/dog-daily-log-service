<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="signUpWrpper" class="container d-flex flex-column align-items-center font-weight-bold">
	<form action="/user/sign_up" method="post" class="" id="signUpForm">
		<div class="form-group input-box">
		    <label for="name">닉네임</label>
		    <div class="d-flex justify-content-between">
		        <input type="text" name="name" class="form-control col-8" id="name" placeholder="닉네임을 입력해주세요">
		        <button class="btn sign-up-btn">중복확인</button>
		    </div>
		</div>
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
		    <label for="email">이메일</label>
		    <div class="d-flex align-items-center justify-content-between">    
		        <input type="text" name="email" class="form-control col-4" id="email" placeholder="">
		        <span>@</span>
		        <select class="custom-select form-control col-4">
		            <option value="" selected disabled>선택하세요</option>
		            <option value="naver.com">naver.com</option>
		            <option value="nate.com">nate.com</option>
		            <option value="nate.com">nate.com</option>
		            <option value="gmail.com">gmail.com</option>
		            <option value="yahoo.com">yahoo.com</option>
		            <option value="hanmail.net">hanmail.net</option>
		        </select>
		        <button class="btn sign-up-btn" id="verifyBtn">인증하기</button>
		    </div>
		    <div class="font-weight-bold" style="font-size:13px">
		        <span class="text-primary d-none">인증이 되었습니다.</span>
		        <span class="text-danger d-none">인증이 실패했습니다.</span>
		    </div>
		</div>
		<div class="form-group input-box">
		    <label for="password">비밀번호</label>
		    <input type="password" name="password" class="form-control col-10 mb-1" id="password" placeholder="비밀번호를 입력해주세요">
		    <input type="password" name="passwordConfirm" class="form-control col-10" id="passwordConfirm" placeholder="비밀번호를 재입력 입력해주세요">
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
	    <a href="" id="goSignIn">다른 계정으로 로그인하기</a>
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
            
            $('#signUpForm').on('submit', function(e) {
            	e.preventDefault();
	            let name = $("#name").val().trim();
	            let puppyName = $("#puppyName").val().trim();
	            let email = $("#email").val().trim();
	            let password = $("#password").val();
	            let confirmPassword = $("#passwordConfirm").val();
	            let adoptionDate = $(".adoptionDate").val();
	            let file = $("#imgUrl").val();
	            
	            // validation
	            if (!name) {
	            	alert("닉네임을 입력해주세요.");
					return false;
	            }
	            if (!puppyName) {
	            	alert("반려견이름을 입력해주세요.");
	            	return false;
	            }
	            if (!email) {
	            	alert("이메일을 입력해주세요.");
	            	return false;
	            }
	            if (!password) {
	            	alert("비밀번호를 입력해주세요.");
	            	return false;
	            }
	            if (!password || !confirmPassword) {
	            	alert("비밀번호를 확인해주세요.");
	            	return false;
	            }
	            
	            let url = $(this).attr('action');
	            let params = $(this).serialize();
	            
	            //  enctype="multipart/form-data" 파일관련 속성 추가 안함 
	            $.post(url, params)
				.done(function(data) {
					if (data.code == 1) {
						alert("가입을 환영합니다. 바로 일지를 작성해보세요!");
						// 로그인 화면으로 이동
						/* location.href = "/user/sign_in_view"; */
					} else {
						alert(data.errorMessage);
					}
				});
	            
            });


           
        })
    </script>
</div>