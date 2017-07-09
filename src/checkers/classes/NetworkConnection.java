package checkers.classes;


import java.net.Socket;

public class NetworkConnection
{


    public NetworkCommProtocolThread networkCommProtocolThread = new NetworkCommProtocolThread();
    public int connectionID;

    public NetworkConnection()
    {

        // networkCommProtocolThread.setSocket(socket);
    }

    public NetworkConnection(int size)
    {
        this.connectionID = size;
    }


    public void startConnection(Socket socket)
    {

        networkCommProtocolThread.setDaemon(true);
        networkCommProtocolThread.socket = socket;
        networkCommProtocolThread.start();
    }

    public void closeConnection()
    {


    }


    public NetworkCommProtocolThread getNetworkCommProtocolThread()
    {
        return networkCommProtocolThread;
    }

    public void setNetworkCommProtocolThread(NetworkCommProtocolThread networkCommProtocolThread)
    {
        this.networkCommProtocolThread = networkCommProtocolThread;
    }

    public int getConnectionID()
    {
        return connectionID;
    }

    public void setConnectionID(int connectionID)
    {
        this.connectionID = connectionID;
    }
}
