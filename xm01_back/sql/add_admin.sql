-- 1. Add user information to sys_user table
-- user_id=3, dept_id=103(R&D Department), username=admin2, nickname=Admin2
-- 密码为123456的BCrypt加密值
INSERT INTO sys_user 
(user_id, dept_id, user_name, nick_name, user_type, email, phonenumber, sex, avatar, password, status, del_flag, login_ip, login_date, create_by, create_time, remark) 
VALUES 
(3, 103, 'admin2', 'Admin2', '00', 'admin2@example.com', '13800138002', '0', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), 'admin', sysdate(), 'Admin2');

-- 2. Set user role association (role_id=1 is super admin)
INSERT INTO sys_user_role 
(user_id, role_id) 
VALUES 
(3, 1);

-- 3. Set user post association (post_id=1 is chairman)
INSERT INTO sys_user_post 
(user_id, post_id) 
VALUES 
(3, 1); 