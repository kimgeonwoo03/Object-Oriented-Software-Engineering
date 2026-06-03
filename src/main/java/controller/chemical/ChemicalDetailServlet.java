package controller.chemical;

import common.Role;
import entity.Chemical;
import service.ChemicalService;
import util.AuthUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/chemicals/detail")
public class ChemicalDetailServlet extends HttpServlet {

    private ChemicalService chemicalService = new ChemicalService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String userRole = (String) request.getSession().getAttribute("userRole");

            if (!AuthUtil.hasRole(userRole, Role.ADMIN, Role.SAFETY_MANAGER, Role.LAB_MANAGER)) {
                request.setAttribute("errorMessage", "화학물질 상세 조회 권한이 없습니다.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            String chemicalId = request.getParameter("chemicalId");

            Chemical chemical = chemicalService.readChemical(chemicalId);

            request.setAttribute("chemical", chemical);
            request.getRequestDispatcher("/chemical/chemicalDetail.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}