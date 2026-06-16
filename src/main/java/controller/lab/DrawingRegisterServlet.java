package controller.lab;

import common.AccessPolicy;
import common.Role;
import entity.DrawingInfo;
import entity.LabBasicInfo;
import service.LabService;
import util.AuthUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/labs/drawings/register")
public class DrawingRegisterServlet extends HttpServlet {

    private LabService labService = new LabService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Role loginRole = AuthUtil.getLoginRole(request);

            if (!AccessPolicy.canRegisterLab(loginRole)) {
                request.setAttribute("errorMessage", "도면 등록 화면 접근 권한이 없습니다.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            String labId = request.getParameter("labId");
            LabBasicInfo lab = labService.readLab(labId);

            request.setAttribute("lab", lab);
            request.getRequestDispatcher("/lab/drawingRegister.jsp").forward(request, response);

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
                request.setAttribute("errorMessage", "도면 등록 권한이 없습니다.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            DrawingInfo drawingInfo = new DrawingInfo();
            drawingInfo.setDrawingId(request.getParameter("drawingId"));
            drawingInfo.setLabId(request.getParameter("labId"));
            drawingInfo.setDrawingName(request.getParameter("drawingName"));
            drawingInfo.setFileType(request.getParameter("fileType"));
            drawingInfo.setFilePath(request.getParameter("filePath"));
            drawingInfo.setBaseDate(request.getParameter("baseDate"));

            labService.createDrawingInfo(drawingInfo);

            response.sendRedirect(request.getContextPath() + "/labs/detail?labId=" + drawingInfo.getLabId());

        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
