package app.constants;

import java.time.Duration;

public abstract class ApplicationConstants {

	public static String URL = "https://dev.routesavvy.com";
	//public static String ADMINURL = "https://stg4admin.1veda.in/site/login";
	public static String browser = "chrome";
	public static String environment = "Automation QA";
	public static final Duration EXP_WAIT = Duration.ofSeconds(30);

	public static final Duration Page_load_timeout = Duration.ofSeconds(30);

	public static final long IMP_WAIT = 10;

}
