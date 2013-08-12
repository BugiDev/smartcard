/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcards.components;

import com.smartcards.util.UserType;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;

/**
 *
 * @author Bogdan Begovic
 */
public class ShowRoleType {

    @Parameter(required = true)
    private long userRoleType;
    @Parameter
    @Property
    private String result;

    @SetupRender
    void initializeValues() {

        if (userRoleType == UserType.ADMIN.getCode()) {
            result = "Admin";
        } else if (userRoleType == UserType.MODERATOR.getCode()) {
            result = "Moderator";
        } else if (userRoleType == UserType.CONTRIBUTOR.getCode()) {
            result = "Contributor";
        } else {
            result = "User";
        }
    }
}
