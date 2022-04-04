/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.sun.faces.util.CollectionsUtils;
import entity.Category;
import entity.Company;
import entity.Galery;
import entity.Game;
import entity.Order;
import entity.OrderDetail;
import entity.Platform;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DAOCategory;
import model.DAOCompany;
import model.DAOGalery;
import model.DAOGame;
import model.DAOGame_Category;
import model.DAOOrder;
import model.DAOOrder_Detail;
import model.DAOPlatform;
import model.DAOUser;
import model.DBConnection;

/**
 *
 * @author dumyd
 */
public class AdminController extends HttpServlet {

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
    DAOGame     daoGame = new DAOGame(dbCon);
    DAOCategory daoCate = new DAOCategory(dbCon);
    DAOPlatform daoPlat = new DAOPlatform(dbCon);
    DAOGame_Category daoGaCa = new DAOGame_Category(dbCon);
    DAOCompany daoCom = new DAOCompany(dbCon);
    DAOUser daoUser = new DAOUser(dbCon);
    DAOOrder daoOrder = new DAOOrder(dbCon);
    DAOGalery daoGalery = new DAOGalery(dbCon);
    DAOOrder_Detail daoDetail = new DAOOrder_Detail(dbCon);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String service = request.getParameter("service");
            
            if (service == null) {
                service = "HomeAdmin";
            }
            //Home.jsp
            if (service.equalsIgnoreCase("HomeAdmin")) {
                // <editor-fold defaultstate="collapsed" desc="SetParams">
                ArrayList<Game> listGame = daoGame.getTrueGame();
                request.setAttribute("listGame", listGame);
                ArrayList<Category> listCategory = daoCate.getTrueCategories();
                request.setAttribute("listCategory", listCategory);
                ArrayList<Platform> listPlatform = daoPlat.getTruePlatforms();
                request.setAttribute("listPlatform", listPlatform);
                ArrayList<Company> listCompany = daoCom.getTrueCompany();
                request.setAttribute("listCompany", listCompany);
                ArrayList<User> listUser = daoUser.getTrueUser();
                request.setAttribute("listUser", listUser);
                HashMap<Game, ArrayList<Galery>> listGameGalery = new HashMap<Game,ArrayList<Galery>>();
                for (Game game : listGame) {
                    ArrayList<Galery> gameGalery = daoGalery.getFullGameGalery(game.getGid());
                    listGameGalery.put(game, gameGalery);                
                }
                request.setAttribute("listGameGalery", listGameGalery);
                ArrayList<Order> orderList = daoOrder.getAllOrderByDate();
                request.setAttribute("orderList", orderList);
                // </editor-fold>
                sendDispatcher(request, response, "admin/adminIndex.jsp");
            }
            
            if (service.equalsIgnoreCase("logout")){
                request.getSession().invalidate();
                sendDispatcher(request, response, "/login.jsp");
            }
            
            if (service.equalsIgnoreCase("addGame")){
                String title = request.getParameter("title");
                int coId = Integer.parseInt(request.getParameter("coId"));
                String desc = request.getParameter("description");
                String version = request.getParameter("version");
                Date releaseDate = Date.valueOf(request.getParameter("releaseDate"));
                int rating = Integer.parseInt(request.getParameter("rating"));
                double price= Double.parseDouble(request.getParameter("price"));
                String state = request.getParameter("state");
                Game newGame = new Game(0, title, coId, desc, version, rating, releaseDate, price, state,0);
                int n = daoGame.insertGame(newGame);
                if (n!=0) request.setAttribute("message", "Insert Game Successfully");
                else request.setAttribute("message", "Insert Game Failed");
                request.setAttribute("tab", "gameAdd");
                request.setAttribute("newGame", newGame);
                // <editor-fold defaultstate="collapsed" desc="SetParams">
                ArrayList<Game> listGame = daoGame.getTrueGame();
                request.setAttribute("listGame", listGame);
                ArrayList<Category> listCategory = daoCate.getTrueCategories();
                request.setAttribute("listCategory", listCategory);
                ArrayList<Platform> listPlatform = daoPlat.getTruePlatforms();
                request.setAttribute("listPlatform", listPlatform);
                ArrayList<Company> listCompany = daoCom.getTrueCompany();
                request.setAttribute("listCompany", listCompany);
                ArrayList<User> listUser = daoUser.getTrueUser();
                request.setAttribute("listUser", listUser);
                HashMap<Game, ArrayList<Galery>> listGameGalery = new HashMap<Game,ArrayList<Galery>>();
                for (Game game : listGame) {
                    ArrayList<Galery> gameGalery = daoGalery.getFullGameGalery(game.getGid());
                    listGameGalery.put(game, gameGalery);                
                }
                request.setAttribute("listGameGalery", listGameGalery);
                ArrayList<Order> orderList = daoOrder.getAllOrderByDate();
                request.setAttribute("orderList", orderList);
                // </editor-fold>
                sendDispatcher(request, response, "admin/adminIndex.jsp");
            }
            
            if (service.equalsIgnoreCase("updateGame")){
                int gId = Integer.parseInt(request.getParameter("gId"));
                Game updateGame = daoGame.getGameById(gId);
                String title = request.getParameter("title");
                if (title.trim().length()!=0) updateGame.setTitle(title);
                int coId = Integer.parseInt(request.getParameter("coId"));
                updateGame.setCoID(coId);
                String desc = request.getParameter("description");
                if (desc!=null && desc.trim().length()!=0) updateGame.setDescription(desc);
                String version = request.getParameter("version");
                if (version.trim().length()!=0) updateGame.setDescription(version);
                String releaseDate = request.getParameter("releaseDate");
                if (releaseDate.trim().length()!=0) updateGame.setReleaseDate(Date.valueOf(releaseDate));
                String rating = request.getParameter("rating");
                if (rating.trim().length()!=0) updateGame.setRating(Integer.parseInt(rating));
                String price = request.getParameter("price");
                if (price.trim().length()!=0) updateGame.setPrice(Double.parseDouble(price));
                String state = request.getParameter("state");
                updateGame.setState(state);
                int n = daoGame.updateInfoGame(updateGame);
                if (n!=0) request.setAttribute("message", "Update Game Successfully");
                else request.setAttribute("message", "Update Game Failed");
                request.setAttribute("tab", "gameUpdate");
                // <editor-fold defaultstate="collapsed" desc="SetParams">
                ArrayList<Game> listGame = daoGame.getTrueGame();
                request.setAttribute("listGame", listGame);
                ArrayList<Category> listCategory = daoCate.getTrueCategories();
                request.setAttribute("listCategory", listCategory);
                ArrayList<Platform> listPlatform = daoPlat.getTruePlatforms();
                request.setAttribute("listPlatform", listPlatform);
                ArrayList<Company> listCompany = daoCom.getTrueCompany();
                request.setAttribute("listCompany", listCompany);
                ArrayList<User> listUser = daoUser.getTrueUser();
                request.setAttribute("listUser", listUser);
                HashMap<Game, ArrayList<Galery>> listGameGalery = new HashMap<Game,ArrayList<Galery>>();
                for (Game game : listGame) {
                    ArrayList<Galery> gameGalery = daoGalery.getFullGameGalery(game.getGid());
                    listGameGalery.put(game, gameGalery);                
                }
                request.setAttribute("listGameGalery", listGameGalery);
                ArrayList<Order> orderList = daoOrder.getAllOrderByDate();
                request.setAttribute("orderList", orderList);
                // </editor-fold>
                request.setAttribute("updateGame", updateGame);
                sendDispatcher(request, response, "admin/adminIndex.jsp");
            }
            
            if (service.equalsIgnoreCase("delGame")) {
                int gId = Integer.parseInt(request.getParameter("gId"));
                int status = Integer.parseInt(request.getParameter("status"));
                int n = daoGame.changeStatus(gId, status);
                if (n!=0) request.setAttribute("message", "Update Game Successfully");
                else request.setAttribute("message", "Update Game Failed");
                request.setAttribute("tab", "gameDel");
                request.setAttribute("delGame", "Set Game with Id="+gId+" status to "+status);
                // <editor-fold defaultstate="collapsed" desc="SetParams">
                ArrayList<Game> listGame = daoGame.getTrueGame();
                request.setAttribute("listGame", listGame);
                ArrayList<Category> listCategory = daoCate.getTrueCategories();
                request.setAttribute("listCategory", listCategory);
                ArrayList<Platform> listPlatform = daoPlat.getTruePlatforms();
                request.setAttribute("listPlatform", listPlatform);
                ArrayList<Company> listCompany = daoCom.getTrueCompany();
                request.setAttribute("listCompany", listCompany);
                ArrayList<User> listUser = daoUser.getTrueUser();
                request.setAttribute("listUser", listUser);
                HashMap<Game, ArrayList<Galery>> listGameGalery = new HashMap<Game,ArrayList<Galery>>();
                for (Game game : listGame) {
                    ArrayList<Galery> gameGalery = daoGalery.getFullGameGalery(game.getGid());
                    listGameGalery.put(game, gameGalery);                
                }
                request.setAttribute("listGameGalery", listGameGalery);
                ArrayList<Order> orderList = daoOrder.getAllOrderByDate();
                request.setAttribute("orderList", orderList);
                // </editor-fold>
                sendDispatcher(request, response, "admin/adminIndex.jsp");
            }
            
            if (service.equalsIgnoreCase("addUser")){
                String username = request.getParameter("username");
                String uname = request.getParameter("uname");
                String mail = request.getParameter("mail");
                String phone = request.getParameter("phone");
                String address = request.getParameter("address");
                String pass = request.getParameter("pass");
                User newUser = new User(0, uname, 0, null, mail, phone, address, 0, "User", username, pass, 1);
                int n = daoUser.addUser(newUser);
                if (n!=0) request.setAttribute("message", "Insert User Successfully");
                else request.setAttribute("message", "Insert User Failed");
                request.setAttribute("tab", "userAdd");
                request.setAttribute("newUser",newUser);
                // <editor-fold defaultstate="collapsed" desc="SetParams">
                ArrayList<Game> listGame = daoGame.getTrueGame();
                request.setAttribute("listGame", listGame);
                ArrayList<Category> listCategory = daoCate.getTrueCategories();
                request.setAttribute("listCategory", listCategory);
                ArrayList<Platform> listPlatform = daoPlat.getTruePlatforms();
                request.setAttribute("listPlatform", listPlatform);
                ArrayList<Company> listCompany = daoCom.getTrueCompany();
                request.setAttribute("listCompany", listCompany);
                ArrayList<User> listUser = daoUser.getTrueUser();
                request.setAttribute("listUser", listUser);
                HashMap<Game, ArrayList<Galery>> listGameGalery = new HashMap<Game,ArrayList<Galery>>();
                for (Game game : listGame) {
                    ArrayList<Galery> gameGalery = daoGalery.getFullGameGalery(game.getGid());
                    listGameGalery.put(game, gameGalery);                
                }
                request.setAttribute("listGameGalery", listGameGalery);
                ArrayList<Order> orderList = daoOrder.getAllOrderByDate();
                request.setAttribute("orderList", orderList);
                // </editor-fold>
                sendDispatcher(request, response, "admin/adminIndex.jsp");
            }
            
            if (service.equalsIgnoreCase("updateUser")){
                int uId = Integer.parseInt(request.getParameter("uId"));
                User updateUser = daoUser.getUserById(uId);
                String username = request.getParameter("username");
                if (username.trim().length()!=0) updateUser.setUsername(username);
                String uname = request.getParameter("uname");
                if (uname.trim().length()!=0) updateUser.setuName(uname);
                String exp = request.getParameter("exp");
                if (exp.trim().length()!=0) updateUser.setExperience(Integer.parseInt(exp));
                String mail = request.getParameter("mail");
                if (mail.trim().length()!=0) updateUser.setuMail(mail);
                String phone = request.getParameter("phone");
                if (phone.trim().length()!=0) updateUser.setuPhone(phone);
                String address = request.getParameter("address");
                if (address.trim().length()!=0) updateUser.setuAddress(address);
                String wallet = request.getParameter("wallet");
                if (wallet.trim().length()!=0) updateUser.setWallet(Double.parseDouble(wallet));
                String role = request.getParameter("role");
                updateUser.setSystem_role(role);
                int n = daoUser.updateInfoUser(updateUser);
                if (n!=0) request.setAttribute("message", "Update User Successfully");
                else request.setAttribute("message", "Update User Failed");
                request.setAttribute("tab", "userUpdate");
                // <editor-fold defaultstate="collapsed" desc="SetParams">
                ArrayList<Game> listGame = daoGame.getTrueGame();
                request.setAttribute("listGame", listGame);
                ArrayList<Category> listCategory = daoCate.getTrueCategories();
                request.setAttribute("listCategory", listCategory);
                ArrayList<Platform> listPlatform = daoPlat.getTruePlatforms();
                request.setAttribute("listPlatform", listPlatform);
                ArrayList<Company> listCompany = daoCom.getTrueCompany();
                request.setAttribute("listCompany", listCompany);
                ArrayList<User> listUser = daoUser.getTrueUser();
                request.setAttribute("listUser", listUser);
                HashMap<Game, ArrayList<Galery>> listGameGalery = new HashMap<Game,ArrayList<Galery>>();
                for (Game game : listGame) {
                    ArrayList<Galery> gameGalery = daoGalery.getFullGameGalery(game.getGid());
                    listGameGalery.put(game, gameGalery);                
                }
                request.setAttribute("listGameGalery", listGameGalery);
                ArrayList<Order> orderList = daoOrder.getAllOrderByDate();
                request.setAttribute("orderList", orderList);
                // </editor-fold>
                request.setAttribute("updateUser", updateUser);
                sendDispatcher(request, response, "admin/adminIndex.jsp");
            }
            
            if (service.equalsIgnoreCase("delUser")) {
                int uId = Integer.parseInt(request.getParameter("uId"));
                int status = Integer.parseInt(request.getParameter("status"));
                int n = daoUser.changeStatus(uId, status);
                if (n!=0) request.setAttribute("message", "Update User Successfully");
                else request.setAttribute("message", "Update User Failed");
                request.setAttribute("tab", "userDel");
                // <editor-fold defaultstate="collapsed" desc="SetParams">
                ArrayList<Game> listGame = daoGame.getTrueGame();
                request.setAttribute("listGame", listGame);
                ArrayList<Category> listCategory = daoCate.getTrueCategories();
                request.setAttribute("listCategory", listCategory);
                ArrayList<Platform> listPlatform = daoPlat.getTruePlatforms();
                request.setAttribute("listPlatform", listPlatform);
                ArrayList<Company> listCompany = daoCom.getTrueCompany();
                request.setAttribute("listCompany", listCompany);
                ArrayList<User> listUser = daoUser.getTrueUser();
                request.setAttribute("listUser", listUser);
                HashMap<Game, ArrayList<Galery>> listGameGalery = new HashMap<Game,ArrayList<Galery>>();
                for (Game game : listGame) {
                    ArrayList<Galery> gameGalery = daoGalery.getFullGameGalery(game.getGid());
                    listGameGalery.put(game, gameGalery);                
                }
                request.setAttribute("listGameGalery", listGameGalery);
                ArrayList<Order> orderList = daoOrder.getAllOrderByDate();
                request.setAttribute("orderList", orderList);
                // </editor-fold>
                request.setAttribute("delUser", "Set User with Id="+uId+" status to "+status);
                sendDispatcher(request, response, "admin/adminIndex.jsp");
            }
            
            if (service.equalsIgnoreCase("addCom")){
                String coname = request.getParameter("coName");
                Date foundDate = Date.valueOf(request.getParameter("foundDate"));
                String desc = request.getParameter("description");
                String logo = request.getParameter("logo");
                String address = request.getParameter("address");
                String phone = request.getParameter("phone");
                if (phone.length()==9) phone+= request.getParameter("country");
                String mail = request.getParameter("mail");
                Company newCom = new Company(0, coname, foundDate, desc, logo, address, phone, mail, 0);
                int n = daoCom.insertCompany(newCom);
                if (n!=0) request.setAttribute("message", "Insert Company Successfully");
                else request.setAttribute("message", "Insert Company Failed");
                request.setAttribute("tab", "companyAdd");
                request.setAttribute("newCom", newCom);
                // <editor-fold defaultstate="collapsed" desc="SetParams">
                ArrayList<Game> listGame = daoGame.getTrueGame();
                request.setAttribute("listGame", listGame);
                ArrayList<Category> listCategory = daoCate.getTrueCategories();
                request.setAttribute("listCategory", listCategory);
                ArrayList<Platform> listPlatform = daoPlat.getTruePlatforms();
                request.setAttribute("listPlatform", listPlatform);
                ArrayList<Company> listCompany = daoCom.getTrueCompany();
                request.setAttribute("listCompany", listCompany);
                ArrayList<User> listUser = daoUser.getTrueUser();
                request.setAttribute("listUser", listUser);
                HashMap<Game, ArrayList<Galery>> listGameGalery = new HashMap<Game,ArrayList<Galery>>();
                for (Game game : listGame) {
                    ArrayList<Galery> gameGalery = daoGalery.getFullGameGalery(game.getGid());
                    listGameGalery.put(game, gameGalery);                
                }
                request.setAttribute("listGameGalery", listGameGalery);
                ArrayList<Order> orderList = daoOrder.getAllOrderByDate();
                request.setAttribute("orderList", orderList);
                // </editor-fold>
                sendDispatcher(request, response, "admin/adminIndex.jsp");
            }
            
            if (service.equalsIgnoreCase("updateCom")){
                int coId = Integer.parseInt(request.getParameter("coId"));
                Company updateCom = daoCom.getCompany(coId);
                String name = request.getParameter("name");
                if (name.trim().length()!=0) updateCom.setCoName(name);
                String foundDate = request.getParameter("foundDate");
                if (foundDate.trim().length()!=0) updateCom.setFoundDate(Date.valueOf(foundDate));
                String desc = request.getParameter("description");
                if (desc!=null && desc.trim().length()!=0) updateCom.setDescription(desc);
                String logo = request.getParameter("logo");
                if (logo.trim().length()!=0) updateCom.setLogo(logo);
                String address = request.getParameter("address");
                if (address.trim().length()!=0) updateCom.setCoAddress(address);
                String phone = request.getParameter("phone");
                if (phone.trim().length()!=0) updateCom.setCoPhone(phone);
                String mail = request.getParameter("mail");
                if (mail.trim().length()!=0) updateCom.setCoMail(mail);
                int n = daoCom.updateInfoCompany(updateCom);
                if (n!=0) request.setAttribute("message", "Update Company Successfully");
                else request.setAttribute("message", "Update Company Failed");
                request.setAttribute("tab", "companyUpdate");
                // <editor-fold defaultstate="collapsed" desc="SetParams">
                ArrayList<Game> listGame = daoGame.getTrueGame();
                request.setAttribute("listGame", listGame);
                ArrayList<Category> listCategory = daoCate.getTrueCategories();
                request.setAttribute("listCategory", listCategory);
                ArrayList<Platform> listPlatform = daoPlat.getTruePlatforms();
                request.setAttribute("listPlatform", listPlatform);
                ArrayList<Company> listCompany = daoCom.getTrueCompany();
                request.setAttribute("listCompany", listCompany);
                ArrayList<User> listUser = daoUser.getTrueUser();
                request.setAttribute("listUser", listUser);
                HashMap<Game, ArrayList<Galery>> listGameGalery = new HashMap<Game,ArrayList<Galery>>();
                for (Game game : listGame) {
                    ArrayList<Galery> gameGalery = daoGalery.getFullGameGalery(game.getGid());
                    listGameGalery.put(game, gameGalery);                
                }
                request.setAttribute("listGameGalery", listGameGalery);
                ArrayList<Order> orderList = daoOrder.getAllOrderByDate();
                request.setAttribute("orderList", orderList);
                // </editor-fold>
                request.setAttribute("updateCom", updateCom);
                sendDispatcher(request, response, "admin/adminIndex.jsp");
            }
            
            if (service.equalsIgnoreCase("delCom")) {
                int coId = Integer.parseInt(request.getParameter("coId"));
                int status = Integer.parseInt(request.getParameter("status"));
                int n = daoCom.changeStatus(coId, status);
                if (n!=0) request.setAttribute("message", "Update Company Successfully");
                else request.setAttribute("message", "Update Company Failed");
                request.setAttribute("tab", "companyDel");
                request.setAttribute("delCom", "Set Company with Id="+coId+" status to "+status);
                // <editor-fold defaultstate="collapsed" desc="SetParams">
                ArrayList<Game> listGame = daoGame.getTrueGame();
                request.setAttribute("listGame", listGame);
                ArrayList<Category> listCategory = daoCate.getTrueCategories();
                request.setAttribute("listCategory", listCategory);
                ArrayList<Platform> listPlatform = daoPlat.getTruePlatforms();
                request.setAttribute("listPlatform", listPlatform);
                ArrayList<Company> listCompany = daoCom.getTrueCompany();
                request.setAttribute("listCompany", listCompany);
                ArrayList<User> listUser = daoUser.getTrueUser();
                request.setAttribute("listUser", listUser);
                HashMap<Game, ArrayList<Galery>> listGameGalery = new HashMap<Game,ArrayList<Galery>>();
                for (Game game : listGame) {
                    ArrayList<Galery> gameGalery = daoGalery.getFullGameGalery(game.getGid());
                    listGameGalery.put(game, gameGalery);                
                }
                request.setAttribute("listGameGalery", listGameGalery);
                ArrayList<Order> orderList = daoOrder.getAllOrderByDate();
                request.setAttribute("orderList", orderList);
                // </editor-fold>
                sendDispatcher(request, response, "admin/adminIndex.jsp");
            }
            
            if (service.equalsIgnoreCase("addGalery")){
                int gId = Integer.parseInt(request.getParameter("gId"));
                String link = request.getParameter("link");
                String type = request.getParameter("type");
                Galery newGalery = new Galery(gId, link, type, 1);
                int n = daoGalery.insertGalery(newGalery);
                if (n!=0) request.setAttribute("message", "Insert Galery Successfully");
                else request.setAttribute("message", "Insert Galery Failed");
                request.setAttribute("tab", "galeryA");
                // <editor-fold defaultstate="collapsed" desc="SetParams">
                ArrayList<Game> listGame = daoGame.getTrueGame();
                request.setAttribute("listGame", listGame);
                ArrayList<Category> listCategory = daoCate.getTrueCategories();
                request.setAttribute("listCategory", listCategory);
                ArrayList<Platform> listPlatform = daoPlat.getTruePlatforms();
                request.setAttribute("listPlatform", listPlatform);
                ArrayList<Company> listCompany = daoCom.getTrueCompany();
                request.setAttribute("listCompany", listCompany);
                ArrayList<User> listUser = daoUser.getTrueUser();
                request.setAttribute("listUser", listUser);
                HashMap<Game, ArrayList<Galery>> listGameGalery = new HashMap<Game,ArrayList<Galery>>();
                for (Game game : listGame) {
                    ArrayList<Galery> gameGalery = daoGalery.getFullGameGalery(game.getGid());
                    listGameGalery.put(game, gameGalery);                
                }
                request.setAttribute("listGameGalery", listGameGalery);
                ArrayList<Order> orderList = daoOrder.getAllOrderByDate();
                request.setAttribute("orderList", orderList);
                // </editor-fold>
                request.setAttribute("newGalery", newGalery);
                sendDispatcher(request, response, "admin/adminIndex.jsp");
            }
            
            if (service.equalsIgnoreCase("updateGalery")){
                String gal = request.getParameter("galery");
                String[] parts = gal.split(" ");
                Galery oldGal = new Galery(Integer.parseInt(parts[0].trim()), parts[1].trim(), parts[2].trim(), 1);
                Galery newGal = new Galery(Integer.parseInt(parts[0].trim()), parts[1].trim(), parts[2].trim(), 1);
                String link = request.getParameter("link");
                if (link.trim().length()!=0) newGal.setLink(link);
                String type = request.getParameter("type");
                newGal.setType(type);
                int status = Integer.parseInt(request.getParameter("status"));
                newGal.setStatus(status);
                int n = daoGalery.updateGalery(oldGal, newGal);
                if (n!=0) request.setAttribute("message", "Update Galery Successfully");
                else request.setAttribute("message", "Update Galery Failed. Info: "+oldGal.toString()+" Original String: "+gal);
                request.setAttribute("tab", "galeryU");
                // <editor-fold defaultstate="collapsed" desc="SetParams">
                ArrayList<Game> listGame = daoGame.getTrueGame();
                request.setAttribute("listGame", listGame);
                ArrayList<Category> listCategory = daoCate.getTrueCategories();
                request.setAttribute("listCategory", listCategory);
                ArrayList<Platform> listPlatform = daoPlat.getTruePlatforms();
                request.setAttribute("listPlatform", listPlatform);
                ArrayList<Company> listCompany = daoCom.getTrueCompany();
                request.setAttribute("listCompany", listCompany);
                ArrayList<User> listUser = daoUser.getTrueUser();
                request.setAttribute("listUser", listUser);
                HashMap<Game, ArrayList<Galery>> listGameGalery = new HashMap<Game,ArrayList<Galery>>();
                for (Game game : listGame) {
                    ArrayList<Galery> gameGalery = daoGalery.getFullGameGalery(game.getGid());
                    listGameGalery.put(game, gameGalery);                
                }
                request.setAttribute("listGameGalery", listGameGalery);
                ArrayList<Order> orderList = daoOrder.getAllOrderByDate();
                request.setAttribute("orderList", orderList);
                // </editor-fold>
                request.setAttribute("updateGalery", newGal);
                sendDispatcher(request, response, "admin/adminIndex.jsp");
            }
            
            if (service.equalsIgnoreCase("viewOrder")){
                int oId = Integer.parseInt(request.getParameter("oId"));
                Order order = daoOrder.getOrderByOId(oId);
                if (order.equals(null)) {
                    request.setAttribute("message", "Order not found");
                } else {
                    ArrayList<OrderDetail> lines = daoDetail.getByOrdId(oId);
                    HashMap<OrderDetail, Game> orderLines = new HashMap<>();
                    for (OrderDetail line : lines) {
                        orderLines.put(line, daoGame.getGameById(line.getgId()));
                    }
                    request.setAttribute("message", "Order has "+orderLines.size()+" line(s)");
                    request.setAttribute("order", order);
                    request.setAttribute("orderLines", orderLines);
                    request.setAttribute("user", daoUser.getUserById(order.getuId()));
                }
                sendDispatcher(request, response, "admin/adminOrder.jsp");
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
