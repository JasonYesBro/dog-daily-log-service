<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="signUpWrpper" class="container d-flex flex-column align-items-center font-weight-bold">
	<form action="/user/sign_up" method="post" class="" id="signUpForm">
		<div class="form-group input-box">
		    <label for="name">닉네임</label>
		    <div class="d-flex justify-content-between">
		        <input type="text" name="name" class="form-control col-8" id="name" placeholder="닉네임을 입력해주세요">
		        <input type="hidden" id="nameDupCheck" value="0"/>
		        <button class="btn sign-up-btn" id="nameCheckBtn">중복확인</button>
		    </div>
		    <small id="nameCheckLength" class="text-danger d-none">닉네임을 4자 이상 입력해주세요.</small>
			<small id="nameCheckDuplicated" class="text-danger d-none">이미 사용중인 닉네임입니다.</small> 
			<small id="nameCheckOk" class="text-primary d-none">사용가능한 닉네임입니다.</small>
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
		        <input type="text" name="email" class="form-control col-5" id="email" placeholder="">
		        <span>@</span>
		        <select class="custom-select form-control col-5" id="domain">
		            <option value="" selected disabled>선택하세요</option>
		            <option value="naver.com">naver.com</option>
		            <option value="nate.com">nate.com</option>
		            <option value="nate.com">nate.com</option>
		            <option value="gmail.com">gmail.com</option>
		            <option value="yahoo.com">yahoo.com</option>
		            <option value="hanmail.net">hanmail.net</option>
		        </select>
		    </div>
		    <div class="d-flex justify-content-end mt-2">
	   	        <button class="btn sign-up-btn" id="verifyCodeEmailBtn">인증코드 보내기</button>
		    </div>		    
	        <div class="d-flex align-items-center justify-content-between mt-3">
	        	<label for="verifyCode">인증코드</label>
	        	<input type="text" name="verifyCode" id="verifyCode" class="form-control col-7" placeholder="인증코드를 입력해주세요." />
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
            
            // name 중복확인
            $('#nameCheckBtn').on('click', function(e) {
            	e.preventDefault();
    			// validation box 초기화
    			$('#nameCheckLength').addClass("d-none");
    			$('#nameCheckDuplicated').addClass("d-none");
    			$('#nameCheckOk').addClass("d-none");
    			
    			// 사용자가 입력한 loginId 변수에 할당
    			let name = $('#name').val().trim();
    			
    			
    			if (name.length < 4) {
    				$('#nameCheckLength').removeClass("d-none");
    				return false;
    			}
    		
    			$.ajax({
    				// request
    				type : "post"
    				, url: "/user/is_duplicated_name"
    				, data : {"name" : name}
    				
    				// response
    				, success : function(data) {
    					if(data.result) {
    						$('#nameCheckDuplicated').removeClass("d-none");
    						$("#nameDupCheck").val(1);
    					} else {
    						$('#nameCheckOk').removeClass("d-none");
    					}
    				}
    				, error : function(request, status, error) {
    					alert("관리자에게 문의바랍니다.");
    				}
    			});
    			
    			
    		});
            
            // 인증코드 보내기
            $('#verifyCodeEmailBtn').on('click', function(e) {
            	e.preventDefault();
            	
				let email = $("#email").val().trim();
				let domain = $("#domain option:selected").val();
            	
				let emailAddress = email + '@' + domain;
				if (emailAddress != null) {
	            	$.ajax({
	            		type : "post"
	            		, url : "/api/mail"
	            		, data : { "email" : emailAddress }
	            		, success : function(data) {
	            			if (data.result == true) {
	            				alert("인증코드를 보냈습니다. 메일을 확인해 주세요.");
	            			} else {
	            				alert("메일발송에 실패했습니다.");
	            			}
	            		}
	            		, error : function(status, request, error) {
	            			alert("관리자에게 문의바랍니다.");
	            		}
	            	});
            	} else {
            		alert("이메일을 입력해주세요.");
            	}
            });
            
            
            // 이메일 인증하기
            $('#verifyBtn').on('click', function(e) {
            	e.preventDefault();
            	
            	let verifyCode = $('#verifyCode').val().trim();
            	
            	$.ajax({
            		type : "post"
            		, url : "/api/verify"
            		, data : { "verifyCode" : verifyCode }
            		, success : function(data) {
            			if (data.code == 1) {
            				alert("인증되었습니다.");
            			} else {
            				alert("인증에 실패했습니다.");
            			}
            		}
            		, error : function(status, request, error) {
            			alert("관리자에게 문의바랍니다.");
            		}
            	});
            });
            
            
            // 회원가입
            $('#signUpForm').on('submit', function(e) {
            	e.preventDefault();
            	
	            let name = $("#name").val().trim();
	            let puppyName = $("#puppyName").val().trim();
	            let email = $("#email").val().trim();
				let domain = $("#domain option:selected").val();
				let emailAddress = email + '@' + domain;
	            let password = $("#password").val();
	            let confirmPassword = $("#passwordConfirm").val();
	            let adoptionDate = $(".adoptionDate").val();
	            let file = $("#imgUrl").val();
	            let nameDupCheck = $("#nameDupCheck").val();
	            
	            // validation
	            if (!name) {
	            	alert("닉네임을 입력해주세요.");
					return false;
	            }
	            if (nameDupCheck == 0) {
	            	alert("닉네임 중복확인을 해주세요.");
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
	            if (!adoptionDate) {
	            	alert("입양날짜를 입력해주세요.");
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
	            
	            let formData = new FormData();
	            formData.append("name", name);
	            formData.append("puppyName", puppyName);
	            formData.append("email", emailAddress);
	            formData.append("password", password);
	            formData.append("adoptionDate", adoptionDate);
				formData.append("file", $('#file')[0].files[0]);
	            
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