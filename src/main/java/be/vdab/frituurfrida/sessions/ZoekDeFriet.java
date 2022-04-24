package be.vdab.frituurfrida.sessions;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.Random;


@Component
@SessionScope
public class ZoekDeFriet implements Serializable {

    private static final long SerialVersionUID = 1L;
    private static final int AANTAL_DEUREN = 7;
    private final Deur [] deuren = new Deur[AANTAL_DEUREN];
    private boolean gevonden = false;

    public ZoekDeFriet() {
        reset();
    }

    public boolean isGevonden() {
        return gevonden;
    }

    public void openDeur(int index) {
        deuren[index].open();
        if (deuren[index].isMetFriet()) {
            gevonden = true;
        }
    }

    public Deur[] getDeuren() {
        return deuren;
    }

    public void reset() {
        var random = new Random();
        var indexMetFriet = random.nextInt(AANTAL_DEUREN);
        for (var index = 0; index != AANTAL_DEUREN; index++) {
            deuren[index]= new Deur(index, index == indexMetFriet);
            gevonden = false;
        }
    }
}