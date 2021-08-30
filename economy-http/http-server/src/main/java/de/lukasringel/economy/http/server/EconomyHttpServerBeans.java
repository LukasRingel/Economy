package de.lukasringel.economy.http.server;

import lombok.SneakyThrows;
import me.xkuyax.utils.mysql.MysqlConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.util.Properties;

@Component
public class EconomyHttpServerBeans {

    private final Properties properties = new Properties();

    /**
     * This method gets called after class construction
     * and loads our properties from the datasource file
     */
    @SneakyThrows
    @PostConstruct
    private void loadProperties() {
        properties.load(new FileInputStream(ResourceUtils.getFile("classpath:datasource.properties")));
    }

    /**
     * This bean provides an instance of our own mysql connection class
     * We don't use the java build in here since there are better third party apis
     *
     * @return - mysql connection instance
     */
    @Bean
    public MysqlConnection mysqlConnection() {
        String[] hostname = properties.getProperty("mysql.hostname").split(":");
        return MysqlConnection.hikari(hostname[0],
                properties.getProperty("mysql.username"),
                properties.getProperty("mysql.password"),
                hostname[1],
                properties.getProperty("mysql.database"));
    }

    /**
     * This bean provides our application properties
     *
     * @return - our properties
     */
    @Bean
    public Properties properties() {
        return properties;
    }

}