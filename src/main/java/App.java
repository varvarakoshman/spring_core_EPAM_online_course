import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class App {

    private Client client;
    private EventLogger eventLogger; //1 модификация - зависимость не от класса конкретного, а от интерфейса

    public App(Client client, EventLogger eventLogger) {
        this.client = client;
        this.eventLogger = eventLogger;
    }

    private void logEvent(String msg, ApplicationContext ctx) {
        //String message = msg.replaceAll(client.getId(), client.getFullName()); //step 1
        //Event currEvent = new Event(new Date()); // прописывание в коде понижает гибкость - выносим в xml
        Event event = (Event) ctx.getBean("event");
        event.setMsg(msg);
        eventLogger.logEvent(event);
    }

    public static void main(String[] args) {
        //App app = new App(); //step 1
        //app.client = new Client("1", "John Smith");//step 1
        //app.eventLogger = new ConsoleEventLogger();//step 1

        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml"); //2 модификация - внедрение зависимостей
        App app = (App) ctx.getBean("app"); //3 - все данные статические вынесли в xml
        app.logEvent("Some event for user 1", ctx);
    }
}
