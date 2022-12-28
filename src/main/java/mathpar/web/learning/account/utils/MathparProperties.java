package mathpar.web.learning.account.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Getter
@Slf4j
@NoArgsConstructor
public class MathparProperties {
    private String databaseUsername;
    private String databasePassword;
    private String databaseUrl;

    private String schoolModulePrefix;

    private String mailHost;
    private int mailPort;
    private String mailUsername;
    private String mailPassword;

    public void loadPropertiesFromManager(String secretmanagerUrlPrefix) {
        RestTemplate restTemplate = new RestTemplate();
        AuthenticationProperties namespaceProperties = restTemplate.getForObject(secretmanagerUrlPrefix+"/getNamespaceProperties?namespace=account", AuthenticationProperties.class);
        if(namespaceProperties==null) throw new RuntimeException("Can't load authentication properties");
        this.databasePassword = namespaceProperties.databasePassword;
        this.databaseUsername = namespaceProperties.databaseUsername;
        this.databaseUrl = namespaceProperties.databaseUrl;
        this.schoolModulePrefix = namespaceProperties.schoolUrl;
        var emailingProperties = restTemplate.getForObject(secretmanagerUrlPrefix+"/getNamespaceProperties?namespace=emailing", MailerProperties.class);
        if(emailingProperties==null) throw new RuntimeException("Can't load mailing properties..");
        this.mailHost = emailingProperties.mailerHost;
        this.mailPort = Integer.parseInt(emailingProperties.mailerPort);
        this.mailUsername = emailingProperties.mailerUsername;
        this.mailPassword = emailingProperties.mailerPassword;
    }

    @Data
    public static class AuthenticationProperties {
        @JsonProperty("databaseUrl")
        private String databaseUrl;
        @JsonProperty("databaseUsername")
        private String databaseUsername;
        @JsonProperty("databasePassword")
        private String databasePassword;
        @JsonProperty("schoolUrl")
        private String schoolUrl;
    }

    @Data
    public static class MailerProperties{
        @JsonProperty("MailerHost")
        private String mailerHost;
        @JsonProperty("MailerPort")
        private String mailerPort;
        @JsonProperty("MailerUsername")
        private String mailerUsername;
        @JsonProperty("MailerPassword")
        private String mailerPassword;
    }
}
