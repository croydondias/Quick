package com.croydon.quick;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.RequestMapping;

import com.croydon.quick.config.RESTAuthenticationFailureHandler;
import com.croydon.quick.config.RESTAuthenticationSuccessHandler;
import com.croydon.quick.domain.Employee;
import com.croydon.quick.domain.EmployeeRepository;

//@RestController
@EnableConfigurationProperties
@Configuration
@EnableAutoConfiguration
@ComponentScan
//@RepositoryRestConfiguration
public class Application {
	
	@RequestMapping("/")
    String home() {
        return "index.html";
    }
	
//	@RequestMapping("/login")
//    String login() {
//        return "signin.html App";
//    }
	
	@Value("${spring.datasource.driverClassName}")
    private String databaseDriverClassName;
 
    @Value("${spring.datasource.url}")
    private String datasourceUrl;
 
    @Value("${spring.datasource.username}")
    private String databaseUsername;
 
    @Value("${spring.datasource.password}")
    private String databasePassword;
	
	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;
	
//	@Bean( name = "quickDatasource")
//    public DataSource datasource() {
//        org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
//        ds.setDriverClassName(databaseDriverClassName);
//        ds.setUrl(datasourceUrl);
//        ds.setUsername(databaseUsername);
//        ds.setPassword(databasePassword);
//        
//        return ds;
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
	
	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;
	
//	@Autowired
//	private DataSource dataSource;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private RESTAuthenticationFailureHandler authenticationFailureHandler;
	@Autowired
	private RESTAuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
//		builder.inMemoryAuthentication().withUser("user").password("user").roles("USER").and().withUser("admin")
//				.password("admin").roles("ADMIN");
		
//		builder.jdbcAuthentication()
//			.usersByUsernameQuery(
//				"select username,password, enabled from users where username=?")
//			.authoritiesByUsernameQuery(
//				"select username, role from user_roles where username=?");
		
		org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
        ds.setDriverClassName(env.getRequiredProperty("spring.datasource.driverClassName"));
        ds.setUrl(env.getRequiredProperty("spring.datasource.url"));
        ds.setUsername(env.getRequiredProperty("spring.datasource.username"));
        ds.setPassword(env.getRequiredProperty("spring.datasource.password"));
//		
//		builder.userDetailsService(userDetailsService);
//		
		builder.jdbcAuthentication()
		.dataSource(ds)
		.usersByUsernameQuery("select email, password, true from employee where email=?")
		.authoritiesByUsernameQuery("select email, 'USER' from employee where email=?")
		.passwordEncoder(this.passwordEncoder());
		
	}
	
  @Override
  protected void configure(HttpSecurity http) throws Exception {
//    http.authorizeRequests().anyRequest().fullyAuthenticated().and().
//    httpBasic().and().
//    csrf().disable();
	  
	  http.authorizeRequests()
	  	.antMatchers("/", "/css/**", "/fonts/**","/img/**","/js/**","/index.html", "/login", "/login/**", "/logout").permitAll() 
	  	.anyRequest().fullyAuthenticated();
	  
	  http.formLogin()
	  .loginPage("/signin.html")
	  .loginProcessingUrl("/login/process")
	  .successHandler(authenticationSuccessHandler)
	  .failureHandler(authenticationFailureHandler)
	  .defaultSuccessUrl("/play.html")
	  .usernameParameter("email").passwordParameter("password")
	  .permitAll();
	  
	  http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/signin.html");
	  
	  
//	  http
//	    .authorizeRequests()
//	      .anyRequest().authenticated()
//	      .and()
//	    .formLogin()
//	      .loginPage("/login")
//	      .usernameParameter("email").passwordParameter("password")
//	      .permitAll();
	  
	  // Disable cross site forgery, since this is just a dev environment
	  // Need to enable it or prod and figure out how it works
	  http.csrf().disable();
	  
	  
  }
  
  @Bean
  public AuthenticationSuccessHandler successHandler() {
      
	  SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
      handler.setUseReferer(true);
      return handler;
  }
  
  @Bean
  public PasswordEncoder passwordEncoder() {
	 return new BCryptPasswordEncoder();
  }
  
}

