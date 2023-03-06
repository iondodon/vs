module bootstrap {
    requires spring.context;
    requires spring.core;
    requires spring.beans;
    requires jakarta.annotation;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires java.sql;

    opens org.example.vs;
}