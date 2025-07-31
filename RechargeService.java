import java.sql.*;
import java.util.Scanner;

public class RechargeService {
    Scanner scanner = new Scanner(System.in);

    public void startRecharge() {
        System.out.print("Enter your mobile number: ");
        String mobile = scanner.nextLine();

        System.out.println("Select Network Provider:");
        System.out.println("1. Jio\n2. Airtel\n3. Vi\n4. BSNL");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String provider = "";
        switch (choice) {
            case 1 -> provider = "Jio";
            case 2 -> provider = "Airtel";
            case 3 -> provider = "Vi";
            case 4 -> provider = "BSNL";
            default -> {
                System.out.println("Invalid provider!");
                return;
            }
        }

        System.out.println("Available Plans:");
        System.out.println("1. ₹199 - 28 days\n2. ₹399 - 56 days\n3. ₹599 - 84 days");
        System.out.print("Choose a plan (1-3): ");
        int planChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String plan = "";
        double amount = 0;
        String validity = "";

        switch (planChoice) {
            case 1 -> {
                plan = "Rs.199 - Unlimited Calls & 1.5GB/day";
                amount = 199;
                validity = "28 days";
            }
            case 2 -> {
                plan = "Rs.399 - Unlimited Calls & 1.5GB/day";
                amount = 399;
                validity = "56 days";
            }
            case 3 -> {
                plan = "Rs.599 - Unlimited Calls & 2GB/day";
                amount = 599;
                validity = "84 days";
            }
            default -> {
                System.out.println("Invalid plan!");
                return;
            }
        }

        // BANK DETAILS
        System.out.println("\n🔐 Enter Bank Details for Payment");
        System.out.print("Enter Account Number: ");
        String accountNo = scanner.nextLine();

        System.out.print("Enter IFSC Code: ");
        String ifsc = scanner.nextLine();

        System.out.print("Enter UPI ID: ");
        String upiId = scanner.nextLine();

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);  // For transaction

            // 1. Validate bank account and balance
            String validateQuery = "SELECT balance FROM bank_account WHERE account_no = ? AND ifsc_code = ? AND upi_id = ?";
            PreparedStatement validateStmt = conn.prepareStatement(validateQuery);
            validateStmt.setString(1, accountNo);
            validateStmt.setString(2, ifsc);
            validateStmt.setString(3, upiId);

            ResultSet rs = validateStmt.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("balance");

                if (balance >= amount) {
                    // 2. Deduct amount
                    String updateBalance = "UPDATE bank_account SET balance = balance - ? WHERE account_no = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateBalance);
                    updateStmt.setDouble(1, amount);
                    updateStmt.setString(2, accountNo);
                    updateStmt.executeUpdate();

                    // 3. Store recharge
                    String insertRecharge = "INSERT INTO recharge_details (mobile_number, provider, plan, amount, validity, upi_id, account_no) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement rechargeStmt = conn.prepareStatement(insertRecharge);
                    rechargeStmt.setString(1, mobile);
                    rechargeStmt.setString(2, provider);
                    rechargeStmt.setString(3, plan);
                    rechargeStmt.setDouble(4, amount);
                    rechargeStmt.setString(5, validity);
                    rechargeStmt.setString(6, upiId);
                    rechargeStmt.setString(7, accountNo);
                    rechargeStmt.executeUpdate();

                    conn.commit();  // Commit transaction

                    System.out.println("\n✅ Recharge Successful!");
                    System.out.println("Mobile: " + mobile);
                    System.out.println("Plan: " + plan);
                    System.out.println("Validity: " + validity);
                    System.out.println("Amount Deducted: ₹" + amount);
                } else {
                    System.out.println("❌ Insufficient balance in your account.");
                }
            } else {
                System.out.println("❌ Bank account or UPI ID not valid.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Recharge failed: " + e.getMessage());
        }


        System.out.println("\n🙏 Thanks for recharging. Visit again!");
    }
    public void showRechargeHistory() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM recharge_details";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n===== Recharge History =====");
            System.out.printf("%-15s %-10s %-35s %-10s %-12s %-20s %-15s%n",
                    "Mobile No", "Provider", "Plan", "Amount", "Validity", "UPI ID", "Account No");
            System.out.println("---------------------------------------------------------------------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                found = true;
                String mobile = rs.getString("mobile_number");
                String provider = rs.getString("provider");
                String plan = rs.getString("plan");
                double amount = rs.getDouble("amount");
                String validity = rs.getString("validity");
                String upi = rs.getString("upi_id");
                String accNo = rs.getString("account_no");

                System.out.printf("%-15s %-10s %-35s ₹%-9.2f %-12s %-20s %-15s%n",
                        mobile, provider, plan, amount, validity, upi, accNo);
            }

            if (!found) {
                System.out.println("⚠️  No recharge history found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error fetching recharge history.");
        }
    }



}