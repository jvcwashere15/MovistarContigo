package pe.com.qallarix.movistarcontigo.flexplace.historial.pojos;

public class FlexPlace {
    private long id;
    private String dateStart;
    private String dateEnd;
    private String dayWeek;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDateStart() { return dateStart; }
    public void setDateStart(String value) { this.dateStart = value; }

    public String getDateEnd() { return dateEnd; }
    public void setDateEnd(String value) { this.dateEnd = value; }

    public String getDayWeek() { return dayWeek; }
    public void setDayWeek(String value) { this.dayWeek = value; }
}
