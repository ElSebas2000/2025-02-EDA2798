package scr;

import java.util.Scanner;
import scr.model.Account;
import scr.model.Balance;
import scr.model.Cards;
import scr.model.Loans;
import scr.services.AccountService;
import scr.services.IBalanceService;
import scr.services.BalanceService;
import scr.services.ILoansService;
import scr.services.LoansService;
import scr.services.ICardService;
import scr.services.CardService;

import java.math.BigDecimal;
import java.time.LocalDate;

public class App {

    private static AccountService accountService = new AccountService();
    private static IBalanceService balanceService = new BalanceService();
    private static ILoansService loansService = new LoansService();
    private static ICardService cardsService = new CardService();

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            boolean running = true;

            while (running) {
                printMainMenu();
                String option = sc.nextLine().trim();

                switch (option) {
                    case "1": runAccountMenu(sc); break;
                    case "2": runBalanceMenu(sc); break;
                    case "3": runLoansMenu(sc); break;
                    case "4": runCardsMenu(sc); break;
                    case "0":
                        running = false;
                        System.out.println("\nSaliendo del sistema. ¡Hasta luego!");
                        break;
                    default:
                        System.out.println("Opción inválida, intenta de nuevo.");
                }
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. Accounts");
        System.out.println("2. Balance");
        System.out.println("3. Loans");
        System.out.println("4. Cards");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void printCrudMenu(String entity) {
        System.out.println("\n--- CRUD " + entity + " ---");
        System.out.println("1. Create");
        System.out.println("2. Read by ID");
        System.out.println("3. List all");
        System.out.println("4. Update");
        System.out.println("5. Delete");
        System.out.println("0. Volver");
        System.out.print("Seleccione: ");
    }

    // ACCOUNTS
    private static void runAccountMenu(Scanner sc) {
        boolean back = false;
        while (!back) {
            printCrudMenu("Account");
            String opt = sc.nextLine().trim();
            switch (opt) {
                case "1":
                    Account account = readAccountFromConsole(sc);
                    accountService.save(account);
                    System.out.println("✔ Cuenta creada exitosamente.");
                    break;
                case "2":
                    System.out.print("Ingrese ID: ");
                    String id = sc.nextLine().trim();
                    accountService.findById(id).ifPresentOrElse(
                        a -> System.out.println(formatAccount(a)),
                        () -> System.out.println("No existe una cuenta con ese ID.")
                    );
                    break;
                case "3": printAllAccounts(); break;
                case "4":
                    System.out.print("Ingrese ID a actualizar: ");
                    String idUp = sc.nextLine().trim();
                    Account updated = readAccountFromConsole(sc);
                    updated.setAccountNumber(idUp);
                    accountService.save(updated);
                    System.out.println("✔ Cuenta actualizada.");
                    break;
                case "5":
                    System.out.print("ID a eliminar: ");
                    String idDel = sc.nextLine().trim();
                    if (accountService.deleteById(idDel))
                        System.out.println("✔ Eliminado.");
                    else
                        System.out.println("No existía una cuenta con ese ID.");
                    break;
                case "0": back = true; break;
                default: System.out.println("Opción inválida, intenta de nuevo.");
            }
        }
    }

    private static Account readAccountFromConsole(Scanner sc) {
        while (true) {
            try {
                System.out.print("AccountNumber (ej: ACC001): ");
                String num = sc.nextLine().trim();
                if(num.isEmpty()) throw new IllegalArgumentException("AccountNumber no puede estar vacío");

                System.out.print("Nombre: ");
                String name = sc.nextLine().trim();
                if(name.isEmpty() || !name.matches("(?i)[a-záéíóúñ ]+"))
                    throw new IllegalArgumentException("Nombre inválido, solo letras y espacios permitidos.");

                System.out.print("Email: ");
                String email = sc.nextLine().trim();
                if(email.isEmpty() || !email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$"))
                    throw new IllegalArgumentException("Email inválido.");

                System.out.print("Teléfono: ");
                String phone = sc.nextLine().trim();
                if(phone.isEmpty() || !phone.matches("\\d+"))
                    throw new IllegalArgumentException("Teléfono inválido, solo números permitidos.");

                System.out.print("Tipo de cuenta (checking/savings): ");
                String type = sc.nextLine().trim().toLowerCase();
                if(!(type.equals("checking") || type.equals("savings")))
                    throw new IllegalArgumentException("Tipo de cuenta inválido. Opciones: checking, savings");

                System.out.print("Dirección (Calle X): ");
                String address = sc.nextLine().trim();
                if(address.isEmpty() || !address.matches("(?i)Calle \\d{1,2}"))
                    throw new IllegalArgumentException("Dirección inválida. Formato: Calle X (1 o 2 dígitos)");

                return new Account(num, name, email, phone, type, address);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Por favor, intente de nuevo.\n");
            }
        }
    }

    private static String formatAccount(Account a) {
        return String.format("[ID: %s | Nombre: %s | Email: %s | Tel: %s | Tipo: %s | Dirección: %s]",
                a.getAccountNumber(), a.getName(), a.getEmail(), a.getMobileNumber(), a.getAccountType(), a.getAddress());
    }

    private static void printAllAccounts() {
        System.out.println("\n=== LISTA DE CUENTAS ===");
        System.out.printf("%-8s %-20s %-25s %-12s %-10s %-15s%n", "ID", "Nombre", "Email", "Teléfono", "Tipo", "Dirección");
        System.out.println("-------------------------------------------------------------------------------------------");
        accountService.findAll().forEach(a ->
            System.out.printf("%-8s %-20s %-25s %-12s %-10s %-15s%n",
                    a.getAccountNumber(), a.getName(), a.getEmail(), a.getMobileNumber(), a.getAccountType(), a.getAddress())
        );
    }

    // BALANCE 
    private static void runBalanceMenu(Scanner sc) {
        boolean back = false;
        while (!back) {
            printCrudMenu("Balance");
            String opt = sc.nextLine().trim();
            switch (opt) {
                case "1":
                    Balance b = readBalanceFromConsole(sc);
                    balanceService.save(b);
                    System.out.println("✔ Balance guardado.");
                    break;
                case "2":
                    System.out.print("Ingrese fecha (YYYY-MM-DD): ");
                    String date = sc.nextLine().trim();
                    balanceService.findById(date).ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("No se encontró balance para esa fecha.")
                    );
                    break;
                case "3":
                    System.out.println("\n=== LISTA DE BALANCES ===");
                    balanceService.findAll().forEach(System.out::println);
                    break;
                case "4":
                    System.out.print("Ingrese fecha a actualizar (YYYY-MM-DD): ");
                    String upDate = sc.nextLine().trim();
                    Balance updated = readBalanceFromConsole(sc);
                    updated.setDate(LocalDate.parse(upDate));
                    balanceService.save(updated);
                    System.out.println("✔ Balance actualizado.");
                    break;
                case "5":
                    System.out.print("Ingrese fecha a eliminar: ");
                    String delDate = sc.nextLine().trim();
                    if (balanceService.deleteById(delDate))
                        System.out.println("✔ Eliminado.");
                    else
                        System.out.println("No existía balance para esa fecha.");
                    break;
                case "0": back = true; break;
                default: System.out.println("Opción inválida.");
            }
        }
    }

    private static Balance readBalanceFromConsole(Scanner sc) {
        LocalDate date = null;
        while (date == null) {
            try {
                System.out.print("Fecha (YYYY-MM-DD): ");
                String s = sc.nextLine().trim();
                if(s.isEmpty()) throw new IllegalArgumentException("La fecha no puede estar vacía.");
                date = LocalDate.parse(s);
            } catch (Exception e) {
                System.out.println("Formato de fecha inválido. Intente nuevamente.");
            }
        }

        String description = "";
        while (description.isEmpty()) {
            System.out.print("Descripción: ");
            description = sc.nextLine().trim();
            if(description.isEmpty()) System.out.println("La descripción no puede estar vacía.");
        }

        BigDecimal cashIn = readBigDecimal(sc, "Cash In (ej: 1500.50): ");
        BigDecimal cashOut = readBigDecimal(sc, "Cash Out (ej: 500.00): ");

        return new Balance(date, description, cashIn, cashOut);
    }

    // LOANS 
    private static void runLoansMenu(Scanner sc) {
        boolean back = false;
        while (!back) {
            printCrudMenu("Loans");
            String opt = sc.nextLine().trim();
            switch (opt) {
                case "1":
                    Loans loan = readLoanFromConsole(sc);
                    loansService.save(loan);
                    System.out.println("✔ Préstamo guardado.");
                    break;
                case "2":
                    System.out.print("Ingrese fecha (YYYY-MM-DD): ");
                    String date = sc.nextLine().trim();
                    loansService.findById(date).ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("No se encontró préstamo para esa fecha.")
                    );
                    break;
                case "3":
                    System.out.println("\n=== LISTA DE PRÉSTAMOS ===");
                    loansService.findAll().forEach(System.out::println);
                    break;
                case "4":
                    System.out.print("Ingrese fecha a actualizar (YYYY-MM-DD): ");
                    String upDate = sc.nextLine().trim();
                    Loans updated = readLoanFromConsole(sc);
                    updated.setDate(LocalDate.parse(upDate));
                    loansService.save(updated);
                    System.out.println("✔ Préstamo actualizado.");
                    break;
                case "5":
                    System.out.print("Ingrese fecha a eliminar: ");
                    String delDate = sc.nextLine().trim();
                    if (loansService.deleteById(delDate))
                        System.out.println("✔ Eliminado.");
                    else
                        System.out.println("No existía préstamo para esa fecha.");
                    break;
                case "0": back = true; break;
                default: System.out.println("Opción inválida.");
            }
        }
    }

    private static Loans readLoanFromConsole(Scanner sc) {
        LocalDate date = null;
        while (date == null) {
            try {
                System.out.print("Fecha (YYYY-MM-DD): ");
                String s = sc.nextLine().trim();
                if(s.isEmpty()) throw new IllegalArgumentException("La fecha no puede estar vacía.");
                date = LocalDate.parse(s);
            } catch (Exception e) {
                System.out.println("Formato de fecha inválido. Intente nuevamente.");
            }
        }

        String[] loanTypes = {"Personal", "Hipotecario", "Auto"};
        String type = readOptionFromList(sc, "Tipo de préstamo: ", loanTypes);

        BigDecimal totalLoan = readBigDecimal(sc, "Total Loan (ej: 20000.00): ");
        BigDecimal amountPaid = readBigDecimal(sc, "Amount Paid (ej: 1500.00): ");

        String description = "";
        while (description.isEmpty()) {
            System.out.print("Descripción: ");
            description = sc.nextLine().trim();
            if(description.isEmpty()) System.out.println("La descripción no puede estar vacía.");
        }

        return new Loans(date, type, totalLoan, amountPaid, description);
    }

    // CARDS 
    private static void runCardsMenu(Scanner sc) {
        boolean back = false;
        while (!back) {
            printCrudMenu("Cards");
            String opt = sc.nextLine().trim();
            switch (opt) {
                case "1":
                    Cards c = readCardFromConsole(sc);
                    cardsService.save(c);
                    System.out.println("✔ Tarjeta guardada.");
                    break;
                case "2":
                    System.out.print("Ingrese número de tarjeta: ");
                    String cardNum = sc.nextLine().trim();
                    cardsService.findById(cardNum).ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("No se encontró tarjeta con ese número.")
                    );
                    break;
                case "3":
                    System.out.println("\n=== LISTA DE TARJETAS ===");
                    cardsService.findAll().forEach(System.out::println);
                    break;
                case "4":
                    System.out.print("Ingrese número de tarjeta a actualizar: ");
                    String cardUp = sc.nextLine().trim();
                    Cards updatedCard = readCardFromConsole(sc);
                    updatedCard.setCardNumber(cardUp);
                    cardsService.save(updatedCard);
                    System.out.println("✔ Tarjeta actualizada.");
                    break;
                case "5":
                    System.out.print("Ingrese número de tarjeta a eliminar: ");
                    String delCard = sc.nextLine().trim();
                    if (cardsService.deleteById(delCard))
                        System.out.println("✔ Eliminado.");
                    else
                        System.out.println("No existía tarjeta con ese número.");
                    break;
                case "0": back = true; break;
                default: System.out.println("Opción inválida.");
            }
        }
    }

    private static Cards readCardFromConsole(Scanner sc) {
        while(true) {
            try {
                System.out.print("Número de tarjeta (8 dígitos): ");
                String num = sc.nextLine().trim();
                if(!num.matches("\\d{8}")) throw new IllegalArgumentException("Número de tarjeta inválido, debe tener 8 dígitos.");

                System.out.print("Titular: ");
                String holder = sc.nextLine().trim();
                if(holder.isEmpty() || !holder.matches("(?i)[a-záéíóúñ ]+")) throw new IllegalArgumentException("Nombre de titular inválido.");

                String[] cardTypes = {"checking", "savings"};
                String type = readOptionFromList(sc, "Tipo de tarjeta (checking/savings): ", cardTypes);

                BigDecimal limit = readBigDecimal(sc, "Límite de crédito (ej: 5000.00): ");

                String description = "";
                while(description.isEmpty()) {
                    System.out.print("Descripción: ");
                    description = sc.nextLine().trim();
                    if(description.isEmpty()) System.out.println("La descripción no puede estar vacía.");
                }

                return new Cards(num, holder, type, limit, description);
            } catch(Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // UTIL
    private static BigDecimal readBigDecimal(Scanner sc, String prompt) {
        BigDecimal val = null;
        while(val == null) {
            try {
                System.out.print(prompt);
                String line = sc.nextLine().trim();
                if(line.isEmpty()) throw new IllegalArgumentException("No puede estar vacío");
                val = new BigDecimal(line).setScale(2, BigDecimal.ROUND_HALF_UP);
            } catch(Exception e) {
                System.out.println("Número inválido. Intente nuevamente.");
            }
        }
        return val;
    }

    private static String readOptionFromList(Scanner sc, String prompt, String[] validOptions) {
        while(true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            for(String opt : validOptions) {
                if(input.equalsIgnoreCase(opt)) return opt;
            }
            System.out.println("Opción inválida. Opciones: " + String.join(", ", validOptions));
        }
    }
}
