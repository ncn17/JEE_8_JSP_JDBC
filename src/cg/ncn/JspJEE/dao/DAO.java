package cg.ncn.JspJEE.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cg.ncn.JspJEE.outils.DAOException;

public abstract class DAO<T> {
    protected Connection conn = null;

    public DAO( Connection conn ) {
        this.conn = conn;
    }

    /* m�thode signature */

    public abstract T find( int id ) throws DAOException;

    public abstract ArrayList<T> findAll() throws DAOException;

    public abstract boolean create( T obj ) throws DAOException;

    public abstract boolean update( T obj ) throws DAOException;

    public abstract boolean delete( int id ) throws DAOException;

    public abstract T Hydrate( ResultSet res ) throws SQLException;

}
