import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;
import java.util.Optional;


public class App {

    private Client client;
    private EventLogger defaultLogger;
    private Map<EventType, EventLogger> loggers;

    public App(Client client, EventLogger defaultLogger, Map<EventType, EventLogger> loggers) {
        this.client = client;
        this.defaultLogger = defaultLogger;
        this.loggers = loggers;
    }

    private void logEvent(String msg, EventType eventType, ApplicationContext ctx) {
        Event event = (Event) ctx.getBean("event");
        event.setMsg(msg);

        EventLogger logger = loggers.get(eventType);
        Optional<EventLogger> optionalLogger = Optional.ofNullable(logger);
        if (!optionalLogger.isPresent()){
            logger = defaultLogger;
        }
        logger.logEvent(event);
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        App app = (App) ctx.getBean("app");
        app.logEvent("Some event for user 1", EventType.valueOf("INFO"), ctx);
        app.logEvent("Some event for user 2", null, ctx);
        app.logEvent("Some event for user 3", EventType.valueOf("ERROR"), ctx);
        ctx.close();
    }
}
