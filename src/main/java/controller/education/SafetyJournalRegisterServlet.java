package controller.education;

import common.AccessPolicy;
import common.Role;
import entity.SafetyJournal;
import entity.User;
import service.SafetyJournalService;
import util.AuthUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/educations/journals/register")
public class SafetyJournalRegisterServlet extends HttpServlet {

    private SafetyJournalService journalService = new SafetyJournalService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Role loginRole = AuthUtil.getLoginRole(request);

            if (!AccessPolicy.canRegisterEducation(loginRole)) {
                request.setAttribute("errorMessage", "안전교육일지 등록 화면 접근 권한이 없습니다.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            request.getRequestDispatcher("/education/journalRegister.jsp").forward(request, response);

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

            if (!AccessPolicy.canRegisterEducation(loginRole)) {
                request.setAttribute("errorMessage", "안전교육일지 등록 권한이 없습니다.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            User loginUser = AuthUtil.getLoginUser(request);

            SafetyJournal journal = new SafetyJournal();
            journal.setLabId(request.getParameter("labId"));
            journal.setEducationDate(request.getParameter("educationDate"));
            journal.setContent(request.getParameter("content"));

            String attendeeCountStr = request.getParameter("attendeeCount");
            if (attendeeCountStr != null && !attendeeCountStr.trim().isEmpty()) {
                journal.setAttendeeCount(Integer.parseInt(attendeeCountStr));
            }

            journal.setAuthorId(loginUser.getUserId());

            journalService.createJournal(journal);

            response.sendRedirect(request.getContextPath() + "/educations/journals");

        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
