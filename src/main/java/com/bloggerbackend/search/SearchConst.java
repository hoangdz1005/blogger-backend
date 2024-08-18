package com.bloggerbackend.search;

import java.util.List;

public class SearchConst {
    public static final String SORT_ASC = "asc";
    public static final String SORT_DESC = "desc";
    public static final List<String> TOPIC_FIELDS_MATCH = List.of("name", "description", "slug");
    public static final String TOPIC_FIELD_NAME = "name";
    public static final String TOPIC_SORT_NAME = "name.keyword";
    public static final String TOPIC_FIELD_DESCRIPTION = "description";
    public static final String TOPIC_SORT_DESCRIPTION = "description.keyword";

    public static String getFieldSortTopic(String field) {
        return TOPIC_FIELD_NAME.equals(field) ? TOPIC_SORT_NAME : TOPIC_FIELD_DESCRIPTION;
    }
}
