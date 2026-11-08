package com.example.jobskills;
import java.net.URI;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/job-skills")
public class JobSkillController {
    private final JobSkillService service;
    public JobSkillController(JobSkillService service) { this.service = service; }
    @GetMapping public List<JobSkill> list() { return service.list(); }
    @GetMapping("/{jobId}/{sId}") public JobSkill get(@PathVariable Long jobId, @PathVariable Long sId) { return service.get(jobId, sId); }
    @PostMapping public ResponseEntity<JobSkill> create(@Valid @RequestBody JobSkillRequest request) {
        JobSkill created = service.create(request);
        return ResponseEntity.created(URI.create("/api/job-skills/" + created.getId().getJobId() + "/" + created.getId().getSId())).body(created);
    }
    @PutMapping("/{jobId}/{sId}") public JobSkill update(@PathVariable Long jobId, @PathVariable Long sId, @Valid @RequestBody JobSkillRequest request) { return service.update(jobId, sId, request); }
    @DeleteMapping("/{jobId}/{sId}") public ResponseEntity<Void> delete(@PathVariable Long jobId, @PathVariable Long sId) {
        service.delete(jobId, sId);
        return ResponseEntity.noContent().build();
    }
}
