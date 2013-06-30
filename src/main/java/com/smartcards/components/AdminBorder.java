package com.smartcards.components;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * Layout component for pages of application smartcards.
 */
@Import(stylesheet = {"context:css/layout.css", "context:css/tooltip.css"},
        library = { "context:js/hideshow.js", "context:js/jquery.tablesorter.min.js", "context:js/jquery.equalHeight.js", "context:js/jquery.index.documentLoad.js"})
public class AdminBorder {

    @Property
    @Parameter(required = true, defaultPrefix = "literal")
    private String pageTitle;
    @Property
    @Parameter(required = true, defaultPrefix = "literal")
    private String tabTitle;
    @Property
    @Parameter(required = true, defaultPrefix = "literal")
    private String breadcrumb;
}
