package Model;

import java.time.LocalDate;

public class Purchase {
    float price;
    LocalDate date;
    String dealer;
    Equipment equipment;
    public Purchase(float price, LocalDate date, String dealer, Equipment equipment){
        this.price=price;
        this.date=date;
        this.dealer=dealer;
        this.equipment=equipment;
    }

}
