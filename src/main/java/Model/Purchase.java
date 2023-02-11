package Model;

import java.time.LocalDate;

public class Purchase {
    float price;
    LocalDate date;
    String dealer;
    Equipment equipment;
    
    /**
     * Costruttore della classe Purchase. Riceve in input i dati necessari per la creazione dell'oggetto Purchase e inizializza i suoi attributi.
     * @param price         Prezzo della vendita
     * @param date          Data in cui è stata effettuata la vendita
     * @param dealer        Il venditore
     * @param equipment     Riferimento all'oggetto equipaggiamento venduto
    */
    public Purchase(float price, LocalDate date, String dealer, Equipment equipment){
        this.price=price;
        this.date=date;
        this.dealer=dealer;
        this.equipment=equipment;
    }

}
