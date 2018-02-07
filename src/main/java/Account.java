public class Account {
    private int account_number;
    private double balance;

    public Account() {
        this.balance = 0;
    }

    public void depost(double num){
        setBalance(num + getBalance());
    }

    public boolean withdraw(double num){
        if (balance >= num) {
            setBalance(getBalance() - num);
            return true;
        }
        return false;
    }

    private void setBalance(double balance){
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setAccount_number(int account_number) {
        this.account_number = account_number;
    }
}
