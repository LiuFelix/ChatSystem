package message;

import java.io.File;
import java.net.InetAddress;

public class MsgFile extends Message{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7746685928835284793L;

	/**
	 * Name of the file to send
	 */
	private String fileName;
	
	/**
	 * File
	 */
	private File theFile;
	
	/**
	 * The size of the file
	 */
	private float fileSize;
	
	
	/**
	 * A beautiful constructor using the fileName
	 * @param sourceAddress
	 * @param sourcePort
	 * @param destinationAddress
	 * @param destinationPort
	 * @param numMessage
	 * @param fileName
	 */
	public MsgFile(InetAddress sourceAddress, int sourcePort, String senderUserName, InetAddress destinationAddress, int destinationPort,int numMessage, String fileName) {
		super(sourceAddress, sourcePort, senderUserName, destinationAddress, destinationPort, numMessage);
		
		this.theFile = new File(fileName);
	}
	
	/**
	 * A beautiful constructor using directly the File
	 * @param sourceAddress
	 * @param sourcePort
	 * @param destinationAddress
	 * @param destinationPort
	 * @param numMessage
	 * @param theFile
	 */
	public MsgFile(InetAddress sourceAddress, int sourcePort, InetAddress destinationAddress, int destinationPort, int numMessage, File theFile) {
		super(sourceAddress, sourcePort, "", destinationAddress, destinationPort, numMessage);
		
		this.theFile = theFile;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public File getTheFile() {
		return theFile;
	}

	public void setTheFile(File theFile) {
		this.theFile = theFile;
	}

	public float getFileSize() {
		return fileSize;
	}

	public void setFileSize(float fileSize) {
		this.fileSize = fileSize;
	}
	
	

}
