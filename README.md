Se necesita cargar manualmente las siguientes tablas despues de correr el proy
INSERT INTO `hospital_db`.`doctor` (`id`, `first_name`, `last_name`, `specialty`) 
VALUES 
  ('4', 'Karla', 'Alvarado', 'Doctora General'),
  ('5', 'Karla', 'Alvarado', 'Doctora General');


  INSERT INTO `hospital_db`.`clinic` (`id`, `floor`, `room_number`) 
VALUES 
    ('4', '10', '1422'),
    ('5', '14', '1507');
Una vez compilado se pueden ir a la ruta http://localhost:8080/appointments/new para insertar, consultar , eliminar,etc
