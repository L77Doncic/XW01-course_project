-- 添加一级菜单
INSERT INTO sys_menu VALUES('5', '交通管理', '0', '5', 'traffic',  NULL, '', '', 1, 0, 'M', '0', '0', '', 'monitor',  'admin', sysdate(), '', null, '交通管理目录');
INSERT INTO sys_menu VALUES('6', '信息服务', '0', '6', 'info',     NULL, '', '', 1, 0, 'M', '0', '0', '', 'guide',    'admin', sysdate(), '', null, '信息服务目录');

-- 添加车流量监控菜单
INSERT INTO sys_menu VALUES('200', '车流量监控', '5', '1', 'traffic', 'statistic/traffic/index', '', '', 1, 0, 'C', '0', '0', 'statistic:traffic:list', 'chart',  'admin', sysdate(), '', null, '车流量监控菜单');

-- 添加天气模块菜单
INSERT INTO sys_menu VALUES('201', '天气信息', '6', '1', 'weather', 'weather/info/index', '', '', 1, 0, 'C', '0', '0', 'weather:info:list', 'cloud', 'admin', sysdate(), '', null, '天气信息菜单');

-- 添加微博热搜菜单
INSERT INTO sys_menu VALUES('202', '微博热搜', '6', '2', 'weibo', 'weibo/hot/index', '', '', 1, 0, 'C', '0', '0', 'weibo:hot:list', 'list', 'admin', sysdate(), '', null, '微博热搜菜单');

-- 添加投诉管理菜单
INSERT INTO sys_menu VALUES('203', '投诉管理', '5', '2', 'complaints', 'complaints/info/index', '', '', 1, 0, 'C', '0', '0', 'complaints:info:list', 'message', 'admin', sysdate(), '', null, '投诉管理菜单');

-- 添加违法信息管理菜单
INSERT INTO sys_menu VALUES('204', '违法信息', '5', '3', 'violation', 'cardata/violation/index', '', '', 1, 0, 'C', '0', '0', 'cardata:violation:list', 'warning', 'admin', sysdate(), '', null, '违法信息菜单');

-- 添加车牌管理菜单
INSERT INTO sys_menu VALUES('205', '车牌管理', '5', '4', 'carcolor', 'carcolor/plate/index', '', '', 1, 0, 'C', '0', '0', 'carcolor:plate:list', 'car', 'admin', sysdate(), '', null, '车牌管理菜单'); 