package org.ayachinene.mail.support;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.ayachinene.mail.config.DefaultMessageMetaData;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.io.File;

@Component
@Validated
public class MessageBuilder {

    private static final String DEFAULT_SUBJECT = "";

    private static final String DEFAULT_TEXT = "";

    private final JavaMailSender mailSender;

    private final DefaultMessageMetaData defaultMessageMetaData;

    public MessageBuilder(JavaMailSender mailSender, DefaultMessageMetaData defaultMessageMetaData) {
        this.mailSender = mailSender;
        this.defaultMessageMetaData = defaultMessageMetaData;
    }

    public MimeMessage build(String attachmentName, File attachmentFile) throws MessagingException {
        return build(DEFAULT_SUBJECT, DEFAULT_TEXT, attachmentName, new FileSystemResource(attachmentFile));
    }

    public MimeMessage build(String attachmentPath) throws MessagingException {
        return build(DEFAULT_SUBJECT, attachmentPath);
    }

    public MimeMessage build(String subject, String attachmentPath) throws MessagingException {
        FileSystemResource file = new FileSystemResource(attachmentPath);
        return build(subject, file);
    }

    public MimeMessage build(String subject, FileSystemResource attachment) throws MessagingException {
        return build(subject, DEFAULT_TEXT, attachment.getFilename(), attachment);
    }

    public MimeMessage build(String subject, String text, String attachmentFilename, FileSystemResource attachment) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(defaultMessageMetaData.getFrom());
        helper.setTo(defaultMessageMetaData.getTo());
        helper.setSubject(subject);
        helper.setText(text);
        helper.addAttachment(attachmentFilename, attachment);
        return message;
    }
}
