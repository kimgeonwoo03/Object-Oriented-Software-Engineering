package controller.user;

import common.AccessPolicy;
import common.Role;
import entity.Group;
import service.GroupService;
import util.AuthUtil;
import util.DateUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/users/groups/register")
public class GroupRegisterServlet extends HttpServlet {

    private GroupService groupService = new GroupService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Role loginRole = AuthUtil.getLoginRole(request);

            if (!AccessPolicy.canRegisterGroup(loginRole)) {
                request.setAttribute("errorMessage", "그룹 등록 화면 접근 권한이 없습니다.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            request.getRequestDispatcher("/user/groupRegister.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            Role loginRole = AuthUtil.getLoginRole(request);

            if (!AccessPolicy.canRegisterGroup(loginRole)) {
                request.setAttribute("errorMessage", "그룹 등록 권한이 없습니다.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            Group group = new Group();

            group.setGroupId(request.getParameter("groupId"));
            group.setGroupName(request.getParameter("groupName"));
            group.setManagerId(request.getParameter("managerId"));
            group.setDescription(request.getParameter("description"));
            group.setCreatedDate(DateUtil.today());
            group.setStatus("ACTIVE");

            groupService.createGroup(group);

            response.sendRedirect(request.getContextPath() + "/users/groups");

        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
