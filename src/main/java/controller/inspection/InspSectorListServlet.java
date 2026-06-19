package controller.inspection;

import entity.InspSector;
import service.InspectionService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {
        "/inspection/sectors",
        "/inspections/sectors"
})
public class InspSectorListServlet extends HttpServlet {

    private InspectionService inspectionService = new InspectionService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String filter = request.getParameter("filter");

            List<InspSector> sectorList = inspectionService.readInspSectorList(filter);

            request.setAttribute("sectorList", sectorList);
            request.setAttribute("filter", filter);

            request.getRequestDispatcher("/inspection/inspSectorList.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}