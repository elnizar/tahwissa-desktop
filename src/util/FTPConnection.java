/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 *
 * @author Meedoch
 */
public class FTPConnection {

    private static void showServerReply(FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                System.out.println("SERVER: " + aReply);
            }
        }
    }
    private static FTPClient client;

    private static void createFTPConnection() {
        String server = "localhost";
        int port = 21;
        String user = "mehdi";
        String pass = "mehdi";
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            showServerReply(ftpClient);
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("Operation failed. Server reply code: " + replyCode);
                return;
            }
            boolean success = ftpClient.login(user, pass);
            showServerReply(ftpClient);
            if (!success) {
                System.out.println("Could not login to the server");
                return;
            } else {
                System.out.println("LOGGED IN SERVER");
                client = ftpClient;
                client.setFileType(FTP.BINARY_FILE_TYPE);

                client.setFileTransferMode(FTP.BINARY_FILE_TYPE);

                client.enterLocalPassiveMode();
            }
        } catch (IOException ex) {
            System.out.println("Oops! Something wrong happened");
            ex.printStackTrace();
        }
    }

    public static FTPClient getInstance() {
        if (client == null) {
            createFTPConnection();
        }
        return client;
    }

    public static void sendFile(String firstRemoteFile, File firstLocalFile) {
        InputStream inputStream = null;
        try {
            FTPClient ftpClient = getInstance();
            inputStream = new FileInputStream(firstLocalFile);
           // System.out.println("Start uploading first file");
            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
            //inputStream.close();
            if (done) {
                //System.out.println("The first file is uploaded successfully.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(FTPConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
