package org.itpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class OutputHandler implements Runnable {
    private final InputStream inputStream;

    private String commandName;
    private volatile boolean stop;

    public OutputHandler(String commandName,InputStream inputStream) {
        this.commandName = commandName;
        this.inputStream = inputStream;
        this.stop = false;
    }

    public void stop(){
        this.stop = true;
    }

    public void run() {
        System.out.println("\n-------- " + commandName + " OUTPUT " +" --------\n");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while (!stop && (line = br.readLine()) != null) {
                // send the output line to another class or do any other processing
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
