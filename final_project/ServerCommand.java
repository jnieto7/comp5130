/**
 * ServerCommand.java
 *
 */
package jn.servlet;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * The ServerCommand class provides static methods to execute SSH commands on
 * the remote host using the JSch library.
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

    private static String executeLocal(String command, int timeoutInMillis) throws InterruptedException, IOException {
        StringBuilder sb = new StringBuilder();

        Process process = Runtime.getRuntime().exec(command);
        if (timeoutInMillis > 0) {
            process.waitFor(timeoutInMillis, TimeUnit.MILLISECONDS);
        } else {
            process.waitFor();
        }

        BufferedReader reader
                = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;

        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    /**
     * Executes a command locally on this machine with a completion timeout.
     *
     * @param command the command to run on the command line locally on this
     * machine.
     * @param timeoutInMillis the time to wait for the command completion in
     * milliseconds. A timeout of 0 indicates an indefinite wait.
     * @return A String containing the command output
     * @throws InterruptedException if the process is interrupted during command
     * execution
     * @throws IOException
     */
    public static String executeLocalCommand(String command, int timeoutInMillis) throws InterruptedException, IOException {
        return executeLocal(command, timeoutInMillis);
    }

    /**
     * Executes a command locally on this machine.
     *
     * @param command the command to run on the command line locally on this
     * machine.
     * @return A String containing the command output
     * @throws InterruptedException if the process is interrupted during command
     * execution
     * @throws IOException
     */
    public static String executeLocalCommand(String command) throws InterruptedException, IOException {
        return executeLocal(command, 0);
    }
}
