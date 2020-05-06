package com.hs;

import java.util.Properties;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.github.pagehelper.PageHelper;

@ComponentScan("com.hs")
@SpringBootApplication
public class HsBsApplication extends SpringBootServletInitializer {

	// 配置mybatis的分页插件pageHelper
	@Bean
	public PageHelper pageHelper() {
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("offsetAsPageNum", "true");
		properties.setProperty("rowBoundsWithCount", "true");
		properties.setProperty("reasonable", "true");
		properties.setProperty("dialect", "mysql"); // 配置mysql数据库的方言
		pageHelper.setProperties(properties);
		return pageHelper;
	}

	public static void main(String[] args) {
		SpringApplication.run(HsBsApplication.class, args);
	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) { // 注意这里要指向原先用main方法执行的Application启动类
	 return builder.sources(HsBsApplication.class);
	}
	
	
//springboot配置SSL
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
