package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.exception.EmailCodeException;
import com.ssafy.ssafymate.repository.properties.EmailProperties;
import com.ssafy.ssafymate.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

    @Autowired
    JavaMailSender emailSender;

    public static String emailVerificationCode;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private EmailProperties emailProperties;

    // 회원가입 2단계 - 이메일 인증
    private MimeMessage createMessage(String to) throws Exception {
        emailVerificationCode = createKey();
        System.out.println("보내는 대상 : " + to);
        System.out.println("인증 번호 : " + emailVerificationCode);
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);// 보내는 대상
        message.setSubject("SSAFY MATE 인증번호가 도착했습니다.");// 제목

        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg += "<h1> 안녕하세요, SSAFY MATE입니다. </h1>";
        msgg += "<br>";
        msgg += "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg += "<br>";
        msgg += "<p>감사합니다!<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>회원가입 코드입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>";
        msgg += emailVerificationCode + "</strong><div><br/> ";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");// 내용
        message.setFrom(new InternetAddress("ssafymate@gmail.com", "SSAFY MATE"));// 보내는 사람

        return message;
    }

    // 비밀번호 찾기 - 이메일 인증
    private MimeMessage pwSearchMessage(String to) throws Exception {
        emailVerificationCode = createKey();
        System.out.println("보내는 대상 : " + to);
        System.out.println("인증 번호 : " + emailVerificationCode);
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);// 보내는 대상
        message.setSubject("SSAFY MATE 인증번호가 도착했습니다.");// 제목

        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg += "<h1> 안녕하세요, SSAFY MATE입니다. </h1>";
        msgg += "<br>";
        msgg += "<p>아래 코드를 비밀번호 찾기 창으로 돌아가 입력해주세요<p>";
        msgg += "<br>";
        msgg += "<p>감사합니다!<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>비밀번호 찾기 인증 코드입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>";
        msgg += emailVerificationCode + "</strong><div><br/> ";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");// 내용
        message.setFrom(new InternetAddress("ssafymate@gmail.com", "SSAFY MATE"));// 보내는 사람

        return message;
    }

    //	인증코드 만들기
    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    // a~z (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    // A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return key.toString();
    }

    // 회원가입 이메일 인증
    @Override
    public String sendSimpleMessage(String to) throws Exception {

        MimeMessage message = createMessage(to);
        try {
            redisUtil.setDataExpire(to, emailVerificationCode, 180); // 인증코드 유효시간 3분
            emailSender.send(message);
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return emailVerificationCode;
    }

    // 비밀번호 찾기 이메일 인증
    @Override
    public String sendPwSimpleMessage(String to) throws Exception {
        MimeMessage message = pwSearchMessage(to);
        try {
            redisUtil.setDataExpire(to, emailVerificationCode, 180); // 인증코드 유효시간 3분
            emailSender.send(message);
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return emailVerificationCode;
    }

    @Override
    public Long getUserIdByCode(String email, String emailVerificationCode) {
        String storedCode = redisUtil.getData(email);
        System.out.println(storedCode);

        if (storedCode==null) {
            throw new NullPointerException();
        }
        if (!emailVerificationCode.equals(storedCode)) {
            throw new EmailCodeException();
        }
        return 1L;
    }
}