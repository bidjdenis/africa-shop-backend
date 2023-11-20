package be.africshop.africshopbackend.emailModule.utils;



import be.africshop.africshopbackend.emailModule.dto.MailRequest;
import be.africshop.africshopbackend.emailModule.entities.Mail;
import be.africshop.africshopbackend.emailModule.response.MailResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
public class JavaConverter {

    public Mail mailRequestToObject(MailRequest mailRequest) {
        Mail mail = new Mail();
        BeanUtils.copyProperties(mailRequest, mail);
        return mail;
    }

    public MailResponse requestToResponse(MailRequest mailRequest){
        MailResponse response = new MailResponse();
        BeanUtils.copyProperties(mailRequest,response);
        response.setDateCreate(Instant.now());
        return response;
    }

    public MailResponse objectToResponse(Mail mail){
        MailResponse response = new MailResponse();
        BeanUtils.copyProperties(mail,response);
        response.setDateCreate(Instant.now());
        return response;
    }

}
