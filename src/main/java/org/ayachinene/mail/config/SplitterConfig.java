package org.ayachinene.mail.config;

import org.apache.commons.io.FileUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("splitter")
@Configuration
public class SplitterConfig {

    private static final long MIN_SIZE = 64 * FileUtils.ONE_KB;

    private String outputDir = "./output";

    private long volumeSize = 20 * FileUtils.ONE_MB;

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public long getVolumeSize() {
        return volumeSize;
    }

    public void setVolumeSize(long volumeSize) {
        this.volumeSize = Math.max(MIN_SIZE, volumeSize);
    }
}
