package server.network;

import common.networkStructures.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class Writer {
    private OutputStream outputStream;

    public Writer(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void write(Response response) throws IOException {
        System.out.println(response.getOutput());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(response);
        byte[] newArray = byteArrayOutputStream.toByteArray();
        outputStream.write(newArray);
        outputStream.flush();
    }
}
