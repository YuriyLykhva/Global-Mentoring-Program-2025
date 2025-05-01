package core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportPortalContentResponse {
    public String owner;
    public int id;
    public String name;
}
