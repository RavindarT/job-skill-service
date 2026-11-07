package com.example.jobskills;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/jobs/{jobId}/skills")
public class JobSkillController {
    private final JdbcClient db;

    public JobSkillController(JdbcClient db) {
        this.db = db;
    }

    public enum Proficiency { BEGINNER, INTERMEDIATE, ADVANCED, EXPERT }

    public record Skill(UUID jobId, String code, String name, Proficiency proficiency) {}
    public record SkillInput(@NotBlank @Size(max = 120) String name,
                             @NotNull Proficiency proficiency) {}

    @GetMapping
    public List<Skill> list(@PathVariable UUID jobId) {
        return db.sql("""
                SELECT job_id, code, name, proficiency
                FROM job_skills WHERE job_id = ? ORDER BY code
                """).param(jobId).query(Skill.class).list();
    }

    @GetMapping("/{code}")
    public Skill get(@PathVariable UUID jobId, @PathVariable String code) {
        return db.sql("""
                SELECT job_id, code, name, proficiency
                FROM job_skills WHERE job_id = ? AND code = ?
                """).params(jobId, normalizeCode(code)).query(Skill.class).optional()
                .orElseThrow(JobSkillController::notFound);
    }

    @PutMapping("/{code}")
    public Skill save(@PathVariable UUID jobId, @PathVariable String code,
                      @Valid @RequestBody SkillInput input) {
        Skill skill = new Skill(jobId, normalizeCode(code), input.name().strip(), input.proficiency());
        db.sql("""
                MERGE INTO job_skills (job_id, code, name, proficiency)
                KEY (job_id, code) VALUES (?, ?, ?, ?)
                """).params(jobId, skill.code(), skill.name(), skill.proficiency().name()).update();
        return skill;
    }

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID jobId, @PathVariable String code) {
        int deleted = db.sql("DELETE FROM job_skills WHERE job_id = ? AND code = ?")
                .params(jobId, normalizeCode(code)).update();
        if (deleted == 0) {
            throw notFound();
        }
    }

    private static String normalizeCode(String code) {
        if (!code.matches("[A-Za-z0-9]+(?:-[A-Za-z0-9]+)*") || code.length() > 60) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Skill code must be 1 to 60 letters, digits, or single separating hyphens");
        }
        return code.toLowerCase(Locale.ROOT);
    }

    private static ResponseStatusException notFound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Job skill not found");
    }
}
