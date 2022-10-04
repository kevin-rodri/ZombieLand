package MoneySystem;
import Players.Alex;

public class MoneyBase {
    public static int moneyCount = 0;

    public static void addMoneyZombie() {
        moneyCount += 50;
    }
    public static void addMoneyPowerup(){
        moneyCount += 20;
    }
    public static void addMoneyX2(){
        moneyCount *= 2;
    }
    public static void addMoneyOT(){
        moneyCount += 10;
    }
}

