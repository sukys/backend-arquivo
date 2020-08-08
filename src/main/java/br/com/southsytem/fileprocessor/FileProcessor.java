package br.com.southsytem.fileprocessor;

import java.io.File;

import br.com.southsytem.fileprocessor.entrypoint.EntryPoint;
import br.com.southsytem.fileprocessor.util.FileUtil;

/**
 * Class responsible for start the application.
 * @author luis
 *
 */
public class FileProcessor {

	
	private static final EntryPoint entry = new EntryPoint();;

	public static void main(String[] args) {
		FileUtil.createDirectories();
		processFiles();
	}

	/**
	 * Retrieve and process all files in the specified directory
	 * with the '.dat' extension.
	 * 
	 */
	private static void processFiles() {
		while (true) {
			for (File file : FileUtil.getAllFiles()) {
				entry.processFile(file);
			}
		}
	}

}
