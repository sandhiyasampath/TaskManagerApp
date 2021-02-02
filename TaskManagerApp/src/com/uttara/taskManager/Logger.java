package com.uttara.taskManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Logger {
	public static final boolean LOGTOMONITOR = true;
	
	// 1.mark the constructor private
	private Logger() {
		
	}
	
	//2.create a single copy reference variable
	private static Logger obj = null;
	
	//3.create a getInstance method
	public static Logger getInstance() {
		//4.do a null check on single copy variable then create object only once
		if(obj==null)
			obj = new Logger();
		return obj;
	}
	public void log(String data) {
		new Thread(new Runnable() {
				public void run() {
					
					Date dt = new Date();
					BufferedWriter bw = null;
					try {
						String msg = dt + ":" +data;
						bw = new BufferedWriter(new FileWriter("log.txt",true));
						bw.write(msg);
						bw.newLine();
						
						if(Logger.LOGTOMONITOR==true)
							System.out.println("Logger:"+msg);
					}
					catch (IOException e) {
						e.printStackTrace();
					}
					finally {
						try {
							if(bw!=null)
								bw.close();
						}
						catch(IOException i) {
							i.printStackTrace();
						}
					}
				}
		}).start();
	}
}
