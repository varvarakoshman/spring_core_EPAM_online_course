import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

/*
Хорошо бы проверить до запуска приложения (но после создания всех бинов),
можно ли писать в указанный файл. Нужно выполнить некоторые действия
по инициализации отдельных бинов(доступен файл?). Такие проверки в конструкторе
бина - идея нехорошая(1.тк еще не все бины успели создаться(зависимости могут из пропертей приходить),
2. конструктор - для создания объектов, логика лишняя)

>init() метод
 */

public class FileEventLogger implements EventLogger {
    private String filename;
    private File file;

    @SneakyThrows
    FileEventLogger(String filename) {
        this.filename = filename;
        init();
    }

    @SneakyThrows
    public void logEvent(Event event) {
        FileUtils.writeStringToFile(file, event.toString(), true);
    }

    public void init() throws IOException {
        this.file = new File(filename);
        if (!file.canWrite()) {
            throw new IOException("unable to access writing in file");
        }
    }
}
