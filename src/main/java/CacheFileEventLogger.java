import lombok.SneakyThrows;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

public class CacheFileEventLogger extends FileEventLogger {
    private Integer cacheSize;
    private List<Event> cache = new ArrayList<>();

    @SneakyThrows
    CacheFileEventLogger(String filename, Integer cacheSize) {
        super(filename);
        super.init();
        this.cacheSize = cacheSize;
    }

    @Override
    public void logEvent(Event event) {
        cache.add(event);
        if (cache.size() == cacheSize) {
            writeEventsFromCache();
            cache.clear();
        }
    }

    private void writeEventsFromCache() {
        for (Event ev : cache) {
            super.logEvent(ev);
        }
    }

    public void destroy() {
        if (!cache.isEmpty()) {
            writeEventsFromCache();
        }
    }

}
