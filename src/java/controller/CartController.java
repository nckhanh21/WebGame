/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.*;
import entity.Order;
import entity.OrderDetail;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DAOCategory;
import model.DAOGame;
import model.DAOGame_Category;
import model.DAOLibrary;
import model.DAOOrder;
import model.DAOOrder_Detail;
import model.DAOPlatform;
import model.DAOUser;
import model.DBConnection;

/**
 *
 * @author Admin
 */
public class CartController extends HttpServlet {

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
    DAOOrder daoOrder = new DAOOrder(dbCon);
    DAOOrder_Detail dAOOrder_Detail = new DAOOrder_Detail(dbCon);
    DAOUser daoUser = new DAOUser(dbCon);
    DAOLibrary daoLibrary = new DAOLibrary(dbCon);
    DAOGame_Category daoGaCa = new DAOGame_Category(dbCon);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String service = request.getParameter("service");
            User user = (User) request.getSession().getAttribute("currUser");
            ArrayList<Game> userLibrary = (ArrayList<Game>) request.getSession().getAttribute("Library");

            if (service.equalsIgnoreCase("AddToCart")) {
                int gameId = Integer.parseInt(request.getParameter("gameId"));
                Game addGame = daoGame.getGameById(gameId);
                ArrayList<Game> ShoppingCart = (ArrayList<Game>) request.getSession().getAttribute("ShoppingCart");
                for (int i = 0; i < ShoppingCart.size(); i++) {
                    if (ShoppingCart.get(i).getGid() == gameId) {
                        request.setAttribute("alMess", "Game already in cart");
                        sendDispatcher(request, response, "Cart.jsp");
                        return;
                    }
                }
                for (int i = 0; i < userLibrary.size(); i++) {
                    if(userLibrary.get(i).getGid() == gameId){
                        request.setAttribute("alMess", "You have already bought this game");
                        sendDispatcher(request, response, "Cart.jsp");
                        return;
                    }
                }
                ShoppingCart.add(addGame);
                request.getSession().setAttribute("ShoppingCart", ShoppingCart);
                sendDispatcher(request, response, "index.jsp");
            }

            if (service.equalsIgnoreCase("RemoveFromCart")) {
                int gameId = Integer.parseInt(request.getParameter("gameId"));
                ArrayList<Game> ShoppingCart = (ArrayList<Game>) request.getSession().getAttribute("ShoppingCart");
                for (int i = 0; i < ShoppingCart.size(); i++) {
                    if (ShoppingCart.get(i).getGid() == gameId) {
                        ShoppingCart.remove(i);
                        break;
                    }
                }
                request.getSession().setAttribute("ShoppingCart", ShoppingCart);
                sendDispatcher(request, response, "Cart.jsp");
            }

            if (service.equalsIgnoreCase("CheckOut")) {
                ArrayList<Game> ShoppingCart = (ArrayList<Game>) request.getSession().getAttribute("ShoppingCart");
                double total = 0.0;
                for (Game game : ShoppingCart) {
                    total += game.getPrice();
                }
                total = total * 1.1;
                total = Double.parseDouble(String.format("%.2f", total));
                double wallet = user.getWallet();
                if (wallet >= total) {
                   
                    Order addOrder = new Order(-1, user.getuId(), "buygame", total);
                    daoOrder.insertOrder(addOrder);
                    int oId = daoOrder.getLatestOrderByUseridAndTotal(user.getuId(), total);
                    OrderDetail orderDetail = new OrderDetail();
                    for (Game game : ShoppingCart) {
                        orderDetail.setoId(oId);
                        orderDetail.setgId(game.getGid());
                        orderDetail.setPrice(game.getPrice());
                        dAOOrder_Detail.insertOrderDetail(orderDetail);
                    }                    
                    
                    Library addLib = new Library();
                    for (int i = 0; i < ShoppingCart.size(); i++) {
                        addLib.setuId(user.getuId());
                        addLib.setgId(ShoppingCart.get(i).getGid());
                        daoLibrary.insertLibrary(addLib);
                    }
                    ArrayList<Game> Library = daoLibrary.getGameByUIdAndStatus(user.getuId(), 1);
                    request.getSession().setAttribute("Library", Library);

                    ShoppingCart.removeAll(ShoppingCart);
                    user.setWallet(wallet - total);
                    daoUser.updateInfoUser(user);
                    request.getSession().setAttribute("currUser", user);
                    request.getSession().setAttribute("ShoppingCart", ShoppingCart);
                    request.setAttribute("messCheckOut", "Your order had been added!");
                    sendDispatcher(request, response, "Cart.jsp");
                } else {
                    request.setAttribute("mess", "Your balance is not enough");
                    sendDispatcher(request, response, "Cart.jsp");
                }
            }
            
            if (service.equalsIgnoreCase("AddToLibrary")) {
                int gameId = Integer.parseInt(request.getParameter("gameId"));
                
                Library lib = new Library();
                lib.setuId(user.getuId());
                lib.setgId(gameId);
                daoLibrary.insertLibrary(lib);
                daoLibrary.deleteOwnedInWishlist(lib);
                ArrayList<Game> Library = daoLibrary.getGameByUIdAndStatus(user.getuId(), 1);
                request.getSession().setAttribute("Library", Library);
                request.setAttribute("alMess", "<script>document.getElementById(\"gameAdd\").click();</script>");
                request.getRequestDispatcher("GameControllerMap?service=getGame&gameID="  + gameId).forward(request, response);
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
