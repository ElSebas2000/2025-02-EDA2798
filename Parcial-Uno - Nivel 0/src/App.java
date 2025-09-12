import java.util.Scanner;

import domain.Ahorro;
import domain.Corriente;
import domain.Cuenta;

public class App {
    public static void main(String[] args) {
        ServiceCuenta serviceCuenta = new ServiceCuenta();
        Scanner sc = new Scanner(System.in);

        int opcion = 0;
        do {
            System.out.println("\n=== MENÚ CUENTAS ===");
            System.out.println("1. Listar cuentas Ahorro");
            System.out.println("2. Listar cuentas Corriente");
            System.out.println("3. Crear cuenta Ahorro");
            System.out.println("4. Crear cuenta Corriente");
            System.out.println("5. Buscar cuenta por número");
            System.out.println("6. Listar todas las cuentas");
            System.out.println("7. Salir");
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.println("\n--- Cuentas Ahorro ---");
                    for (Ahorro a : serviceCuenta.obtenerCuentasAhorro()) {
                        System.out.println(a);
                    }
                    break;

                case 2:
                    System.out.println("\n--- Cuentas Corriente ---");
                    for (Corriente c : serviceCuenta.obtenerCuentasCorriente()) {
                        System.out.println(c);
                    }
                    break;

                case 3:
                    System.out.print("Número de cuenta: ");
                    String numAhorro = sc.nextLine();
                    System.out.print("DNI cliente: ");
                    long dniAhorro = sc.nextLong();
                    System.out.print("Saldo inicial: ");
                    double saldoAhorro = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Fecha de creación (YYYY-MM-DD): ");
                    String fecha = sc.nextLine();
                    serviceCuenta.crearCuenta(new Ahorro(numAhorro, dniAhorro, saldoAhorro, fecha));
                    System.out.println("✅ Cuenta de Ahorro creada!");
                    break;

                case 4:
                    System.out.print("Número de cuenta: ");
                    String numCorr = sc.nextLine();
                    System.out.print("DNI cliente: ");
                    long dniCorr = sc.nextLong();
                    System.out.print("Saldo inicial: ");
                    double saldoCorr = sc.nextDouble();
                    System.out.print("Impuesto: ");
                    double imp = sc.nextDouble();
                    serviceCuenta.crearCuenta(new Corriente(numCorr, dniCorr, saldoCorr, imp));
                    System.out.println("✅ Cuenta Corriente creada!");
                    break;

                case 5:
                    System.out.print("Ingrese el número de cuenta: ");
                    String buscar = sc.nextLine();
                    Cuenta encontrada = serviceCuenta.obtenernumeroCuenta(buscar);
                    if (encontrada != null) {
                        System.out.println("🔎 Cuenta encontrada: " + encontrada);
                    } else {
                        System.out.println("⚠️ No se encontró la cuenta.");
                    }
                    break;

                case 6:
                    System.out.println("\n--- Todas las cuentas ---");
                    for (Cuenta c : serviceCuenta.obtenerCuentas()) {
                        System.out.println(c);
                    }
                    break;

                case 7:
                    System.out.println("👋 Saliendo del sistema...");
                    break;

                default:
                    System.out.println("❌ Opción inválida.");
            }
        } while (opcion != 7);

        sc.close();
    }
}
