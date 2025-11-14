package scr.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class Cards {

    private String cardNumber;
    private String cardHolder;
    private String cardType;  // opciones: "checking", "savings"
    private BigDecimal creditLimit;
    private String description;

    public Cards() {}

    public Cards(String cardNumber, String cardHolder, String cardType, BigDecimal creditLimit, String description) {
        setCardNumber(cardNumber);
        setCardHolder(cardHolder);
        setCardType(cardType);
        setCreditLimit(creditLimit);
        setDescription(description);
    }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) {
        if (!cardNumber.matches("\\d{8}")) throw new IllegalArgumentException("Número de tarjeta debe tener 8 dígitos.");
        this.cardNumber = cardNumber;
    }

    public String getCardHolder() { return cardHolder; }
    public void setCardHolder(String cardHolder) {
        if (cardHolder == null || cardHolder.trim().isEmpty()) throw new IllegalArgumentException("Titular no puede estar vacío.");
        if (!cardHolder.matches("[a-zA-Z ]+")) throw new IllegalArgumentException("Titular solo puede contener letras y espacios.");
        this.cardHolder = cardHolder.trim();
    }

    public String getCardType() { return cardType; }
    public void setCardType(String cardType) {
        String[] options = {"checking", "savings"};
        boolean valid = false;
        for (String opt : options) if (opt.equalsIgnoreCase(cardType)) valid = true;
        if (!valid) throw new IllegalArgumentException("Tipo de tarjeta inválido. Opciones: checking, savings");
        this.cardType = cardType;
    }

    public BigDecimal getCreditLimit() { return creditLimit; }
    public void setCreditLimit(BigDecimal creditLimit) {
        if (creditLimit.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Límite de crédito no puede ser negativo.");
        this.creditLimit = creditLimit.setScale(2, RoundingMode.HALF_UP);
    }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) throw new IllegalArgumentException("Descripción no puede estar vacía.");
        this.description = description.trim();
    }

    @Override
    public String toString() {
        return String.format("Cards [Número=%s, Titular=%s, Tipo=%s, Límite=%s, Desc=%s]",
                cardNumber, cardHolder, cardType, creditLimit, description);
    }

    public static Cards fromConsole(Scanner sc) {
        String cardNumber, cardHolder, cardType, description;
        BigDecimal creditLimit;

        while (true) {
            try {
                System.out.print("Número de tarjeta (8 dígitos): ");
                cardNumber = sc.nextLine().trim();

                System.out.print("Titular de la tarjeta: ");
                cardHolder = sc.nextLine().trim();

                System.out.print("Tipo de tarjeta (checking\",savings): ");
                cardType = sc.nextLine().trim();

                creditLimit = readBigDecimal(sc, "Límite de crédito: ");

                System.out.print("Descripción: ");
                description = sc.nextLine().trim();

                return new Cards(cardNumber, cardHolder, cardType, creditLimit, description);
            } catch (IllegalArgumentException e) {
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
