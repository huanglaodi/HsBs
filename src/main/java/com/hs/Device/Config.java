package com.hs.Device;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class Config {
	
	  private CorsConfiguration buildConfig() {  
	        CorsConfiguration corsConfiguration = new CorsConfiguration();  
	        corsConfiguration.addAllowedOrigin("*"); // 1允许任何域名使用
	        corsConfiguration.addAllowedHeader("*"); // 2允许任何头
	        corsConfiguration.addAllowedMethod("*"); // 3允许任何方法（post、get等） 
	        return corsConfiguration;  
	    }  
	  
	    @Bean  
	    public CorsFilter corsFilter() {  
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();  
	        source.registerCorsConfiguration("/**", buildConfig()); // 4  
	        return new CorsFilter(source);  
	       
	    }  
	    
	/*
	 * @Bean public ServletWebServerFactory servletWebServerFactory() {
	 * TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory() {
	 * 
	 * @Override protected void postProcessContext(Context context) {
	 * SecurityConstraint securityConstraint = new SecurityConstraint();
	 * securityConstraint.setUserConstraint("CONFIDENTIAL"); SecurityCollection
	 * securityCollection = new SecurityCollection();
	 * securityCollection.addPattern("/*");
	 * securityConstraint.addCollection(securityCollection);
	 * context.addConstraint(securityConstraint); } };
	 * factory.addAdditionalTomcatConnectors(redirectConnector()); return factory; }
	 * 
	 * private Connector redirectConnector() { Connector connector = new
	 * Connector(Http11NioProtocol.class.getName()); connector.setScheme("http");
	 * connector.setPort(8099); connector.setSecure(false);
	 * connector.setRedirectPort(443); return connector; }
	 */

	    
	    

}
