import service.Admin;
import service.Chir;
import service.Service;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Service service = Service.getInstance();
        Scanner scanner = new Scanner(System.in);

        // Login
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("id: ");
        Integer id = Integer.valueOf(scanner.nextLine());

        Object ob = null;

        try {
            ob = service.login(username, password, id);
            if (ob != null) {

                service.RutinaPrincipala(ob,scanner);

            } else {
                System.out.println("Invalid username or password.");
            }
        } catch (java.lang.IndexOutOfBoundsException i) {
            System.out.println("Index invalid");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        service.RutinaFinal();
    }

}
