import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class App {
    private static final int PORT = 25;

    public static void main(String[] args) {
        try {
            Socket socket = createSocket();
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter socketWriter = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String request, response;
            System.out.println("If you want to close the connection, type 'Stop'");
            do {
                System.out.print("Enter your expression:  ");
                request = getUserInput(consoleReader);
                socketWriter.println(request);
                if (request.equalsIgnoreCase("stop")) {
                    break;
                }
                response = receiveServerResponse(socketReader);
                System.out.println("Calculated Result (from Server): " + response);
            } while (true);

            closeSocket(socket);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Socket createSocket() throws Exception {
        return new Socket("localhost", PORT);
    }

    private static String getUserInput(BufferedReader consoleReader) throws Exception {
        return consoleReader.readLine();
    }

    private static String receiveServerResponse(BufferedReader socketReader) throws Exception {
        return socketReader.readLine();
    }

    private static void closeSocket(Socket socket) throws Exception {
        socket.close();
    }
}



