package connection;

import exceptions.ConnectionException;
import exceptions.InvalidDataException;
import io.ClientUserInputHandler;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class Client extends Thread {
    private SocketAddress address;
    private DatagramSocket socket;
    private final int BUFFER_SIZE = 2^16;
    private boolean isRunning;
    private ClientUserInputHandler handler;


    public Client(String addr, int port) throws ConnectionException {
        connect(addr, port);
        isRunning = true;
        handler = new ClientUserInputHandler(this);
        setName("client thread");
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
        try{
            socket = new DatagramSocket();
            int MAX_TIME_OUT = 100;
            socket.setSoTimeout(MAX_TIME_OUT);
        } catch(IOException e){
            throw new ConnectionException("cannot open socket");
        }
    }

    public void send(RequestMsg request) throws ConnectionException {
        try{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(BUFFER_SIZE);
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
        ByteBuffer bytes = ByteBuffer.allocate(BUFFER_SIZE);
        DatagramPacket receivePacket = new DatagramPacket(bytes.array(), bytes.array().length);
        try{
            socket.receive(receivePacket);
        }
        catch (SocketTimeoutException e){
            int MAX_ATTEMPTS = 3;
            int attempts = MAX_ATTEMPTS;
            boolean success = false;
            for( ; attempts>0; attempts--) {
                System.err.print("server response timeout exceeded, trying to reconnect. " + attempts+ " attempts left");
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
