package be.africshop.africshopbackend.emailModule.services;



import be.africshop.africshopbackend.emailModule.dto.MailRequest;
import be.africshop.africshopbackend.emailModule.entities.Mail;
import be.africshop.africshopbackend.emailModule.response.MailResponse;
import org.springframework.context.annotation.Primary;

import java.util.List;

public interface MailService {

     MailResponse sendMail(MailRequest request);

     List<Mail> getAllMaill();

     boolean emailValidation(String email);

}
