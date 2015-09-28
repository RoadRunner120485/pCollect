package de.sturmm.pcollect.rest.model;

/**
 * Created by sturmm on 20.09.15.
 */
public class LinkResponse {
    private final String href;

    public LinkResponse(String href) {
        this.href = href;
    }

    public String getHref() {
        return href;
    }

}
