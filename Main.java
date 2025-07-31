import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RechargeService rechargeService = new RechargeService();
        BankAccountService bankService = new BankAccountService();

        while (true) {
            System.out.println("\n===== Mobile Recharge Management =====");
            System.out.println("1. Recharge Mobile");
            System.out.println("2. Add Bank Account");
            System.out.println("3. Show Bank Accounts");
            System.out.println("4. Show Recharge History");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> rechargeService.startRecharge();
                case 2 -> bankService.addBankAccount();
                case 3 -> bankService.displayBankAccounts();
                case 4 -> rechargeService.showRechargeHistory();
                case 5 -> {
                    System.out.println("👋 Exiting. Goodbye!");
                    return;
                }
                default -> System.out.println("❌ Invalid choice. Try again.");
            }

        }
    }
}
