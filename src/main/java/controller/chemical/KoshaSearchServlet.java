package controller.chemical;

import entity.Chemical;
import service.ChemicalService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/chemicals/kosha")
public class KoshaSearchServlet extends HttpServlet {

    private ChemicalService chemicalService = new ChemicalService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            String casNo = request.getParameter("casNo");

            Chemical koshaInfo = chemicalService.searchKOSHAInfo(casNo);

            request.setAttribute("koshaInfo", koshaInfo);
            request.getRequestDispatcher("/chemical/chemicalRegister.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}