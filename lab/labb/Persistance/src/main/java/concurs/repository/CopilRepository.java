package concurs.repository;

import concurs.model.Copil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class CopilRepository implements ICopilRepository{
    private JdbcUtils dbUtils;

    public CopilRepository(Properties props){
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public int size() {
        try(Connection con = dbUtils.getConnection();PreparedStatement preStmt = con.prepareStatement("SELECT count(*) as SIZE from Copil ")){
            try(ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    return result.getInt("SIZE");
                }
            }
        }catch(SQLException e){
            System.out.println("Error size concurs.model.Copil " + e.getMessage() );
        }
        return 0;
    }

    @Override
    public void save(Copil entity) {
        try(Connection con=dbUtils.getConnection(); PreparedStatement preStmt=con.prepareStatement("insert into Copil values (null,?,?,?)")){
            preStmt.setString(1,entity.getNume());
            preStmt.setString(2,entity.getPrenume());
            preStmt.setInt(3,entity.getVarsta());
            int result=preStmt.executeUpdate();
        }catch (SQLException e) {
            System.out.println("Error save concurs.model.Copil " + e.getMessage());
        }
    }

    @Override
    public void delete(Integer integer) {
        try(Connection con=dbUtils.getConnection();PreparedStatement preStmt=con.prepareStatement("delete from Copil where id_copil=?")){
            preStmt.setInt(1,integer);
            int result=preStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error delete concurs.model.Copil " + e.getMessage());
        }
    }

    @Override
    public void update(Integer integer, Copil entity) {
        try(Connection con = dbUtils.getConnection(); PreparedStatement preStmt=con.prepareStatement("UPDATE Copil SET nume=?,prenume=?,varsta=? WHERE id_copil=?")){
            preStmt.setString(1,entity.getNume());
            preStmt.setString(2,entity.getPrenume());
            preStmt.setInt(3,entity.getVarsta());
            preStmt.setInt(4,integer);
            int result=preStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error update concurs.model.Copil" + e.getMessage());
        }
    }

    @Override
    public Copil findOne(Integer integer) {
        try(Connection con=dbUtils.getConnection(); PreparedStatement preStmt=con.prepareStatement("SELECT * FROM Copil WHERE id_copil=?")) {
            preStmt.setInt(1,integer);
            try(ResultSet result = preStmt.executeQuery()){
                if(result.next()){
                    String nume = result.getString(2);
                    String prenume = result.getString(3);
                    int varsta = result.getInt(4);
                    return new Copil(nume, prenume, varsta);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error findOne concurs.model.Copil " + e.getMessage());
        }
        return null;
    }

    @Override
    public Iterable<Copil> findAll() {
        List<Copil> rez = new ArrayList<Copil>();
        try(Connection con = dbUtils.getConnection(); PreparedStatement preStmt = con.prepareStatement("SELECT * From Copil")){
            try(ResultSet result = preStmt.executeQuery()) {
                while (result.next()){
                    int id=result.getInt(1);
                    String nume = result.getString(2);
                    String prenume = result.getString(3);
                    int varsta = result.getInt(4);
                    Copil copil = new Copil(id, nume, prenume, varsta);
                    rez.add(copil);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error findAll concurs.model.Copil" + e.getMessage());
        }
        return rez;
    }

    @Override
    public int getId(String nume, String prenume, int varsta){
        try(Connection con = dbUtils.getConnection(); PreparedStatement preStmt = con.prepareStatement("SELECT id_copil FROM Copil WHERE nume=? AND prenume=? AND varsta=?")){
            preStmt.setString(1, nume);
            preStmt.setString(2,prenume);
            preStmt.setInt(3, varsta);
            try(ResultSet result = preStmt.executeQuery()){
                if(result.next()){
                    return result.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
