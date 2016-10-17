package com.github.sparkboot.driver.service;

import static org.apache.spark.sql.functions.column;
import static org.apache.spark.sql.functions.explode;
import static org.apache.spark.sql.functions.split;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.SneakyThrows;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordCountService {

    private final SparkSession sparkSession;

    @Autowired
    public WordCountService(SparkSession sparkSession) {
        this.sparkSession = sparkSession;
    }

    @SneakyThrows
    public long countWords() {
        List<Row> rows;
        try (
                Stream<String> stream = new BufferedReader(new InputStreamReader(
                        Thread.currentThread().getContextClassLoader().getResourceAsStream("lorem_ipsum_500.txt")
                )).lines()
        ) {
            rows = stream
                    .map(RowFactory::create)
                    .collect(Collectors.toList());
        }
        // NOTE: Using parallelize, so it may work in different modes (local / standalone / mesos / yarn)
        Dataset<Row> lines = sparkSession.createDataFrame(rows, DataTypes.createStructType(new StructField[]{
                DataTypes.createStructField("line", DataTypes.StringType, false)
        }));
        return lines
                .select(explode(split(column("line"), "\\s")).as("word"))
                .count();
    }
}
