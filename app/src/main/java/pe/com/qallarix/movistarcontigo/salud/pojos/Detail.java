package pe.com.qallarix.movistarcontigo.salud.pojos;

import java.io.Serializable;
import java.util.List;

public class Detail implements Serializable {
    private String planAlignment;
    private String planAlignmentNameFile;
    private String planAlignmentFile;
    private List<String> benefitsList;
    private String contributionDescription;
    private String contributionFile;
    private String contributionTable;

    public String getPlanAlignment() { return planAlignment; }
    public void setPlanAlignment(String value) { this.planAlignment = value; }

    public String getPlanAlignmentNameFile() { return planAlignmentNameFile; }
    public void setPlanAlignmentNameFile(String value) { this.planAlignmentNameFile = value; }

    public String getPlanAlignmentFile() { return planAlignmentFile; }
    public void setPlanAlignmentFile(String value) { this.planAlignmentFile = value; }

    public List<String> getBenefitsList() { return benefitsList; }
    public void setBenefitsList(List<String> value) { this.benefitsList = value; }

    public String getContributionDescription() { return contributionDescription; }
    public void setContributionDescription(String value) { this.contributionDescription = value; }

    public String getContributionFile() { return contributionFile; }
    public void setContributionFile(String value) { this.contributionFile = value; }

    public String getContributionTable() { return contributionTable; }
    public void setContributionTable(String value) { this.contributionTable = value; }
}
