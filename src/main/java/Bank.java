import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bank {
    static public void main(String args[]) {
        Bank bank = new Bank();
        //bank.inputSingleCommand(); // по одной комманде
         bank.inputOfSeveralCommands(); // несколько комманд
    }

    private Map<Integer, Account> accounts;

    Bank() {
        accounts = new HashMap<Integer, Account>();
    }

    // Ввод по одной комманде,
    // сразу выводится результат.
    // Чтобы закончить выполнения программы,
    // нужно ввести пустую строку
    void inputSingleCommand() {
        Scanner input = new Scanner(System.in);
        input.useLocale(Locale.US);
        String line, response;
        while (true) {
            line = input.nextLine();
            if (line.equals("")) {
                System.out.println("The exit was successful");
                break;
            }
            System.out.println(processingInput(line));
        }
    }

    // Ввод группы комманд,
    // результат выполнения комманд выведится после пустой строки.
    void inputOfSeveralCommands() {
        Scanner input = new Scanner(System.in);
        input.useLocale(Locale.US);
        String line;
        List<String> lines = new ArrayList<String>();
        while (true) {
            line = input.nextLine();
            if (line.equals("")) {
                break;
            }
            lines.add(line);
        }
        for (String l : lines) {
            System.out.println(processingInput(l));
        }

    }

    String processingInput(String line) {
        Pattern p = Pattern.compile(
            "^((NEWACCOUNT)|(DEPOSIT)|(WITHDRAW)|(BALANCE)) ([0-9]{5})( ([0-9]+(\\.[0-9]+)?))?$"
        );
        Matcher m = p.matcher(line);
        if (m.find()) {
            String command = m.group(1);
            int account_number = Integer.parseInt(m.group(6));
            String num = m.group(8);
            return executeCommand(command, account_number, num);
        }
        return "ERROR";
    }

    String executeCommand(String command, int account_number, String num) {
        Double balance;
        if (command.equals("NEWACCOUNT")&& (num == null)) {
            return createNewAccount(account_number);
        } else if (command.equals("DEPOSIT") && (num != null)) {
            return deposit(account_number, Double.parseDouble(num));
        } else if (command.equals("WITHDRAW") && (num != null)) {
            return withdraw(account_number, Double.parseDouble(num));
        } else if (command.equals("BALANCE") && (num == null)) {
            balance = balance(account_number);
            if (balance >= 0)
                return Double.toString(balance);
        }
        return "ERROR";
    }

    String createNewAccount(int account_number) {
        if (checkAccountExist(account_number) != null)
            return "ERROR";
        setAccount(account_number, new Account());
        return "OK";
    }

    String deposit(int account_number, double num) {
        Account account = checkAccountExist(account_number);
        if (account == null)
            return "ERROR";
        account.depost(num);
        return "OK";
    }

    String withdraw(int account_number, double num) {
        Account account = checkAccountExist(account_number);
        if (account == null)
            return "ERROR";
        if (account.withdraw(num))
            return "OK";
        return "ERROR";
    }

    double balance(int account_number) {
        Account account = checkAccountExist(account_number);
        if (account == null)
            return -1;
        return account.getBalance();
    }

    private Account checkAccountExist(int account_number) {
        Map<Integer, Account> accounts = getAccounts();
        try {
            return accounts.get(account_number);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Map<Integer, Account> getAccounts() {
        return accounts;
    }

    public void setAccount(int account_number, Account account) {
        account.setAccount_number(account_number);
        accounts.put(account_number, account);
    }

}
