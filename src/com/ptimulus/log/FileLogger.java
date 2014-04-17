package com.ptimulus.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.ptimulus.log.IPtimulusLogger;

import android.content.Context;
import android.telephony.SmsManager;
import android.text.format.Time;
import android.widget.CompoundButton;

public class FileLogger implements IPtimulusLogger {

	final String logfileName = "ptimulus.log";
	private PrintWriter file;
	
	private boolean logging;

	public FileLogger() {
		logging = false;
	}

	public void logDataEvent(LogEntryType type, String data, long ts) {
		if (!logging)
			return;
		
		file.println(System.currentTimeMillis() + " " + type + ": " + data
				+ " " + ts);
		file.flush();

	}

	public void startLogging() {
		try {
			File f = new File("/sdcard", logfileName);
			file = new PrintWriter(new FileOutputStream(f, true));
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		logging = true;
		this.logDataEvent(LogEntryType.APP_LIFECYCLE, "File logging Started", 0);
	}

	public void stopLogging() {
		if (logging)
			return;
		file.close();
		logging = false;
	}

}
