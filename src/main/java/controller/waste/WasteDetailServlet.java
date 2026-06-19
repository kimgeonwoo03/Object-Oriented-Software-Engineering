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

@WebServlet("/wastes/detail")
public class WasteDetailServlet extends HttpServlet {

    private WasteService wasteService = new WasteService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Role loginRole = AuthUtil.getLoginRole(request);

            if (!AccessPolicy.canViewWaste(loginRole)) {
                request.setAttribute("errorMessage", "폐기물 상세 조회 권한이 없습니다.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            String wasteId = request.getParameter("wasteId");

            WasteDischarge waste = wasteService.readWasteDischarge(wasteId);

            request.setAttribute("waste", waste);
            request.getRequestDispatcher("/waste/wasteDetail.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}