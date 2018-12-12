/**
 * Server.java
 *
 */
package jn.servlet;

import java.io.IOException;

/**
 * The Server class holds the user information for making a connection to a
 * remote host.
 *
 * @author jnieto
 */
class Server extends Thread {

    private final String host;
    private final String user;
    private final String password;
    private CommandType type = CommandType.SSH;
    private String command;
    private String results = "";

    enum CommandType {
        LOCAL,
        SSH
    }

    Server(String name, String user, String password) {
        super.setDaemon(true);
        this.host = name;
        this.user = user;
        this.password = password;
    }

    Server(String name, String password) {
        this(name, "root", password);
    }

    void executeLocalCommand(String command) throws IOException, InterruptedException {
        this.results = ServerCommand.executeLocalCommand(command, 20000);
    }

    void executeSSHCommand(String command) throws Exception {
        this.results = ServerCommand.executeSSHCommand(this.host, this.user, this.password, command);
    }

    String getHost() {
        return this.host;
    }

    String getPassword() {
        return this.password;
    }
    
    String getResults(){
        return this.results;
    }

    String getUser() {
        return this.user;
    }

    @Override
    public void run() {
        try {
            if (this.type == CommandType.LOCAL) {
                executeLocalCommand(this.command);
            } else {
                executeSSHCommand(this.command);
            }
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }

    }

    void setCommand(String command) {
        this.command = command;
    }

    void setCommandType(CommandType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.host;
    }
}
