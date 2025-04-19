package core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportPortalDashboardsResponse {
    public List<ReportPortalContentResponse> content;
}
