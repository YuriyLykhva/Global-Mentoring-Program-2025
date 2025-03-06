package core.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "classpath:local.properties",
        "classpath:${env}.properties"
})
public interface EnvironmentProperties extends Config {

        @Key("report_portal.base.url")
        String reportPortalBaseUrl();

}
