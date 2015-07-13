package com.croydon.quick.page.component;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.croydon.quick.domain.Employee;
import com.croydon.quick.exception.EmployeeDoesntExistException;
import com.croydon.quick.page.AdminPage;
import com.croydon.quick.service.EmployeeService;

public class EmployeeForm extends StatelessForm {
	
	private static final Logger LOG = Logger.getLogger(EmployeeForm.class);
	private static final long serialVersionUID = 1L;
	
	@SpringBean
    private EmployeeService employeeService;
	
	public EmployeeForm(String id, IModel model) {
		super(id, model);
		TextField firstName = new TextField("firstName"); 
        add(firstName);
        TextField lastName = new TextField("lastName");
        add(lastName);
        Label email = new Label("email");
        add(email);
        add(new Button("save") { 
	            @Override
	        	public void onSubmit() { 
	                Employee selected = (Employee) getForm().getModelObject(); 
	                try {
						employeeService.save(selected);
					} catch (EmployeeDoesntExistException e) {
						LOG.error("Error saving employee", e);
					}
	                setResponsePage(AdminPage.class);
	            } 
        }); 
        add(new Button("remove") { 
                @Override 
                public void onSubmit() { 
                	Employee selected = (Employee) getForm().getModelObject();
                	employeeService.delete(selected.getId());
                    setResponsePage(AdminPage.class); 
                } 
            }); 
	}

}
