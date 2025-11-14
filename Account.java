package scr.model;

public class Account {
    private String accountNumber; // ACC + número
    private String name;          // letras, acentos y espacios
    private String email;         // formato email
    private String mobileNumber;  // solo números
    private String accountType;   // "checking" o "savings"
    private String address;       // formato "Calle X"

    public Account() {}

    public Account(String accountNumber, String name, String email, String mobileNumber, String accountType,
            String address) {
        setAccountNumber(accountNumber);
        setName(name);
        setEmail(email);
        setMobileNumber(mobileNumber);
        setAccountType(accountType);
        setAddress(address);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        if (accountNumber != null && accountNumber.toUpperCase().matches("ACC\\d+")) {
            this.accountNumber = accountNumber.toUpperCase();
        } else {
            throw new IllegalArgumentException("ID inválido, debe comenzar con ACC seguido de números.");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && name.matches("(?i)[a-záéíóúñ ]+")) { // (?i) ignora mayúsculas
            this.name = name;
        } else {
            throw new IllegalArgumentException("Nombre inválido, solo letras y espacios permitidos.");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null && email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            this.email = email.toLowerCase(); // guardamos en minúscula
        } else {
            throw new IllegalArgumentException("Email inválido.");
        }
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        if (mobileNumber != null && mobileNumber.matches("\\d+")) {
            this.mobileNumber = mobileNumber;
        } else {
            throw new IllegalArgumentException("Teléfono inválido, solo números permitidos.");
        }
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        if (accountType != null && (accountType.equalsIgnoreCase("checking") || accountType.equalsIgnoreCase("savings"))) {
            this.accountType = accountType.toLowerCase(); // guardamos en minúscula
        } else {
            throw new IllegalArgumentException("Tipo de cuenta inválido, solo 'checking' o 'savings'.");
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address != null && address.matches("(?i)Calle \\d+")) { // insensible a mayúsculas
            this.address = address;
        } else {
            throw new IllegalArgumentException("Dirección inválida, debe ser tipo 'Calle X' con número.");
        }
    }

    @Override
    public String toString() {
        return String.format("Account [accountNumber=%s, name=%s, email=%s, mobileNumber=%s, accountType=%s, address=%s]",
                accountNumber, name, email, mobileNumber, accountType, address);
    }
}
