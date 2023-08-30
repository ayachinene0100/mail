package org.ayachinene.mail;

import org.apache.commons.io.FilenameUtils;
import org.ayachinene.mail.config.SplitterConfig;
import org.ayachinene.mail.support.MultiSender;
import org.ayachinene.mail.support.MultiSenderBuilder;
import org.ayachinene.mail.support.Splitter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    private final SplitterConfig splitterConfig;

    private final Splitter splitter;

    private final MultiSenderBuilder multiSenderBuilder;

    @Value("${file-path}")
    private String filePath;

    public Runner(SplitterConfig splitterConfig, Splitter splitter, MultiSenderBuilder multiSenderBuilder) {
        this.splitterConfig = splitterConfig;
        this.splitter = splitter;
        this.multiSenderBuilder = multiSenderBuilder;
    }

    @Override
    public void run(String... args) throws Exception {
        splitter.split(filePath);
        MultiSender multiSender = multiSenderBuilder.build(splitterConfig.getOutputDir(), FilenameUtils.getBaseName(filePath));
        multiSender.sendAll();
    }
}
