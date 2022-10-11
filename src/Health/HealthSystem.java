package Health;

public class HealthSystem {
    public static int healthCount = 100;

    public static void zombieTouchPlayer() {
        healthCount -= 15;
    }
    public static void setMaxHealth(){
        healthCount = 100;
    }

}


