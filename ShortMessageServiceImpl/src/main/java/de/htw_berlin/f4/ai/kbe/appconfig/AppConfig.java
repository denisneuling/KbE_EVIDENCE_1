package de.htw_berlin.f4.ai.kbe.appconfig;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(value = {"classpath:persistencyContext.xml","classpath:applicationContext.xml"})
public class AppConfig {
	
}
