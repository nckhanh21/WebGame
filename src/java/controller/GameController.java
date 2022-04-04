/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.*;
import entity.Company;
import entity.Galery;
import entity.Game;
import entity.Library;
import entity.Platform;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.*;
import model.DAOCompany;
import model.DAOGalery;
import model.DAOGame;
import model.DAOGame_Category;
import model.DAOGame_Platform;
import model.DAOLibrary;
import model.DAOPlatform;
import model.DBConnection;

/**
 *
 * @author dumyd
 */
public class GameController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    DBConnection dbCon = new DBConnection();
    DAOGame daoGame = new DAOGame(dbCon);
    DAOCategory daoCate = new DAOCategory(dbCon);
    DAOPlatform daoPlat = new DAOPlatform(dbCon);
    DAOGame_Category daoGaCa = new DAOGame_Category(dbCon);
    DAOGame_Platform daoGaPl = new DAOGame_Platform(dbCon);
    DAOCompany daoCom = new DAOCompany(dbCon);
    DAOGalery daoGalery = new DAOGalery(dbCon);
    DAOLibrary daoLibrary = new DAOLibrary(dbCon);
    DAOComment daoComment = new DAOComment(dbCon);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            User user = (User) request.getSession().getAttribute("currUser");
            String service = request.getParameter("service");
            if (service == null) {
                service = "";
            }

            if (service.equalsIgnoreCase("getGame")) {
                int gameID = Integer.parseInt(request.getParameter("gameID"));
                Game game = daoGame.getGameById(gameID);
                request.setAttribute("game", game);
                Company com = daoCom.getCompany(game.getCoID());
                request.setAttribute("com", com);
                ArrayList<Platform> listPl = daoPlat.getPlatform(gameID);
                request.setAttribute("listPl", listPl);
                ArrayList<Category> listCa = daoCate.getCategory(gameID);
                request.setAttribute("listCa", listCa);
                ArrayList<Game> listGaCa = daoGame.getGame_SameCategory(gameID);
                request.setAttribute("listGaCa", listGaCa);
                ArrayList<Game> listGaPl = daoGame.getGame_SamePlatform(gameID);
                request.setAttribute("listGaPl", listGaPl);
                
                ArrayList<Game> listGaCo = daoGame.getGame_SameCom(gameID);
                request.setAttribute("listGaCo", listGaCo);
                ArrayList<Galery> listGameGalery = daoGalery.getFullGameGalery(gameID);
                request.setAttribute("listGameGalery", listGameGalery);
                ArrayList<Comment> comments = daoComment.getCommentsByGameId(gameID);
                request.setAttribute("comment", comments);

                if (user != null) {
                    ArrayList<Game> wishlist = daoLibrary.getGameInWishList(user.getuId(), 1);
                    request.getSession().setAttribute("wishlist", wishlist);
                }
                sendDispatcher(request, response, "game.jsp");
            }

            if (service.equalsIgnoreCase("addWishlist")) {
                int gameId = Integer.parseInt(request.getParameter("gameID"));
                Library list = new Library();
                list.setuId(user.getuId());
                list.setgId(gameId);
                daoLibrary.insertWishLish(list);
                request.getRequestDispatcher("GameControllerMap?service=getGame&gameID=" + gameId).forward(request, response);
            }

            if (service.equalsIgnoreCase("deleteWishlist")) {
                int gameId = Integer.parseInt(request.getParameter("gameID"));
                Library list = new Library();
                list.setuId(user.getuId());
                list.setgId(gameId);
                daoLibrary.deleteWishlist(list);
                request.getRequestDispatcher("GameControllerMap?service=getGame&gameID=" + gameId).forward(request, response);
            }

            if (service.equalsIgnoreCase("displayGaCa")) {
                int gameId = Integer.parseInt(request.getParameter("gameID"));
                ArrayList<Game> listGaCa = daoGame.getGame_SameCategory(gameId);
                request.setAttribute("listGame", listGaCa);
                sendDispatcher(request, response, "allgame.jsp");
            }
   
            if (service.equalsIgnoreCase("displayGaPl")) {
                int gameId = Integer.parseInt(request.getParameter("gameID"));
                ArrayList<Game> listGaPl = daoGame.getGame_SamePlatform(gameId);
                request.setAttribute("listGame", listGaPl);
                sendDispatcher(request, response, "allgame.jsp");
            }

            if (service.equalsIgnoreCase("comment")) {
                int gameId = Integer.parseInt(request.getParameter("gameID"));
                int rating = Integer.parseInt(request.getParameter("rating"));
                String content = (String) request.getParameter("content");
                ArrayList<Comment> comments = daoComment.getCommentsByGameId(gameId);
                int numOfComment = comments.size() + 2;
                Game update = daoGame.getGameById(gameId);
                double newRating = rating / numOfComment + update.getRating() * (numOfComment - 1) / numOfComment;
                int x = (int) Math.round(newRating);
                update.setRating(x);
                daoGame.updateInfoGame(update);
                Comment newCom = new Comment();
                newCom.setgId(gameId);
                newCom.setuId(user.getuId());
                newCom.setRating(rating);
                newCom.setContent(content);
                daoComment.insertComment(newCom);
                sendDispatcher(request, response, "GameControllerMap?service=getGame&gameID=" + gameId);
            }
        }

    }

    public void sendDispatcher(HttpServletRequest request, HttpServletResponse response, String path) {
        try {
            RequestDispatcher rd = request.getRequestDispatcher(path);
            rd.forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
