package com.croydon.quick.page;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import com.croydon.quick.domain.Employee;
import com.croydon.quick.domain.util.EmployeeComparator;
import com.croydon.quick.page.component.DeleteEmployeeForm;
import com.croydon.quick.page.component.EmployeeForm;
import com.croydon.quick.service.EmployeeService;

@MountPath("/admin.html")
public class AdminPage extends WebPage implements AuthenticatedWebPage {

	private static final long serialVersionUID = 1L;
	
    @SpringBean
    private EmployeeService employeeService;

    public AdminPage() {
    	add(new BookmarkablePageLink<String>("home", HomePage.class));
    	add(new BookmarkablePageLink<String>("play", PlayPage.class));
        add(new BookmarkablePageLink<String>("logout", HomePage.class));
    	
        List<Employee> employees = employeeService.findAll();
        Collections.sort(employees, new EmployeeComparator());
        for (Employee employee : employees) {
        	employee.setPassword(""); // clear all the passwords as we don't want to show it in the UI
		}
        
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
        		item.add(new DeleteEmployeeForm("deleteEmployeeForm", 
                        new CompoundPropertyModel(new LoadableDetachableModel() { 
                            @Override 
                            protected Object load() { 
                                return e; 
                            } 
                        }) 
                ));
        	}			
           });
    }
}
