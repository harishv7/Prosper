package DataSci.main;
import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;

public class JsonRequestResponseParser {

	public String JSON_DEFAULT_FORMAT = "{"
  											+ "\"Inputs\": {"
  												+ "\"input1\": {"
        												+ "\"ColumnNames\": ["
        																+ "\"ym\", "
        																+ "\"year\", "
        																+ "\"month\", "
        												                + "\"normalized_month\", "
        												                + "\"town\", "
        												                + "\"flat_type\", "
        												                + "\"floor_area_sqm\", "
        												                + "\"flat_model\", "
        												                + "\"lease_commence_date\", "
        												                + "\"normalized_lease_commence_date\", "
        												                + "\"resale_price\""
        												+ "],"
        												+ "\"Values\": ["
        												           		+ "["
        												            	+ "\"2000-01\", "
        												            	+ "\"0\", "
        												            	+ "\"0\", "
        												            	+ "\"%1$s\", "
        												            	+ "\"%2$s\", "
        												            	+ "\"%3$s\", "
        												            	+ "\"%4$s\", "
        												            	+ "\"%5$s\", "
        												            	+ "\"0\", "
        												            	+ "\"%6$s\", "
        												            	+ "\"0\""
        																+ "]"
        												+ "]"
        										+ "}"
        									+ "}, "
        									+ "\"GlobalParameters\": {}"
        								+ "}";
	
	public String parseInputParameters(Parameters parameters) {
		String jsonString = "";
		jsonString = String.format(JSON_DEFAULT_FORMAT, 
				parameters.getNormalizedYearMonth(), 
				parameters.getRegion(), 
				parameters.getFlatType(), 
				parameters.getFloorArea(), 
				parameters.getFlatModel(), 
				parameters.getLeaseCommenceYear());
		return jsonString;
	}
	
	public Parameters parseOutputJson(String jsonString) throws IOException {
		Parameters parameters = new Parameters();
		BufferedReader reader = new BufferedReader(new StringReader(jsonString));
		int predictedFlatPrice = 0;
		String currentLine;
		int counter = 0;
		while ((currentLine = reader.readLine()) != null) {
			if (counter == 35) {
				currentLine.replaceAll(" ", "");
				currentLine.replaceAll("\"", "");
				currentLine.replaceAll(",", "");
				predictedFlatPrice = Integer.parseInt(currentLine);
			}
		}
		parameters.setResalePrice(predictedFlatPrice);
		return parameters;
	}
	
}
