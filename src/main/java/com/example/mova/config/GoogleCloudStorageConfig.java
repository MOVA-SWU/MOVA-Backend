package com.example.mova.config;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Configuration
@Component
@Getter
public class GoogleCloudStorageConfig {

    /*
    @Value("${GCP_PROJECT_ID}")
    private String projectId;

    @Value("${GCP_STORAGE_CREDENTIAL_LOCATIONS}")
    private String credentialPath;

    @Bean
    public Storage storage() throws IOException {
        try (InputStream keyFile = getClass()
                .getClassLoader()
                .getResourceAsStream(credentialPath.replace("classpath:", ""))) {
            return StorageOptions.newBuilder()
                    .setProjectId(projectId)
                    .setCredentials(ServiceAccountCredentials.fromStream(keyFile))
                    .build()
                    .getService();
        }
    }

     */
}
