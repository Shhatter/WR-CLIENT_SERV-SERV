package com.company.Core;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkConnection {


    public NetworkCommProtocolThread networkCommProtocolThread = new NetworkCommProtocolThread();


    public NetworkConnection() {

       // networkCommProtocolThread.setSocket(socket);
    }



public void startConnection(Socket socket )
{

    networkCommProtocolThread.setDaemon(true);
    networkCommProtocolThread.socket = socket;
    networkCommProtocolThread.start();
}

public void closeConnection()
{



}

}
