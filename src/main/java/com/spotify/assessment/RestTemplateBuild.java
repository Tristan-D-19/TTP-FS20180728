package com.spotify.assessment;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Resttemplate used to make a request to an external API
 * @author Tristan
 *
 */
@Configuration
public class RestTemplateBuild {

	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder rtb) {
		return rtb.build();		
	}

}
