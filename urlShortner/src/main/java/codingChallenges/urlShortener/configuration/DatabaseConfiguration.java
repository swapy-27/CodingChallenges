package codingChallenges.urlShortener.configuration;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
//@EnableJpaRepositories(
//        entityManagerFactoryRef = "multiEntityManager",
//        transactionManagerRef = "multiTransactionManager")

@EnableJpaRepositories(basePackages = "codingChallenges.urlShortener.repositories")
@EntityScan("codingChallenges.urlShortener.entity")
public class DatabaseConfiguration {

    //add JPA entities path here
    private final String PACKAGE_SCAN = "codingChallenges.urlShortener.entity";

    //set db1 as the primary and default database connection
    @Primary
    @Bean(name = "db1DataSource")
    @ConfigurationProperties("app.datasource.db1")
    public DataSource db1DataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    //connection objects for the remaining 2 databases
    @Bean(name = "db2DataSource")
    @ConfigurationProperties("app.datasource.db2")
    public DataSource db2DataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
    @Bean(name = "db3DataSource")
    @ConfigurationProperties("app.datasource.db3")
    public DataSource db3DataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    //The multidatasource configuration
    @Bean(name = "multiRoutingDataSource")
    public DataSource multiRoutingDataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(ClientNames.DB1, db1DataSource());
        targetDataSources.put(ClientNames.DB2, db2DataSource());
        targetDataSources.put(ClientNames.DB3, db3DataSource());
        MultiRoutingDataSource multiRoutingDataSource
                = new MultiRoutingDataSource();
        multiRoutingDataSource.setDefaultTargetDataSource(db1DataSource());
        multiRoutingDataSource.setTargetDataSources(targetDataSources);
        return multiRoutingDataSource;
    }

    //add multi entity configuration code
    @Bean(name = "entityManager")
    public LocalContainerEntityManagerFactoryBean multiEntityManager() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(multiRoutingDataSource());
        em.setPackagesToScan(PACKAGE_SCAN);
        HibernateJpaVendorAdapter vendorAdapter
                = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());
        return em;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager multiTransactionManager(@Qualifier("entityManager") EntityManagerFactory multiEntityManager) {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager(multiEntityManager);
//        transactionManager.setEntityManagerFactory(
//                multiEntityManager().getObject());
        return transactionManager;
    }

    @Primary
    @Bean(name="entityManagerFactory")
    public LocalSessionFactoryBean dbSessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(multiRoutingDataSource());
        sessionFactoryBean.setPackagesToScan(PACKAGE_SCAN);
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        return sessionFactoryBean;
    }

    //add hibernate properties
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", true);
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.id.new_generator_mappings", false);
        properties.put("hibernate.jdbc.lob.non_contextual_creation", true);
//        properties.put("hibernate.ddl-auto", "create");
        return properties;
    }

}
