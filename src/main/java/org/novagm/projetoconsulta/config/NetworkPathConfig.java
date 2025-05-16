package org.novagm.projetoconsulta.config;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "network")
public class NetworkPathConfig {
    private Map<String, PathConfig> paths;
    
    @Data
    public static class PathConfig {
        private String basePath;
        private List<String> subDirectories;
        private List<String> allowedExtensions;
        private boolean active;
    }
}