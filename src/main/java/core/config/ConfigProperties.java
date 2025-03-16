package core.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "classpath:local.properties",
        "classpath:environments/${env}/config.properties",
        "system:properties",
        "system:env"})
public interface ConfigProperties extends Config {
    // mvn clean -Dtest=LoginTest -Denv=qa -Dpassword=%password% -Dtoken=%token% test
    // -ea -Denv=qa -Dpassword=${password} -Dtoken={token}

    String login();
    String password();

    @Key("token")
    String token();

    @Key("browser")
    String browser();

    @Key("runType")
    String runType();

    @Key("browserVersion")
    String browserVersion();

    @Key("localUrl")
    String localUrl();

    @Key("remoteUrl")
    String remoteUrl();

    @Key("timeOutSeconds")
    Long timeOutSeconds();

    @Key("pollingTimeOutMilliSeconds")
    Long pollingTimeOutMilliSeconds();

    @Key("readTimeOutSeconds")
    Long readTimeOutSeconds();

    @Key("report_portal.base.url")
    String reportPortalBaseUrl();

}
