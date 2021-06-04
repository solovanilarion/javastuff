package concurs.repository;

import concurs.model.Inscriere;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class InscriereRepository implements IRepository<Integer, Inscriere> {
    private JdbcUtils dbUtils;

    public InscriereRepository(Properties props){
        this.dbUtils =  new JdbcUtils(props);
    }

    @Override
    public int size() {
        try(Connection con = dbUtils.getConnection(); PreparedStatement preStmt = con.prepareStatement("SELECT COUNT(*) as SIZE FROM Inscriere")){
            try(ResultSet result = preStmt.executeQuery()){
                if(result.next()){
                    return result.getInt("SIZE");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error size concurs.model.Inscriere " + e.getMessage());
        }
        return 0;
    }

    @Override
    public void save(Inscriere entity) {
        try(Connection con = dbUtils.getConnection(); PreparedStatement preStmt = con.prepareStatement("INSERT INTO Inscriere VALUES (?,?,?)")){
            preStmt.setInt(1,entity.getIdCopil());
            preStmt.setInt(2,entity.getIdProba1());
            preStmt.setInt(3,entity.getIdProba2());
            int result = preStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error save concurs.model.Inscriere " + e.getMessage());
        }
    }

    @Override
    public void delete(Integer integer) {
        try(Connection con = dbUtils.getConnection(); PreparedStatement preStmt = con.prepareStatement("DELETE FROM Inscriere WHERE idCopil=?")){
            preStmt.setInt(1,integer);
            int result = preStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error delete concurs.model.Inscriere " + e.getMessage());
        }
    }

    @Override
    public void update(Integer integer, Inscriere entity) {
        try(Connection con = dbUtils.getConnection(); PreparedStatement preStmt = con.prepareStatement("UPDATE Inscriere SET idProba1=?,idProba2=?")){
            preStmt.setInt(1, entity.getIdProba1());
            preStmt.setInt(2,entity.getIdProba2());
            int result = preStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error update concurs.model.Inscriere " + e.getMessage());
        }
    }

    @Override
    public Inscriere findOne(Integer integer) {
        try(Connection con = dbUtils.getConnection(); PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Inscriere WHERE idCopil=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result = preStmt.executeQuery()){
                if(result.next()){
                    int idCopil = result.getInt(1);
                    int idProba1 = result.getInt(2);
                    int idProba2 = result.getInt(3);
                    Inscriere inscriere = new Inscriere(idCopil,idProba1,idProba2);
                    return inscriere;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error findOne concurs.model.Inscriere " + e.getMessage());
        }
        return null;
    }

    @Override
    public Iterable<Inscriere> findAll() {
        List<Inscriere> rez = new ArrayList<>();
        try(Connection con = dbUtils.getConnection(); PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Inscriere")){
            try(ResultSet result = preStmt.executeQuery()){
                while(result.next()){
                    int idCopil = result.getInt(1);
                    int idProba1 = result.getInt(2);
                    int idProba2 = result.getInt(3);
                    Inscriere inscriere = new Inscriere(idCopil,idProba1,idProba2);
                    rez.add(inscriere);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rez;
    }
}
