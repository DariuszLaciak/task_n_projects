package pl.edu.pk.laciak.functions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FTPCommon {
	static FTPClient client = new FTPClient();
	static FileInputStream fis = null;
	final static String ftp_host = Common.getProjetProperty("ftp_host");
	final static int ftp_port = Integer.parseInt(Common.getProjetProperty("ftp_port"));
	final static String ftp_user = Common.getProjetProperty("ftp_user");
	final static String ftp_pass = Common.getProjetProperty("ftp_pass");

	public static boolean uploadFile(File tempFile, String filename, String directory){
		boolean success = false;
		try {
			client.connect(ftp_host, ftp_port);
			client.login(ftp_user,ftp_pass);
			client.enterLocalPassiveMode();
			client.setFileType(FTP.BINARY_FILE_TYPE);
			client.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
			fis = new FileInputStream(tempFile);
			ftpCreateDirectoryTree(client,directory);
			success = client.storeFile(filename, fis);
			System.out.println(client.getReplyString());
			fis.close();
			client.logout();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return success;

	}
	
	public static boolean deleteFile(String filepath){
		boolean success = false;
		try {
			client.connect(ftp_host, ftp_port);
			client.login(ftp_user,ftp_pass);
			client.enterLocalPassiveMode();
			client.setFileType(FTP.BINARY_FILE_TYPE);
			client.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
			success = client.deleteFile(filepath);
			System.out.println(client.getReplyString());
			fis.close();
			client.logout();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return success;
	}
	
	public static File getPhoto(long user_id){
		File file = null;
		InputStream input = null;
		OutputStream output = null;
		try {
			client.connect(ftp_host, ftp_port);
			client.login(ftp_user,ftp_pass);
			client.enterLocalPassiveMode();
			client.setFileType(FTP.BINARY_FILE_TYPE);
			client.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
			client.changeWorkingDirectory("images/"+user_id);
			input = client.retrieveFileStream("photo.jpg");
			System.out.println(client.getReplyString());
			if(input != null)
				file = File.createTempFile("temp", ".jpg");
			else throw new IOException();
			output = new FileOutputStream(file);
			int read = 0;
			byte[] bytes = new byte[1024];
			if(input != null)
			while ((read = input.read(bytes)) != -1) {
				output.write(bytes, 0, read);
			}
			client.logout();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try {
				if(client.isConnected())
					client.disconnect();
				if(output != null)
					output.close();
				if(input != null)
					input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return file;
	}
	
	private static void ftpCreateDirectoryTree( FTPClient client, String dirTree ) throws IOException {

		  boolean dirExists = true;

		  //tokenize the string and attempt to change into each directory level.  If you cannot, then start creating.
		  String[] directories = dirTree.split("/");
		  for (String dir : directories ) {
		    if (!dir.isEmpty() ) {
		      if (dirExists) {
		        dirExists = client.changeWorkingDirectory(dir);
		      }
		      if (!dirExists) {
		        if (!client.makeDirectory(dir)) {
		          System.err.println("Unable to create remote directory '" + dir + "'.  error='" + client.getReplyString()+"'");
		        }
		        if (!client.changeWorkingDirectory(dir)) {
		        	System.err.println("Unable to change into newly created remote directory '" + dir + "'.  error='" + client.getReplyString()+"'");
		        }
		      }
		    }
		  }     
		}
}
