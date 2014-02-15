package de.htw_berlin.f4.ai.kbe.appconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@ImportResource(value = { "classpath:persistencyContext.xml", "classpath:applicationContext.xml" })
public class AppConfig {

}
