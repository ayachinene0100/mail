package org.ayachinene.mail.support;

import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class MultiSenderBuilder implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public MultiSender build(@NotBlank String dirPath, @NotBlank String baseName) {
        return applicationContext.getBean(MultiSender.class, dirPath, baseName);
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
