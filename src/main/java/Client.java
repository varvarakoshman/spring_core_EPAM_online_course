import lombok.Data;

@Data
public class Client {
    String id;
    String fullName;

    Client(String id, String fullName){
        this.id = id;
        this.fullName = fullName;
    }

    String greeting;

    public void setGreeting(String gr){
        this.greeting = gr;
    }
}
