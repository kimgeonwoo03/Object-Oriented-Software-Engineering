package controller.chemical;

import common.AccessPolicy;
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
import java.util.List;

@WebServlet("/chemicals")
public class ChemicalListServlet extends HttpServlet {

    private ChemicalService chemicalService = new ChemicalService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Role loginRole = AuthUtil.getLoginRole(request);

            if (!AccessPolicy.canViewChemical(loginRole)) {
                request.setAttribute("errorMessage", "화학물질 조회 권한이 없습니다.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            String condition = request.getParameter("condition");

            List<Chemical> chemicalList = chemicalService.readChemicalList(condition);

            request.setAttribute("chemicalList", chemicalList);
            request.setAttribute("condition", condition);

            request.getRequestDispatcher("/chemical/chemicalList.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}