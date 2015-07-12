package com.croydon.quick.page;

import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import com.croydon.quick.WicketWebApplication;
import com.croydon.quick.domain.Employee;
import com.croydon.quick.page.component.EmployeeForm;
import com.croydon.quick.service.EmployeeService;

@MountPath("/admin.html")
public class AdminPage extends WebPage implements AuthenticatedWebPage {

	private static final long serialVersionUID = 1L;
	
    @SpringBean
    private EmployeeService employeeService;

    public AdminPage() {
        //add(new BookmarkablePageLink<String>("link", Homepage.class));
        
        final List<Employee> employees = employeeService.findAll(); 
        
        add(new ListView<Employee>("employeeList", employees) {
        	@Override
        	protected void populateItem(ListItem<Employee> item) {
        		final Employee e = (Employee) item.getModelObject();
        		item.add(new EmployeeForm("employeeForm", 
                        new CompoundPropertyModel(new LoadableDetachableModel() { 
                            @Override 
                            protected Object load() { 
                                return e; 
                            } 
                        }) 
                ));
        	}			
           });
        
//        RepeatingView listItems = new RepeatingView("listItems");
//        add(listItems);
//        for (Employee e : employees) {
//        	listItems.add(new Label(listItems.newChildId(), e.getFirstName() + " " + e.getLastName()));
//		}
//        
//        add(new ListView<Employee>("employees", employees) {
//        	@Override
//        	protected void populateItem(ListItem<Employee> item) {
//        	   item.add(new Label("firstName", new PropertyModel(item.getModel(), "firstName")));
//        	   item.add(new Label("lastName", new PropertyModel(item.getModel(), "lastName")));
//        	}			
//           });
    }
    
    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        CssResourceReference cssResourceReference = new CssResourceReference(
                WicketWebApplication.class, "example.css");
        response.render(CssHeaderItem.forReference(cssResourceReference));
    }

}
