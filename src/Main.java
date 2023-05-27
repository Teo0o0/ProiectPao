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


        Object ob = service.login(username, password, id);

        if (ob != null) {
            // Successful login

        } else {
            System.out.println("Invalid username or password.");
        }
    }
}
