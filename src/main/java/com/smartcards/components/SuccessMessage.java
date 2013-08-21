package com.smartcards.components;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * Komponenta koja prikazuje success message, odnosno, čuva podatke o uspešnoj
 * poruci.
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
