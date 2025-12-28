
insert into floor(id, name, level) values (1, 'Ground', 0);
insert into floor(id, name, level) values (2, 'Level 1', 1);

insert into zone(id, name, floor_id) values (1, 'A', 1);
insert into zone(id, name, floor_id) values (2, 'B', 2);

-- Some spots
insert into parking_spot(id, spot_code, spot_type, status, reserved, floor_id, zone_id, last_status_change)
values (1, 'G-A-001', 'SMALL', 'AVAILABLE', false, 1, 1, CURRENT_TIMESTAMP());
insert into parking_spot(id, spot_code, spot_type, status, reserved, floor_id, zone_id, last_status_change)
values (2, 'G-A-002', 'MEDIUM', 'AVAILABLE', false, 1, 1, CURRENT_TIMESTAMP());
insert into parking_spot(id, spot_code, spot_type, status, reserved, floor_id, zone_id, last_status_change)
values (3, 'L1-B-001', 'LARGE', 'AVAILABLE', false, 2, 2, CURRENT_TIMESTAMP());

-- Rate plan and rules (illustrative)
insert into rate_plan(id, name, active) values (1, 'Default', true);

insert into rate_rule(id, rate_plan_id, vehicle_type, unit, base_cents, per_unit_cents, grace_minutes, max_daily_cents, weekend_only)
values (1, 1, 'MOTORCYCLE', 'PER_30_MIN', 1000, 500, 10, 6000, false);
insert into rate_rule(id, rate_plan_id, vehicle_type, unit, base_cents, per_unit_cents, grace_minutes, max_daily_cents, weekend_only)
values (2, 1, 'CAR', 'PER_HOUR', 3000, 1500, 15, 15000, false);
insert into rate_rule(id, rate_plan_id, vehicle_type, unit, base_cents, per_unit_cents, grace_minutes, max_daily_cents, weekend_only)
values (3, 1, 'BUS', 'PER_HOUR', 5000, 3000, 15, 25000, false);
