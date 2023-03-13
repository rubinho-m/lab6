package server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        NetworkConnection networkConnection = new NetworkConnection(2828);
        while (true){
            networkConnection.receive();
            networkConnection.send();
        }
    }
}
