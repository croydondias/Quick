package com.croydon.quick.page;

import org.apache.log4j.Logger;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.Strings;
import org.wicketstuff.annotation.mount.MountPath;

import com.croydon.quick.config.AppProperties;

@MountPath("/signin.html")
public class SignInPage extends WebPage {

	private static final Logger LOG = Logger.getLogger(SignInPage.class);
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String password;
	
    public SignInPage() {
    }

    public SignInPage(final PageParameters parameters) {
		super(parameters);
    }
    
    @Override
    protected void onInitialize() {
       super.onInitialize();
       
       add(createLoginStatusLabel());
       add(createLoginForm());
    }
    
    private Label createLoginStatusLabel() {
    	// Fetch the error message if present in the session
        final String signInError = (String) getSession().getAttribute(AppProperties.SIGN_IN_ERROR);
        
        String html = "";
        if (!Strings.isEmpty(signInError)) {
     	   StringBuilder sb = new StringBuilder();
     	   sb.append("<div id=\"signin-warning-alert\" class=\"alert alert-warning fade in\" style=\"width: 50%;\">");
     	   sb.append(String.format("<span id=\"signin-warning-alert-msg\">%s</span>", signInError));
     	   sb.append("</div>");
     	   html = sb.toString();
        }
        
        Label loginStatus = new Label("loginStatus", Model.of(html));
        loginStatus.setEscapeModelStrings(false);
        return loginStatus;
    }
    
    private StatelessForm createLoginForm() {
    	StatelessForm form = new StatelessForm("signinForm") {
            @Override
            protected void onSubmit() {
               if (Strings.isEmpty(email) || Strings.isEmpty(password)) {
                  return;
               }

               boolean authResult = AuthenticatedWebSession.get().signIn(email, password);
               //if authentication succeeds redirect user to the requested page
               if (authResult) { 
  					// continueToOriginalDestination();
  					// Add user to session
  					getSession().bind();
  					getSession().setAttribute(AppProperties.CURRENT_USER, email);
  					getSession().removeAttribute(AppProperties.SIGN_IN_ERROR);
  					setResponsePage(PlayPage.class);
               } else {
  					final String errorMsg = "Unable to sign in";
  					// Add error message to the session
  					getSession().bind();
  					getSession().setAttribute(AppProperties.SIGN_IN_ERROR, errorMsg + " (session)");
  					setResponsePage(SignInPage.class);
              	 
               }
            }
         };

         form.setDefaultModel(new CompoundPropertyModel(this));

         form.add(new TextField("email"));
         form.add(new PasswordTextField("password"));
         return form;
    }
}
