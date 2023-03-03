import java.io.*;  
import java.net.Socket;  
import java.util.Scanner;  
  
public class Client {  
    public static void main(String[] args) {  
        Socket socket = null;  
        InputStreamReader inputStreamReader = null;  
        OutputStreamWriter outputStreamWriter = null;  
        BufferedReader bufferedReader = null;  
        BufferedWriter bufferedWriter = null;  
        
        try {  
            socket = new Socket("localhost", 5000);  
            inputStreamReader = new InputStreamReader(socket.getInputStream());  
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());  
            bufferedReader = new BufferedReader(inputStreamReader);  
            bufferedWriter = new BufferedWriter(outputStreamWriter);  

            System.out.println("Connection Established");
            ATM atm = new ATM();
            atm.displayATMMenu();
            System.out.println("Enter a number: ");
            Scanner scanner = new Scanner(System.in);  
            while (true){  
                String msgToSend = scanner.nextLine();  
                bufferedWriter.write(msgToSend);  
                bufferedWriter.newLine();  
                bufferedWriter.flush();  

                System.out.println("Server: " + bufferedReader.readLine());  //printing the server message

                if (msgToSend.equalsIgnoreCase("BYE"))  
                    break;  
            }  
            System.out.println("Connection Terminated");
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
             try {  
                  if (socket != null)  
                  socket.close();  
                  if (inputStreamReader != null)  
                    inputStreamReader.close();  
                  if (outputStreamWriter != null)  
                  outputStreamWriter.close();  
                  if (bufferedReader != null)  
                  bufferedReader.close();  
                  if (bufferedWriter != null)  
                  bufferedWriter.close();  
             } catch (IOException e) {  
            e.printStackTrace();  
          }  
       }  
    }  
}
