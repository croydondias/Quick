package com.croydon.quick.page.component;

import org.apache.log4j.Logger;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.croydon.quick.config.AppProperties;
import com.croydon.quick.domain.Employee;
import com.croydon.quick.exception.EmployeeDoesntExistException;
import com.croydon.quick.page.AdminPage;
import com.croydon.quick.service.EmployeeService;

public class EmployeeForm extends StatelessForm {
	
	private static final Logger LOG = Logger.getLogger(EmployeeForm.class);
	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private PasswordEncoder passwordEncoder;
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
        PasswordTextField password = new PasswordTextField("password");
        add(password);
        
        Employee employee = (Employee)model.getObject();
        
        getSession().bind();
    	final String currentUserEmail = (String) getSession().getAttribute(AppProperties.CURRENT_USER);
    	boolean matchesCurrentUser = employee.getEmail().equals(currentUserEmail);
        
        add(new Button("save") { 
	            @Override
	        	public void onSubmit() {
	            	LOG.info("SAVE clicked");
	                Employee editedEmployee = (Employee) getForm().getModelObject();
	                updateEmployeeInDatabase(editedEmployee);
	                setResponsePage(AdminPage.class);
	            } 
        });
        
        Button removeButton = new Button("remove") { 
            @Override 
            public void onSubmit() { 
            	LOG.info("REMOVE clicked");
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
	
	private void updateEmployeeInDatabase(Employee editedEmployee) {
		Employee updateEmployee = employeeService.findByEmail(editedEmployee.getEmail());
        if (updateEmployee != null) {
        	boolean performUpdate = false;
            if (!updateEmployee.getFirstName().equals(editedEmployee.getFirstName())) {
            	updateEmployee.setFirstName(editedEmployee.getFirstName());
            	performUpdate = true;
            }
            if (!updateEmployee.getLastName().equals(editedEmployee.getLastName())) {
            	updateEmployee.setLastName(editedEmployee.getLastName());
            	performUpdate = true;
            }
            if (editedEmployee.getPassword() != null && !editedEmployee.getPassword().isEmpty()) {
            	final String encryptedPass = passwordEncoder.encode(editedEmployee.getPassword());
            	updateEmployee.setPassword(encryptedPass);
            	performUpdate = true;
            }
            
            if (performUpdate) {
            	LOG.info("Updating ...");
            	try {
					employeeService.save(updateEmployee);
				} catch (EmployeeDoesntExistException e) {
					LOG.error("Error saving employee", e);
				}
            }
        }
	}

}
