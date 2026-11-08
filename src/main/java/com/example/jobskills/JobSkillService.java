package com.example.jobskills;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class JobSkillService {
    private final JobSkillRepository repository;
    public JobSkillService(JobSkillRepository repository) { this.repository = repository; }
    @Transactional(readOnly = true)
    public List<JobSkill> list() { return repository.findAll(); }
    @Transactional(readOnly = true)
    public JobSkill get(Long jobId, Long sId) { return repository.findById(new JobSkillId(jobId, sId)).orElseThrow(() -> new ResourceNotFoundException("JobSkill not found")); }
    @Transactional
    public JobSkill create(JobSkillRequest request) {
        if (repository.existsById(new JobSkillId(request.jobId(), request.sId()))) throw new DuplicateResourceException("JobSkill already exists");
        JobSkill record = new JobSkill();
        record.setId(new JobSkillId(request.jobId(), request.sId()));
        record.setYearsOfExp(request.yearsOfExp());
        return repository.save(record);
    }
    @Transactional
    public JobSkill update(Long jobId, Long sId, JobSkillRequest request) {
        JobSkill record = get(jobId, sId);
        record.setYearsOfExp(request.yearsOfExp());
        return repository.save(record);
    }
    @Transactional
    public void delete(Long jobId, Long sId) { repository.delete(get(jobId, sId)); }
}
