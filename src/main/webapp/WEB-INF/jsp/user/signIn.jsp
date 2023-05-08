<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="signUpWrpper" class="container d-flex flex-column align-items-center font-weight-bold">
    <div class="form-group input-box">
        <label for="email">이메일</label>
        <input type="text" name="email" class="form-control col-12" id="email" placeholder="이메일을 입력해주세요">
    </div>
    <div class="form-group input-box">
        <div class="d-flex justify-content-between align-items-center">
            <label for="password">비밀번호</label><a href="" class="reset-pwd">비밀번호 재설정</a>
        </div>
        <input type="password" name="password" class="form-control col-12" id="password" placeholder="비밀번호를 입력해주세요">
    </div>
    <button type="submit" class="btn col-3 sign-up-btn" id="signUpBtn">로그인</button>

    <!-- signIn page로 -->
    <div class="mt-2">
        <a href="" id="goSignIn">다른 계정으로 로그인하기</a>
    </div>
</div>