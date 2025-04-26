ALTER SEQUENCE "Role_id_seq" RESTART WITH 1;

INSERT INTO "Role" (name)
VALUES
    ('USER'),
    ('TEAM_LEAD'),
    ('LEAD_OF_LEADS'),
    ('CTO'),
    ('ADMIN');

ALTER SEQUENCE "RolePermission_id_seq" RESTART WITH 1;

INSERT INTO "RolePermission" (role_id, permission_id)
VALUES
    (1, 2),

    (2, 2),
    (2, 4),
    (2, 6),

    (3, 2),
    (3, 4),
    (3, 6),

    (4, 1),
    (4, 2),
    (4, 3),
    (4, 5),
    (4, 6),
    (4, 7),
    (4, 8),
    (4, 9),
    (4, 10),
    (4, 12),
    (4, 13),
    (4, 14),

    (5, 11);

--USER:
-- INTERACT_WITH_TASK

--TEAM LEAD:
-- INTERACT_WITH_TASK
-- VIEW_TEAM_PROFILES
-- COMMENT_ON_PROFILE

--LEAD OF LEADS:
-- INTERACT_WITH_TASK
-- VIEW_TEAM_PROFILES
-- COMMENT_ON_PROFILE

--CTO:
-- MANAGE_PERMISSIONS
-- MANAGE_TASK
-- MANAGE_TEMPLATE
-- APPROVE_PROMOTION
-- MANAGE_ROLES
-- INTERACT_WITH_TASK
-- VIEW_ALL_PROFILES
-- COMMENT_ON_PROFILE
-- REVIEW_EMPLOYEE
-- MANAGE_TEAMS
-- MANAGE_POSITIONS
-- MANAGE_REPORTS

--ADMIN:
-- MANAGE_USERS




