package DataSci.main;
public class Parameters {

	private String yearMonth = "";
	private int year;
	private int month;
	private int normalizedYearMonth;
	private String region = "";
	private String flatType = "";
	private int floorArea;
	private String flatModel = "";
	private int leaseCommenceYear;
	private int resalePrice;
	
	public Parameters(int year, int month, String region, String flatType, 
			int floorArea, String flatModel, int leaseCommenceYear) {
		this.yearMonth = year + "-" + String.format("%02d", month);
		this.year = year;
		this.month = month;
		this.normalizedYearMonth = (year - 2000) * 12 + month;
		this.region = region;
		this.flatType = flatType;
		this.floorArea = floorArea;
		this.flatModel = flatModel;
		this.leaseCommenceYear = leaseCommenceYear;
		this.resalePrice = 0;
	}
	
	public Parameters() {
		
	}
	
	public String getYearMonth() {
		return yearMonth;
	}
	
	public int getYear() {
		return year;
	}
	
	public int getMonth() {
		return month;
	}
	
	public int getNormalizedYearMonth() {
		return normalizedYearMonth;
	}
	
	public String getRegion() {
		return region;
	}
	
	public String getFlatType() {
		return flatType;
	}
	
	public int getFloorArea() {
		return floorArea;
	}
	
	public String getFlatModel() {
		return flatModel;
	}
	
	public int getLeaseCommenceYear() {
		return leaseCommenceYear;
	}
	
	public int getResalePrice() {
		return resalePrice;
	}
	
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
	
	public void setYear(int year) {
		this.year= year;
	}
	
	public void setMonth(int month) {
		this.month = month;
	}
	
	public void setNormalizedYearMonth(int normalizedYearMonth) {
		this.normalizedYearMonth = normalizedYearMonth;
	}
	
	public void setRegion(String region) {
		this.region = region;
	}
	
	public void setFlatType(String flatType) {
		this.flatType = flatType;
	}
	
	public void setFloorArea(int floorArea) {
		this.floorArea = floorArea;
	}
	
	public void setFlatModel(String flatModel) {
		this.flatModel = flatModel;
	}
	
	public void setLeaseCommenceYear(int leaseCommenceYear) {
		this.leaseCommenceYear = leaseCommenceYear;
	}
	
	public void setResalePrice(int resalePrice) {
		this.resalePrice = resalePrice;
	}
	
}
