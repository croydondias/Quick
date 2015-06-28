package com.croydon.quick.config;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

import com.croydon.quick.domain.EmployeeRepository;

@Configuration        
public class HibernateConfig {
	
	private static final Logger LOG = Logger.getLogger(HibernateConfig.class);
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory(EntityManagerFactory emf) {
         HibernateJpaSessionFactoryBean factory = new HibernateJpaSessionFactoryBean();
         factory.setEntityManagerFactory(emf);
         return factory;
    }
    
    @PostConstruct
	public void checkDatabase() {
		LOG.info("Checking database connection...");
		LOG.info(this.employeeRepository.findAll());
		LOG.info("Found employee table [DB connected]");
		
	}
}