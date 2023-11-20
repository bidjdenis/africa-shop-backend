package be.africshop.africshopbackend.emailModule.utils;


import be.africshop.africshopbackend.emailModule.dto.MailRequest;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class EmailUtils {
    private final JavaMailSender mailSender;
    private final Configuration config;

    @SneakyThrows
    public void sendMimeMessage(MailRequest request) {
        MimeMessage message = mailSender.createMimeMessage();
        Map<String, Object> model = new HashMap<>();
        model.put("name", request.getName());
        model.put("text", request.getText());
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        Template t = config.getTemplate("account-mail.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
        helper.setTo(request.getMailTo());
        helper.setFrom("marcobignandi@gmail.com");
        helper.setText(html, true);
        helper.setSubject(request.getSubject());
        mailSender.send(message);
    }


    @SneakyThrows
    public void sendAccountCreateMail(MailRequest request) {
        MimeMessage message = mailSender.createMimeMessage();
        Map<String, Object> model = new HashMap<>();
        model.put("name", request.getName());
        model.put("text", request.getText());
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        Template t = config.getTemplate("account-mail.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
        helper.setTo(request.getMailTo());
        helper.setFrom("marcobignandi@gmail.com");
        helper.setText(html, true);
        helper.setSubject(request.getSubject());
        mailSender.send(message);
    }



}
