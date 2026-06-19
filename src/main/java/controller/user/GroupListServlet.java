package controller.user;

import common.AccessPolicy;
import common.Role;
import entity.Group;
import service.GroupService;
import util.AuthUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/users/groups")
public class GroupListServlet extends HttpServlet {

    private GroupService groupService = new GroupService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Role loginRole = AuthUtil.getLoginRole(request);

            if (!AccessPolicy.canViewGroup(loginRole)) {
                request.setAttribute("errorMessage", "그룹 조회 권한이 없습니다.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            String condition = request.getParameter("condition");

            List<Group> groupList = groupService.readGroupList(condition);

            request.setAttribute("groupList", groupList);
            request.setAttribute("condition", condition);

            request.getRequestDispatcher("/user/groupList.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
