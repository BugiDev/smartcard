/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcards.services;

import com.smartcards.entities.User;
import com.smartcards.pages.Login;
import com.smartcards.util.UserType;
import java.io.IOException;
import org.apache.tapestry5.runtime.Component;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.ComponentSource;
import org.apache.tapestry5.services.Dispatcher;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mateja
 */
public class ProtectPageService implements Dispatcher {
    private Logger logger = LoggerFactory.getLogger(Login.class);
    private final static String LOGIN_PAGE = "/login";
    private ApplicationStateManager applicationStateManager;
    private final ComponentClassResolver componentClassResolver;
    private final ComponentSource componentSource;

    public ProtectPageService(ApplicationStateManager asm, ComponentClassResolver resolver, ComponentSource componentSource) {
        this.applicationStateManager = asm;
        this.componentClassResolver = resolver;
        this.componentSource = componentSource;
    }

    public boolean dispatch(Request request, Response response) throws IOException {
        /*
         * We need to get the Tapestry page requested by the user. So we parse the path extracted from the request
         */
        String path = request.getPath();
        int nextslashx = path.length();
        String pageName;

        while (true) {
            pageName = path.substring(1, nextslashx);
            if (!pageName.endsWith("/") && componentClassResolver.isPageName(pageName)) {
                break;
            }
            nextslashx = path.lastIndexOf('/', nextslashx - 1);
            if (nextslashx <= 1) {
                return false;
            }
        }
        return checkAccess(pageName, request, response);
    }

    public boolean checkAccess(String pageName, Request request, Response response) throws IOException {
        boolean canAccess = true;

        /* Is the requested page private ? */
        Component page = componentSource.getPage(pageName);
        boolean protectedPage = page.getClass().getAnnotation(ProtectedPage.class) != null;

        if (protectedPage) {
            canAccess = false;
            // If a Visit already exists in the session then you have already been authenticated
            if (applicationStateManager.exists(User.class)) {
                boolean adminPage = isAdminPage(page);
                User userCurrent = applicationStateManager.getIfExists(User.class);
                logger.debug("Protected Username ASO = "+userCurrent.getUsername());
                logger.debug("Protected RoleType ASO = "+userCurrent.getRoleType());
                logger.debug("Test admin code = " + UserType.ADMIN.getCode());
                logger.debug("Admin page = " + adminPage);
                
                if (adminPage && (userCurrent.getRoleType() == UserType.ADMIN.getCode() || userCurrent.getRoleType() == UserType.MODERATOR.getCode() || userCurrent.getRoleType() == UserType.CONTRIBUTOR.getCode())) {
                    canAccess = true;
                } else if (adminPage) {
                    canAccess = false;
                } else {
                    canAccess = false;
                }
            }
        }
        /*
         * This page can't be requested by a non-authenticated user => we redirect him to the LogIn page
         */
        if (!canAccess) {
            response.sendRedirect(request.getContextPath() + LOGIN_PAGE);
            return true; // Make sure to leave the chain
        }
        return false;
    }

    private boolean isAdminPage(Component page) {
        UserType[] userTypeArray = page.getClass().getAnnotation(ProtectedPage.class).getRoles();
        for (UserType userType : userTypeArray) {
            if (userType == UserType.ADMIN || userType == UserType.CONTRIBUTOR || userType == UserType.MODERATOR) {
                return true;
            }
        }
        return false;
    }
}
