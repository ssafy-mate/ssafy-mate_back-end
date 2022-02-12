package com.ssafy.ssafymate.service;

public interface EmailService {

    // 입력받은 이메일로 인증코드 보내기 - 회원가입 단계에서 사용
    String sendSimpleMessage(String to) throws Exception;

    // 입력받은 이메일로 인증코드 보내기 - 비밀번호 재설정 시 사용
    String sendPwSimpleMessage(String to) throws Exception;

    // Redis에서 이메일 - 인증코드 쌍 찾기
    Long getUserIdByCode(String email, String ePw);

}
