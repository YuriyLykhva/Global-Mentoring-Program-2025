package core.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "classpath:local.properties",
        "classpath:environments/${env}/config.properties",
        "system:properties",
        "system:env"})
public interface ConfigProperties extends Config {
    // mvn -Dtest=Test1 -Denv=qa -Dpassword=%password% test
    // -ea -Denv=qa -Dpassword=${password}

    String login();
    String password();

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
