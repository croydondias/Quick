package com.croydon.quick;

import org.apache.wicket.Page;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wicketstuff.annotation.scan.AnnotatedMountScanner;
import org.wicketstuff.rest.utils.mounting.PackageScanner;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.component.IRequestableComponent;

import com.croydon.quick.config.BasicAuthenticationSession;
import com.croydon.quick.page.AdminPage;
import com.croydon.quick.page.AuthenticatedWebPage;
import com.croydon.quick.page.HomePage;
import com.croydon.quick.page.PlayPage;
import com.croydon.quick.page.SignInPage;

import de.agilecoders.wicket.core.Bootstrap;

@EnableConfigurationProperties
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class WicketWebApplication extends AuthenticatedWebApplication {
	
	@Autowired
    private ApplicationContext applicationContext;
	
//	@RequestMapping("/")
//    String home() {
//        return "index.html";
//    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
    public static void main(String[] args) {
        SpringApplication.run(WicketWebApplication.class, args);
    }

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}
	
	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass(){
		return BasicAuthenticationSession.class;
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return SignInPage.class;
	}
	
	/**
     * <ul>
     * <li>making the wicket components injectable by activating the
     * SpringComponentInjector</li>
     * <li>mounting the test page</li>
     * <li>logging spring service method output to showcase working
     * integration</li>
     * </ul>
     */
    @Override
    protected void init() {
        super.init();
        //Bootstrap.install(this);
        //PackageScanner.scanPackage("com.croydon.quick.api.wicket");
        
        // This is so that pages are automatically mounts based on their annotations
        new AnnotatedMountScanner().scanPackage("com.croydon.quick.page").mount(this);
        
        // Register the authorization strategy
        getSecuritySettings().setAuthorizationStrategy(new IAuthorizationStrategy()
        {
            public boolean isActionAuthorized(Component component, Action action)
            {
                // authorize everything
                return true;
            }

            public <T extends IRequestableComponent> boolean isInstantiationAuthorized(
                Class<T> componentClass)
            {
                // Check if the new Page requires authentication (implements the marker interface)
                if (AuthenticatedWebPage.class.isAssignableFrom(componentClass))
                {
                    // Is user signed in?
                    if (((BasicAuthenticationSession)Session.get()).isSignedIn())
                    {
                        // okay to proceed
                        return true;
                    }

                    // Intercept the request, but remember the target for later.
                    // Invoke Component.continueToOriginalDestination() after successful logon to
                    // continue with the target remembered.

                    throw new RestartResponseAtInterceptPageException(SignInPage.class);
                }

                // okay to proceed
                return true;
            }
        });
        
        // This is so that spring beans can be injected in wicket
        getComponentInstantiationListeners().add(new SpringComponentInjector(this, applicationContext));
        
//        mountPage("/admin.html", AdminPage.class);
//        mountPage("/signin.html", SignInPage.class);
        //mountPage("/play.html", PlayPage.class);
    }

}
