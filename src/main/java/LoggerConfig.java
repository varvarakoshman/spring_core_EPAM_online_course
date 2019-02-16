import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class LoggerConfig {

    @Bean(initMethod = "init")
    public static FileEventLogger fileLogger() {
        return new FileEventLogger("textFile.txt");
    }

    @Bean
    public static ConsoleEventLogger consoleLogger() {
        return new ConsoleEventLogger();
    }

    @Bean(destroyMethod = "destroy")
    public static CacheFileEventLogger cachedLogger() {
        return new CacheFileEventLogger("textFile.txt", 10);
    }

    @Bean
    public static CombinedEventLogger combinedLogger(){
        List<EventLogger> list = new ArrayList<>();
        list.add(consoleLogger());
        list.add(fileLogger());
        return new CombinedEventLogger(list);
    }
}
