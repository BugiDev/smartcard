package com.smartcards.pages;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;

@Import(stylesheet = {"context:css/login_style.css"}, library = {"context:js/jquery-1.5.2.min.js", "context:js/login_onStart.js"})
public class Login {

    @Persist(PersistenceConstants.FLASH)
    @Validate("required")
    @Property
    private String username;
    @Validate("required")
    @Persist(PersistenceConstants.FLASH)
    @Property
    private String password;
    
    @InjectPage
    private NewCardsUsers newCardsUsers;
    
        @Component(id = "loginForm")
    private Form form;
        
     public NewCardsUsers onSubmit(){
     return newCardsUsers;
   }
}
