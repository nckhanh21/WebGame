/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.*;
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
import model.DBConnection;

/**
 *
 * @author Admin
 */
public class ProductController extends HttpServlet {

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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String service = request.getParameter("service");

            if (service == null) {
                service = "HomePage";
            }
            //Home.jsp
            if (service.equalsIgnoreCase("HomePage")) {
                ArrayList<Game> listGame = daoGame.getAllGame();
                request.setAttribute("listGame", listGame);
                ArrayList<Game> listHotGame = daoGame.getGameByRating();
                request.setAttribute("listHotGame", listHotGame);
                ArrayList<Game> listNewGame = daoGame.getGamesSort("releaseDate", true);
                request.setAttribute("listNewGame", listNewGame);
                ArrayList<Game> listFreeGame = daoGame.getGamesSort("price", false);
                request.setAttribute("listFreeGame", listFreeGame);
                ArrayList<Category> listCategory = daoCate.getAllCategories();
                request.setAttribute("listCategory", listCategory);
                ArrayList<Platform> listPlatform = daoPlat.getAllPlatforms();
                request.setAttribute("listPlatform", listPlatform);
                out.print("<h1>Okay</h1>");
                sendDispatcher(request, response, "/index.jsp");
            }

            if (service.equalsIgnoreCase("searchByName")) {
                ArrayList<Game> listGame = daoGame.getGameByName(request.getParameter("gameName"));
                request.setAttribute("listGame", listGame);
                ArrayList<Game> listHotGame = daoGame.getGameByRating();
                request.setAttribute("listHotGame", listHotGame);
                ArrayList<Game> listNewGame = daoGame.getGamesSort("releaseDate", true);
                request.setAttribute("listNewGame", listNewGame);
                ArrayList<Game> listFreeGame = daoGame.getGamesSort("price", false);
                request.setAttribute("listFreeGame", listFreeGame);
                ArrayList<Category> listCategory = daoCate.getAllCategories();
                request.setAttribute("listCategory", listCategory);
                ArrayList<Platform> listPlatform = daoPlat.getAllPlatforms();
                request.setAttribute("listPlatform", listPlatform);
                sendDispatcher(request, response, "allgame.jsp");
            }

            //menu.jsp
            if (service.equalsIgnoreCase("filterByCate")) {
                String cateID[] = request.getParameterValues("cateID");
                ArrayList<Game> listGame = daoGame.getGameByMultiCategoryId(cateID);
                request.setAttribute("listGame", listGame);
                ArrayList<Game> listHotGame = daoGame.getGameByRating();
                request.setAttribute("listHotGame", listHotGame);
                ArrayList<Game> listNewGame = daoGame.getGamesSort("releaseDate", true);
                request.setAttribute("listNewGame", listNewGame);
                ArrayList<Game> listFreeGame = daoGame.getGamesSort("price", false);
                request.setAttribute("listFreeGame", listFreeGame);
                ArrayList<Game> listAllGame = daoGame.getAllGame();
                request.setAttribute("listAllGame", listAllGame);
                ArrayList<Category> listCategory = daoCate.getAllCategories();
                request.setAttribute("listCategory", listCategory);
                ArrayList<Platform> listPlatform = daoPlat.getAllPlatforms();
                request.setAttribute("listPlatform", listPlatform);
                sendDispatcher(request, response, "/index.jsp");
            }

            if (service.equalsIgnoreCase("searchByCate")) {
                int cateID = Integer.parseInt(request.getParameter("cateID"));
                ArrayList<Game> listGame = daoGame.getGameByCategoryId(cateID);
                request.setAttribute("listGame", listGame);
                ArrayList<Game> listHotGame = daoGame.getGameByRating();
                request.setAttribute("listHotGame", listHotGame);
                ArrayList<Game> listNewGame = daoGame.getGamesSort("releaseDate", true);
                request.setAttribute("listNewGame", listNewGame);
                ArrayList<Game> listFreeGame = daoGame.getGamesSort("price", false);
                request.setAttribute("listFreeGame", listFreeGame);
                ArrayList<Category> listCategory = daoCate.getAllCategories();
                request.setAttribute("listCategory", listCategory);
                ArrayList<Platform> listPlatform = daoPlat.getAllPlatforms();
                request.setAttribute("listPlatform", listPlatform);
                sendDispatcher(request, response, "/index.jsp");
            }
            
            if (service.equalsIgnoreCase("searchByCate1")) {
                int cateID = Integer.parseInt(request.getParameter("cateID"));
                ArrayList<Game> listGame = daoGame.getGameByCategoryId(cateID);
                request.setAttribute("listGame", listGame);
                ArrayList<Game> listHotGame = daoGame.getGameByRating();
                request.setAttribute("listHotGame", listHotGame);
                ArrayList<Game> listNewGame = daoGame.getGamesSort("releaseDate", true);
                request.setAttribute("listNewGame", listNewGame);
                ArrayList<Game> listFreeGame = daoGame.getGamesSort("price", false);
                request.setAttribute("listFreeGame", listFreeGame);
                ArrayList<Category> listCategory = daoCate.getAllCategories();
                request.setAttribute("listCategory", listCategory);
                ArrayList<Platform> listPlatform = daoPlat.getAllPlatforms();
                request.setAttribute("listPlatform", listPlatform);
                sendDispatcher(request, response, "allgame.jsp");
            }

            if (service.equalsIgnoreCase("searchByPlat")) {
                String title;
                int platID = Integer.parseInt(request.getParameter("platID"));
                ArrayList<Game> listGame = daoGame.getGameByPlatformId(platID);
                request.setAttribute("listGame", listGame);
                ArrayList<Game> listNewGame = daoGame.getGamesSort("releaseDate", true);
                request.setAttribute("listNewGame", listNewGame);
                ArrayList<Game> listHotGame = daoGame.getGameByRating();
                request.setAttribute("listHotGame", listHotGame);
                ArrayList<Game> listFreeGame = daoGame.getGamesSort("price", false);
                request.setAttribute("listFreeGame", listFreeGame);
                ArrayList<Category> listCategory = daoCate.getAllCategories();
                request.setAttribute("listCategory", listCategory);
                ArrayList<Platform> listPlatform = daoPlat.getAllPlatforms();
                request.setAttribute("listPlatform", listPlatform);
                if (platID == 1) {
                    title = "PS4 GAME";
                } else if (platID == 2) {
                    title = "XBOX GAME";
                } else if (platID == 3) {
                    title = "PC GAME";
                } else if (platID == 4) {
                    title = "Mobile GAME";
                } else {
                    title = "NS GAME";
                }
                request.setAttribute("title", title);
                sendDispatcher(request, response, "/index.jsp");
            }
            
            if (service.equalsIgnoreCase("searchByPlat1")) {
                int platID = Integer.parseInt(request.getParameter("platID"));
                ArrayList<Game> listGame = daoGame.getGameByPlatformId(platID);
                request.setAttribute("listGame", listGame);
                ArrayList<Game> listNewGame = daoGame.getGamesSort("releaseDate", true);
                request.setAttribute("listNewGame", listNewGame);
                ArrayList<Game> listHotGame = daoGame.getGameByRating();
                request.setAttribute("listHotGame", listHotGame);
                ArrayList<Game> listFreeGame = daoGame.getGamesSort("price", false);
                request.setAttribute("listFreeGame", listFreeGame);
                ArrayList<Category> listCategory = daoCate.getAllCategories();
                request.setAttribute("listCategory", listCategory);
                ArrayList<Platform> listPlatform = daoPlat.getAllPlatforms();
                request.setAttribute("listPlatform", listPlatform);
                sendDispatcher(request, response, "/allgame.jsp");
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

    public String getPictureName(String path) {
        for (int i = path.length() - 1; i > 0; i--) {
            if (path.charAt(i) == '\\') {
                return path.substring(i + 1);
            }
        }
        return path;
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
