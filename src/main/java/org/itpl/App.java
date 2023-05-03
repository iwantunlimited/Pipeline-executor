package org.itpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App 
{
    public static void main( String[] args )
    {
        // for single execution commands like : "mvn clean package"
        List<String> command = new ArrayList<String>();
        command.add("lt");
        command.add("-al");

        CommandConfig commandConfig1 = new CommandConfig();
        commandConfig1.setCommand(command);
        commandConfig1.setDirectoryPath("/home/malav/Downloads");
        commandConfig1.setCurrDir(false);

        // command 2
        List<String> command2 = new ArrayList<String>();
        command2.add("ls");
        command2.add("-al");

        CommandConfig commandConfig2 = new CommandConfig();
        commandConfig2.setCommand(command2);
        commandConfig2.setDirectoryPath("/home/malav/Downloads");
        commandConfig2.setCurrDir(false);

        Map<String,CommandConfig> allCommandsConfig = new HashMap<>();
        allCommandsConfig.put("Command1",commandConfig1);
        allCommandsConfig.put("Command2",commandConfig2);

        CommandPipeLine commandPipeLine = new CommandPipeLine();
        commandPipeLine.executeCommands(allCommandsConfig);

    }
}
