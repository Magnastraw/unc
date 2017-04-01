package src.bank;


import java.util.Random;

public class Main {
    private static final int N = 4;
    private static Random random = new Random();

    public static void main(String[] arg) throws InterruptedException {
        Bank bank = new Bank(N);
        while (true) {
            Client client = generateNewRandomClient();
            bank.getFreeWorker().addClient(client);
            Thread.sleep(1000);
        }
    }

    private static Client generateNewRandomClient() {
        double money = 200 * random.nextDouble();
        long time = (long) (random.nextDouble() * 8000);
        boolean operationType = random.nextBoolean();
        return new Client(money, operationType, time);
    }
}
