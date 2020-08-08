package br.com.southsytem.fileprocessor.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import br.com.southsytem.fileprocessor.exceptions.BusinessException;

public class FileUtil {

	private static final List<String> EXTENSIONS_ALLOWED = PropertiesUtil.getExtensionsAllowed();
	private static final String HOME_VARIABLE = PropertiesUtil.getHomeVariable();
	private static final String BASE_DIR = PropertiesUtil.getBaseDirectory();
	private static final String INPUT_DIR = PropertiesUtil.getInputDirectory();
	private static final String OUTPUT_DIR = PropertiesUtil.getOutputDirectory();
	private static final String ERROR_DIR = PropertiesUtil.getErrorDirectory();

	private static final String IN = getBasePath(INPUT_DIR);
	private static final String OUT = getBasePath(OUTPUT_DIR);
	private static final String ERR = getBasePath(ERROR_DIR);

	/**
	 * Check if Environment Variable "HOMEPATH" exists or use the "user.home"
	 * property as base path. Retrieves the path for the informed directory.
	 * 
	 * @param dir
	 * @return
	 */
	private static final String getBasePath(String dir) {
		String basePath = System.getenv().containsKey(HOME_VARIABLE) ? System.getenv(HOME_VARIABLE)
				: System.getProperty("user.home");
		basePath += File.separator.concat(BASE_DIR).concat(File.separator).concat(dir).concat(File.separator);
		return basePath;
	}

	/**
	 * Create the directories for input and output files, if they don't exists.
	 * 
	 */
	public static void createDirectories() {
		createDirectory(IN);
		createDirectory(OUT);
		createDirectory(ERR);
	}

	/**
	 * Retrieves All files inside the 'in' directory with the allowed extensions.
	 * 
	 * @return
	 */
	public static List<File> getAllFiles() {
		File inDirectory = new File(IN);
		List<File> files = new ArrayList<>();
		String extension = "";
		for (File file : inDirectory.listFiles()) {
			extension = getFileExtension(file.getName());
			if (file.isFile() && EXTENSIONS_ALLOWED.contains(extension)) {
				files.add(file);
			} else {
				moveFileToErrorDir(file);
			}
		}
		return files;
	}

	/**
	 * Read the content of a file and returns a list of String where each list item
	 * is a line of the file.
	 * 
	 * @param file
	 * @return
	 */
	public static List<String> readFileContent(File file) throws BusinessException {
		List<String> fileContent = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(file));) {
			String line = "";
			while ((line = reader.readLine()) != null) {
				fileContent.add(line);
			}
		} catch (IOException e) {
			throw new BusinessException("Error reading the file '" + file.getName() + "'.\n[" + e.getMessage() + "]");
		}
		return fileContent;
	}

	/**
	 * Creates a new file with name and content specified
	 * 
	 * @param filename
	 * @param content
	 */
	public static void createFile(String filename, String content) throws BusinessException {
		Path path = Paths.get(OUT.concat(filename));
		try {
			Files.write(path, content.getBytes());
		} catch (IOException e) {
			throw new BusinessException("Error creating file '" + filename + "'.\n[" + e.getMessage() + "]",
					e.getCause());
		}
	}

	/**
	 * Moves a file with error to specified Error directory, to avoid processing it
	 * again.
	 * 
	 * @param file
	 */
	public static void moveFileToErrorDir(File file) throws BusinessException {
		String filename = file.getName();
		String extension = getFileExtension(filename);
		filename = filename.replace(extension, ".err" + extension);
		File destination = new File(ERR.concat(filename));
		try {
			moveFileToDir(file, destination);
		} catch (Exception e) {
			System.err.println(e.getMessage() + "\n File will be deleted instead move it to error directory.");
			file.delete();
		}
	}

	/**
	 * Move a file to another directory
	 * 
	 * @param origin
	 * @param destination
	 */
	private static void moveFileToDir(File origin, File destination) {
		try {
			Files.move(Paths.get(origin.getPath()), Paths.get(destination.getPath()),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new BusinessException("Failed to move file.");
		}
	}

	/**
	 * Checks if the path exists and if is a directory. Create the directory if it
	 * doesn't exists.
	 * 
	 * @param path
	 */
	private static void createDirectory(String path) {
		File directory = new File(path);
		if (!directory.exists() || !directory.isDirectory()) {
			directory.mkdirs();
		}
	}

	/**
	 * Get the extension from a filename informed
	 * 
	 * @param filename
	 * @return
	 */
	private static String getFileExtension(String filename) {
		return filename.substring(filename.lastIndexOf(".")).toLowerCase();
	}

}
