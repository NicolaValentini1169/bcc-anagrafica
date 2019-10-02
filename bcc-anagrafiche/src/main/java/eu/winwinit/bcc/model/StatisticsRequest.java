package eu.winwinit.bcc.model;

public class StatisticsRequest {
    private String branch;
    private String startDate;
    private String endDate;
    
    public StatisticsRequest() {
    }

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
