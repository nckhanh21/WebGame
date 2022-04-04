/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Game;
import entity.Platform;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class DAOGame {

    DBConnection dbConn;
    Connection conn;
    String sql;

    public DAOGame(DBConnection dbconn) {
        this.dbConn = dbconn;
        conn = dbconn.getConnection();
    }

    public int insertGame(Game game) {
        sql = "Insert into Game(Title,coId,description,version,rating,releaseDate,price,state,status) values (?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps;
        int n =0;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, game.getTitle());
            ps.setInt(2, game.getCoID());
            ps.setString(3, game.getDescription());
            ps.setString(4, game.getVersion());
            ps.setInt(5, game.getRating());
            ps.setDate(6, game.getReleaseDate());
            ps.setDouble(7, game.getPrice());
            ps.setString(8, game.getState());
            ps.setInt(9, game.getStatus());
            n = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int updateInfoGame(Game game) {
        int n = 0;
        String sql = "update Game set Title=?, coId=?, description=?, version=?, rating=?, releaseDate=?, price=?, state=?, status=? where gId=? ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, game.getTitle());
            ps.setInt(2, game.getCoID());
            ps.setString(3, game.getDescription());
            ps.setString(4, game.getVersion());
            ps.setInt(5, game.getRating());
            ps.setDate(6, game.getReleaseDate());
            ps.setDouble(7, game.getPrice());
            ps.setString(8, game.getState());
            ps.setInt(9, game.getStatus());
            ps.setInt(10, game.getGid());
            n = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int changeStatus(int id, int status) {
        int n = 0;
        String sql = "update Game set status = " + (status == 1 ? 1 : 0) + " where gId = '" + id + "'";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int removeGame(int id) {
        int n = 0;
        String sql = "Select * from Game as a join Order_Detail as b on a.gId = b.gId where a.gId = '" + id + "'";
        ResultSet rs = dbConn.getData(sql);
        try {
            if (rs.next()) {
                changeStatus(rs.getInt("gId"), 0);
            } else {
                String sqlDelete = "delete from Game where gId = '" + id + "'";
                Statement state = conn.createStatement();
                n = state.executeUpdate(sqlDelete);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public boolean checkExistGameTitle(String gameTitle) {
        String sql = "SELECT * FROM Game WHERE Title = '" + gameTitle + "'";
        ResultSet rs = dbConn.getData(sql);
        try {
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public ArrayList<Game> getGameByCompanyId(int coId) {
        ArrayList<Game> list = new ArrayList<>();
        String sql = "SELECT * FROM Game WHERE status=1 and coId = " + coId;
        ResultSet rs = dbConn.getData(sql);
        try {
            while (rs.next()) {
                Game pro = new Game();
                pro.setGid(rs.getInt("gId"));
                pro.setTitle(rs.getString("Title"));
                pro.setCoID(rs.getInt("coId"));
                pro.setDescription(rs.getString("description"));
                pro.setVersion(rs.getString("version"));
                pro.setRating(rs.getInt("rating"));
                pro.setReleaseDate(rs.getDate("releaseDate"));
                pro.setPrice(rs.getDouble("price"));
                pro.setState(rs.getString("state"));
                pro.setStatus(rs.getInt("status"));
                list.add(pro);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Game> getGameByName(String Title) {
        ArrayList<Game> list = new ArrayList<>();
        String sql = "SELECT * FROM Game WHERE status=1 and Title like '%" + Title + "%'";
        ResultSet rs = dbConn.getData(sql);
        try {
            while (rs.next()) {
                Game pro = new Game();
                pro.setGid(rs.getInt("gId"));
                pro.setTitle(rs.getString("Title"));
                pro.setCoID(rs.getInt("coId"));
                pro.setDescription(rs.getString("description"));
                pro.setVersion(rs.getString("version"));
                pro.setRating(rs.getInt("rating"));
                pro.setReleaseDate(rs.getDate("releaseDate"));
                pro.setPrice(rs.getDouble("price"));
                pro.setState(rs.getString("state"));
                pro.setStatus(rs.getInt("status"));
                list.add(pro);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Game getGameById(int gId) {
        String sql = "SELECT * FROM Game WHERE gId =" + gId;
        ResultSet rs = dbConn.getData(sql);
        try {
            while (rs.next()) {
                Game game = new Game();
                game.setGid(rs.getInt("gId"));
                game.setTitle(rs.getString("Title"));
                game.setCoID(rs.getInt("coId"));
                game.setDescription(rs.getString("description"));
                game.setVersion(rs.getString("version"));
                game.setRating(rs.getInt("rating"));
                game.setReleaseDate(rs.getDate("releaseDate"));
                game.setPrice(rs.getDouble("price"));
                game.setState(rs.getString("state"));
                game.setStatus(rs.getInt("status"));
                return game;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Game> getGameByRating() {
        ArrayList<Game> list = new ArrayList<>();
        String sql = "SELECT TOP 10 * FROM GAME where status=1 ORDER BY rating desc";
        ResultSet rs = dbConn.getData(sql);
        try {
            while (rs.next()) {
                Game pro = new Game();
                pro.setGid(rs.getInt("gId"));
                pro.setTitle(rs.getString("Title"));
                pro.setCoID(rs.getInt("coId"));
                pro.setDescription(rs.getString("description"));
                pro.setVersion(rs.getString("version"));
                pro.setRating(rs.getInt("rating"));
                pro.setReleaseDate(rs.getDate("releaseDate"));
                pro.setPrice(rs.getDouble("price"));
                pro.setState(rs.getString("state"));
                pro.setStatus(rs.getInt("status"));
                list.add(pro);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Game> getAllGame() {
        ArrayList<Game> list = new ArrayList<>();
        String sql = "SELECT * FROM Game where status=1 ";
        ResultSet rs = dbConn.getData(sql);
        try {
            while (rs.next()) {
                Game pro = new Game();
                pro.setGid(rs.getInt("gId"));
                pro.setTitle(rs.getString("Title"));
                pro.setCoID(rs.getInt("coId"));
                pro.setDescription(rs.getString("description"));
                pro.setVersion(rs.getString("version"));
                pro.setRating(rs.getInt("rating"));
                pro.setReleaseDate(rs.getDate("releaseDate"));
                pro.setPrice(rs.getDouble("price"));
                pro.setState(rs.getString("state"));
                pro.setStatus(rs.getInt("status"));
                list.add(pro);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Game> getTrueGame() {
        ArrayList<Game> list = new ArrayList<>();
        String sql = "SELECT * FROM Game";
        ResultSet rs = dbConn.getData(sql);
        try {
            while (rs.next()) {
                Game pro = new Game();
                pro.setGid(rs.getInt("gId"));
                pro.setTitle(rs.getString("Title"));
                pro.setCoID(rs.getInt("coId"));
                pro.setDescription(rs.getString("description"));
                pro.setVersion(rs.getString("version"));
                pro.setRating(rs.getInt("rating"));
                pro.setReleaseDate(rs.getDate("releaseDate"));
                pro.setPrice(rs.getDouble("price"));
                pro.setState(rs.getString("state"));
                pro.setStatus(rs.getInt("status"));
                list.add(pro);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public ArrayList<Game> getGameByCategoryId(int caId) {
        ArrayList<Game> list = new ArrayList<>();
        String sql = "SELECT * FROM Game as a join Game_Category as b on a.gId = b.gId WHERE a.status=1 and b.caId =" + caId;
        ResultSet rs = dbConn.getData(sql);
        try {
            while (rs.next()) {
                Game pro = new Game();
                pro.setGid(rs.getInt("gId"));
                pro.setTitle(rs.getString("Title"));
                pro.setCoID(rs.getInt("coId"));
                pro.setDescription(rs.getString("description"));
                pro.setVersion(rs.getString("version"));
                pro.setRating(rs.getInt("rating"));
                pro.setReleaseDate(rs.getDate("releaseDate"));
                pro.setPrice(rs.getDouble("price"));
                pro.setState(rs.getString("state"));
                pro.setStatus(rs.getInt("status"));
                list.add(pro);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Game> getGameByPlatformId(int plId) {
        ArrayList<Game> list = new ArrayList<>();
        String sql = "SELECT * FROM Game as a join Game_Platform as b on a.gId = b.gId WHERE a.status=1 and b.plId =" + plId;
        ResultSet rs = dbConn.getData(sql);
        try {
            while (rs.next()) {
                Game pro = new Game();
                pro.setGid(rs.getInt("gId"));
                pro.setTitle(rs.getString("Title"));
                pro.setCoID(rs.getInt("coId"));
                pro.setDescription(rs.getString("description"));
                pro.setVersion(rs.getString("version"));
                pro.setRating(rs.getInt("rating"));
                pro.setReleaseDate(rs.getDate("releaseDate"));
                pro.setPrice(rs.getDouble("price"));
                pro.setState(rs.getString("state"));
                pro.setStatus(rs.getInt("status"));
                list.add(pro);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Game> getGameByMultiCategoryId(String[] caIds) {
        if (caIds.length == 0) {
            return null;
        }
        ArrayList<Game> list = new ArrayList<>();
        for (String caId : caIds) {
            String sql = "SELECT * FROM [Web_Game_DB].[dbo].[Game] WHERE status=1 and gId IN (SELECT gId FROM [Web_Game_DB].[dbo].[Game_Category] WHERE caId = " + caId + ")";
            ResultSet rs = dbConn.getData(sql);
            try {
                while (rs.next()) {
                    boolean check = true;
                    Game game = new Game();
                    game.setGid(rs.getInt("gId"));
                    game.setTitle(rs.getString("Title"));
                    game.setCoID(rs.getInt("coId"));
                    game.setDescription(rs.getString("description"));
                    game.setVersion(rs.getString("version"));
                    game.setRating(rs.getInt("rating"));
                    game.setReleaseDate(rs.getDate("releaseDate"));
                    game.setPrice(rs.getDouble("price"));
                    game.setState(rs.getString("state"));
                    game.setStatus(rs.getInt("status"));
                    for (Game game1 : list) {
                        if (game1.getGid() == game.getGid()) {
                            game.setStatus(game.getStatus() + 2);
                            check = false;
                            break;
                        }
                    }
                    if (check) {
                        list.add(game);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(DAOGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collections.sort(list, (Game g1, Game g2) -> g1.getStatus() - g2.getStatus());
        return list;
    }

    public ArrayList<Game> getGame_SameCategory(int gId) {
        sql = "select distinct gid from Game_Category "
                + "where caId in (Select top 1 caid from Game_Category where status=1 and gId=" + gId + ")" + "and gId <> " + gId;
        ArrayList<Game> list = new ArrayList<>();
        Game x = null;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int gameId = rs.getInt("gId");
                x = getGameById(gameId);
                list.add(x);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Game> getGame_SamePlatform(int gId) {
        sql = "select distinct gid from Game_Platform where plId in (Select top 1 plid from Game_Platform where status=1 and gId=" + gId + ")";
        ArrayList<Game> list = new ArrayList<>();
        Game x = null;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int gameId = rs.getInt("gId");
                x = getGameById(gameId);
                list.add(x);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Game> getGame_SameCom(int gId) {
        sql = "select gid from Game where coId = (Select coid from Game where status=1 and gId=" + gId + ")";
        ArrayList<Game> list = new ArrayList<>();
        Game x = null;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int gameId = rs.getInt("gId");
                x = getGameById(gameId);
                list.add(x);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Game> getGamesSort(String crite, boolean descend) {
        String sql = "SELECT TOP 10 * FROM Game where status=1 ORDER BY " + crite;
        if (descend) {
            sql += " desc";
        } else {
            sql += " asc";
        }
        ArrayList<Game> list = new ArrayList<>();
        ResultSet rs = dbConn.getData(sql);
        try {
            while (rs.next()) {
                Game game = new Game();
                game.setGid(rs.getInt("gId"));
                game.setTitle(rs.getString("Title"));
                game.setCoID(rs.getInt("coId"));
                game.setDescription(rs.getString("description"));
                game.setVersion(rs.getString("version"));
                game.setRating(rs.getInt("rating"));
                game.setReleaseDate(rs.getDate("releaseDate"));
                game.setPrice(rs.getDouble("price"));
                game.setState(rs.getString("state"));
                game.setStatus(rs.getInt("status"));
                list.add(game);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Game> getGameByUIdFromLibrary(int uId) {
        ArrayList<Game> list = new ArrayList<>();

        String sql = "select gId from Library where uId = " + uId + " and [type] = 'owned'";
        ResultSet rs = dbConn.getData(sql);
        try {
            while (rs.next()) {
                Game g = getGameById(rs.getInt("gId"));
                list.add(g);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public ArrayList<Game> getGameByUIdFromLibrarySorted(int uId) {
        ArrayList<Game> list = new ArrayList<>();

        String sql = "select gId from Library where uId = " + uId + " and [type] = 'owned'";
        ResultSet rs = dbConn.getData(sql);
        try {
            while (rs.next()) {
                Game g = getGameById(rs.getInt("gId"));
                list.add(g);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        Collections.sort(list, new Comparator<Game>() {
           
            @Override
            public int compare(Game g, Game g1) {
                return (g.getTitle().compareTo(g1.getTitle()));
            }
        });
        return list;
    }
    
    public ArrayList<Game> getGameByUIdFromWishlist(int uId) {
        ArrayList<Game> list = new ArrayList<>();

        String sql = "select gId from Library where uId = " + uId + " and [type] = 'favour'";
        ResultSet rs = dbConn.getData(sql);
        try {
            while (rs.next()) {
                Game g = getGameById(rs.getInt("gId"));
                list.add(g);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public ArrayList<Game> getGameByUIdFromWishlistSorted(int uId) {
        ArrayList<Game> list = new ArrayList<>();

        String sql = "select gId from Library where uId = " + uId + " and [type] = 'favour'";
        ResultSet rs = dbConn.getData(sql);
        try {
            while (rs.next()) {
                Game g = getGameById(rs.getInt("gId"));
                list.add(g);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        Collections.sort(list, new Comparator<Game>() {
           
            @Override
            public int compare(Game g, Game g1) {
                return (g.getTitle().compareTo(g1.getTitle()));
            }
        });
        return list;
    }
    
//    public static void main(String[] args) {
//        DAOGame dao = new DAOGame(new DBConnection());
//        System.out.println(dao.updateInfoGame(dao.getGameById(22)));
//    }
}
