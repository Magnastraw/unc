package src.bank;


public class Client {
    private double moneyAmount;
    private double clientMoney;
    private long time;
    private boolean operationType;

    public Client(double moneyAmount, boolean operationType, long time) {
        this.moneyAmount = moneyAmount;
        this.operationType = operationType;
        this.time = time;
        if(operationType){
            setClientMoney(moneyAmount);
        }
    }

    public boolean moneyOperation() {
        return operationType;
    }

    public double getMoney() {
        return moneyAmount;
    }

    public long getTime() {
        return time;
    }

    public void setClientMoney(double money) {
        this.clientMoney = money;
    }

    public double getClientMoney() {
        return clientMoney;
    }


}
