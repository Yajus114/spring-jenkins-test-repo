package com.dawnbit.common.utils;

import com.dawnbit.master.external.dto.QueryDTO;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import jakarta.persistence.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
@Slf4j
public class PagingSortingUtils {

    private static final String ENUM = "ENUM";
    private static final String STRING = "STRING";
    private static final String SINGLE_FIELD_LIKE = "SINGLE_FIELD_LIKE";
    private static final String DOUBLE = "DOUBLE";
    private static final String INTEGER = "INTEGER";
    private static final String BOOLEAN = "BOOLEAN";
    private static final String EXACT_NUMBER = "EXACT_NUMBER";
    private static final String SINGLE_FIELD_EQUALS = "SINGLE_FIELD_EQUALS";
    private static final String TIME = "TIME";
    private static final String ALL = "ALL";

    private static final String ACTUAL_TIME = "ACTUAL_TIME";

//    public static Map<String, Object> fetchDataListWithUpdatedQueryStringInMap(final Map<String, Object> params,
//                                                                               String hqlQuery, final SearchModelDTO searchModel, final Session session) {
//
//        final Map<String, Object> map = new HashMap<>();
//
//        final Query<?> query = fetchQuery(params, hqlQuery, searchModel, session);
//        hqlQuery = fetchQueryString(params, hqlQuery, searchModel, session);
//
//        query.setFirstResult(searchModel.getOffset() * searchModel.getLimit())
//                .setMaxResults(searchModel.getLimit());
//        map.put(ConstantUtils.DATA_LIST, query.list());
//        map.put("hqlQuery", hqlQuery);
//        return map;
//    }

    public static Map<String, Object> fetchDataListWithUpdatedQueryStringInMaps(final Map<String, Object> params,
                                                                                String hqlQuery, final SearchModelDTO searchModel, final Session session) {

        final Map<String, Object> map = new HashMap<>();
        hqlQuery = fetchQueryStrings(params, hqlQuery, searchModel, session);
        final Query<?> query = fetchQuery(params, hqlQuery, searchModel, session);


        query.setFirstResult(searchModel.getOffset() * searchModel.getLimit())
                .setMaxResults(searchModel.getLimit());
        map.put(ConstantUtils.DATA_LIST, query.list());
        map.put("hqlQuery", hqlQuery);
        return map;
    }


    public static Query<?> fetchQuery(final Map<String, Object> params, String hqlQuery,
                                      final SearchModelDTO searchModel, final Session session) {
        hqlQuery = fetchQueryString(params, hqlQuery, searchModel, session);

        hqlQuery += " ORDER BY " + searchModel.getSortingField() + " " + searchModel.getSortDirection();
        final Query<?> query = session.createQuery(hqlQuery);
        // query.setCacheable(true);
        if (params != null || !params.isEmpty()) {
            for (final Parameter<?> str : query.getParameters()) {
                query.setParameter(str.getName(), params.get(str.getName()));
            }
        }

        return query;
    }

    public static Map<String, Object> addPaginationDataInDataMap(final Map<String, Object> map,
                                                                 final Map<String, Object> params,
                                                                 final SearchModelDTO searchModel, final Session session, final String alias) {
        String hqlQuery = (String) map.get("hqlQuery");
        hqlQuery = Arrays.asList(hqlQuery.split("(?i)FROM")).get(1);

        if (searchModel.getHavingString() != null) {
            hqlQuery = hqlQuery.replace(searchModel.getHavingString(), searchModel.getHavingAlternate());
        }

        if (searchModel.getGroupByField() != null) {
            hqlQuery = hqlQuery.replace("GROUP BY " + searchModel.getGroupByField(), "");
            hqlQuery = "Select count(distinct " + searchModel.getGroupByField() + ") From " + hqlQuery;
        } else {
            hqlQuery = "Select count(" + alias + ") From " + hqlQuery;
        }

        final long totalRows = PagingSortingUtils.totalRows(params, hqlQuery, session);

        map.put(ConstantUtils.TOTAL_ROWS, totalRows);
        map.put(ConstantUtils.TOTAL_PAGES,
                totalRows != 0 ? (totalRows % searchModel.getLimit()) > 0 ? (totalRows / searchModel.getLimit()) + 1
                        : totalRows / searchModel.getLimit() : 0);
        return map;
    }

    public static long totalRows(final Map<String, Object> params, final String hqlQuery, final Session session) {
        final Query<?> query = session.createQuery(hqlQuery);
        for (final Parameter<?> str : query.getParameters()) {
            query.setParameter(str.getName(), params.get(str.getName()));
        }
        return (Long) query.uniqueResult();
    }

    public static Map<String, String> joinAndFetchFieldsForSorting(String hqlQuery, final SearchModelDTO searchModel) {
        final Map<String, String> map = new HashMap<>();
        if (StringUtils.countMatches(searchModel.getSortingField(), ".") > 1) {
            final String alias = searchModel.getSortingField()
                    .substring(0, searchModel.getSortingField().lastIndexOf('.')).trim();
            final String fieldName = searchModel.getSortingField()
                    .substring(searchModel.getSortingField().lastIndexOf('.') + 1).trim();

            hqlQuery += "LEFT JOIN " + alias + " AS alias";


            searchModel.setSortingField("alias." + fieldName);
            map.put(ConstantUtils.QUERY, hqlQuery);
            map.put(ConstantUtils.SORTING_ALIAS, "alias." + fieldName);
            return map;
        }
        return null;
    }

    public static String fetchQueryStrings(final Map<String, Object> params, String hqlQuery,
                                           final SearchModelDTO searchingModel, final Session session) {
        final Map<String, QueryDTO> filterMap = searchingModel.getFilterFieldsWithValue();
        final Object[] list = searchingModel.getFilterFieldsWithValue().keySet().toArray();
        int i = 1;
        if (!filterMap.isEmpty()) {
            StringBuilder hqlQueryBuilder = new StringBuilder(hqlQuery);
            for (final Object key : list) {
                final QueryDTO queryDetail = filterMap.get(key);
                final String queryField = queryDetail.getQueryField();

                if (queryDetail.getQueryType().equals(SINGLE_FIELD_LIKE)) {
                    if (queryDetail.getFieldType().equals(STRING)) {
                        hqlQueryBuilder.append(" AND ").append(queryField).append(" LIKE :param").append(i);
                        params.put("param" + i, "%" + queryDetail.getValue().trim() + "%");
                    }
                    if (queryDetail.getFieldType().equals(DOUBLE) || queryDetail.getFieldType().equals(INTEGER)
                            || queryDetail.getFieldType().equals(BOOLEAN) || queryDetail.getFieldType().equals(ENUM)) {
                        hqlQueryBuilder.append(" AND concat(").append(queryField).append(",'')").append(" LIKE :param").append(i);
                        params.put("param" + i, "%" + queryDetail.getValue().trim() + "%");
                    }
                    if (queryDetail.getFieldType().equals(EXACT_NUMBER)) {
                        hqlQueryBuilder.append(" AND concat(").append(queryField).append(",'')").append(" = :param").append(i);
                        params.put("param" + i, queryDetail.getValue().trim());
                    }
                    if (queryDetail.getFieldType().equals(TIME)) {
                        hqlQueryBuilder.append(" AND concat( TIME_FORMAT(CONVERT_TZ(").append(queryField).append(",'+00:00','+05:30'), '%h:%i %p') ,'')").append(" LIKE :param").append(i);
                        params.put("param" + i, "%" + queryDetail.getValue().trim() + "%");
                    }
                    if (queryDetail.getFieldType().equals(ENUM) && !queryDetail.getValue().equals(ALL)) {
                        hqlQueryBuilder.append(" AND concat(").append(queryField).append(",'')").append(" LIKE :param").append(i);

                    }
                    if (queryDetail.getFieldType().equals(ACTUAL_TIME)) {
                        hqlQueryBuilder.append(" AND concat( TIME_FORMAT(CONVERT_TZ(").append(queryField).append(",'+00:00','+00:00'), '%h:%i %p') ,'')").append(" LIKE :param").append(i);
                        params.put("param" + i, "%" + queryDetail.getValue().trim() + "%");
                    }
                }


                if (queryDetail.getQueryType().equals(SINGLE_FIELD_EQUALS)) {
                    hqlQueryBuilder.append(" AND ").append(queryField).append(" = :param").append(i);
                    params.put("param" + i, queryDetail.getValue().trim());
                }
                if (queryDetail.getQueryType().equals("DATE")) {
                    try {
                        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        Date fromDate = formatter.parse(queryDetail.getValue());
                        Date toDate = new Date(fromDate.getTime() + 86400000);
                        hqlQueryBuilder.append(" AND ").append(queryField).append(" between ").append("  :param").append(i).append(" and ").append("  :param").append(i + 1);
                        params.put("param" + i, fromDate);
                        i++;
                        params.put("param" + i, toDate);
                    } catch (Exception e) {
                        //The handling for the code
                    }
                }
                i++;
            }
            hqlQuery = hqlQueryBuilder.toString();
        }


//		 else {
//			hqlQuery += " ORDER BY " + searchingModel.getSortingField() + " " + searchingModel.getSortDirection();
//		}

        return hqlQuery;
    }

    public static String fetchQueryString(final Map<String, Object> params, String hqlQuery,
                                          final SearchModelDTO searchModel, final Session session) {
        final int i = 1;
        if (params == null || params.isEmpty()) {
            if (searchModel.getSearchValue() != null && !searchModel.getSearchValue().isEmpty()) {
                hqlQuery += " where " + searchModel.getSearchField() + " LIKE :param" + i;
                assert params != null;
                params.put("param" + i, "%" + searchModel.getSearchValue() + "%");
            }
        } else {
            if (searchModel.getSearchValue() != null && !searchModel.getSearchValue().isEmpty()) {
                hqlQuery += " and " + searchModel.getSearchField() + " LIKE :param" + i;
                params.put("param" + i, "%" + searchModel.getSearchValue().trim() + "%");
            }
        }

        return hqlQuery;
    }

    public static Map<String, Object> addPaginationDataInDataMap(final Map<String, Object> map,
                                                                 final Map<String, Object> params, String hqlQuery, final boolean isGroupByQuery,
                                                                 final SearchModelDTO searchModel, final Session session, final String alias) {
        hqlQuery = (String) map.get("hqlQuery");
        hqlQuery = Arrays.asList(hqlQuery.split("(?i)FROM")).get(1);

        if (searchModel.getHavingString() != null) {
            hqlQuery = hqlQuery.replace(searchModel.getHavingString(), searchModel.getHavingAlternate());
        }

        if (searchModel.getGroupByField() != null) {
            hqlQuery = hqlQuery.replace("GROUP BY " + searchModel.getGroupByField(), "");
            hqlQuery = "Select count(distinct " + searchModel.getGroupByField() + ") From " + hqlQuery;
        } else {
            hqlQuery = "Select count(" + alias + ") From " + hqlQuery;
        }

        final long totalRows = PagingSortingUtils.totalRows(params, hqlQuery, session);

        map.put(ConstantUtils.TOTAL_ROWS, totalRows);
        map.put(ConstantUtils.TOTAL_PAGES,
                totalRows != 0 ? (totalRows % searchModel.getLimit()) > 0 ? (totalRows / searchModel.getLimit()) + 1
                        : totalRows / searchModel.getLimit() : 0);
        return map;
    }

    public static Map<String, Object> fetchDataListWithUpdatedQueryStringInMap(final Map<String, Object> params,
                                                                               String hqlQuery, final SearchModelDTO searchModel, final Session session) {

        final Map<String, Object> map = new HashMap<>();

        final Query<?> query = fetchQuery(params, hqlQuery, searchModel, session);
        hqlQuery = fetchQueryString(params, hqlQuery, searchModel, session);

        query.setFirstResult(searchModel.getOffset() * searchModel.getLimit())
                .setMaxResults(searchModel.getLimit());
        map.put(ConstantUtils.DATA_LIST, query.list());
        map.put("hqlQuery", hqlQuery);
        return map;
    }
}
