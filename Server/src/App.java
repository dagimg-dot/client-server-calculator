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
    
                String[] inputs = message.split(" "); // Split the message into three parts
                if (inputs.length != 3) {
                    response = "Invalid Expression";
                } else {
                    boolean isValid = isValidArithmeticExpression(message);
                    if (isValid) {
                        response = handleMessage(message);
                    } else {
                        response = "Invalid Expression";
                    }
                }
    
                System.out.println("Expression to be calculated (from Client): " + message);
                System.out.println("Server Response: " + response);
                clientWriter.println(response);
            } while (true);
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    private static Socket createConnection(ServerSocket server) throws Exception {
        return server.accept();
    }

    public static boolean isValidArithmeticExpression(String expression) {
        String[] inputs = expression.split(" ");
    
        if (inputs.length != 3) {
            return false;
        }
    
        String operand1 = inputs[0];
        String operator = inputs[1];
        String operand2 = inputs[2];
    
        if (!operand1.matches("-?\\d+")) {
            return false;
        }
    
        if (!operator.matches("[+\\-*/%^]")) {
            return false;
        }
    
        if (!operand2.matches("-?\\d+")) {
            return false;
        }
    
        return true;
    }
    

    private static String handleMessage(String message) {
        String[] tokens = message.split(" ");
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
                if(resultDouble == Double.POSITIVE_INFINITY || resultDouble == Double.NEGATIVE_INFINITY)
                    return "Cannot divide by 0";
                return String.valueOf(resultDouble);
            case "%":
                result = Calculator.modulus(a, b);
                break;
            case "^":
                result = Calculator.power(a, b);
                break;
            default:
                break;
        }
        
        return String.valueOf(result);
    }
}

