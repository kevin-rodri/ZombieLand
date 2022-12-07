package Health;

public class HealthSystem {
    public static int healthCount = 50;

    public static void zombieTouchPlayer() {
        healthCount -= 5;
    }
    public static void setMaxHealth(){
        healthCount += 50;
    }

}


