package com.gemicle.chat.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import javax.swing.JFileChooser;

public class FileService {

	public byte[] convertToByteArray(File file) throws IOException {
        long length = file.length();

        if (length > Integer.MAX_VALUE) {
            throw new IOException("File is too large!");
        }

        byte[] bytes = new byte[(int)length];

        int offset = 0;
        int numRead = 0;

        InputStream is = new FileInputStream(file);
        try {
            while (offset < bytes.length
                   && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }
        } finally {
            is.close();
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
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

	public String getSavePath(){
		JFileChooser chooser=new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.showSaveDialog(null);

		String path=chooser.getSelectedFile().getAbsolutePath();
		return path;
	}
	
}
