package br.com.bandtec.banco.teste;

import org.apache.commons.dbcp2.BasicDataSource;

public class Connection {

    private BasicDataSource datasource;

    public Connection() {
        this.datasource = new BasicDataSource();

        this.datasource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        this.datasource.setUrl("jdbc:mysql://172.17.0.2:3306/faculdade");

        this.datasource.setUsername("root");

        this.datasource.setPassword("admin");
    }

    public BasicDataSource getDatasource() {
        return datasource;
    }
}
