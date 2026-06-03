package controller.chemical;

import common.Role;
import entity.Chemical;
import service.ChemicalService;
import util.AuthUtil;
import util.DateUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/chemicals/register")
public class ChemicalRegisterServlet extends HttpServlet {

    private ChemicalService chemicalService = new ChemicalService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/chemical/chemicalRegister.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            String userRole = (String) request.getSession().getAttribute("userRole");

            if (!AuthUtil.hasRole(userRole, Role.ADMIN, Role.SAFETY_MANAGER)) {
                request.setAttribute("errorMessage", "화학물질 등록 권한이 없습니다.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            Chemical chemical = new Chemical();

            chemical.setChemicalId(request.getParameter("chemicalId"));
            chemical.setProductName(request.getParameter("productName"));
            chemical.setManufacturer(request.getParameter("manufacturer"));
            chemical.setCatNo(request.getParameter("catNo"));
            chemical.setCasNo(request.getParameter("casNo"));
            chemical.setCapacity(request.getParameter("capacity"));
            chemical.setUnit(request.getParameter("unit"));
            chemical.setComponentInfo(request.getParameter("componentInfo"));
            chemical.setLegalRegulation(request.getParameter("legalRegulation"));
            chemical.setGhsLabel(request.getParameter("ghsLabel"));
            chemical.setPhysicalProperties(request.getParameter("physicalProperties"));
            chemical.setHazardInfo(request.getParameter("hazardInfo"));

            String isComplexValue = request.getParameter("isComplex");

            if (isComplexValue == null || isComplexValue.isBlank()) {
                chemical.setIsComplex(0);
            } else {
                chemical.setIsComplex(Integer.parseInt(isComplexValue));
            }

            chemical.setRegisteredDate(DateUtil.today());

            chemicalService.createChemical(chemical);

            response.sendRedirect(request.getContextPath() + "/chemicals");

        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}