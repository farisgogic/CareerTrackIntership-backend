INSERT INTO Role (name)
VALUES
    ('USER'),
    ('TEAM_LEAD'),
    ('LEAD_OF_LEADS'),
    ('CTO'),
    ('ADMIN')
ON CONFLICT DO NOTHING;

INSERT INTO Permission (name)
VALUES
    ('MANAGE_TASK'),
    ('INTERACT_WITH_TASK'),
    ('MANAGE_TEMPLATE'),
    ('VIEW_TEAM_PROFILES'),
    ('VIEW_ALL_PROFILES'),
    ('COMMENT_ON_PROFILE'),
    ('REVIEW_EMPLOYEE'),
    ('APPROVE_PROMOTION'),
    ('MANAGE_ROLES'),
    ('MANAGE_PERMISSIONS'),
    ('MANAGE_USERS'),
    ('MANAGE_TEAMS'),
    ('MANAGE_POSITIONS'),
    ('MANAGE_REPORTS')
ON CONFLICT DO NOTHING;

INSERT INTO RolePermission (role_id, permission_id)
VALUES
    (1, 2),
    (2, 2), (2, 4), (2, 6),
    (3, 2), (3, 4), (3, 6),
    (4, 1), (4, 2), (4, 3), (4, 5), (4, 6), (4, 7), (4, 8), (4, 9), (4, 10), (4, 12), (4, 13), (4, 14),
    (5, 11)
ON CONFLICT DO NOTHING;

INSERT INTO Users (first_name, last_name, email, role_id) VALUES
('Faris', 'Gogić', 'farisgogic1@gmail.com', 1),
('Eldin', 'Popara', 'popara.eldin@gmail.com', 1),
('Nermin', 'Obućina', 'nermin_obucina@hotmail.com', 1),
('Dino', 'Alić', 'dino.alic@stu.ssst.edu.ba', 1),
('Deni', 'Alić', 'deni.alic@stu.ssst.edu.ba', 1),
('Jasmin', 'Dudić', 'jasmin.dudic@bloomteq.com', 2),
('Elvir', 'Vlahovljak', 'elvir.vlahovljak@bloomteq.com', 2),
('Hani', 'Zahirović', 'hani@bloomteq.com', 4),
('Admin', 'Admin', 'admin@bloomteq.com', 5)
ON CONFLICT DO NOTHING;

INSERT INTO TaskTemplate (name, description, task_requirements) VALUES
('Task Template 1', 'Description for Task Template 1', 'Requirement 1, Requirement 2'),
('Task Template 2', 'Description for Task Template 2', 'Requirement 3, Requirement 4')
ON CONFLICT DO NOTHING;

INSERT INTO Task (title, description, status, created_at, updated_at, assigned_to, template_id) VALUES
('Task 1', 'Description of Task 1', 'Todo', NOW(), NOW(), 1, 1),
('Task 2', 'Description of Task 2', 'In Progress', NOW(), NOW(), 2, 2),
('Task 3', 'Description of Task 3', 'In Review', NOW(), NOW(), 3, 1),
('Task 4', 'Description of Task 4', 'In Review', NOW(), NOW(), 4, 1),
('Task 5', 'Description of Task 5', 'Todo', NOW(), NOW(), 5, 2),
('Task 6', 'Description of Task 6', 'In Review', NOW(), NOW(), 2, 1),
('Task 7', 'Description of Task 7', 'In Progress', NOW(), NOW(), 3, 1),
('Task 8', 'Description of Task 8', 'In Review', NOW(), NOW(), 1, 2)
ON CONFLICT DO NOTHING;

INSERT INTO TaskComment (message, created_at, updated_at, task_id, user_id, author_id) VALUES
('This is a comment on Task 1', NOW(), NOW(), 1, 1, 2),
('This is a comment on Task 2', NOW(), NOW(), 2, 2, 3)
ON CONFLICT DO NOTHING;

INSERT INTO Review (feedback, created_at, updated_at, user_id) VALUES
('Good performance', NOW(), NOW(), 1),
('Needs improvement', NOW(), NOW(), 2)
ON CONFLICT DO NOTHING;

INSERT INTO OnDemandReport (data, created_at, recipient_id) VALUES
('Report data for User 1', NOW(), 1),
('Report data for User 2', NOW(), 2)
ON CONFLICT DO NOTHING;

INSERT INTO Notification (message, created_at, recipient_id) VALUES
('You have a new task assigned', NOW(), 1),
('Your task status has been updated', NOW(), 2)
ON CONFLICT DO NOTHING;

INSERT INTO PromotionStatus (name) VALUES ('PENDING'), ('APPROVED'), ('REJECTED')
ON CONFLICT DO NOTHING;

INSERT INTO PromotionRequest (status_id, created_at, updated_at, user_id) VALUES
((SELECT id FROM PromotionStatus WHERE name = 'PENDING'), NOW(), NOW(), 1),
((SELECT id FROM PromotionStatus WHERE name = 'APPROVED'), NOW(), NOW(), 2)
ON CONFLICT DO NOTHING;

INSERT INTO Team (name, lead_id) VALUES
('Team 1', 6),
('Team 2', 7)
ON CONFLICT DO NOTHING;

INSERT INTO TeamMembership (user_id, team_id) VALUES
(1, 1),
(2, 1),
(3, 2),
(4, 2),
(5, 2)
ON CONFLICT DO NOTHING;

INSERT INTO Position (name) VALUES
('AI Engineer'),
('Full Stack Developer'),
('Data Scientist'),
('Product Manager'),
('UX/UI Designer'),
('DevOps Engineer'),
('Cloud Architect'),
('Quality Assurance Engineer'),
('Mobile App Developer'),
('Business Analyst')
ON CONFLICT DO NOTHING;

INSERT INTO UserPosition (level, user_id, position_id) VALUES
(1, 1, 1),
(2, 2, 2),
(3, 3, 3),
(2, 4, 4),
(3, 5, 5)
ON CONFLICT DO NOTHING;

INSERT INTO ReportSnapshot (created_at, report_data) VALUES
(NOW(), 'Snapshot data for report 1'),
(NOW(), 'Snapshot data for report 2')
ON CONFLICT DO NOTHING;