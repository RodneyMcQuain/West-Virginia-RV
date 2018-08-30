package model.utility;

public class GeneralUtils {
	public static String formatDate(String date) {
 		String[] splitDate = date.split("-");
 		String formattedDate = splitDate[1] + "/" + splitDate[2] + "/" + splitDate[0];
 		
		return formattedDate;
	}
}
