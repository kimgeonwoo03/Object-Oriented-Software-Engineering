package controller.lab;

import common.AccessPolicy;
import common.Role;
import entity.DrawingInfo;
import entity.LabBasicInfo;
import entity.LabLocationInfo;
import service.LabService;
import util.AuthUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/labs/detail")
public class LabDetailServlet extends HttpServlet {

    private LabService labService = new LabService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Role loginRole = AuthUtil.getLoginRole(request);

            if (!AccessPolicy.canViewLab(loginRole)) {
                request.setAttribute("errorMessage", "연구실 상세 조회 권한이 없습니다.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            String labId = request.getParameter("labId");

            LabBasicInfo lab = labService.readLab(labId);
            DrawingInfo drawingInfo = labService.readDrawingInfo(labId);
            LabLocationInfo locationInfo = labService.readLabLocationInfo(labId);

            request.setAttribute("lab", lab);
            request.setAttribute("drawingInfo", drawingInfo);
            request.setAttribute("locationInfo", locationInfo);

            request.getRequestDispatcher("/lab/labDetail.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
