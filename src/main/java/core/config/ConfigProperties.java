package core.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "classpath:local.properties",
        "classpath:environments/${env}/config.properties",
        "system:properties",
        "system:env"})
public interface ConfigProperties extends Config {
    // mvn clean -Dtest=LoginTest -Denv=demo "-Drp.password=%password%" "-Drp.api.key=%token%" test
    // -ea -Denv=demo -Drp.password=${password} -Drp.api.key={rp.api.key}

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


}
