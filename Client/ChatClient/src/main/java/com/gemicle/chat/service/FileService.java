package com.gemicle.chat.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;

public class FileService {

	public byte[] convertToByteArray(File file) throws IOException {
		byte[] bytes = null;
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(file);
			oos.flush();
			bytes = baos.toByteArray();
		} finally {
			if (oos != null) {
				oos.close();
			}
			if (baos != null) {
				baos.close();
			}
		}
		return bytes;
	}

	public File convertToFile(byte[] bytes) throws IOException, ClassNotFoundException {
		Object obj = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			bis = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bis);
			obj = ois.readObject();
		} finally {
			if (bis != null) {
				bis.close();
			}
			if (ois != null) {
				ois.close();
			}
		}
		return (File) obj;
	}

	public String getPathFile() {
		return getFile().getAbsolutePath();
	}

	public File getFile() {
		File file = null;

		JFileChooser fc = new JFileChooser();
		fc.setAcceptAllFileFilterUsed(true);
		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			file = new File(fc.getSelectedFile().getAbsolutePath());
		}
		return file;
	}

}
