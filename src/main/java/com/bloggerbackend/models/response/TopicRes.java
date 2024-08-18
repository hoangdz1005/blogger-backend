package com.bloggerbackend.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopicRes {
    Long id;
    String name;
    String description;
    String slug;
    String imageBase64;
}
