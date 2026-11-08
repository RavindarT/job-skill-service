package com.example.jobskills;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;
@Embeddable
public class JobSkillId implements Serializable {
    @Column(name = "job_id", nullable = false)
    private Long jobId;
    @Column(name = "s_id", nullable = false)
    private Long sId;
    protected JobSkillId() {}
    public JobSkillId(Long jobId, Long sId) { this.jobId = jobId; this.sId = sId; }
    public Long getJobId() { return jobId; }
    public void setJobId(Long value) { this.jobId = value; }
    public Long getSId() { return sId; }
    public void setSId(Long value) { this.sId = value; }
    @Override public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof JobSkillId id)) return false;
        return Objects.equals(jobId, id.jobId) && Objects.equals(sId, id.sId);
    }
    @Override public int hashCode() { return Objects.hash(jobId, sId); }
}
