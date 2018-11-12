/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.CommentEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.MyConnection;

/**
 *
 * @author User
 */
public class CommentEventService implements CommentEventServiceInterface {

    private Connection connection;
    private PreparedStatement ps;

    public CommentEventService() {

        connection = MyConnection.getInstance();

    }

    @Override
    public void ajouterCommentEvent(CommentEvent c) {
        PreparedStatement ps = null;
        String req = "INSERT INTO commentevent(evenement_id,contenu,dateHeureCommentEvent,user_id) VALUES (?,?,?,?)";
        try {
            ps = connection.prepareStatement(req);
            ps.setInt(1, c.getEvenement_id());
            ps.setString(2, c.getContenu());
            ps.setTimestamp(3, c.getDateHeureCommentEvent());
            ps.setInt(4, c.getUser_id()
            );

            //EXECUTION
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerCommentEvent(CommentEvent c) {

        try {
            String sql = "DELETE FROM commentevent WHERE id = ?";

            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
            System.out.println(c.getId());
            preparedStatement.setInt(1, c.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int modifierCommentEvent(CommentEvent c) {
        int result = 0;
        try {
            String sql = "UPDATE commentevent SET contenu = ? WHERE commentevent.id = ?";

            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);

            preparedStatement.setString(1, c.getContenu());
            preparedStatement.setInt(2, c.getId());

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List getAll() {
        String req = " select * from commentevent ";
        List<CommentEvent> comments = new ArrayList<>();
        try {
            ps = connection.prepareStatement(req);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                CommentEvent c = new CommentEvent(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getInt(5));
                c.setId(resultSet.getInt("id"));
                comments.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comments;
    }

    public List<CommentEvent> getCommentaires(int evenement_id) throws SQLException {
        List<CommentEvent> listCommentaires = new ArrayList<>();

        PreparedStatement ps = null;

        String req = "SELECT * FROM commentevent WHERE commentevent.evenement_id = ? ";
        ps = connection.prepareStatement(req);
        ps.setInt(1, evenement_id);

        //ResultSet  
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            CommentEvent c = new CommentEvent();
            c.setId(rs.getInt("id"));
           // c.setId(rs.getInt("evenement_id"));
            c.setContenu(rs.getString("contenu"));
            

            listCommentaires.add(c);
        }
        return listCommentaires;
    }

}
