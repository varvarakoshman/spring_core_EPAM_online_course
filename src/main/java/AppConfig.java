import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;

@Configuration
@Import(LoggerConfig.class)
@PropertySource("classpath:config/client.properties")
public class AppConfig {

    @Value("${id}")
    private String id;
    @Value("${name}")
    private String name;
    @Value("${greeting}")
    private String greeting;

    // needed to resolve the properties injected with @Value
    @Bean
    public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer(){
        PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
        propertyPlaceholderConfigurer.setLocation(new ClassPathResource("config/client.properties"));
        return  propertyPlaceholderConfigurer;
    }

    @Bean
    @Scope("prototype")
    public Event event(){
        return new Event(new Date(), DateFormat.getDateTimeInstance());
    }

    @Bean
    public Client client() {
        Client client = new Client(id, name);
        client.setGreeting(greeting);
        return client;
    }

    @Bean
    public HashMap<EventType, EventLogger> loggersMap(){
        HashMap<EventType,EventLogger> map = new HashMap<>();
        map.put(EventType.INFO, LoggerConfig.consoleLogger());
        map.put(EventType.ERROR, LoggerConfig.combinedLogger());
        return map;
    }

    @Bean
    public App app() {
        return new App(client(), LoggerConfig.cachedLogger(),loggersMap());
    }

}
