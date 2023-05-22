import exceptions.ConnectionException;
import exceptions.InvalidDataException;
import connection.Server;
import exceptions.ServerException;

public class Main {
    public static void main(String[] args) {
        int port = 0;
        String strPort = "";
        String path = "route database";
        try {
            if(args.length >= 2) {
                path = args[1];
                strPort = args[0];
            }
            if (args.length == 1) strPort = args[0];
            if(args.length == 0) throw new InvalidDataException("no address passed by arguments");
            try {
                port = Integer.parseInt(strPort);
            } catch (NumberFormatException e){
                throw new ConnectionException("invalid port");
            }
            Server server = new Server(port, path);
            server.start();
            server.consoleMode();

        }
        catch (InvalidDataException | ConnectionException | ServerException e){
            System.out.println(e.getMessage());
        }
    }
}
