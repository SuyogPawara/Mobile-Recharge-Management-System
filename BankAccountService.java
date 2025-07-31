import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class BankAccountService {
    Scanner scanner = new Scanner(System.in);

    public void addBankAccount() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("\n===== Add New Bank Account =====");
            System.out.print("Enter Account Number: ");
            String accountNo = scanner.nextLine();

            System.out.print("Enter IFSC Code: ");
            String ifsc = scanner.nextLine();

            System.out.print("Enter UPI ID: ");
            String upi = scanner.nextLine();

            System.out.print("Enter Balance: ");
            double balance = Double.parseDouble(scanner.nextLine());

            String insertQuery = "INSERT INTO bank_account (account_no, ifsc_code, upi_id, balance) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setString(1, accountNo);
            pstmt.setString(2, ifsc);
            pstmt.setString(3, upi);
            pstmt.setDouble(4, balance);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Bank account added successfully!");
            } else {
                System.out.println("❌ Failed to add bank account.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void displayBankAccounts() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM bank_account";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n===== Bank Accounts =====");
            System.out.printf("%-15s %-15s %-20s %-10s%n", "Account No", "IFSC Code", "UPI ID", "Balance");
            System.out.println("-------------------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                found = true;
                String accNo = rs.getString("account_no");
                String ifsc = rs.getString("ifsc_code");
                String upi = rs.getString("upi_id");
                double balance = rs.getDouble("balance");

                System.out.printf("%-15s %-15s %-20s ₹%-10.2f%n", accNo, ifsc, upi, balance);
            }

            if (!found) {
                System.out.println("⚠️  No bank accounts found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

