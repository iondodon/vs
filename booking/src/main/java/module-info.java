open module booking {
    requires lombok;
    requires spring.tx;
    requires jakarta.validation;
    requires spring.context;
    requires spring.webmvc;
    requires spring.web;
    requires org.apache.tomcat.embed.core;
    requires io.swagger.v3.oas.annotations;
    requires jakarta.persistence;
    requires spring.data.jpa;
    requires org.slf4j;
}