package com.example.jobskills;
import jakarta.validation.constraints.*;
public record JobSkillRequest(
    @NotNull @Positive Long jobId,
    @NotNull @Positive Long sId,
    @NotNull @Min(0) @Max(80) Integer yearsOfExp
) {}
