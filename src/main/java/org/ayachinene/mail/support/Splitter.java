package org.ayachinene.mail.support;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import org.apache.commons.io.FilenameUtils;
import org.ayachinene.mail.config.SplitterConfig;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
@Validated
public class Splitter {

    private final SplitterConfig splitterConfig;

    @SuppressWarnings("FieldCanBeLocal")
    private final String ZIP_EXTENSION = ".zip";

    public Splitter(SplitterConfig splitterConfig) {
        this.splitterConfig = splitterConfig;
    }

    public void split(@NotBlank String filePath) throws IOException {
        split(new File(filePath));
    }

    public void split(@NotNull File file) throws IOException {
        Assert.isTrue(file.exists() && file.isFile(), "Invalid file.");
        try (ZipFile zipFile = new ZipFile(splitterConfig.getOutputDir() + "/" +
                FilenameUtils.getBaseName(file.getName()) + ZIP_EXTENSION)) {
            zipFile.createSplitZipFile(List.of(file), new ZipParameters(), true, splitterConfig.getVolumeSize());
        }
    }
}
