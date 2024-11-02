INSERT INTO influencer (name, img_url, job)
VALUES ('Influencer 1', 'imgUrl1', 'job1'),
       ('Influencer 2', 'imgUrl2', 'job2'),
       ('Influencer 3', 'imgUrl3', 'job3'),
       ('Influencer 4', 'imgUrl4', 'job4'),
       ('Influencer 5', 'imgUrl5', 'job5'),
       ('Influencer 6', 'imgUrl6', 'job6'),
       ('Influencer 7', 'imgUrl7', 'job7'),
       ('Influencer 8', 'imgUrl8', 'job8'),
       ('Influencer 9', 'imgUrl9', 'job9'),
       ('Influencer 10', 'imgUrl10', 'job10');

INSERT INTO inplace.places (menu_updated_at, address1, address2, address3, name, facility, latitude, longitude,
                            menu_img_url, category)
VALUES ('2024-03-28 05:30:00.000000', 'Address 1', 'Address 2', 'Address 3', 'Place 1',
        '{"wifi": true, "pet": false, "parking": false, "forDisabled": true, "nursery": false, "smokingRoom": false}',
        10.0, 10.0, 'menuImg.url', 'CAFE'),
       ('2024-03-28 05:30:00.000000', 'Address 1', 'Address 2', 'Address 3', 'Place 2',
        '{"wifi": true, "pet": false, "parking": false, "forDisabled": true, "nursery": false, "smokingRoom": false}',
        50.0, 10.0, 'menuImg.url', 'JAPANESE'),
       ('2024-03-28 05:30:00.000000', 'Address 1', 'Address 2', 'Address 3', 'Place 3',
        '{"wifi": true, "pet": false, "parking": false, "forDisabled": true, "nursery": false, "smokingRoom": false}',
        100.0, 10.0, 'menuImg.url', 'CAFE'),
       ('2024-03-28 05:30:00.000000', 'Address 1', 'Address 2', 'Address 3', 'Place 4',
        '{"wifi": true, "pet": false, "parking": false, "forDisabled": true, "nursery": false, "smokingRoom": false}',
        50.0, 50.0, 'menuImg.url', 'JAPANESE');
