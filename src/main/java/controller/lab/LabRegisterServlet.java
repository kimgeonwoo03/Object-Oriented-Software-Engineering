package controller.lab;

import common.AccessPolicy;
import common.Role;
import entity.DrawingInfo;
import entity.LabBasicInfo;
import entity.LabLocationInfo;
import service.LabService;
import util.AuthUtil;
import util.DateUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/labs/register")
public class LabRegisterServlet extends HttpServlet {

    private final LabService labService = new LabService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Role loginRole = AuthUtil.getLoginRole(request);

            if (!AccessPolicy.canRegisterLab(loginRole)) {
                request.setAttribute(
                        "errorMessage",
                        "연구실 등록 화면 접근 권한이 없습니다."
                );
                request.getRequestDispatcher("/error.jsp")
                        .forward(request, response);
                return;
            }

            request.getRequestDispatcher("/lab/labRegister.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/error.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            Role loginRole = AuthUtil.getLoginRole(request);

            if (!AccessPolicy.canRegisterLab(loginRole)) {
                request.setAttribute(
                        "errorMessage",
                        "연구실 등록 권한이 없습니다."
                );
                request.getRequestDispatcher("/error.jsp")
                        .forward(request, response);
                return;
            }

            LabBasicInfo lab = new LabBasicInfo();
            lab.setLabId(request.getParameter("labId"));
            lab.setLabName(request.getParameter("labName"));
            lab.setDepartment(request.getParameter("department"));
            lab.setBuilding(request.getParameter("building"));
            lab.setFloor(request.getParameter("floor"));
            lab.setRoomNo(request.getParameter("roomNo"));
            lab.setManagerId(request.getParameter("managerId"));
            lab.setAccessUserInfo(request.getParameter("accessUserInfo"));
            lab.setRegisteredDate(DateUtil.today());

            DrawingInfo drawingInfo = createDrawingInfo(request, lab);
            LabLocationInfo locationInfo =
                    createLocationInfo(request, lab, drawingInfo);

            labService.createLabBasicInfo(
                    lab,
                    drawingInfo,
                    locationInfo
            );

            // 등록 성공 메시지를 세션에 저장
            request.getSession().setAttribute(
                    "successMessage",
                    lab.getLabName() + " 등록이 완료되었습니다."
            );

            response.sendRedirect(
                    request.getContextPath() + "/labs"
            );

        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/error.jsp")
                    .forward(request, response);
        }
    }

    private DrawingInfo createDrawingInfo(
            HttpServletRequest request,
            LabBasicInfo lab
    ) {
        String drawingName = request.getParameter("drawingName");
        String filePath = request.getParameter("filePath");

        if (!hasText(drawingName) && !hasText(filePath)) {
            return null;
        }

        DrawingInfo drawingInfo = new DrawingInfo();

        drawingInfo.setDrawingId(
                defaultValue(
                        request.getParameter("drawingId"),
                        "DRW_" + lab.getLabId()
                )
        );

        drawingInfo.setLabId(lab.getLabId());
        drawingInfo.setDrawingName(drawingName);
        drawingInfo.setFileType(request.getParameter("fileType"));
        drawingInfo.setFilePath(filePath);
        drawingInfo.setBaseDate(request.getParameter("baseDate"));

        return drawingInfo;
    }

    private LabLocationInfo createLocationInfo(
            HttpServletRequest request,
            LabBasicInfo lab,
            DrawingInfo drawingInfo
    ) {
        String coordX = request.getParameter("coordX");

        if (!hasText(coordX)) {
            return null;
        }

        LabLocationInfo locationInfo = new LabLocationInfo();

        locationInfo.setLocationId(
                defaultValue(
                        request.getParameter("locationId"),
                        "LOC_" + lab.getLabId()
                )
        );

        locationInfo.setLabId(lab.getLabId());

        if (drawingInfo != null) {
            locationInfo.setDrawingId(
                    drawingInfo.getDrawingId()
            );
        } else {
            locationInfo.setDrawingId(
                    request.getParameter("drawingId")
            );
        }

        locationInfo.setCoordX(coordX);
        locationInfo.setCoordY(
                request.getParameter("coordY")
        );
        locationInfo.setWidth(
                request.getParameter("width")
        );
        locationInfo.setHeight(
                request.getParameter("height")
        );

        return locationInfo;
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private String defaultValue(
            String value,
            String defaultValue
    ) {
        if (!hasText(value)) {
            return defaultValue;
        }

        return value.trim();
    }
}