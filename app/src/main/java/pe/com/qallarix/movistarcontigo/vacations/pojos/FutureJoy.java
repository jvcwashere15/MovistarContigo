package pe.com.qallarix.movistarcontigo.vacations.pojos;

public class FutureJoy {
    private long plannedDaysPending;
    private long plannedDaysExpired;
    private long plannedDaysTruncate;
    private String pendingRightDate;
    private String rightDateExpired;
    private String finalDateCalendar;
    private String dateOfRight;
    private String dayValueExpired;
    private boolean isLeadership;
    private String leadershipCip;
    private String leadershipName;

    public long getPlannedDaysPending() {
        return plannedDaysPending;
    }

    public void setPlannedDaysPending(long plannedDaysPending) {
        this.plannedDaysPending = plannedDaysPending;
    }

    public long getPlannedDaysExpired() {
        return plannedDaysExpired;
    }

    public void setPlannedDaysExpired(long plannedDaysExpired) {
        this.plannedDaysExpired = plannedDaysExpired;
    }

    public String getPendingRightDate() {
        return pendingRightDate;
    }

    public void setPendingRightDate(String pendingRightDate) {
        this.pendingRightDate = pendingRightDate;
    }

    public String getRightDateExpired() {
        return rightDateExpired;
    }

    public void setRightDateExpired(String rightDateExpired) {
        this.rightDateExpired = rightDateExpired;
    }

    public String getFinalDateCalendar() {
        return finalDateCalendar;
    }

    public void setFinalDateCalendar(String finalDateCalendar) {
        this.finalDateCalendar = finalDateCalendar;
    }

    public String getDateOfRight() {
        return dateOfRight;
    }

    public void setDateOfRight(String dateOfRight) {
        this.dateOfRight = dateOfRight;
    }

    public String getDayValueExpired() {
        return dayValueExpired;
    }

    public void setDayValueExpired(String dayValueExpired) {
        this.dayValueExpired = dayValueExpired;
    }

    public long getPlannedDaysTruncate() {
        return plannedDaysTruncate;
    }

    public void setPlannedDaysTruncate(long plannedDaysTruncate) {
        this.plannedDaysTruncate = plannedDaysTruncate;
    }

    public boolean isLeadership() {
        return isLeadership;
    }

    public void setLeadership(boolean leadership) {
        isLeadership = leadership;
    }

    public String getLeadershipCip() {
        return leadershipCip;
    }

    public void setLeadershipCip(String leadershipCip) {
        this.leadershipCip = leadershipCip;
    }

    public String getLeadershipName() {
        return leadershipName;
    }

    public void setLeadershipName(String leadershipName) {
        this.leadershipName = leadershipName;
    }
}
