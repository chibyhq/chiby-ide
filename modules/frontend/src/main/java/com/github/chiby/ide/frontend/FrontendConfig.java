package com.github.chiby.ide.frontend;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "chiby.frontend",ignoreUnknownFields = false)
public class FrontendConfig {

	String home;
}
