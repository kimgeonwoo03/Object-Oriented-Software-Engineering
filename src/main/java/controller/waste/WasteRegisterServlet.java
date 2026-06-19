package controller.waste;

import common.AccessPolicy;
import common.Role;
import entity.WasteDischarge;
import service.WasteService;
import util.AuthUtil;
import util.DateUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/wastes/register")
public class WasteRegisterServlet extends HttpServlet {

    private WasteService wasteService = new WasteService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Role loginRole = AuthUtil.getLoginRole(request);

            if (!AccessPolicy.canRegisterWaste(loginRole)) {
                request.setAttribute("errorMessage", "폐기물 등록 화면 접근 권한이 없습니다.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            request.getRequestDispatcher("/waste/wasteRegister.jsp").forward(request, response);

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

            if (!AccessPolicy.canRegisterWaste(loginRole)) {
                request.setAttribute("errorMessage", "폐기물 등록 권한이 없습니다.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            WasteDischarge waste = new WasteDischarge();

            waste.setWasteId(request.getParameter("wasteId"));
            waste.setLabId(request.getParameter("labId"));
            waste.setDischargeDate(request.getParameter("dischargeDate"));
            waste.setWasteType(request.getParameter("wasteType"));
            waste.setWasteState(request.getParameter("wasteState"));

            String quantityValue = request.getParameter("quantity");
            if (quantityValue == null || quantityValue.isBlank()) {
                waste.setQuantity(0);
            } else {
                waste.setQuantity(Double.parseDouble(quantityValue));
            }

            waste.setUnit(request.getParameter("unit"));
            waste.setStorageLocation(request.getParameter("storageLocation"));
            waste.setStatus("STORED");

            wasteService.createWasteDischarge(waste);

            response.sendRedirect(request.getContextPath() + "/wastes");

        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}