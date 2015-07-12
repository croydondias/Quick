package com.croydon.quick.page;

import org.apache.log4j.Logger;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.apache.wicket.util.string.Strings;
import org.wicketstuff.annotation.mount.MountPath;

import com.croydon.quick.config.HibernateConfig;

@MountPath("/signin.html")
public class SignInPage extends WebPage {

	private static final Logger LOG = Logger.getLogger(SignInPage.class);
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String password;
	private String message = "";
	
    public SignInPage() {
    }

    public SignInPage(final PageParameters parameters) {
		super(parameters);
		StringValue result = parameters.get("msg");
		if (!result.isNull()) {
			message = result.toString();
		}
		LOG.info("message = " + message);
    }
    
    @Override
    protected void onInitialize() {
       super.onInitialize();
       
//       Label loginStatus = new Label("signin-warning-alert:loginStatus", Model.of(message));
//       if (!Strings.isEmpty(message)) {
//    	   add(loginStatus);
//       }
       
       String html = message;
       if (!Strings.isEmpty(message)) {
    	   StringBuilder sb = new StringBuilder();
    	   sb.append("<div id=\"signin-warning-alert\" class=\"alert alert-warning fade in\" style=\"width: 50%;\">");
    	   sb.append(String.format("<span id=\"signin-warning-alert-msg\">%s</span>", message));
    	   sb.append("</div>");
    	   html = sb.toString();
       }
       
       Label loginStatus = new Label("loginStatus", Model.of(html));
       loginStatus.setEscapeModelStrings(false);
       
       add(loginStatus);
       
       Form form = new Form("signinForm"){
          @Override
          protected void onSubmit() {
             LOG.info("Wicket LOGGING IN ++++++++++++");
        	 if (Strings.isEmpty(email)) {
                return;
             }

             boolean authResult = AuthenticatedWebSession.get().signIn(email, password);
             //if authentication succeeds redirect user to the requested page
             if (authResult) { 
                //continueToOriginalDestination();
                setResponsePage(PlayPage.class);
             } else {
					final String errorMsg = "Unable to sign in";

					PageParameters pageParameters = new PageParameters();
					pageParameters.add("msg", errorMsg);
					setResponsePage(SignInPage.class, pageParameters);

//					loginStatus.setDefaultModelObject(errorMsg);
//					error(errorMsg);
            	 
             }
          }
       };

       form.setDefaultModel(new CompoundPropertyModel(this));

       form.add(new TextField("email"));
       form.add(new PasswordTextField("password"));

       add(form);
       //add(response());
    }
    
//    private WebMarkupContainer response() {
//        WebMarkupContainer r = new WebMarkupContainer("signin-warning-alert") {
//
//           @Override
//           protected void onConfigure() {
//              super.onConfigure();
//              setVisible(!Strings.isEmpty(message));
//           }
//
//        };
//        r.setOutputMarkupPlaceholderTag(true);
//        return r;
//     }
}
