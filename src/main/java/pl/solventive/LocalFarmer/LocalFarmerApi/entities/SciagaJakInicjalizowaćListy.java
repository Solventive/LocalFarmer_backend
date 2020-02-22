package pl.solventive.LocalFarmer.LocalFarmerApi.entities;

import javax.persistence.ElementCollection;
import java.util.List;

public class SciagaJakInicjalizowaćListy {

    @ElementCollection(targetClass = Integer.class)
    private List<Integer> postingsIssued;
    @ElementCollection(targetClass = Integer.class)
    private List<Integer> itemsSold;
    @ElementCollection(targetClass = Integer.class)
    private List<Integer> itemsBought;
    @ElementCollection(targetClass = Integer.class)
    private List<Integer> transactions;
    @ElementCollection(targetClass = Integer.class)
    private List<Integer> opinions;
    @ElementCollection(targetClass = Integer.class)
    private List<Integer> messages;
}
