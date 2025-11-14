package scr.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Scanner;

public class Loans {

    private LocalDate date;
    private String type; // Opciones: Personal, Hipotecario, Auto
    private BigDecimal totalLoan;
    private BigDecimal amountPaid;
    private String description;

    public Loans() {}

    public Loans(LocalDate date, String type, BigDecimal totalLoan, BigDecimal amountPaid, String description) {
        setDate(date);
        setType(type);
        setTotalLoan(totalLoan);
        setAmountPaid(amountPaid);
        setDescription(description);
    }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) {
        if (date == null) throw new IllegalArgumentException("Fecha no puede ser nula.");
        this.date = date;
    }

    public String getType() { return type; }
    public void setType(String type) {
        String[] options = {"Personal", "Hipotecario", "Auto"};
        boolean valid = false;
        for (String opt : options) if (opt.equalsIgnoreCase(type)) valid = true;
        if (!valid) throw new IllegalArgumentException("Tipo inválido. Opciones: Personal, Hipotecario, Auto.");
        this.type = type;
    }

    public BigDecimal getTotalLoan() { return totalLoan; }
    public void setTotalLoan(BigDecimal totalLoan) {
        if (totalLoan.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Total Loan no puede ser negativo.");
        this.totalLoan = totalLoan.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getAmountPaid() { return amountPaid; }
    public void setAmountPaid(BigDecimal amountPaid) {
        if (amountPaid.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Amount Paid no puede ser negativo.");
        this.amountPaid = amountPaid.setScale(2, RoundingMode.HALF_UP);
    }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) throw new IllegalArgumentException("Descripción no puede estar vacía.");
        this.description = description.trim();
    }

    @Override
    public String toString() {
        return String.format("Loans [Fecha=%s, Tipo=%s, Total=%s, Pagado=%s, Desc=%s]",
                date, type, totalLoan, amountPaid, description);
    }

    public static Loans fromConsole(Scanner sc) {
        LocalDate date = null;
        String type = "";
        BigDecimal totalLoan = null, amountPaid = null;
        String description = "";

        while (true) {
            try {
                System.out.print("Fecha (YYYY-MM-DD): ");
                date = LocalDate.parse(sc.nextLine().trim());

                System.out.print("Tipo de préstamo (Personal/Hipotecario/Auto): ");
                type = sc.nextLine().trim();

                totalLoan = readBigDecimal(sc, "Total Loan: ");
                amountPaid = readBigDecimal(sc, "Amount Paid: ");

                System.out.print("Descripción: ");
                description = sc.nextLine().trim();

                return new Loans(date, type, totalLoan, amountPaid, description);
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
