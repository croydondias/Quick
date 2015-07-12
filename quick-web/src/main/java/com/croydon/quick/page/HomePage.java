package com.croydon.quick.page;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.resource.CssResourceReference;
import org.wicketstuff.annotation.mount.MountPath;

import com.croydon.quick.WicketWebApplication;

@MountPath(value = "/", alt= {"/index.html", "/logout"})
public class HomePage extends WebPage {
	
	private static final long serialVersionUID = 1L;
	
    public HomePage() {
    	getSession().invalidate();
//        add(new BookmarkablePageLink<String>("link", AdminPage.class));
    }
}
