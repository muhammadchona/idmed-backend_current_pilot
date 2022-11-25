package mz.org.fgh.sifmoz.backend.tansreference;

import java.util.Date;

public class SyncTempEpisode {



    private Long id;

    private Date startdate;

    private Date stopdate;

    private String startreason;

    private String stopreason;

    private String startnotes;

    private String stopnotes;

    private String patientuuid;

    private char syncstatus;

    private String usuuid;

    private String clinicuuid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getStopdate() {
        return stopdate;
    }

    public void setStopdate(Date stopdate) {
        this.stopdate = stopdate;
    }

    String getStartreason() {
        return startreason;
    }

    public void setStartreason(String startreason) {
        this.startreason = startreason;
    }

    String getStopreason() {
        return stopreason;
    }

    public void setStopreason(String stopreason) {
        this.stopreason = stopreason;
    }

    String getStartnotes() {
        return startnotes;
    }

    public void setStartnotes(String startnotes) {
        this.startnotes = startnotes;
    }

    String getStopnotes() {
        return stopnotes;
    }

    public void setStopnotes(String stopnotes) {
        this.stopnotes = stopnotes;
    }

    String getPatientuuid() {
        return patientuuid;
    }

    public void setPatientuuid(String patientuuid) {
        this.patientuuid = patientuuid;
    }

    public char getSyncstatus() {
        return syncstatus;
    }

    public void setSyncstatus(char syncstatus) {
        this.syncstatus = syncstatus;
    }

    String getUsuuid() {
        return usuuid;
    }

    public void setUsuuid(String usuuid) {
        this.usuuid = usuuid;
    }

    String getClinicuuid() {
        return clinicuuid;
    }

    public void setClinicuuid(String clinicuuid) {
        this.clinicuuid = clinicuuid;
    }
}
