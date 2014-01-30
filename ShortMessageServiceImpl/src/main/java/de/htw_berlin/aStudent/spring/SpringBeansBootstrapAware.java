package de.htw_berlin.aStudent.spring;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class SpringBeansBootstrapAware implements BeanPostProcessor {
	protected Logger log = Logger.getLogger(getClass());

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		log.info("Initialized: " + beanName + " {" + bean.getClass().getName() + "}");
		return bean;
	}
}
