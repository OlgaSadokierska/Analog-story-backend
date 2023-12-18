
-- insert  account type table
INSERT INTO account_type (name_type) VALUES
    ('administrator'),
    ('client'),
    ('employee');
-- insert  user
INSERT INTO user (first_name, last_name, email, login, password, phone, account_type_id)
VALUES
    ('Ola','Poneta','Ola@wp.pl','Ola','$2a$10$Utol7CQ4xsUVR8dIP34lA.rZ8BFVSRtp.HSRgIPoo9BZOHaKlmfHi','456789123',1),
    ('Olga','Sadokierska','Olga@wp.pl','Olga','$2a$10$1IBxHrDWJFQB0MW9L/1cN.VxDgnZwfxI.OGADl0QBtEeUa4ZAWsHi','147852369',1),
    ('Iza','Najder','iza@wp.pl','Iza','$2a$10$qEM4jofX2wYJTT.rljTq.ekftbquXLSLz.PojwnFtPMUngzUW9eDi','123456789',1),
    ('Jan', 'Kowalski', 'jan.kowalski@gamil.com', 'jan.kowalski', '$2a$10$sB4twg9dtgQTd9hcfWnQtOG8k3YIWdPTRqqBW5lmZz54VAMwI6Yhy', '123456789', 2),
    ('Anna', 'Nowak', 'anna.nowak@gamil.com', 'anna.nowak', '$2a$10$Zg0qtxfWdutZYiF1U0/XD.ZLLWvLkve7rqqR/X.rNSOcWCMiWMd5y', '987654321', 2),
    ('Piotr', 'Wiśniewski', 'piotr.wisniewski@gmail.com', 'piotr.wisniewski', '$2a$10$SDoaS8RTb2IB/YMcqtGzpO0Cw7/peLUWIu7iQLCUUgGXWkIk8PL4O', '555666777', 2),
    ('Alicja', 'Dąbrowska', 'alicja.dabrowska@gmail.com', 'alicja.dabrowska', '$2a$10$.gbjkNegdLvsxsrajWmh/u9NHZOikM0hMIcErck2PclvaPHBjbXuC', '999888777', 2),
    ('Marcin', 'Lewandowski', 'marcin.lewandowski@gmail.com', 'marcin.lewandowski', '$2a$10$yYdPrJNY5DUFLtGY2oqXnOEoSaWusg90xQnDOE0W9MzlOX54UQeNS', '111223344', 2),
    ('Karolina', 'Wójcik', 'karolina.wojcik@example.com', 'karolina.wojcik', '$2a$10$idAj10/1J9UndneNPpumNeV8Bw1TTrGCjF4k9YRBvGafA4sf7fE02', '555666999', 2),
    ('Michał', 'Kamiński', 'michal.kaminski@example.com', 'michal.kaminski', '$2a$10$ZMZ5HptwK0xiYL7fdjnctuI0ih.TIxcZB.SZ/RLqNFhC80ObFTn3u', '777888999', 2),
    ('Magdalena', 'Jankowska', 'magdalena.jankowska@example.com', 'magdalena.jankowska', '$2a$10$O1.HbxbcOnz1Dp3NjjG/3erQPrGvJOpZb/TodWMZK.UE1Uor8APt.', '111222333', 2),
    ('Artur', 'Szymański', 'artur.szymanski@example.com', 'artur.szymanski', '$2a$10$fBcpZdTwz9l0z5nsSzHWMe3qC/YJ5ummmmPiRqBRvpVsli9vJDwY2', '444555666', 2),
    ('Dominika', 'Witkowska', 'dominika.witkowska@example.com', 'dominika.witkowska', '$2a$10$7bqGLkfm9vaAbF4LjbL81eOKFMKnqcnG8G/27IeqWBfNQSP0LeRBi', '999888777', 2),
    ('Kamil', 'Kowalczyk', 'kamil.kowalczyk@example.com', 'kamil.kowalczyk', '$2a$10$GPJVeFDDSqnOx7Bbx.q3nOUg.peJGMsTzMrtgmWtJJXq3yoMUFYp6', '333222111', 2),
    ('Natalia', 'Mazur', 'natalia.mazur@example.com', 'natalia.mazur', '$2a$10$GPJVeFDDSqnOx7Bbx.q3nOUg.peJGMsTzMrtgmWtJJXq3yoMUFYp6', '666555444', 2),
    ('Damian', 'Piotrowski', 'damian.piotrowski@example.com', 'damian.piotrowski', '$2a$10$cJ7UiExqrMbgbqLB7MmaJ.iJQDT2BP/b263xRgc7OEx.YkEc47y5a', '777888999', 2),
    ('Maja', 'Górska', 'maja.gorska@example.com', 'maja.gorska', '$2a$10$wSED60TQuxR/ySG88uZ1oOiEyYg0Fe2a2VA9jiEnk7B6kvB534G.C', '111222333', 2),
    ('Bartosz', 'Włodarczyk', 'bartosz.wlodarczyk@example.com', 'bartosz.wlodarczyk', '$2a$10$rQv16VcF1kZ8/w02s9JzBODK.jRM8TWRb1gXamp2nqa4ImNZIY8Be', '444555666', 2),
    ('Jan', 'Kowalski', 'jan.kowalski@example.com', 'jan.kowalski', '$2a$10$BBs/rON0yNxSA5436xoBbOkCouJrWsvTXsTQm78N5cl9H81CJ7oC2', '123456789', 3),
    ('Anna', 'Nowak', 'anna.nowak@example.com', 'anna.nowak', '$2a$10$kDJQD2p.zPthyVk6uzjoGuLfiAU7aWAeRcbtSz0vzIbRmMx/vLt.m', '987654321', 3),
    ('Piotr', 'Wiśniewski', 'piotr.wisniewski@example.com', 'piotr.wisniewski', '$2a$10$Euq3kiXh42RS7MMU7wUE8eX8trOZCmtpUKF/ZqkgF8BgCIzZP.qGe', '555666777', 3),
    ('Monika', 'Kaczmarek', 'monika.kaczmarek@example.com', 'monika.kaczmarek', '$2a$10$9g1PhIzrcCObiy2klJADweZ3WbAciysyVpHCxB/q2bKvlWBJCM5XC', '123789456', 3),
    ('Grzegorz', 'Lis', 'grzegorz.lis@example.com', 'grzegorz.lis', '$2a$10$wJMFIFRYj2CvTktsvQj8buq10R79AJe0wr9zHHksuoGUgzTb0M8Jq', '987321654', 3);
-- insert ProductType
INSERT INTO product_type (name_type_product) VALUES
    ('Camera'),
    ('Film'),
    ('Lens'),
    ('Trivet'),
    ('Case'),
    ('Flash');
-- insert Product
INSERT INTO product (product_type_id, description, price) VALUES
    (1, 'Aparat Horyzont horizont panoramiczny', 650.00),
    (1, 'Auto Flash aparat fotograficzny', 150.55),
    (1,'Praktycznie jak nowy aparat analogowy ELIKON 35mm wraz oryginalnym futerale w ładnym stanie',120.45),
    (1, 'Aparat cyfrowy marki XYZ', 258.78),
    (1, 'Lustrzanka analogowa w dobrym stanie', 147.45),
    (1, 'Kompaktowy aparat do fotografii podróżniczej', 159.12),
    (1, 'Klasyczny aparat analogowy', 236.56),
    (1, 'Antyczny aparat z lat 60.', 74.45),
    (1, 'Kieszonkowy aparat na klisze', 45.45),
    (1, 'Nowoczesny aparat cyfrowy z wieloma funkcjami', 199.99),
    (1, 'Profesjonalny aparat do fotografii studyjnej', 159.78),
    (1, 'Retro aparat z klapką', 145.55),
    (1, 'Klasyczny aparat analogowy', 147.45),
    (1, 'Aparat do fotografii krajobrazowej', 45.78),
    (1, 'Lustrzanka cyfrowa dla zaawansowanych', 147.74),
    (1, 'Aparat do fotografii ulicznej', 45.45),
    (1, 'Aparat kompaktowy do codziennego użytku', 65.15),
    (2,'Klisza bez żadnych nagrań.',12.15),
    (2,'Klisza z ujęciami pięknych widoków na morze i plażę',5.18),
    (2,'Klisza bez żadnych nagrań',47.45),
    (2,'Klisza bez żadnych nagrań',12.25),
    (2,'Klisza bez żadnych nagrań',14.15),
    (2,'Klisza bez żadnych nagrań',12.15),
    (2,'Klisza bez żadnych nagrań',5.6),
    (2,'Klisza bez żadnych nagrań',14.15),
    (2,'Klisza z zapisem koncertu ulubionego zespołu',10.99),
    (2,'Klisza z ujęciami z egzotycznych podróży i miejsc.',11.11),
    (2,'Klisza z zapisem emocjonujących wydarzeń sportowych.',14.17),
    (2,'Klisza z zapisem emocjonujących wydarzeń sportowych.',13.15),
    (2,'Klisza zawierająca eksperymentalne ujęcia artystyczne',50.6),
    (2,'Klisza z ujęciami związanych z nowoczesną technologią.',12.99),
    (3, 'Obiektyw Model A', 150.00),
    (3, 'Obiektyw Model B', 200.00),
    (3, 'Zoom Lens XYZ', 300.00),
    (3, 'Obiektyw stałoogniskowy 50mm', 120.00),
    (3, 'Obiektyw szerokokątny', 250.00),
    (4, 'Drewniany podstawek', 20.00),
    (4, 'Metalowy podstawek', 15.00),
    (4, 'Dekoracyjny podstawek', 25.00),
    (4, 'Podstawek odporny na ciepło', 18.00),
    (4, 'Silikonowy podstawek', 12.00),
    (5, 'Skórzane etui na aparat', 40.00),
    (5, 'Etui o twardej powłoce', 35.00),
    (5, 'Miękkie, wyściełane etui', 30.00),
    (5, 'Wodoodporne etui', 45.00),
    (5, 'Kompaktowe etui na aparat', 25.00),
    (6, 'Zewnętrzna lampa błyskowa Model X', 80.00),
    (6, 'Zestaw lamp błyskowych bezprzewodowych', 120.00),
    (6, 'Lampa błyskowa LED do aparatu', 65.00),
    (6, 'Kompaktowa jednostka błyskowa', 50.00),
    (6, 'Lampa studyjna', 150.00);
-- insert Camera
INSERT INTO camera (user_id, model, brand, film_loaded, is_for_sale, product_id) VALUES
    (4, 'Model123', 'BrandXYZ', true, false, 1),
    (4,'Instax mini','Instax',false,true,2),
    (5,'ELIKON 35mm','ELIKON ',false,true,3),
    (5,'ZSE158','Canon',true,false,4),
    (5,'WWER34','Nikon',true,false,5),
    (6,'3TRE','Polaroid',true,false,6),
    (6,'45FGHN','Polaroid',true,false,7),
    (6,'ASDW3','Canon',false,false,8),
    (6,'WER4','Nikon',false,false,9),
    (7,'EHU78','Nikon',false,false,10),
    (8,'7U7','Polaroid',true,false,11),
    (9,'JHT5','Polaroid',true,false,12),
    (9,'AQW34','Canon',false,false,13),
    (10,'FGHY56','Nikon',false,true,14),
    (10,'FG4','Polaroid',false,true,15),
    (10,'TY6','Canon',true,false,16);
-- insert Films
INSERT INTO film (id_camera, loaded_frames, is_full, id_produktu, is_for_sale, user_id) VALUE
   (1,25,false,17,false,4),
    (16,30,true,18,true,10),
    (null,0,false,19,true, null),
    (null,0,false,20,true,null),
    (null,0,false,21,true,null),
    (null,0,false,22,true,null),
    (null,0,false,23,true,null),
    (null,0,false,24,true,null),
    (10,30,true,25,true,7),
    (4,30,true,26,false,5),
    (9,15,false,27,true,6),
    (11,5,false,28,false,8),
    (14,12,false,29,false,10),
    (3,30,true,30,true,5);
-- insert reservation
INSERT INTO reservation (user_id, product_id, reservation_date, expiration_date) values
       (18,2,'2023-11-28 12:00:00', '2023-12-05 12:00:00'),
       (17,3,'2023-10-28 13:13:45', '2023-12-30 1:00:00'),
       (12,13,'2023-09-17 09:09:45', '2023-12-1 1:00:00'),
       (17,14,'2023-08-28 14:32:14', '2023-11-30 12:00:00'),
       (11,19,'2023-11-01 08:23:01', '2023-12-01 00:00:00'),
       (16,23,'2023-11-15 04:46:00', '2023-12-04 12:00:00'),
       (8,3,'2023-11-17 13:00:00', '2023-12-30 14:05:00'),
       (5,20,'2023-10-12 12:14:00', '2023-12-05 17:05:00');






