package ru.job4j.todolist.servlet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.job4j.todolist.model.Category;
import ru.job4j.todolist.model.Task;
import ru.job4j.todolist.model.User;
import ru.job4j.todolist.store.HibStore;
import ru.job4j.todolist.store.HibUserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TaskServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String showAllStr = req.getParameter("show_all");
        JSONObject obj = new JSONObject();
        JSONArray allCategories = new JSONArray();
        List<Category> categoriesList = HibStore.instOf().getAllCategories();
        req.setAttribute("allCategories", categoriesList);
        for (Category category : categoriesList) {
            JSONObject jsonCategory = new JSONObject();
            jsonCategory.put("name", category.getName());
            jsonCategory.put("id", category.getId());
            allCategories.add(jsonCategory);
        }
        List<Task> allTasks;
        if (showAllStr.equals("true")) {
            allTasks = HibStore.instOf().findAll();
        } else {
            allTasks = HibStore.instOf().findNotDone();
        }
        JSONArray tasks = new JSONArray();
        for (Task task : allTasks) {
            JSONObject taskObj = new JSONObject();
            taskObj.put("id", task.getId());
            taskObj.put("head", task.getHead());
            taskObj.put("desc", task.getDescription());
            taskObj.put("done", task.isDone());
            taskObj.put("author", task.getUser().getName());
            JSONArray categories  = new JSONArray();
            for (Category category : task.getCategories()) {
                JSONObject jsonCategory = new JSONObject();
                jsonCategory.put("name", category.getName());
                categories.add(jsonCategory);
            }
            taskObj.put("categories", categories);
            tasks.add(taskObj);
        }
        obj.put("tasks", tasks);
        obj.put("allCategories", allCategories);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        PrintWriter writer = new PrintWriter(resp.getWriter());
        writer.write(obj.toJSONString());
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        req.setCharacterEncoding("utf-8");
        String taskIdStr = req.getParameter("task_id");
        List<User> usersList = (List<User>) req.getSession().getAttribute("user");
        User user = usersList.get(0);
        String[] cIds = req.getParameterValues("cIds");
        if (taskIdStr != null) {
            HibStore.instOf().changeStatusToDone(Integer.parseInt(taskIdStr));
        } else {
            HibStore.instOf().add(
                    new Task(req.getParameter("head"),
                            req.getParameter("desc"),
                            user),
                    cIds
            );
            resp.sendRedirect("index.jsp");
        }
    }

}
