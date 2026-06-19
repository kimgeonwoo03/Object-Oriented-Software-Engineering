package controller.lab;

import common.AccessPolicy;
import common.Role;
import entity.LabBasicInfo;
import service.LabService;
import util.AuthUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/labs")
public class LabListServlet extends HttpServlet {

    private final LabService labService = new LabService();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        try {
            Role loginRole = AuthUtil.getLoginRole(request);

            if (!AccessPolicy.canViewLab(loginRole)) {
                request.setAttribute(
                        "errorMessage",
                        "연구실 조회 권한이 없습니다."
                );
                request.getRequestDispatcher("/error.jsp")
                        .forward(request, response);
                return;
            }

            String condition =
                    request.getParameter("condition");

            String drawingRegistered =
                    request.getParameter("drawingRegistered");

            List<LabBasicInfo> labList =
                    labService.readLabList(
                            condition,
                            drawingRegistered
                    );

            request.setAttribute("labList", labList);
            request.setAttribute("condition", condition);
            request.setAttribute(
                    "drawingRegistered",
                    drawingRegistered
            );

            // 등록 성공 메시지를 세션에서 가져옴
            Object successMessage = request.getSession()
                    .getAttribute("successMessage");

            if (successMessage != null) {
                request.setAttribute(
                        "successMessage",
                        successMessage
                );

                // 새로고침할 때 다시 표시되지 않도록 삭제
                request.getSession()
                        .removeAttribute("successMessage");
            }

            request.getRequestDispatcher("/lab/labList.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/error.jsp")
                    .forward(request, response);
        }
    }
}