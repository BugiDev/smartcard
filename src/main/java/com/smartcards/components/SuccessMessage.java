/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcards.components;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 *
 * @author Bogdan
 */
public class SuccessMessage {
    @Parameter(required = true)
    @Property
    private String text;
    
    @Parameter(required = true)
    @Property
    private String cssClass;
    
}
