import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8080);

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(
                     new OutputStreamWriter(socket.getOutputStream()), true);
             Scanner scanner = new Scanner(System.in))
        {
            String message;
            while (true) {
                System.out.println("Введите целое число,для ряда фибоначи или end для окончания");
                message = scanner.nextLine();
                if (message.equals("end")) {
                    break;
                }
                else {
                    try {
                        int outMessage = Integer.parseInt(message);
                        out.println(outMessage);
                        System.out.println(in.readLine());
                    }
                    catch (NumberFormatException e){
                        System.out.println("Неверный ввод");
                    }
                }
            }
        }
    }
}
