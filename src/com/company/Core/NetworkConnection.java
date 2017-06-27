package com.company.Core;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkConnection {


    public Socket socket;
    public NetworkCommProtocolThread networkCommProtocolThread = new NetworkCommProtocolThread();


    public NetworkConnection(Socket socket) {
        networkCommProtocolThread.setSocket(socket);
    }



public void startConnection()
{


    networkCommProtocolThread.start();
}


/*

    public NetworkConnection(Consumer<Serializable> consumer) {
        this.consumer = consumer;
        commProtocolThread.setDaemon(true);
    }


    public void startConnection () throws Exception
    {
        commProtocolThread.start();
    }

    public void sendData(Serializable data) throws Exception
    {
        commProtocolThread.out.writeObject(data);
    }
    public Consumer<Serializable> getData() throws Exception
    {
        return consumer;
    }


*/



}
