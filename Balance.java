package scr.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Scanner;

public class Balance {

    private LocalDate date;
    private String description;
    private BigDecimal cashIn;
    private BigDecimal cashOut;
    private BigDecimal closingBalance;

    public Balance() {}

    public Balance(LocalDate date, String description, BigDecimal cashIn, BigDecimal cashOut) {
        setDate(date);
        setDescription(description);
        setCashIn(cashIn);
        setCashOut(cashOut);
        this.closingBalance = cashIn.subtract(cashOut).setScale(2, RoundingMode.HALF_UP);
    }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) {
        if (date == null) throw new IllegalArgumentException("Fecha no puede ser nula.");
        this.date = date;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) throw new IllegalArgumentException("Descripción no puede estar vacía.");
        this.description = description.trim();
    }

    public BigDecimal getCashIn() { return cashIn; }
    public void setCashIn(BigDecimal cashIn) {
        if (cashIn.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Cash In no puede ser negativo.");
        this.cashIn = cashIn.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getCashOut() { return cashOut; }
    public void setCashOut(BigDecimal cashOut) {
        if (cashOut.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Cash Out no puede ser negativo.");
        this.cashOut = cashOut.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getClosingBalance() { return closingBalance; }

    @Override
    public String toString() {
        return String.format("Balance [Fecha=%s, Desc=%s, CashIn=%s, CashOut=%s, Closing=%s]",
                date, description, cashIn, cashOut, closingBalance);
    }

    public static Balance fromConsole(Scanner sc) {
        LocalDate date = null;
        String description = "";
        BigDecimal cashIn = null, cashOut = null;

        while (true) {
            try {
                System.out.print("Fecha (YYYY-MM-DD): ");
                date = LocalDate.parse(sc.nextLine().trim());

                System.out.print("Descripción: ");
                description = sc.nextLine().trim();

                cashIn = readBigDecimal(sc, "Cash In: ");
                cashOut = readBigDecimal(sc, "Cash Out: ");

                return new Balance(date, description, cashIn, cashOut);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage() + " Intente nuevamente.\n");
            }
        }
    }

    private static BigDecimal readBigDecimal(Scanner sc, String prompt) {
        BigDecimal val = null;
        while (val == null) {
            try {
                System.out.print(prompt);
                String line = sc.nextLine().trim();
                val = new BigDecimal(line);
                if (val.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("No puede ser negativo.");
            } catch (Exception e) {
                System.out.println("Valor inválido. " + e.getMessage());
            }
        }
        return val.setScale(2, RoundingMode.HALF_UP);
    }
}
