create table user_to_role(
    user_id bigint(20) not null,
    role_id bigint(20) not null,
    foreign key (user_id) references user(id),
    foreign key (role_id) references role(id),
    index user_id_idx (user_id),
    index role_id_idx (role_id)
)
engine=INNODB;
