package core.config;

import core.meta.HttpClientType;
import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "classpath:local.properties",
        "classpath:environments/${env}/config.properties",
        "system:properties",
        "system:env"})
public interface ConfigProperties extends Config {
    // mvn clean test -Denv=demo "-Drp.password=1q2w3e" "-Drp.api.key=test123_08OFgrPXRjqJfGVTL53G-_2vN5-vvBYoJituz-Jo-eUcROJeMnUB1ruNnA7TPa3y"

    // mvn clean test -Denv=dev -Dtest=AddDashboardTest "-Drp.password=%password%" "-Drp.api.key=%token%"
    // mvn clean test -Denv=dev -Dtest=DeleteDashboardTest "-Drp.password=%password%" "-Drp.api.key=%token%"

    //todo: mvn clean test -Denv=dev -Dcucumber.filter.tags=@API "-Drp.password=%password%" "-Drp.api.key=%token%"
    //todo: mvn clean test -Denv=dev -Dcucumber.filter.tags="@API" "-Drp.password=%password%" "-Drp.api.key=%token%"
    //todo: mvn clean test -Denv=dev "-Dcucumber.filter.tags=@API" "-Drp.password=%password%" "-Drp.api.key=%token%"
    // mvn clean test -Denv=dev -Dcucumber.filter.tags="@API"
    // mvn clean test "-Dcucumber.filter.tags=@API" -Denv=dev

    // -ea -Denv=demo -Drp.password=1q2w3e -Drp.api.key=test123_08OFgrPXRjqJfGVTL53G-_2vN5-vvBYoJituz-Jo-eUcROJeMnUB1ruNnA7TPa3y
    // -ea -Denv=dev -Drp.password=${password} -Drp.api.key=${token} -DprojectName=2025-project


    //rp props
    @Key("rp.username")
    String login();
    @Key("rp.password")
    String password();
    @Key("rp.api.key")
    String apiKey();
    @Key("rp.project")
    String defaultRpProjectName();
    @Key("rp.endpoint")
    String rpUrl();

    //web props
    @Key("browser")
    String browser();
    @Key("runType")
    String runType();
    @Key("browserVersion")
    String browserVersion();
    @Key("webDriverRemoteUrl")
    String webDriverRemoteUrl();
    @Key("timeOutSeconds")
    Long timeOutSeconds();
    @Key("pollingTimeOutMilliSeconds")
    Long pollingTimeOutMilliSeconds();
    @Key("readTimeOutSeconds")
    Long readTimeOutSeconds();
    @Key("httpClientType")
    HttpClientType httpClientType();
    @Key("ui.engine")
    String engine();
    @Key("remote.username")
    String remoteUsername();
    @Key("remote.accessKey")
    String remoteAccessKey();
    @Key("platform")
    String platform();

}
