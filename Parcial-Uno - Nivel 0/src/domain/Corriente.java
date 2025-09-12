package domain;

public class Corriente extends Cuenta {
    private double impuesto;

    // Constructor vacío
    public Corriente() {
        super();
    }

    // Constructor con parámetros
    public Corriente(String numeroCuenta, long dniCliente, double saldoActual, double impuesto) {
        super(numeroCuenta, dniCliente, saldoActual);
        this.impuesto = impuesto;
    }

    // Getter y Setter
    public double getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(double impuesto) {
        this.impuesto = impuesto;
    }

    // toString
    @Override
    public String toString() {
        return "Corriente{" +
                "numeroCuenta='" + getNumeroCuenta() + '\'' +
                ", dniCliente=" + getDniCliente() +
                ", saldoActual=" + getSaldoActual() +
                ", impuesto=" + impuesto +
                '}';
    }
}
