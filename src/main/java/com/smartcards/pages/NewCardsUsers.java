package com.smartcards.pages;

import com.smartcards.services.ProtectedPage;
import com.smartcards.util.UserType;

/**
 *
 * @author Bogdan Begovic
 */
@ProtectedPage(getRoles = {UserType.ADMIN, UserType.MODERATOR, UserType.CONTRIBUTOR})
public class NewCardsUsers
{

}
