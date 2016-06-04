DELETE FROM menu_groups;
UPDATE menus SET menu_parent = null;
DELETE FROM menus;

INSERT INTO menus (id, node_text, icon_cls, version, view_xtype, menu_parent) VALUES
(1, 'home', 'fa fa-home fa-lg fa-icon-triton', 0, NULL, NULL),
(2, 'dashboard', 'xf0e4', 0, 'content-dashboard', 1),
(3, 'menu1', 'fa fa-group fa-lg fa-icon-triton', 0, NULL, NULL),
(4, 'menu11', 'xf0c0', 0, 'content-groups-permissions', 3),
(5, 'menu12', 'xf007', 0, 'content-users', 3),
(6, 'staticData', 'fa fa-database fa-lg fa-icon-triton', 0, NULL, NULL),
(7, 'actors', 'xf005', 0, 'content-actors', 6),
(8, 'categories', 'xf013', 0, 'content-categories', 6),
(9, 'languages', 'xf1ab', 0, 'content-languages', 6),
(10, 'cities', 'xf018', 0, 'content-cities', 6),
(11, 'countries', 'xf0ac', 0, 'content-countries', 6),
(12, 'cms', 'fa fa-film fa-lg fa-icon-triton', 0, NULL, NULL),
(13, 'films', 'xf1c8', 0, 'content-films', 12),
(14, 'reports', 'fa fa-line-chart fa-lg fa-icon-triton', 0, NULL, NULL),
(15, 'salesfilmcategory', 'xf200', 0, 'content-report-sales', 14);

INSERT INTO menu_groups (menu_id, group_id) VALUES
(1,1), (2,1), (3,1), (4,1), (5,1), (6,1), (7,1), (8,1), (9,1), (10,1), (11,1), (12,1), (13,1), (14,1), (15,1);