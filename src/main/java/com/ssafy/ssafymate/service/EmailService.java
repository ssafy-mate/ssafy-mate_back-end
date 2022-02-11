package com.ssafy.ssafymate.service;

public interface EmailService {

    String sendSimpleMessage(String to) throws Exception;

    String sendPwSimpleMessage(String to) throws Exception;

    Long getUserIdByCode(String email, String ePw);

}
