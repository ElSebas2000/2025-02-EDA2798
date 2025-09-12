package domain;

public class Cuenta {
    private String numeroCuenta;
    private long dniCliente;
    private double saldoActual;

    // 🔹 Constructor vacío
    public Cuenta() {
    }

    // 🔹 Constructor con parámetros
    public Cuenta(String numeroCuenta, long dniCliente, double saldoActual) {
        this.numeroCuenta = numeroCuenta;
        this.dniCliente = dniCliente;
        this.saldoActual = saldoActual;
    }

    // 🔹 Getters y setters
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public long getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(long dniCliente) {
        this.dniCliente = dniCliente;
    }

    public double getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(double saldoActual) {
        this.saldoActual = saldoActual;
    }

    // 🔹 toString
    @Override
    public String toString() {
        return "Cuenta{" +
                "numeroCuenta='" + numeroCuenta + '\'' +
                ", dniCliente=" + dniCliente +
                ", saldoActual=" + saldoActual +
                '}';
    }
}
