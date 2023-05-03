package org.itpl;

import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Data
public class CommandPipeLine {

    private ProcessBuilder processBuilder;

    public CommandPipeLine() {
        this.processBuilder = new ProcessBuilder();
    }

    public void executeCommands(Map<String, CommandConfig> configuration) {
        for (Map.Entry<String, CommandConfig> entry : configuration.entrySet()) {
            String commandName = entry.getKey();
            CommandConfig commandConfig = entry.getValue();
            List<String> command = commandConfig.getCommand();
            processBuilder.command(command);

            if (!commandConfig.isCurrDir()) {
                String directoryPath = commandConfig.getDirectoryPath();
                if (directoryPath.equals(" ") || directoryPath.length() == 0) {
                    System.out.println("You must add right or proper directoryPath");
                }
                processBuilder.directory(new File(directoryPath));
            }

            try {
                Process process = processBuilder.start();
                System.out.println("\n*********** Execution started for : " + commandName + " ***********\n");

                // Get the output stream of the first process
                InputStream inputStream = process.getInputStream();

                // output handler thread
                OutputHandler outputHandler = new OutputHandler(commandName,inputStream);
                Thread outputHandleThread = new Thread(outputHandler);
                outputHandleThread.setName("OutputHandler Thread");
                outputHandleThread.start();

                // wait for the process to finish
                int exitCode = process.waitFor();
                if (exitCode == 1) {
                    break;
                }

                // wait for the thread to execute
                outputHandleThread.join();

                // After process is finished stop the outputHandler thread;
                // outputHandler.stop();
                System.out.println("\n*********** Successfully executed : "+ commandName + " ***********\n");

            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
