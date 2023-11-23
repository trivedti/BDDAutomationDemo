package app.constants;

import java.io.File;

public abstract class FilePaths {

	/**
	 * This is Base path for all the page locators
	 */

	public static final String PROPERTIES_FOLDER_PATH = System.getProperty("user.dir") + File.separator + "config"
			+ File.separator;

	public static final String PNG_FOLDER_PATH = System.getProperty("user.dir") + File.separator + "src"
			+ File.separator + "main" + File.separator + "resources" + File.separator + "MediaFiles" + File.separator
			+ "PNG.png";

	public static final String CSV_FOLDER_PATH = System.getProperty("user.dir") + File.separator + "src"
			+ File.separator + "main" + File.separator + "resources" + File.separator + "MediaFiles" + File.separator
			+ "RouteSavvy_sample_Valid.csv";

	public static final String CSV_Invalid_FOLDER_PATH = System.getProperty("user.dir") + File.separator + "src"
			+ File.separator + "main" + File.separator + "resources" + File.separator + "MediaFiles" + File.separator
			+ "RouteSavvy_sample_InValid.csv";

//	public static final String CSV_FOLDER_PATH = System.getProperty("user.dir") + File.separator + "src"
//			+ File.separator + "main" + File.separator + "resources" + File.separator + "MediaFiles" + File.separator
//			+ "02_RouteSavvy_sample_5_Valid.csv";
//	

//	public static final String CSV_FOLDER_PATH = System.getProperty("user.dir") + File.separator + "src"
//			+ File.separator + "main" + File.separator + "resources" + File.separator + "MediaFiles" + File.separator
//			+ "01_sample_addresses_Test_78.xlsx";
//	
//	public static final String CSV_FOLDER_PATH = System.getProperty("user.dir") + File.separator + "src"
//			+ File.separator + "main" + File.separator + "resources" + File.separator + "MediaFiles" + File.separator
//			+ "02_RouteSavvy_sample_Test_20.csv";

}
