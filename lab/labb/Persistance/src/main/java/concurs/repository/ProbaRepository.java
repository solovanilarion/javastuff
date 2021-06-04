package concurs.repository;

import concurs.model.Proba;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ProbaRepository implements IProbaRepository {
    private JdbcUtils dbUtils;

    public ProbaRepository(Properties props) {
        this.dbUtils = new JdbcUtils(props);
    }

    @Override
    public int size() {
        try(Connection con = dbUtils.getConnection(); PreparedStatement preStmt = con.prepareStatement("SELECT count(*) as SIZE FROM Probe")){
            try(ResultSet result = preStmt.executeQuery()){
                if (result.next()) {
                    return result.getInt("SIZE");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error size Probe " + e.getMessage());
        }
        return 0;
    }

    @Override
    public void save(Proba entity) {
        try(Connection con = dbUtils.getConnection(); PreparedStatement preStmt = con.prepareStatement("INSERT INTO Probe VALUES (null,?,?,0)")) {
            preStmt.setString(1,entity.getDenumire());
            preStmt.setString(2,entity.getCategorie());
            int result = preStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error save Probe " + e.getMessage());
        }
    }

    @Override
    public void delete(Integer integer) {
        try(Connection con = dbUtils.getConnection(); PreparedStatement preStmt = con.prepareStatement("DELETE FROM Probe WHERE idProba=?")){
            preStmt.setInt(1,integer);
            int result = preStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error delete Probe " + e.getMessage());
        }
    }

    @Override
    public void update(Integer integer, Proba entity) {
        try(Connection con = dbUtils.getConnection(); PreparedStatement preStmt = con.prepareStatement("UPDATE Probe SET denumire=?,categorie=?,nrParticipanti=? where idProba=?")) {
            preStmt.setString(1,entity.getDenumire());
            preStmt.setString(2,entity.getCategorie());
            preStmt.setInt(3,entity.getNrParticipanti());
            preStmt.setInt(4,integer);
            int result = preStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error update Probe " + e.getMessage());
        }
    }

    @Override
    public Proba findOne(Integer integer) {
        try(Connection con = dbUtils.getConnection(); PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Probe WHERE idProba=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result = preStmt.executeQuery() ){
                if (result.next()){
                    String denumire = result.getString(2);
                    String categorie = result.getString(3);
                    int nrParticipanti = result.getInt(4);
                    Proba proba = new Proba(denumire, categorie,nrParticipanti);
                    return proba;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error findOne Probe " + e.getMessage());
        }
        return null;
    }

    @Override
    public Iterable<Proba> findAll() {
        List<Proba> rez = new ArrayList<>();
        try (Connection con = dbUtils.getConnection(); PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Probe")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt(1);
                    String denumire = result.getString(2);
                    String categorie = result.getString(3);
                    int nrParticipanti = result.getInt(4);
                    Proba proba = new Proba(id, denumire, categorie, nrParticipanti);
                    rez.add(proba);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error findAll Probe " + e.getMessage());
        }
        return rez;
    }

    @Override
    public Iterable<String> getDenumiri() {
        List<String> rez = new ArrayList<>();
        try(Connection con = dbUtils.getConnection();PreparedStatement preStmt = con.prepareStatement("SELECT DISTINCT denumire FROM Probe")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    String denumire = result.getString(1);
                    rez.add(denumire);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rez;
    }

    @Override
    public Iterable<String> getCategorii() {
        List<String> rez = new ArrayList<>();
        try(Connection con = dbUtils.getConnection();PreparedStatement preStmt = con.prepareStatement("SELECT DISTINCT categorie FROM Probe")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    String categorie = result.getString(1);
                    rez.add(categorie);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rez;
    }

    @Override
    public int getIDProba(String denumire, String categorie) {
        try(Connection con = dbUtils.getConnection();PreparedStatement preStmt = con.prepareStatement("SELECT idProba FROM Probe WHERE denumire=? and categorie=?")){
            preStmt.setString(1,denumire);
            preStmt.setString(2,categorie);
            try(ResultSet result = preStmt.executeQuery() ){
                if (result.next()){
                    int id = result.getInt(1);
                    return id;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}