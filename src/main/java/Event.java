import lombok.Setter;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

//@ToString
class Event {
    private Random rn = new Random();
    private int id;
    @Setter
    private String msg;
    private Date date;
    private DateFormat df;

    Event(Date date, DateFormat df) {
        this.id = rn.nextInt();
        this.date = date;
        this.df = df;
    }

    public String toString(){
        return "id: " + id + " message: " + msg + " date: " + df.format(date) + " ";
    }

}
