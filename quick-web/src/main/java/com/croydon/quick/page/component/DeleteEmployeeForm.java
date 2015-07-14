package com.croydon.quick.page.component;

import org.apache.log4j.Logger;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.croydon.quick.config.AppProperties;
import com.croydon.quick.domain.Employee;
import com.croydon.quick.page.AdminPage;
import com.croydon.quick.service.EmployeeService;

public class DeleteEmployeeForm extends StatelessForm {
	
	private static final Logger LOG = Logger.getLogger(DeleteEmployeeForm.class);
	private static final long serialVersionUID = 1L;
	
	@SpringBean
    private EmployeeService employeeService;
	
	public DeleteEmployeeForm(String id, IModel model) {
		super(id, model);
		
        Employee employee = (Employee)model.getObject();
        
        getSession().bind();
    	final String currentUserEmail = (String) getSession().getAttribute(AppProperties.CURRENT_USER);
    	boolean matchesCurrentUser = employee.getEmail().equals(currentUserEmail);
        
        Button removeButton = new Button("delete") { 
            @Override 
            public void onSubmit() { 
            	LOG.info("DELETE clicked");
            	Employee selected = (Employee) getForm().getModelObject();
            	employeeService.delete(selected.getId());
                setResponsePage(AdminPage.class); 
            } 
        };
        if (matchesCurrentUser) {
        	// Disable the remove button when the current user matches the employee item
        	removeButton.add(AttributeModifier.replace("disabled", "disabled"));
        }
        add(removeButton); 
	}

}
