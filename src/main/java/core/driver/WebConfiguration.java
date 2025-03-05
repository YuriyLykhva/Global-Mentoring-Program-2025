package core.driver;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class WebConfiguration {

    private String browserName;
    private RunType runType;
    private String browserVersion;
    private String localUrl;
    private String remoteUrl;
    private Long timeOutSeconds;
    private Long pollingTimeOutMilliSeconds;
    private Long readTimeOutSeconds;
    private Map<String, String> remoteCapabilities;

}
