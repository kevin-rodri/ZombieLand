package MoneySystem;
import Players.Alex;

public class MoneyBase {
    public static int moneyCount = 10;

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
    public static void addMoneyMini(){ moneyCount += 25;}
    public static void buyAssault(){ moneyCount -= 100;}
    public static void buyPistol(){ moneyCount -= 50;}
    public static void buyMG(){ moneyCount-=500;}
    public static void buy30Rounds(){ moneyCount-=100;}
    public static void buy60Rounds(){ moneyCount-=150;}
    public static void buy120Rounds(){ moneyCount-=200;}

}

