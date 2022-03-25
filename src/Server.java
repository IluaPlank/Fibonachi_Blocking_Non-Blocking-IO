import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        log("Server start");

        try {
            Socket socket = serverSocket.accept();
            handle(socket);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
            serverSocket.close();
        }
    }

    private static void handle(Socket socket) throws IOException {
        log("client connected " + socket.getRemoteSocketAddress());

        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String number;
            while ((number = in.readLine()) != null) {
                if (number.equals("end")) {
                    break;
                }
                out.println("число фибоначи: " + estimate(Integer.parseInt(number)));
                log("число отправлено");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static final MathContext mathContext = new MathContext(20, RoundingMode.HALF_UP);
    private static final double sqrt5 = Math.sqrt(5);
    public static final BigDecimal Sqrt5 = new BigDecimal(sqrt5, mathContext);
    public static final BigDecimal Phi = new BigDecimal((1 + sqrt5) / 2, mathContext);

    public static BigDecimal estimate(int number) {
        return Phi.pow(number, mathContext).divide(Sqrt5, mathContext);
    }

    private static void log(String message) {
        System.out.println("[" + Thread.currentThread().getName() + "] " + message);
    }
}
