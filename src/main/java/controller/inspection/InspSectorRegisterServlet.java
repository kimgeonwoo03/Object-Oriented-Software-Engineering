package controller.inspection;

import entity.InspSector;
import service.InspectionService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet(urlPatterns = {
        "/inspection/sectors/register",
        "/inspections/sectors/register"
})
public class InspSectorRegisterServlet extends HttpServlet {

    private InspectionService inspectionService = new InspectionService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/inspection/inspSectorRegister.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String sectorId = request.getParameter("sectorId");
        String sectorName = request.getParameter("sectorName");
        String sectorDesc = request.getParameter("sectorDesc");
        String useYnParam = request.getParameter("useYn");

        boolean hasError = false;

        if (sectorId == null || sectorId.isBlank()) {
            request.setAttribute("sectorIdError", "점검 분야 ID는 필수입니다.");
            hasError = true;
        }

        if (sectorName == null || sectorName.isBlank()) {
            request.setAttribute("sectorNameError", "점검 분야명은 필수입니다.");
            hasError = true;
        }

        if (sectorDesc == null || sectorDesc.isBlank()) {
            request.setAttribute("sectorDescError", "점검 분야 설명은 필수입니다.");
            hasError = true;
        }

        if (useYnParam == null || useYnParam.isBlank()) {
            request.setAttribute("useYnError", "사용 여부는 필수입니다.");
            hasError = true;
        }

        if (hasError) {
            request.setAttribute("sectorId", sectorId);
            request.setAttribute("sectorName", sectorName);
            request.setAttribute("sectorDesc", sectorDesc);
            request.setAttribute("useYn", useYnParam);

            request.getRequestDispatcher("/inspection/inspSectorRegister.jsp").forward(request, response);
            return;
        }

        try {
            InspSector inspSector = new InspSector();

            inspSector.setSectorId(sectorId);
            inspSector.setSectorName(sectorName);
            inspSector.setSectorDesc(sectorDesc);
            inspSector.setUseYn(Integer.parseInt(useYnParam));
            inspSector.setRegDate(LocalDate.now().toString());

            inspectionService.createInspSector(inspSector);

            response.sendRedirect(request.getContextPath() + "/inspections/sectors");

        } catch (Exception e) {
            String message = e.getMessage();

            if (message != null && message.contains("이미 존재하는 점검 분야명")) {
                request.setAttribute("sectorNameError", message);
            } else {
                request.setAttribute("formError", message);
            }

            request.setAttribute("sectorId", sectorId);
            request.setAttribute("sectorName", sectorName);
            request.setAttribute("sectorDesc", sectorDesc);
            request.setAttribute("useYn", useYnParam);

            request.getRequestDispatcher("/inspection/inspSectorRegister.jsp").forward(request, response);
        }
    }
}