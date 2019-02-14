import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CombinedEventLogger implements EventLogger {
    private List<EventLogger> loggers;


    public void logEvent(Event event) {
        for (EventLogger logger : loggers) {
            logger.logEvent(event);
        }
    }
}
