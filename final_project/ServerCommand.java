/**
 * ServerCommand.java
 *
 */
package jn.servlet;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 *
 * @author Jeremie Nieto
 */
final class ServerCommand {

    private static final int CONNECT_TIMEOUT = 10000;

    private ServerCommand() {

    }

    /**
     * Executes the provided ssh command on the server provided.
     *
     * @param server, the server information for the server you are waiting for
     * @param password, the password for that server
     * @param command, the command you want to execute
     * @return the result of the command if one exists.
     * @throws Exception
     */
    static String executeSSHCommand(String server, String user, String password, String command) throws Exception {
        String msg = null;
        Session session = null;
        ChannelExec channel = null;
        try {
            StringBuilder result = new StringBuilder();
            session = new JSch().getSession(user, server, 22);
            session.setPassword(password);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect(CONNECT_TIMEOUT);

            channel = (ChannelExec) session.openChannel("exec");
            BufferedReader in = new BufferedReader(new InputStreamReader(channel.getInputStream()));

            channel.setCommand(command);
            channel.connect();

            while ((msg = in.readLine()) != null) {
                result.append(msg);
            }

            return result.toString();

        } finally {
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }

    }
}
