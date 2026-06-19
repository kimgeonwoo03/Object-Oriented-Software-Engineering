package controller.waste;

import common.AccessPolicy;
import common.Role;
import entity.WasteDischarge;
import service.WasteService;
import util.AuthUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/wastes")
public class WasteListServlet extends HttpServlet {

    private WasteService wasteService = new WasteService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Role loginRole = AuthUtil.getLoginRole(request);

            if (!AccessPolicy.canViewWaste(loginRole)) {
                request.setAttribute("errorMessage", "폐기물 조회 권한이 없습니다.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            String wasteId = request.getParameter("wasteId");
            String labId = request.getParameter("labId");
            String dischargeDate = request.getParameter("dischargeDate");
            String wasteType = request.getParameter("wasteType");
            String wasteState = request.getParameter("wasteState");
            String status = request.getParameter("status");

            List<WasteDischarge> wasteList = wasteService.readWasteDischargeList(
                    wasteId, labId, dischargeDate, wasteType, wasteState, status
            );

            request.setAttribute("wasteList", wasteList);
            request.setAttribute("wasteId", wasteId);
            request.setAttribute("labId", labId);
            request.setAttribute("dischargeDate", dischargeDate);
            request.setAttribute("wasteType", wasteType);
            request.setAttribute("wasteState", wasteState);
            request.setAttribute("status", status);

            request.getRequestDispatcher("/waste/wasteList.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}