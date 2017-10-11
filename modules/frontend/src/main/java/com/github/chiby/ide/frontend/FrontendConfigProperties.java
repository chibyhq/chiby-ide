package com.github.chiby.ide.frontend;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ConfigurationProperties(prefix = "chiby.ide",ignoreUnknownFields = false)
public class FrontendConfigProperties {

	String home;
	boolean initializeHome;
}
