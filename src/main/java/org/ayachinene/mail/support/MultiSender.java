package org.ayachinene.mail.support;

import jakarta.mail.MessagingException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Validated
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class MultiSender {

    private static final Logger logger = LoggerFactory.getLogger(MultiSender.class);

    private List<File> files = new ArrayList<>();

    private MessageBuilder builder;

    private JavaMailSender mailSender;

    MultiSender(@NotBlank String dirPath, @NotBlank String baseName) {
        File dir = new File(dirPath);
        Assert.isTrue(dir.exists() && dir.isDirectory(), "Invalid dir path.");
        File[] files = dir.listFiles(f -> isZipVolumeFile(f, baseName));
        if (files != null) {
            this.files = Arrays.stream(files).sorted().toList();
        }
    }

    private boolean isZipVolumeFile(@NotNull File file, @NotBlank String baseName) {
        return StringUtils.equals(FilenameUtils.getBaseName(file.getName()), baseName) &&
            StringUtils.defaultString(FilenameUtils.getExtension(file.getName())).matches("z((ip)|\\d*)");
    }

    public void send(int i) throws MessagingException {
        mailSender.send(builder.build(files.get(i).getAbsolutePath()));
        logger.info("{} has been sent.", files.get(i).getAbsolutePath());
    }

    public void send(int start, int end) throws MessagingException {
        for (int i = start; i <= end; ++i) {
            send(i);
        }
    }

    public void send(int[] indexes) throws MessagingException {
        Assert.isTrue(indexes != null, "indexes cannot be null");
        for (int i : indexes) {
            send(i);
        }
    }

    public void sendAll() throws MessagingException {
        for (int i = 0; i < files.size(); i++) {
            send(i);
        }
    }

    @Autowired
    public void setBuilder(MessageBuilder builder) {
        this.builder = builder;
    }

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
}
