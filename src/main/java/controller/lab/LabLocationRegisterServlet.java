package controller.lab;

import common.AccessPolicy;
import common.Role;
import entity.DrawingInfo;
import entity.LabBasicInfo;
import service.LabService;
import util.AuthUtil;
import entity.LabLocationInfo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/labs/locations/register")
public class LabLocationRegisterServlet extends HttpServlet {

    private LabService labService = new LabService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Role loginRole = AuthUtil.getLoginRole(request);

            if (!AccessPolicy.canRegisterLab(loginRole)) {
                request.setAttribute("errorMessage", "연구실 위치 등록 화면 접근 권한이 없습니다.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            String labId = request.getParameter("labId");
            LabBasicInfo lab = labService.readLab(labId);
            DrawingInfo drawingInfo = labService.readDrawingInfo(labId);

            request.setAttribute("lab", lab);
            request.setAttribute("drawingInfo", drawingInfo);
            request.getRequestDispatcher("/lab/labLocationRegister.jsp").forward(request, response);

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

            if (!AccessPolicy.canRegisterLab(loginRole)) {
                request.setAttribute("errorMessage", "연구실 위치 등록 권한이 없습니다.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            LabLocationInfo locationInfo = new LabLocationInfo();
            locationInfo.setLocationId(request.getParameter("locationId"));
            locationInfo.setLabId(request.getParameter("labId"));
            locationInfo.setDrawingId(request.getParameter("drawingId"));
            locationInfo.setCoordX(request.getParameter("coordX"));
            locationInfo.setCoordY(request.getParameter("coordY"));
            locationInfo.setWidth(request.getParameter("width"));
            locationInfo.setHeight(request.getParameter("height"));

            labService.createLabLocationInfo(locationInfo);

            response.sendRedirect(request.getContextPath() + "/labs/detail?labId=" + locationInfo.getLabId());

        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
