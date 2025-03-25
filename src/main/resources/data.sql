-- Event Types
INSERT INTO event_type (name) VALUES ('SCAN');
INSERT INTO event_type (name) VALUES ('MOVE');
INSERT INTO event_type (name) VALUES ('OPEN');
INSERT INTO event_type (name) VALUES ('CLOSE');
INSERT INTO event_type (name) VALUES ('ERROR');

-- Location Sites
INSERT INTO location_site (id, name, address) VALUES (1, 'Entrepôt Nord', 'Zone Industrielle, Cavaillon');
INSERT INTO location_site (id, name, address) VALUES (2, 'Hub Sud', 'Rue des Transports, Marseille');

-- Containers
INSERT INTO container (id, code, status) VALUES (1, 'CNT-001', 'ACTIVE');
INSERT INTO container (id, code, status) VALUES (2, 'CNT-002', 'ACTIVE');

-- Events
INSERT INTO tracking_event (id, container_id, type_name, timestamp, location_id, metadata)
VALUES (1, 1, 'SCAN', '2025-03-25T08:00:00', 1, 'Première entrée');

INSERT INTO tracking_event (id, container_id, type_name, timestamp, location_id, metadata)
VALUES (2, 1, 'MOVE', '2025-03-25T10:00:00', 2, 'Transfert vers hub sud');

INSERT INTO tracking_event (id, container_id, type_name, timestamp, location_id, metadata)
VALUES (3, 2, 'ERROR', '2025-03-25T09:30:00', null, 'Problème de fermeture détecté');
