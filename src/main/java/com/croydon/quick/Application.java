package com.croydon.quick;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.croydon.quick.domain.Employee;
import com.croydon.quick.domain.EmployeeRepository;

//@RestController
@EnableConfigurationProperties
@Configuration
@EnableAutoConfiguration
@ComponentScan
//@RepositoryRestConfiguration
public class Application {
	
//	@RequestMapping("/")
//    String home() {
//        return "Hello World! xxx yyy";
//    }
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {
 
  @Autowired
  EmployeeRepository accountRepository;
 
  @Override
  public void init(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService());
  }
 
  @Bean
  UserDetailsService userDetailsService() {
    return new UserDetailsService() {
 
      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee account = accountRepository.findByEmail(username);
        if(account != null) {
	        return new User(account.getEmail(), account.getPassword(), true, true, true, true,
	                AuthorityUtils.createAuthorityList("USER"));
        } else {
          throw new UsernameNotFoundException("could not find the user with email address '"
                  + username + "'");
        }
      }
      
    };
  }
}
 
@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
  @Override
  protected void configure(HttpSecurity http) throws Exception {
//    http.authorizeRequests().anyRequest().fullyAuthenticated().and().
//    httpBasic().and().
//    csrf().disable();
	  
	  http.authorizeRequests()
	  	.antMatchers("/css/**", "/fonts/**","/img/**","/js/**","/index.html").permitAll() 
	  .anyRequest().fullyAuthenticated().and().
	  	formLogin().loginPage("/signin.html").permitAll()
	  	.and().csrf().disable();
	  
  }
  
}