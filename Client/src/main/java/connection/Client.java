package connection;

import exceptions.ConnectionException;
import exceptions.InvalidDataException;
import io.ClientUserInputHandler;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;


public class Client extends Thread implements Closeable {
    private SocketAddress address;
    private DatagramSocket socket;
    private boolean isRunning;
    private ClientUserInputHandler handler;


    private void init(String addr, int p) throws ConnectionException {
        connect(addr,p);
        isRunning = true;
        handler = new ClientUserInputHandler(this);
        setName("client thread");
    }
    public Client(String addr, int port) throws ConnectionException {
        init(addr, port);
    }

    public void connect(String addr, int port) throws ConnectionException {
        try{
            address = new InetSocketAddress(InetAddress.getByName(addr), port);
        }
        catch(UnknownHostException e){
            throw new ConnectionException("Invalid address");
        }
        catch(IllegalArgumentException e){
            throw new ConnectionException("Invalid port");
        }
        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(500);
        } catch (IOException e) {
            throw new ConnectionException("cannot open socket");
        }
        System.out.println("connected!");
    }

    public void send(Request request) throws ConnectionException {
        try{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objOutput = new ObjectOutputStream(byteArrayOutputStream);
            objOutput.writeObject(request);
            DatagramPacket requestPacket = new DatagramPacket(byteArrayOutputStream.toByteArray(), byteArrayOutputStream.size(), address);
            socket.send(requestPacket);
            byteArrayOutputStream.close();
        }
        catch (IOException e){
            throw new ConnectionException("cannot send request");
        }
    }

    public AnswerMsg receive() throws ConnectionException, InvalidDataException {
        ByteBuffer bytes = ByteBuffer.allocate(20000);
        DatagramPacket receivePacket = new DatagramPacket(bytes.array(), bytes.array().length);
        try{
            socket.receive(receivePacket);
        }
        catch (SocketTimeoutException e){
            int MAX_ATTEMPTS = 3;
            int attempts = MAX_ATTEMPTS;
            boolean success = false;
            for( ; attempts>0; attempts--) {
                System.err.print("server response timeout exceeded, trying to reconnect. " + attempts+ " attempts left\n");
                try{
                    socket.receive(receivePacket);
                    success = true;
                    break;
                }
                catch (IOException ignored){

                }
            }

            throw new ConnectionException("exceeded connection tries limit");
        }

        catch(IOException e){
            throw new ConnectionException("something went wrong while receiving response");
        }

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes.array()));
            return (AnswerMsg) objectInputStream.readObject();
        } catch (ClassNotFoundException|ClassCastException|IOException e) {
            throw new InvalidDataException("received corrupted data");
        }
    }

    @Override
    public void run() {
        handler.consoleMode();
        close();
    }

    public void close(){
            isRunning = false;
            handler.exit();
            socket.close();
    }
}
