import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class SimpleCsvParser {
	static int validRecord = 0;
	static int skippedRow = 0;

	private static final String CSV_FILE = "C:\\Users\\User\\Documents\\SMU\\MCDA5510\\MCDA5510_Assignments\\Assignment1\\Output\\DataResult.csv";

	public static void parseFile(ArrayList<String> allCSVFile) {
		Reader in;
		SimpleLogging log = new SimpleLogging();

		try {

			BufferedWriter writer = Files.newBufferedWriter(Paths.get(CSV_FILE));
			CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
					.withHeader("First Name", "Last Name", "Street Number", "Street","City","Province",
							"Postal Code","Country","Phone Number","Email Address", "Date"));
			try {

				for(int i = 0; i < allCSVFile.size(); i++) {
					in = new FileReader(allCSVFile.get(i));
					Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);

					String pathDate = getStringDate(allCSVFile.get(i));

					for (CSVRecord record : records) {
						if(record.size() == 10) {
							String firstName = record.get(0); 			
							String lastName = record.get(1);
							String streetNo = record.get(2);
							String streetName = record.get(3);
							String city = record.get(4);
							String province = record.get(5);
							String postalCode = record.get(6);
							String country = record.get(7);
							String phoneNum = record.get(8);
							String email = record.get(9);

							if(firstName.equals("First Name")){
								//do nothing
							}else if(isValid(firstName, lastName, email)) {
								validRecord++;
								csvPrinter.printRecord(firstName,lastName,streetNo,streetName,
										city,province,postalCode,country,phoneNum,email,pathDate);
							}else {
								skippedRow++;
							}
						}else {
							skippedRow++;
						}
					}			
				}

				csvPrinter.flush();
				csvPrinter.close();

			} catch ( IOException e) {
				log.logging(e.getMessage());
			} 
		} catch ( IOException e) {
			log.logging(e.getMessage());
		}
	}
	public static boolean isValid(String firstName, String lastName, String email) {

		//has valid first name, last name and email
		if(firstName.isEmpty() == true || lastName.isEmpty() == true ||
				email.contains("@null") == true || email.contains("null@") == true || email.isEmpty() == true) {
			return false;
		}else {
			return true;

		}
	}
	public static String getStringDate(String path) {
		String[] z = path.split("\\\\");
		String day = z[z.length-2];
		String month = z[z.length-3];
		String year = z[z.length-4];
		String pathDate = year + "/" + month + "/" + day;
		return pathDate;
	}
	public static int getValidRecord() {
		return validRecord;
	}
	public static int getSkippedRow() {
		return skippedRow;
	}
}
