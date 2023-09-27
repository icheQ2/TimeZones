package com.timezones.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class City {
    private String name;
    private String timeZone;
}