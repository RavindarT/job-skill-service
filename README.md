# Job Skill Service

A Spring Boot REST API for tracking required job skills and proficiency. Use the job UUID returned by Job Service. The skill service stores those UUIDs as references and accepts them without calling Job Service to verify them.

## Build and run

Use JDK 21 or later and Maven 3.6.3 or later.

```sh
mvn clean package
java -jar target/job-skill-service-0.0.1-SNAPSHOT.jar
```

The API listens at `http://127.0.0.1:8083` so it can run alongside Job Service on port 8082. It stores records in a local H2 database under `data/` and has no authentication.

## API

| Method | Path | Behavior |
| --- | --- | --- |
| GET | /api/jobs/{jobId}/skills | List skills for a job |
| GET | /api/jobs/{jobId}/skills/{code} | Read a skill |
| PUT | /api/jobs/{jobId}/skills/{code} | Add or replace a skill |
| DELETE | /api/jobs/{jobId}/skills/{code} | Remove a skill |

For PUT, set `Content-Type: application/json` and send a body such as `{"name":"Spring Boot","proficiency":"ADVANCED"}`. Use a code such as `spring-boot`. Codes are case-insensitive and accept up to 60 letters, digits, and single separating hyphens. Proficiency is one of `BEGINNER`, `INTERMEDIATE`, `ADVANCED`, or `EXPERT`.

GET and PUT return 200. DELETE returns 204. Invalid inputs return 400, and reading or deleting a missing skill returns 404.

## Upload to GitHub

Extract the ZIP and upload its contents, including `.gitignore`, to the repository root.
