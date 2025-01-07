BEGIN;
CREATE TABLE IF NOT EXISTS student(
    id SERIAL PRIMARY KEY,
    student_name TEXT NOT NULL,
    student_group TEXT NOT NULL,
    student_course INTEGER NOT NULL
);
COMMIT;