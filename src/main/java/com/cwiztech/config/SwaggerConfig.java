//package com.cwiztech.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.AuthorizationScope;
//import springfox.documentation.service.Contact;
//import springfox.documentation.service.GrantType;
//import springfox.documentation.service.OAuth;
//import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
//import springfox.documentation.service.SecurityReference;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.spi.service.contexts.SecurityContext;
//// import springfox.documentation.swagger.web.ApiKeyVehicle;
//// import springfox.documentation.swagger.web.SecurityConfiguration;
//// import org.springframework.http.HttpHeaders;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import static org.hibernate.validator.internal.util.CollectionHelper.newArrayList;
//
//@Component
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig extends WebMvcConfigurationSupport {
//	private static final Logger log = LoggerFactory.getLogger(SwaggerConfig.class);
//
//	@Value("${config.oauth2.accessTokenUri}")
//	private String accessTokenUri;
//
//	@Bean
//	public Docket productApi() {
//
//		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
//				.paths(PathSelectors.any()).build().securityContexts(Collections.singletonList(securityContext()))
//				.securitySchemes(Arrays.asList(securitySchema())).apiInfo(apiInfo());
//
//	}
//
//	private OAuth securitySchema() {
//
//		log.info("oauth path " + accessTokenUri);
//		List<AuthorizationScope> authorizationScopeList = newArrayList();
//		authorizationScopeList.add(new AuthorizationScope("read", "read all"));
//		authorizationScopeList.add(new AuthorizationScope("write", "access all"));
//
//		List<GrantType> grantTypes = newArrayList();
//		GrantType passwordCredentialsGrant = new ResourceOwnerPasswordCredentialsGrant(accessTokenUri);
//		grantTypes.add(passwordCredentialsGrant);
//
//		return new OAuth("oauth2", authorizationScopeList, grantTypes);
//	}
//
//	private SecurityContext securityContext() {
//		return SecurityContext.builder().securityReferences(defaultAuth()).build();
//	}
//
//	private List<SecurityReference> defaultAuth() {
//
//		final AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
//		authorizationScopes[0] = new AuthorizationScope("read", "read all");
//		authorizationScopes[1] = new AuthorizationScope("trust", "trust all");
//		authorizationScopes[2] = new AuthorizationScope("write", "write all");
//
//		return Collections.singletonList(new SecurityReference("oauth2", authorizationScopes));
//	}
//
//	// @SuppressWarnings("deprecation")
//	// @Bean
//	// public SecurityConfiguration security() {
//	// return new SecurityConfiguration("cmis", "secret", "", "", "Bearer access
//	// token", ApiKeyVehicle.HEADER,
//	// HttpHeaders.AUTHORIZATION, "");
//	// }
//
//	private ApiInfo apiInfo() {
//		return new ApiInfo("Campus Managment Services", "Description", "", "Terms of service",
//				new Contact("www.compuwiztech.com", "CompuWizTech", "CompuWizTech@company.com"), "API license",
//				"API license", Collections.emptyList());
//	}
//
//	@Override
//	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
//
//		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//	}
//}
