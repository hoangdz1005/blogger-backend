package com.bloggerbackend.search;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.bloggerbackend.constants.AppConfigs;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SearchUtils {
    public static String getFiledSort(String sort) {
        String[] parts = sort.trim().split(":", 2);
        return parts[0];
    }

    public static String getValueSort(String sort) {
        String[] parts = sort.trim().split(":", 2);
        return parts[1];
    }

    public static void addPageRequest (NativeQueryBuilder queryBuilder, Integer page, Integer pageSize) {
        var pageable = page!=null && pageSize!=null ? PageRequest.of(page, pageSize) : PageRequest.of(AppConfigs.PAGE_START, AppConfigs.PAGE_SIZE_DEFAULT);
        queryBuilder.withPageable(pageable);
    }
    public static void addQuery (NativeQueryBuilder queryBuilder, String search, List<String> fields) {
        if(search!=null && !search.isEmpty()) {
            var criteria = QueryBuilders.multiMatch(b->b.fields(fields).query(search));
            queryBuilder.withQuery(criteria);
        }
    }
}
