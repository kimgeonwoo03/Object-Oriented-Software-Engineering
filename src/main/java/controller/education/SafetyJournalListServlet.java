package controller.education;

import common.AccessPolicy;
import common.Role;
import entity.SafetyJournal;
import service.SafetyJournalService;
import util.AuthUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/educations/journals")
public class SafetyJournalListServlet extends HttpServlet {

    private SafetyJournalService journalService = new SafetyJournalService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Role loginRole = AuthUtil.getLoginRole(request);

            if (!AccessPolicy.canViewEducation(loginRole)) {
                request.setAttribute("errorMessage", "안전교육일지 조회 권한이 없습니다.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            String labId = request.getParameter("labId");

            List<SafetyJournal> journalList = journalService.readJournalList(startDate, endDate, labId);

            request.setAttribute("journalList", journalList);
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);
            request.setAttribute("labId", labId);

            request.getRequestDispatcher("/education/journalList.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
