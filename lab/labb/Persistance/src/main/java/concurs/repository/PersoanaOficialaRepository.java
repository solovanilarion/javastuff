package concurs.repository;

import concurs.model.PersoanaOficiala;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;


public class PersoanaOficialaRepository implements IPersoanaOficialaRepository {
    private JdbcUtils dbUtils;

    public PersoanaOficialaRepository(Properties props){
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void save(PersoanaOficiala entity) {}

    @Override
    public void delete(Integer integer) {}

    @Override
    public void update(Integer integer, PersoanaOficiala entity) {}

    @Override
    public PersoanaOficiala findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<PersoanaOficiala> findAll() {
        return null;
    }

    @Override
    public PersoanaOficiala validareDate(String username, String password) {
        try(Connection con = dbUtils.getConnection(); PreparedStatement preStmt = con.prepareStatement("SELECT * FROM  Persoana_oficiu WHERE username=? AND password=?")){
            preStmt.setString(1, username);
            preStmt.setString(2,password);
            try(ResultSet result = preStmt.executeQuery()){
                if(result.next()){
                    String user = result.getString(3);
                    String pass = result.getString(4);
                    String nume = result.getString(2);
                    return new PersoanaOficiala(pass, user, nume);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error validareDate PersOf " + e.getMessage());
        }
        return null;
    }

    @Override
    public PersoanaOficiala findBy(String username, String parola) {
        try(Connection con = dbUtils.getConnection(); PreparedStatement preStmt = con.prepareStatement("SELECT nume FROM  Persoana_oficiu WHERE username=? AND password=?")){
            preStmt.setString(1, username);
            preStmt.setString(2, parola);
            try(ResultSet result = preStmt.executeQuery()){
                if(result.next()){
                    String nume = result.getString("nume");
                    return new PersoanaOficiala(username, parola, nume);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error findBy PersOf " + e.getMessage());
        }
        return null;
    }
}