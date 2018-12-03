/**
 * Server.java
 *
 */
package jn.servlet;

/**
 *
 * @author jnieto
 */
class Server {

    private final String host;
    private final String user;
    private final String password;

    Server(String name, String user, String password) {
        this.host = name;
        this.user = user;
        this.password = password;
    }

    Server(String name, String password) {
        this.host = name;
        this.user = "root";
        this.password = password;
    }

    String executeSSHCommand(String command) throws Exception {
        return ServerCommand.executeSSHCommand(this.host, this.user, this.password, command);
    }

    @Override
    public String toString() {
        return this.host;
    }
}
