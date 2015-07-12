package com.croydon.quick.page;

import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath("/play.html")
public class PlayPage extends WebPage implements AuthenticatedWebPage {

    private static final long serialVersionUID = 1L;

	public PlayPage() {
    
    }
}
