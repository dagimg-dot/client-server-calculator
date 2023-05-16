import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class App {
    private static final int PORT = 25;

    public static void main(String[] args) throws Exception {
        try {
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("Server Listening on port:" + PORT);
            Socket connection = createConnection(server);
            System.out.println("Connected");

            handleClient(connection);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket connection) throws Exception {
        try (
            BufferedReader clientReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            PrintWriter clientWriter = new PrintWriter(connection.getOutputStream(), true);
        ) {
            String response;
            String message;

            do {
                message = clientReader.readLine();
                if (message.equalsIgnoreCase("stop")) {
                    break;
                }
                boolean isValid = checkMessage(message);
                if(isValid) {
                    double result =  handleMessage(message);
                    response = String.valueOf(result);
                } else {
                    response = "Invalid Expression";
                }
                System.out.println("Expression to be calculated (from Client): " + message);
                System.out.println("Server Response:" + response);
                clientWriter.println(response);
            } while (true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Socket createConnection(ServerSocket server) throws Exception {
        return server.accept();
    }

    private static boolean checkMessage(String message) {
        String regex = "^\\d+\\s*[+\\-*/%^]\\s*\\d+$";
        String regexPrime = "^\\d+\\s+isPrime$";
        if(message.matches(regex) || message.matches(regexPrime)) {
            return true;
        } else {
            return false;
        }
    }

    private static double handleMessage(String message) {
        // for isPrime
        if(message.contains("isPrime")) {
            String[] tokens = message.split("\\s+");
            int a = Integer.parseInt(tokens[0]);
            boolean result = Calculator.isPrime(a);
            if(result) {
                return 1;
            } else {
                return 0;
            }
        }

        // for other operations
        String[] tokens = message.split("\\s*");
        int a = Integer.parseInt(tokens[0]);
        int b = Integer.parseInt(tokens[2]);
        String operator = tokens[1];
        int result = 0;
        double resultDouble = 0;
        switch(operator) {
            case "+":
                result = Calculator.add(a, b);
                break;
            case "-":
                result = Calculator.subtract(a, b);
                break;
            case "*":
                result = Calculator.multiply(a, b);
                break;
            case "/":
                resultDouble = Calculator.divide(a, b);
                return resultDouble;
            case "%":
                result = Calculator.modulus(a, b);
                break;
            case "^":
                result = Calculator.power(a, b);
                break;
            default:
                break;
        }
        
        return result;
    }
}

