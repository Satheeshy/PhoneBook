package com.satheesh.Config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages= {"com.satheesh"})
@EnableTransactionManagement
public class HibernateConfig {

	@Bean
	public DataSource getdatasource() {
		BasicDataSource ds = new BasicDataSource();
		
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://satheesh-database.c3wl8cxgbntx.us-east-2.rds.amazonaws.com/phonebook");
		ds.setUsername("satheesh");
		ds.setPassword("Sakhi4us");
		return ds;
	}
	
	@Bean
	public LocalSessionFactoryBean getsessionfactory() {
	  LocalSessionFactoryBean session = new LocalSessionFactoryBean();
	  session.setDataSource(getdatasource());
	  session.setPackagesToScan("com.satheesh.Domain");
	  session.setHibernateProperties(getHibernateProperties());
	  return session;
	  
	}
	
	public Properties getHibernateProperties() {
		Properties props = new Properties();
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		props.setProperty("hibernate.show_sql", "true");
		props.setProperty("hibernate.hbm2ddl.auto", "update");
		
		return props;
	}
	@Bean
	public HibernateTransactionManager getmanager() {
		HibernateTransactionManager htm =  new HibernateTransactionManager();
		htm.setSessionFactory(getsessionfactory().getObject());
		return htm;
	}
	@Bean
	public JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(587);
	     
	    mailSender.setUsername("satheeshyadava@gmail.com");
	    mailSender.setPassword("Sakhi4us");
	     
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	     
	    return mailSender;
	}
	

}
