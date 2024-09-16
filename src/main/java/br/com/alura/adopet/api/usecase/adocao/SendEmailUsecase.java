package br.com.alura.adopet.api.usecase.adocao;

import br.com.alura.adopet.api.controller.dto.EmailRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendEmailUsecase {

    private final JavaMailSender emailSender;

    public void execute( EmailRequest emailRequest){
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(emailRequest.getFrom());
        email.setTo(emailRequest.getTo());
        email.setSubject(emailRequest.getSubject());
        email.setText(emailRequest.getMsg());
        emailSender.send(email);
    }
}
