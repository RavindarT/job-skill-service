CREATE TABLE IF NOT EXISTS job_skills (
    job_id UUID NOT NULL,
    code VARCHAR(60) NOT NULL,
    name VARCHAR(120) NOT NULL,
    proficiency VARCHAR(12) NOT NULL CHECK (proficiency IN ('BEGINNER', 'INTERMEDIATE', 'ADVANCED', 'EXPERT')),
    PRIMARY KEY (job_id, code)
);
