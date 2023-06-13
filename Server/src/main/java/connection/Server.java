package connection;

import collection.RouteCollectionHandler;
import commands.ExceptionWrapper;
import exceptions.CommandException;
import exceptions.ConnectionException;

import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.nio.*;
import java.time.Instant;
import java.util.UUID;
import exceptions.InvalidDataException;
import exceptions.ServerException;
import file.FileHandler;
import io.ConsoleInputHandler;
import io.ServerUserInputHandler;

public class Server extends Thread implements Closeable{
    private final int BUFFER_SIZE = 2^16;
    private RouteCollectionHandler collectionHandler;
    private FileHandler fileHandler;
    private ServerUserInputHandler inputHandler;
    private int port;
    private InetSocketAddress clientAddress;
    private DatagramChannel channel;

    private volatile boolean isRunning;

    private void init(int p, String path) throws ConnectionException, ServerException {
        isRunning=true;
        port = p;
        collectionHandler = new RouteCollectionHandler();
        fileHandler = new FileHandler(path);
        inputHandler = new ServerUserInputHandler(this, new ConsoleInputHandler());
        collectionHandler.deserializeCollection(fileHandler.read());
        host(port);
        setName("server thread");
    }

    private void host(int p) throws ConnectionException, ServerException {
        try{
            if(channel!=null && channel.isOpen()) {
                channel.close();
            }
            channel = DatagramChannel.open();
            channel.bind(new InetSocketAddress(port));
        }
        catch(AlreadyBoundException e){
            throw new ServerException("port is already bound");
        }
        catch(IllegalArgumentException e){
            throw new ConnectionException("invalid port");
        }
        catch(IOException e){
            throw new ServerException("Something went wrong during server initialization");
        }
    }

    public Server(int p, String path) throws ConnectionException, ServerException {
        init(p,path);
    }

    public RequestMsg receive() throws ConnectionException, InvalidDataException{
        while(true) {
            try {
                ByteBuffer buf = ByteBuffer.allocate(20000);
                clientAddress = (InetSocketAddress) channel.receive(buf);
                ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(buf.array()));
                RequestMsg msg = (RequestMsg) objectInputStream.readObject();
                if(msg != null){
                    return msg;
                }
            } catch (ClassNotFoundException | ClassCastException | IOException e) {
                throw new InvalidDataException("something went wrong during receiving request");
            }
        }
    }

    public void send(AnswerMsg response)throws ConnectionException{
        if (clientAddress == null) throw new ConnectionException("no such client address found");
        try{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(20000);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(response);
            channel.send(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()), clientAddress);
        } catch(IOException e){
            throw new ConnectionException("something went wrong during sending response");
        }
    }


    public void run() {
        while (isRunning) {
            AnswerMsg answerMsg = new AnswerMsg();
            try {
                try {
                    RequestMsg commandMsg = receive();
                    if (commandMsg.getRoute() != null) {
                        commandMsg.getRoute().setCreationDate(java.util.Date.from(Instant.now()));
                        commandMsg.getRoute().setId(UUID.randomUUID());
                    }
                    answerMsg = inputHandler.runCommand(commandMsg);
                } catch (CommandException e) {
                    ExceptionWrapper.outException(e.getMessage());
                }
                send(answerMsg);
            } catch (ConnectionException | InvalidDataException e) {
                ExceptionWrapper.outException(e.getMessage());
            }
        }
    }

    public void consoleMode(){
        inputHandler.consoleMode();
    }

    /**
     * close server and connection
     */
    public void close(){
        try{
            isRunning=false;
            channel.close();
        } catch (IOException e){
            ExceptionWrapper.outException("cannot close channel");
        }
    }

    public RouteCollectionHandler getCollectionHandler() {
        return collectionHandler;
    }

    public FileHandler getFileHandler() {
        return fileHandler;
    }
}
