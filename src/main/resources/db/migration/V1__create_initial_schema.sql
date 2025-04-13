-- Create ENUM type for Role
CREATE TYPE "role_enum" AS ENUM ('Employee', 'Team Lead', 'Lead of Leads', 'Admin', 'CTO');

-- Create Users table
CREATE TABLE "Users" (
    "id" SERIAL PRIMARY KEY,
    "first_name" VARCHAR(255),
    "last_name" VARCHAR(255),
    "email" VARCHAR(255),
    "role" "role_enum"
);

-- Create TaskTemplate table
CREATE TABLE "TaskTemplate" (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(255),
    "description" TEXT,
    "task_requirements" TEXT
);

-- Create ENUM type for Task status
CREATE TYPE "task_status_enum" AS ENUM ('Todo', 'In Progress', 'In Review', 'Done');

-- Create Task table
CREATE TABLE "Task" (
    "id" SERIAL PRIMARY KEY,
    "title" VARCHAR(255),
    "description" TEXT,
    "status" "task_status_enum",
    "created_at" TIMESTAMPTZ,
    "updated_at" TIMESTAMPTZ,
    "assigned_to" INT,
    "template_id" INT,
    FOREIGN KEY ("assigned_to") REFERENCES "Users"("id"),
    FOREIGN KEY ("template_id") REFERENCES "TaskTemplate"("id")
);

-- Create TaskComment table
CREATE TABLE "TaskComment" (
    "id" SERIAL PRIMARY KEY,
    "message" TEXT,
    "created_at" TIMESTAMPTZ,
    "updated_at" TIMESTAMPTZ,
    "task_id" INT,
    "user_id" INT,
    "author_id" INT,
    FOREIGN KEY ("task_id") REFERENCES "Task"("id"),
    FOREIGN KEY ("user_id") REFERENCES "Users"("id"),
    FOREIGN KEY ("author_id") REFERENCES "Users"("id")
);

-- Create Review table
CREATE TABLE "Review" (
    "id" SERIAL PRIMARY KEY,
    "feedback" TEXT,
    "created_at" TIMESTAMPTZ,
    "updated_at" TIMESTAMPTZ,
    "user_id" INT,
    FOREIGN KEY ("user_id") REFERENCES "Users"("id")
);

-- Create OnDemandReport table
CREATE TABLE "OnDemandReport" (
    "id" SERIAL PRIMARY KEY,
    "data" TEXT,
    "created_at" TIMESTAMPTZ,
    "recipient_id" INT,
    FOREIGN KEY ("recipient_id") REFERENCES "Users"("id")
);

-- Create Notification table
CREATE TABLE "Notification" (
    "id" SERIAL PRIMARY KEY,
    "message" TEXT,
    "created_at" TIMESTAMPTZ,
    "recipient_id" INT,
    FOREIGN KEY ("recipient_id") REFERENCES "Users"("id")
);

-- Create ENUM type for Promotion status
CREATE TYPE "promotion_status_enum" AS ENUM ('Pending', 'Approved', 'Rejected');

-- Create PromotionRequest table
CREATE TABLE "PromotionRequest" (
    "id" SERIAL PRIMARY KEY,
    "status" "promotion_status_enum",
    "created_at" TIMESTAMPTZ,
    "updated_at" TIMESTAMPTZ,
    "user_id" INT,
    FOREIGN KEY ("user_id") REFERENCES "Users"("id")
);

-- Create Team table
CREATE TABLE "Team" (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(255),
    "lead_id" INT,
    FOREIGN KEY ("lead_id") REFERENCES "Users"("id")
);

-- Create TeamMembership table
CREATE TABLE "TeamMembership" (
    "id" SERIAL PRIMARY KEY,
    "user_id" INT,
    "team_id" INT,
    FOREIGN KEY ("user_id") REFERENCES "Users"("id"),
    FOREIGN KEY ("team_id") REFERENCES "Team"("id")
);

-- Create Position table
CREATE TABLE "Position" (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(255)
);

-- Create UserPosition table
CREATE TABLE "UserPosition" (
    "id" SERIAL PRIMARY KEY,
    "level" INT,
    "user_id" INT,
    "position_id" INT,
    FOREIGN KEY ("user_id") REFERENCES "Users"("id"),
    FOREIGN KEY ("position_id") REFERENCES "Position"("id")
);

-- Create ReportSnapshot table
CREATE TABLE "ReportSnapshot" (
    "id" SERIAL PRIMARY KEY,
    "created_at" TIMESTAMPTZ,
    "report_data" TEXT
);
