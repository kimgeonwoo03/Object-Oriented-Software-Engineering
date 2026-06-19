package controller.inspection;

import entity.InspSector;
import service.InspectionService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = {
        "/inspection/sectors/detail",
        "/inspections/sectors/detail"
})
public class InspSectorDetailServlet extends HttpServlet {

    private InspectionService inspectionService = new InspectionService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String sectorId = request.getParameter("sectorId");

            InspSector inspSector = inspectionService.readInspSector(sectorId);

            request.setAttribute("sector", inspSector);

            request.getRequestDispatcher("/inspection/inspSectorDetail.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}