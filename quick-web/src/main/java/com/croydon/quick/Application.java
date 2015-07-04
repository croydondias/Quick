package com.croydon.quick;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.RequestMapping;

import com.croydon.quick.config.RESTAuthenticationFailureHandler;
import com.croydon.quick.config.RESTAuthenticationSuccessHandler;

@EnableConfigurationProperties
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {
	
	@RequestMapping("/")
    String home() {
        return "index.html";
    }
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
 
@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Value("${spring.datasource.driverClassName}")
    private String databaseDriverClassName;
 
    @Value("${spring.datasource.url}")
    private String databaseUrl;
 
    @Value("${spring.datasource.username}")
    private String databaseUsername;
 
    @Value("${spring.datasource.password}")
    private String databasePassword;
	
	@Autowired
	private RESTAuthenticationFailureHandler authenticationFailureHandler;
	@Autowired
	private RESTAuthenticationSuccessHandler authenticationSuccessHandler;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		
		org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
		
		ds.setDriverClassName(databaseDriverClassName);
        ds.setUrl(databaseUrl);
        ds.setUsername(databaseUsername);
        ds.setPassword(databasePassword);
        
		builder.jdbcAuthentication()
			.dataSource(ds)
			// This query is used by spring-security to get the password associated with a user
			// when they sign in. The third column for this query is supposed to be 'isUserEnabled'.
			// But since we don't have the ability to enable/disable users, we hardcode this to true.
			.usersByUsernameQuery("select email, password, true from employee where email=?")
			// We don't actually have 'roles' for each user
			// So we're just hardcoding a one role at this point to pleases spring-security
			.authoritiesByUsernameQuery("select email, 'USER' from employee where email=?")
			.passwordEncoder(passwordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers("/", "/css/**", "/fonts/**", "/img/**", "/js/**", "/index.html", "/login", "/login/**", "/logout")
			.permitAll().anyRequest().fullyAuthenticated();

		http.formLogin().loginPage("/signin.html").loginProcessingUrl("/login/process")
			.successHandler(authenticationSuccessHandler).failureHandler(authenticationFailureHandler)
			.defaultSuccessUrl("/play.html")
			.usernameParameter("email").passwordParameter("password")
			.permitAll();

		http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/signin.html");

		// Disable cross site forgery, since this is just a dev environment
		// Need to enable it or prod and figure out how it works
		http.csrf().disable();
	}
  
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
  
}

