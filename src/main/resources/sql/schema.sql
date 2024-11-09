create table influencer
(
    id      bigint auto_increment
        primary key,
    job     varchar(20) not null,
    name    varchar(30) not null,
    img_url text        not null
);

create table places
(
    id              bigint auto_increment
        primary key,
    menu_updated_at datetime(6)                                                          null,
    address1        varchar(50)                                                          not null,
    address2        varchar(50)                                                          not null,
    address3        varchar(50)                                                          not null,
    name            varchar(50)                                                          not null,
    facility        varchar(255)                                                         null,
    latitude        text                                                                 not null,
    longitude       text                                                                 not null,
    menu_img_url    text                                                                 null,
    category        enum ('CAFE', 'JAPANESE', 'KOREAN', 'NONE', 'RESTAURANT', 'WESTERN') not null
);

create table places_menuboardphotourl_list
(
    places_id              bigint       not null,
    menuboardphotourl_list varchar(255) null,
    constraint FKtmhlcak5ftr3ci4lkvj3u9hi4
        foreign key (places_id) references inplace.places (id) ON DELETE CASCADE
);

create table places_menus
(
    recommend    bit default b'0' not null,
    places_id    bigint           not null,
    menu_name    varchar(50)      not null,
    description  varchar(255)     null,
    menu_img_url varchar(255)     null,
    price        varchar(255)     null,
    constraint FK25iaekuierywsqcdisrunisvv
        foreign key (places_id) references inplace.places (id) ON DELETE CASCADE
);

create table places_off_days
(
    places_id          bigint       not null,
    holiday_name       varchar(255) null,
    temporary_holidays varchar(255) null,
    week_and_day       varchar(255) null,
    constraint FKpepqysie7r3tcu8s0lmc491s2
        foreign key (places_id) references inplace.places (id) ON DELETE CASCADE
);

create table places_open_periods
(
    places_id   bigint       not null,
    day_of_week varchar(255) null,
    time_name   varchar(255) null,
    timese      varchar(255) null,
    constraint FKra4w0ld7a59n8xaq03ig8kckl
        foreign key (places_id) references inplace.places (id) ON DELETE CASCADE
);

create table user
(
    id        bigint auto_increment
        primary key,
    nickname  varchar(255)                         null,
    password  varchar(255)                         null,
    username  varchar(255)                         not null,
    role      enum ('ADMIN', 'FIRST_USER', 'USER') null,
    user_type enum ('KAKAO')                       null
);

create table favorite_influencer
(
    is_liked      bit    null,
    id            bigint auto_increment
        primary key,
    influencer_id bigint null,
    user_id       bigint null,
    constraint FKnj1e0rj7131hw37gl1aakjvf7
        foreign key (user_id) references inplace.user (id),
    constraint FKtqhnxc6yju6tpxvfohmj1fk12
        foreign key (influencer_id) references inplace.influencer (id)
);

create table video
(
    id                  bigint auto_increment
        primary key,
    influencer_id       bigint not null,
    place_id            bigint null,
    view_count          bigint null,
    view_count_increase bigint null,
    video_url           text   not null,
    constraint FK7hqjxq4phfph44evu5d5atq8d
        foreign key (place_id) references places (id),
    constraint FKl9cosu6upjb1w6wcfjrb5n2l2
        foreign key (influencer_id) references influencer (id)
);

create table youtube_channel
(
    id             bigint auto_increment
        primary key,
    influencer_id  bigint        null,
    channeluuid    varchar(255)  null,
    last_videouuid varchar(255)  null,
    play_listuuid  varchar(255)  null,
    channel_type   enum ('FOOD') null
);

create table error_log
(
    id            bigint auto_increment
        primary key,
    error_message varchar(255) null,
    error_url     varchar(255) null,
    stack_trace   text         null,
    is_resolved   bit          not null
);

create table review
(
    id           bigint auto_increment
        primary key,
    is_liked     bit          not null,
    created_date datetime(6)  null,
    place_id     bigint       not null,
    user_id      bigint       not null,
    comment      varchar(100) null,
    constraint UKktwck3ql0eo5oss6sw8nftsbq
        unique (user_id, place_id),
    constraint FKfb1me8dhpyswvhgjmbvgkcti2
        foreign key (place_id) references places (id),
    constraint FKj8m0asijw8lfl4jxhcps8tf4y
        foreign key (user_id) references user (id)
);

create table oauth_token
(
    expires_at   datetime(6)  null,
    id           bigint auto_increment
        primary key,
    user_id      bigint       null,
    access_token varchar(255) null,
    constraint UKr56mcwhkhg1tts2riw1cifaw8
        unique (user_id),
    constraint FK9bq3t9xrvellgudfdm1xrore7
        foreign key (user_id) references user (id)
);

create table liked_places
(
    is_liked bit    null,
    id       bigint auto_increment
        primary key,
    place_id bigint null,
    user_id  bigint null,
    constraint FK3rej4k2wur49vvsmkvy99051h
        foreign key (user_id) references user (id),
    constraint FK7x24ubd2axm81hx0ggiy4l4ao
        foreign key (place_id) references places (id)
);
