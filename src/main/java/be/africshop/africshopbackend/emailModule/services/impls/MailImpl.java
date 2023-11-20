package be.africshop.africshopbackend.emailModule.services.impls;


import be.africshop.africshopbackend.emailModule.dto.MailRequest;
import be.africshop.africshopbackend.emailModule.entities.Mail;
import be.africshop.africshopbackend.emailModule.repository.MailRepository;
import be.africshop.africshopbackend.emailModule.response.MailResponse;
import be.africshop.africshopbackend.emailModule.services.MailService;
import be.africshop.africshopbackend.emailModule.utils.EmailUtils;
import be.africshop.africshopbackend.emailModule.utils.JavaConverter;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class MailImpl implements MailService {
    private final MailRepository mailRepository;
    private final JavaConverter convert;
    private final EmailUtils utils;
    private final JavaMailSender mailSender;

    @Override
    @SneakyThrows
    public MailResponse sendMail(MailRequest request) {
        return Optional.of(request).stream()
                .peek(utils::sendMimeMessage)
                .map(convert::mailRequestToObject)
                .peek(mailRepository::save)
                .map(convert::objectToResponse)
                .findFirst()
                .orElseThrow(() -> new Exception("Error while saving due to send mail"));
    }
    @Override
    public List<Mail> getAllMaill() {
        return mailRepository.findAll();
    }

    @Override
    public boolean emailValidation(String email) {
        // regex pour valider une adresse e-mail
        String regex = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }


}
