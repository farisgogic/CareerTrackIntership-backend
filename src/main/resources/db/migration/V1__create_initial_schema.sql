-- Create table for Roles
CREATE TABLE "Role" (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  name VARCHAR UNIQUE NOT NULL
);

-- Create table for Permissions
CREATE TABLE "Permission" (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  name VARCHAR UNIQUE NOT NULL
);

-- Create table for Role-Permission relationship
CREATE TABLE "RolePermission" (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  role_id INT REFERENCES "Role"(id),
  permission_id INT REFERENCES "Permission"(id)
);

-- Create User table
CREATE TABLE "User" (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  first_name VARCHAR,
  last_name VARCHAR,
  email VARCHAR UNIQUE NOT NULL,
  role_id INT REFERENCES "Role"(id)
);

-- Create TaskTemplate table
CREATE TABLE "TaskTemplate" (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  name VARCHAR,
  description VARCHAR,
  task_requirements VARCHAR
);

-- Create Task table
CREATE TABLE "Task" (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  title VARCHAR,
  description VARCHAR,
  status VARCHAR,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  assigned_to INT REFERENCES "User"(id),
  template_id INT REFERENCES "TaskTemplate"(id)
);

-- Create TaskComment table
CREATE TABLE "TaskComment" (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  message VARCHAR,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  task_id INT REFERENCES "Task"(id),
  user_id INT REFERENCES "User"(id),
  author_id INT REFERENCES "User"(id)
);

-- Create Review table
CREATE TABLE "Review" (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  feedback VARCHAR,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  user_id INT REFERENCES "User"(id)
);

-- Create OnDemandReport table
CREATE TABLE "OnDemandReport" (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  data VARCHAR,
  created_at TIMESTAMP,
  recipient_id INT REFERENCES "User"(id)
);

-- Create Notification table
CREATE TABLE "Notification" (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  message VARCHAR,
  created_at TIMESTAMP,
  recipient_id INT REFERENCES "User"(id)
);

-- Create PromotionStatus table
CREATE TABLE "PromotionStatus" (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  name VARCHAR UNIQUE NOT NULL
);

-- Create PromotionRequest table
CREATE TABLE "PromotionRequest" (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  status_id INT REFERENCES "PromotionStatus"(id),
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  user_id INT REFERENCES "User"(id)
);

-- Create Team table
CREATE TABLE "Team" (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  name VARCHAR,
  lead_id INT REFERENCES "User"(id)
);

-- Create TeamMembership table
CREATE TABLE "TeamMembership" (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  user_id INT REFERENCES "User"(id),
  team_id INT REFERENCES "Team"(id)
);

-- Create Position table
CREATE TABLE "Position" (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  name VARCHAR
);

-- Create UserPosition table
CREATE TABLE "UserPosition" (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  level INT,
  user_id INT REFERENCES "User"(id),
  position_id INT REFERENCES "Position"(id)
);

-- Create ReportSnapshot table
CREATE TABLE "ReportSnapshot" (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  created_at TIMESTAMP,
  report_data TEXT
);