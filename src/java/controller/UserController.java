/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Game;
import entity.Order;
import entity.OrderDetail;
import entity.User;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DAOGame;
import model.DAOLibrary;
import model.DAOOrder;
import model.DAOOrder_Detail;
import model.DAOUser;
import model.DBConnection;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Admin
 */
public class UserController extends HttpServlet {

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
    DAOUser daoUser = new DAOUser(dbCon);
    DAOLibrary daoLib = new DAOLibrary(dbCon);
    DAOOrder daoOrder = new DAOOrder(dbCon);
    DAOGame daoGame = new DAOGame(dbCon);
    DAOLibrary daolibrary = new DAOLibrary(dbCon);
    DAOOrder_Detail daoOrDe = new DAOOrder_Detail(dbCon);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        DBConnection dbCon = new DBConnection();
        DAOUser daoUser = new DAOUser(dbCon);
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String service = request.getParameter("service");
            String abc = request.getParameter("gId");
//            out.println(service);
            if (service == null) {
                service = "info";
            }

//            if(service.equalsIgnoreCase("HomePage")){
//                sendDispatcher(request, response, "index.html");
//            }
            if (service.equalsIgnoreCase("login")) {
                String userName = request.getParameter("username");
                String mess = "";
                String userPass = request.getParameter("userpassword");
                User log = daoUser.getUserLogin(userName, userPass);
                if (log != null) {
                    request.getSession().setAttribute("currUser", log);
                    request.getSession().setAttribute("role", log.getSystem_role());
                    ArrayList<Game> ShoppingCart = new ArrayList<>();
                    request.getSession().setAttribute("ShoppingCart", ShoppingCart);
                    ArrayList<Game> Library = daolibrary.getGameByUIdAndStatus(log.getuId(), 1);
                    request.getSession().setAttribute("Library", Library);
                    sendDispatcher(request, response, "index.jsp");
                } else {
                    mess = "Login failed, check your username or your password.";
                    request.setAttribute("mess", mess);
                    sendDispatcher(request, response, "jsp/login.jsp");
                }
            }

            //dang ky
            if (service.equalsIgnoreCase("register")) {
                String displayName = request.getParameter("displayName");
                String username = request.getParameter("username");
                String regMail = request.getParameter("regMail");
                String regPhone = request.getParameter("regPhone");
                String address = request.getParameter("address");
                String cPass = request.getParameter("confirm-password");
                String regPass = request.getParameter("regPass");

                User user = new User(displayName, regMail, regPhone, address, "User", username, cPass);
                String mess = "";
                boolean checkUser = true;
                if (username.length() < 6) {
                    mess = "Username must be more than 5 characters !!";
                    checkUser = false;
                }
                if (daoUser.checkExistUserName(username)) {
                    mess = "Username has been used !!";
                    checkUser = false;
                }
                if (!cPass.equalsIgnoreCase(regPass)) {
                    mess = "The confirm-password is not match with the password!";
                    checkUser = false;
                }
                String regexStr = "(09|03|07|08|05)+([0-9]{8})";
                if (!regPhone.matches(regexStr) || regPhone.length() != 10) {
                    mess = "The phone number is invalid";
                    checkUser = false;
                }
                if (daoUser.checkExistMail(regMail)) {
                    mess = "The email has been used !!";
                    checkUser = false;
                }
                if (daoUser.checkExistPhone(regPhone)) {
                    mess = "The phone has been used !!";
                    checkUser = false;
                }

                request.setAttribute("mess", mess);
                request.setAttribute("user", user);
                if (checkUser) {
                    daoUser.addUser(user);
                    response.sendRedirect("index.jsp");
                } else {
                    sendDispatcher(request, response, "jsp/register.jsp");
                }
            }

            if (service.equalsIgnoreCase("logout")) {
                request.getSession().invalidate();
                sendDispatcher(request, response, "index.jsp");
            }

            if (service.equalsIgnoreCase("changepass")) {
                String mess;
                String oldPassword = request.getParameter("oldPassword");
                String newPassword = request.getParameter("newPassword");
                String confirmPass = request.getParameter("confirm-password");

                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("currUser");
                String username = user.getUsername();
                String password = user.getPass();
                if (!password.equals(oldPassword)) {
                    mess = "Old Password is not correct";
                    request.setAttribute("mess", mess);
                    sendDispatcher(request, response, "jsp/changepass.jsp");
                } else if (newPassword.length() < 6) {
                    mess = "New Password is too week";
                    request.setAttribute("mess", mess);
                    sendDispatcher(request, response, "jsp/changepass.jsp");
                } else if (!confirmPass.equalsIgnoreCase(newPassword)) {
                    mess = "The confirm password is not match";
                    request.setAttribute("mess", mess);
                    sendDispatcher(request, response, "jsp/changepass.jsp");
                } else {
                    daoUser.changePassword(username, newPassword);
                    mess = "Change password successfully !!";
                    user = daoUser.getUserByUsername(username);
                    session.setAttribute("currUser", user);
                    request.setAttribute("mess", mess);
                    sendDispatcher(request, response, "index.jsp");
                }
            }

            if (service.equalsIgnoreCase("forgotPass")) {
                String mess;
                String username = request.getParameter("username");
                String newPassword = request.getParameter("confirm-password");
                String checkMail = request.getParameter("mail");
                String checkPhone = request.getParameter("phone");
                User user = daoUser.getUserByUsername(username);
                String mail = user.getuMail();
                String phone = user.getuPhone();
                if (!mail.equalsIgnoreCase(checkMail)) {
                    mess = "Your mail is not correct!";
                    request.setAttribute("mess", mess);
                    sendDispatcher(request, response, "jsp/forgot.jsp");
                } else if (!phone.equalsIgnoreCase(checkPhone)) {
                    mess = "Your phone is not correct!";
                    request.setAttribute("mess", mess);
                    sendDispatcher(request, response, "jsp/forgot.jsp");
                } else if (!mail.equalsIgnoreCase(checkMail) && !phone.equalsIgnoreCase(checkPhone)) {
                    mess = "Your mail or your phone is not correct. Please re-enter.";
                    request.setAttribute("mess", mess);
                    sendDispatcher(request, response, "jsp/forgot.jsp");
                } else {
                    daoUser.changePassword(username, newPassword);
                    mess = "Change password successfully !!";
                    HttpSession session = request.getSession();
                    session.setAttribute("currUser", user);
                    request.setAttribute("mess", mess);
                    sendDispatcher(request, response, "jsp/login.jsp");
                }
            }

            if (service.equalsIgnoreCase("info")) {
                User x = (User) request.getSession().getAttribute("currUser");
                request.setAttribute("currUser", x);
                ArrayList<Game> listGame = daoGame.getGameByUIdFromLibrarySorted(x.getuId());
                request.setAttribute("listGame", listGame);
                ArrayList<Game> listWishlist = daoGame.getGameByUIdFromWishlistSorted(x.getuId());
                request.setAttribute("wishlistGame", listWishlist);
                ArrayList<Order> listOrder = daoOrder.getOrdersByUId(x.getuId());
                request.setAttribute("listOrder", listOrder);
                ArrayList<Game> list = daoUser.getRecentGames(x);
                request.setAttribute("recentgames", list);
                sendDispatcher(request, response, "profile.jsp");
            }

            if (service.equalsIgnoreCase("recharge")) {
                User x = (User) request.getSession().getAttribute("currUser");
                request.setAttribute("currUser", x);
                sendDispatcher(request, response, "recharge.jsp");
            }

            if (service.equalsIgnoreCase("checkwallet")) {
                
                User x = (User) request.getSession().getAttribute("currUser");
                request.setAttribute("currUser", x);

                String mess;
                String phone = request.getParameter("phone");
                String pass = request.getParameter("pass");
                double amount = 0;
                try {
                    amount = Double.parseDouble(request.getParameter("amount"));
                } catch (NumberFormatException ex) {
                    mess = "Your money form is not correct. Please re-enter.";
                    request.setAttribute("mess", mess);

                    sendDispatcher(request, response, "recharge.jsp");
                }
                if (!phone.equals(x.getuPhone())) {
                    mess = "Your mail or your phone is not correct. Please re-enter.";
                    request.setAttribute("mess", mess);

                    sendDispatcher(request, response, "recharge.jsp");
                } else if (!pass.equals(x.getPass())) {
                    mess = "Your mail or your phone is not correct. Please re-enter.";
                    request.setAttribute("mess", mess);

                    sendDispatcher(request, response, "recharge.jsp");

                } else {
                    Order order = new Order(x.getuId(), "recharge", amount);
                    daoOrder.insertOrder(order);
                    daoUser.updateWalletUser(x, amount);
                    request.getSession().setAttribute("currUser", daoUser.getUserById(x.getuId()));
                    sendDispatcher(request, response, "UserControllerMap?service=info");
                }
            }

            if (service.equalsIgnoreCase("vieworder")) {
                int oId = Integer.parseInt(request.getParameter("orderId"));
                Order order = daoOrder.getOrderByOId(oId);

                if (order.getType().equals("buygame")) {
                    ArrayList<OrderDetail> listOD = daoOrDe.getByOrdId(oId);
                    ArrayList<Game> listG = new ArrayList<>();
                    for (OrderDetail od : listOD) {
                        Game g = daoGame.getGameById(od.getgId());
                        listG.add(g);
                    }
                    request.setAttribute("order", order);
                    request.setAttribute("listG", listG);
                    request.setAttribute("listOD", listOD);
                    sendDispatcher(request, response, "order.jsp");
                } else if (order.getType().equals("recharge")) {
                    request.setAttribute("order", order);
                    sendDispatcher(request, response, "order.jsp");
                } else {
                    request.setAttribute("order", order);
                    OrderDetail od = daoOrDe.getTopUp(oId);
                    ArrayList<OrderDetail> listOD = daoOrDe.getByOrdId(oId);
                    request.setAttribute("od", od);
                    request.setAttribute("listOD", listOD);
                    sendDispatcher(request, response, "order.jsp");
                }

            }

            if (service.equalsIgnoreCase("edit")) {
                User x = (User) request.getSession().getAttribute("currUser");
                request.setAttribute("currUser", x);
                sendDispatcher(request, response, "edit.jsp");
            }

            if (service.equalsIgnoreCase("changeinfo")) {
                User x = (User) request.getSession().getAttribute("currUser");
                request.setAttribute("currUser", x);
                String name = request.getParameter("name");
                String mail = request.getParameter("mail");
                String phone = request.getParameter("phone");
                String address = request.getParameter("address");
                String pass = request.getParameter("pass");
                User u = new User(x.getuId(), name, mail, phone, address);
                if (pass.equals(x.getPass())) {
                    daoUser.updateinfo(u);
                    request.getSession().setAttribute("currUser", daoUser.getUserById(x.getuId()));
                    sendDispatcher(request, response, "UserControllerMap?service=info");
                }
                out.print("wrong pass");
                sendDispatcher1(request, response, "UserControllerMap?service=edit");
            }

            if (service.equalsIgnoreCase("topup")) {
                User x = (User) request.getSession().getAttribute("currUser");
                int gId = Integer.parseInt(request.getParameter("gameId"));
                request.setAttribute("gId", gId);
                request.setAttribute("currUser", x);
                sendDispatcher(request, response, "topup.jsp");
            }

            if (service.equalsIgnoreCase("checkwallet2")) {
                User x = (User) request.getSession().getAttribute("currUser");
                request.setAttribute("currUser", x);
                int gId = Integer.parseInt(abc);

                String mess;
                String phone = request.getParameter("phone");
                String pass = request.getParameter("pass");
                String method = request.getParameter("method");
                String wallet = "wallet";
                double amount = 0;
                try {
                    amount = Double.parseDouble(request.getParameter("amount"));
                } catch (NumberFormatException ex) {
                    mess = "Your money form is not correct. Please re-enter.";
                    request.setAttribute("mess", mess);

                    sendDispatcher(request, response, "topup.jsp");
                }
                if (!phone.equals(x.getuPhone())) {
                    mess = "Your mail or your phone is not correct. Please re-enter.";
                    request.setAttribute("mess", mess);

                    sendDispatcher(request, response, "topup.jsp");
                } else if (!pass.equals(x.getPass())) {
                    mess = "Your mail or your phone is not correct. Please re-enter.";
                    request.setAttribute("mess", mess);

                    sendDispatcher(request, response, "topup.jsp");
                } else {
                    if (method.equals(wallet)) {
                        if (amount > x.getWallet()) {
                            mess = "Your balance is not enough, please Recharge before Top Up!";
                            request.setAttribute("mess", mess);

                            sendDispatcher(request, response, "topup.jsp");
                        } else {
                            Order order = new Order(x.getuId(), "topupwallet", amount);
                            daoOrder.insertOrder(order);
                            int oId = daoOrder.getLatestOrderByUseridAndTotal(x.getuId(), amount);
                            OrderDetail od = new OrderDetail(oId, gId, amount, 1);
                            daoOrDe.insertOrderDetail(od);

                            daoUser.updateWalletUser(x,-amount);
                            request.getSession().setAttribute("currUser", daoUser.getUserById(x.getuId()));
                            sendDispatcher(request, response, "UserControllerMap?service=info");
                        }
                    } else {
                        Order order = new Order(x.getuId(), "topupother", amount);
                        daoOrder.insertOrder(order);
                        int oId = daoOrder.getLatestOrderByUseridAndTotal(x.getuId(), amount);
                        OrderDetail od = new OrderDetail(oId, gId, amount, 1);
                        daoOrDe.insertOrderDetail(od);

                        request.getSession().setAttribute("currUser", daoUser.getUserById(x.getuId()));
                        sendDispatcher(request, response, "UserControllerMap?service=info");
                    }
                }
            }

            if (service.equalsIgnoreCase("uploadImage")) {
                User x = (User) request.getSession().getAttribute("currUser");
                request.setAttribute("currUser", x);
                sendDispatcher(request, response, "jsp/uploadImage.jsp");
            }

            if (service.equalsIgnoreCase("updateImage")) {
                String filename = null;
                // Create a factory for disk-based file items
                try {
                    DiskFileItemFactory factory = new DiskFileItemFactory();
                    ServletContext servletContext = this.getServletConfig().getServletContext();
                    File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
                    factory.setRepository(repository);
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    List<FileItem> items = upload.parseRequest(request);
                    // Process the uploaded items
                    Iterator<FileItem> iter = items.iterator();
                    HashMap<String, String> fields = new HashMap<>();
                    while (iter.hasNext()) {
                        FileItem item = iter.next();
                        if (item.isFormField()) {
                            fields.put(item.getFieldName(), item.getString());
                            String name = item.getFieldName();
                            String value = item.getString();
                            System.out.println(name + " " + value);
                        } else {
                            filename = item.getName();
                            if (filename == null || filename.equals("")) {
                                break;
                            } else {
                                Path path = Paths.get(filename);
                                String storePath = servletContext.getRealPath("/upload");
                                File uploadFile = new File(storePath + "/" + path.getFileName());
                                item.write(uploadFile);
                            }
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                }
                User x = (User) request.getSession().getAttribute("currUser");
                x.setProfilePicture(filename);
                daoUser.uploadImage(x, filename);
                request.getSession().setAttribute("currUser", x);
                sendDispatcher(request, response, "UserControllerMap?service=info");
            }
        }
    }

    public void sendDispatcher(HttpServletRequest request, HttpServletResponse response, String path) {
        try {
            RequestDispatcher rd = request.getRequestDispatcher(path);
            rd.forward(request, response);

        } catch (ServletException | IOException ex) {
            Logger.getLogger(ProductController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendDispatcher1(HttpServletRequest request, HttpServletResponse response, String path) {
        try {
            RequestDispatcher rd = request.getRequestDispatcher(path);
            rd.include(request, response);

        } catch (ServletException | IOException ex) {
            Logger.getLogger(ProductController.class
                    .getName()).log(Level.SEVERE, null, ex);
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
