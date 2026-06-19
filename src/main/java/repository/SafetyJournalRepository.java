package repository;

import entity.SafetyJournal;
import util.JsonFileUtil;

import java.util.List;
import java.util.stream.Collectors;

public class SafetyJournalRepository {

    private static final String FILE = "safety_journal.json";

    public boolean insert(SafetyJournal journal) {
        List<SafetyJournal> list = JsonFileUtil.readList(FILE, SafetyJournal.class);
        list.add(journal);
        JsonFileUtil.writeList(FILE, list);
        return true;
    }

    public List<SafetyJournal> findByCondition(String startDate, String endDate, String labId) {
        return JsonFileUtil.readList(FILE, SafetyJournal.class).stream()
                .filter(j -> labId == null || labId.trim().isEmpty() || j.getLabId().equals(labId))
                .filter(j -> startDate == null || startDate.trim().isEmpty() || j.getEducationDate().compareTo(startDate) >= 0)
                .filter(j -> endDate == null || endDate.trim().isEmpty() || j.getEducationDate().compareTo(endDate) <= 0)
                .collect(Collectors.toList());
    }

    public SafetyJournal findById(String journalId) {
        return JsonFileUtil.readList(FILE, SafetyJournal.class).stream()
                .filter(j -> j.getJournalId().equals(journalId))
                .findFirst()
                .orElse(null);
    }

    public boolean existsByDateAndLab(String educationDate, String labId) {
        return JsonFileUtil.readList(FILE, SafetyJournal.class).stream()
                .anyMatch(j -> j.getEducationDate().equals(educationDate) && j.getLabId().equals(labId));
    }
}
