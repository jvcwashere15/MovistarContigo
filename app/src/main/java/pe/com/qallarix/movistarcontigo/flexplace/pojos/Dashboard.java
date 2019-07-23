package pe.com.qallarix.movistarcontigo.flexplace.pojos;

public class Dashboard {

    private String dateEnd;
    private String dateStart;
    private String dayWeek;
    private boolean isLeadership;
    private long isStatus;
    private String leadership;
    private String monthTaked;


    public String getLeadership() {
        return leadership;
    }

    public void setLeadership(String leadership) {
        this.leadership = leadership;
    }

    public String getDayWeek() { return dayWeek; }
    public void setDayWeek(String value) { this.dayWeek = value; }

    public String getMonthTaked() { return monthTaked; }
    public void setMonthTaked(String value) { this.monthTaked = value; }

    public String getDateEnd() { return dateEnd; }
    public void setDateEnd(String value) { this.dateEnd = value; }

    public boolean isLeadership() {
        return isLeadership;
    }

    public void setLeadership(boolean leadership) {
        isLeadership = leadership;
    }

    public long getIsStatus() {
        return isStatus;
    }

    public void setIsStatus(long isStatus) {
        this.isStatus = isStatus;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }
}
