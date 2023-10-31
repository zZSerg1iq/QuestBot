package com.zinoviev.entity.data;

import lombok.Data;


@Table
@Entity
@Data
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private User user;




}
