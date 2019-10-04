package eu.winwinit.bcc.model;

public class StatisticsResponse {
	int totCustomers=0;
	int totConfirmedRecords=0;
	int totChangedRecords=0;
	int totNotConfirmedRecords=0;
	int totEditedPhone=0;
	int totEditedEmail=0;
	int totEditedPrivacy1=0;
	int totEditedPrivacy2=0;
	int totEditedPrivacy3=0;
	int totEditedPrivacy4=0;
	int totEditedPrivacy5=0;
	int totEditedPrivacy6=0;
	
	
	public StatisticsResponse() {
		super();
	}
	
	public StatisticsResponse(int totCustomers, int totConfirmedRecords, int totChangedRecords,
			int totNotConfirmedRecords, int totEditedPhone, int totEditedEmail, int totEditedPrivacy1,
			int totEditedPrivacy2, int totEditedPrivacy3, int totEditedPrivacy4, int totEditedPrivacy5,
			int totEditedPrivacy6) {
		super();
		this.totCustomers = totCustomers;
		this.totConfirmedRecords = totConfirmedRecords;
		this.totChangedRecords = totChangedRecords;
		this.totNotConfirmedRecords = totNotConfirmedRecords;
		this.totEditedPhone = totEditedPhone;
		this.totEditedEmail = totEditedEmail;
		this.totEditedPrivacy1 = totEditedPrivacy1;
		this.totEditedPrivacy2 = totEditedPrivacy2;
		this.totEditedPrivacy3 = totEditedPrivacy3;
		this.totEditedPrivacy4 = totEditedPrivacy4;
		this.totEditedPrivacy5 = totEditedPrivacy5;
		this.totEditedPrivacy6 = totEditedPrivacy6;
	}

	public int getTotCustomers() {
		return totCustomers;
	}
	public void setTotCustomers(int totCustomers) {
		this.totCustomers = totCustomers;
	}
	public int getTotConfirmedRecords() {
		return totConfirmedRecords;
	}
	public void setTotConfirmedRecords(int totConfirmedRecords) {
		this.totConfirmedRecords = totConfirmedRecords;
	}
	public int getTotChangedRecords() {
		return totChangedRecords;
	}
	public void setTotChangedRecords(int totChangedRecords) {
		this.totChangedRecords = totChangedRecords;
	}
	public int getTotNotConfirmedRecords() {
		return totNotConfirmedRecords;
	}
	public void setTotNotConfirmedRecords(int totNotConfirmedRecords) {
		this.totNotConfirmedRecords = totNotConfirmedRecords;
	}
	public int getTotEditedPhone() {
		return totEditedPhone;
	}
	public void setTotEditedPhone(int totEditedPhone) {
		this.totEditedPhone = totEditedPhone;
	}
	public int getTotEditedEmail() {
		return totEditedEmail;
	}
	public void setTotEditedEmail(int totEditedEmail) {
		this.totEditedEmail = totEditedEmail;
	}
	public int getTotEditedPrivacy1() {
		return totEditedPrivacy1;
	}
	public void setTotEditedPrivacy1(int totEditedPrivacy1) {
		this.totEditedPrivacy1 = totEditedPrivacy1;
	}
	public int getTotEditedPrivacy2() {
		return totEditedPrivacy2;
	}
	public void setTotEditedPrivacy2(int totEditedPrivacy2) {
		this.totEditedPrivacy2 = totEditedPrivacy2;
	}
	public int getTotEditedPrivacy3() {
		return totEditedPrivacy3;
	}
	public void setTotEditedPrivacy3(int totEditedPrivacy3) {
		this.totEditedPrivacy3 = totEditedPrivacy3;
	}
	public int getTotEditedPrivacy4() {
		return totEditedPrivacy4;
	}
	public void setTotEditedPrivacy4(int totEditedPrivacy4) {
		this.totEditedPrivacy4 = totEditedPrivacy4;
	}
	public int getTotEditedPrivacy5() {
		return totEditedPrivacy5;
	}
	public void setTotEditedPrivacy5(int totEditedPrivacy5) {
		this.totEditedPrivacy5 = totEditedPrivacy5;
	}
	public int getTotEditedPrivacy6() {
		return totEditedPrivacy6;
	}
	public void setTotEditedPrivacy6(int totEditedPrivacy6) {
		this.totEditedPrivacy6 = totEditedPrivacy6;
	}
	
}
