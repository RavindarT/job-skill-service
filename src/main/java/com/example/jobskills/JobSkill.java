package com.example.jobskills;
import jakarta.persistence.*;
@Entity
@Table(name = "job_skill")
public class JobSkill {
    @EmbeddedId
    private JobSkillId id;
    @Column(name = "years_of_exp", nullable = false)
    private Integer yearsOfExp;
    public JobSkillId getId() { return id; }
    public void setId(JobSkillId value) { id = value; }
    public Integer getYearsOfExp() { return yearsOfExp; }
    public void setYearsOfExp(Integer value) { yearsOfExp = value; }
}
