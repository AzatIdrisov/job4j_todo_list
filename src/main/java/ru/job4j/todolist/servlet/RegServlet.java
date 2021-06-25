package ru.job4j.todolist.servlet;

import ru.job4j.todolist.model.User;
import ru.job4j.todolist.store.HibUserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("reg.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");
        if (HibUserStore.instOf().findUserByEmail(email).size() == 0) {
            HibUserStore.instOf().addUser(
                    User.of(
                            req.getParameter("name"),
                            req.getParameter("email"),
                            req.getParameter("password")
                    )
            );
            resp.sendRedirect(req.getContextPath() + "/auth.do");
        } else {
            req.setAttribute("error", "Пользователь с данным email уже существует");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        }
    }
}
