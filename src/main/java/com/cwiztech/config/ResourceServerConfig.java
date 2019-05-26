package com.cwiztech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import java.util.Collections;
import java.util.List;

@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Bean
	protected ResourceServerConfiguration adminResources() {

		ResourceServerConfiguration resource = new ResourceServerConfiguration() {
			public void setConfigurers(List<ResourceServerConfigurer> configurers) {
				super.setConfigurers(configurers);
			}
		};
		resource.setConfigurers(
				Collections.<ResourceServerConfigurer>singletonList(new ResourceServerConfigurerAdapter() {
					@Override
					public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
						resources.resourceId(OAuth2Config.RESOURCE_ID1);
					}

					@Override
					public void configure(HttpSecurity http) throws Exception {
						http.requestMatchers().antMatchers("/loginuser", "/loginuser/**").and().authorizeRequests()
								.anyRequest().access("#oauth2.hasScope('write')");
						http.requestMatchers().antMatchers("/oauth/token", "/oauth/token").and().authorizeRequests()
								.anyRequest().access("#oauth2.hasScope('write')");
						http.requestMatchers().antMatchers("/login", "/login/**").and().authorizeRequests().anyRequest()
								.access("#oauth2.hasScope('write')");
						
						http.requestMatchers().antMatchers("/lookup", "/lookup/**").and().authorizeRequests()
						.anyRequest().access("#oauth2.hasScope('write')");

					}
				}));

		resource.setOrder(1);
		return resource;
	}
}
