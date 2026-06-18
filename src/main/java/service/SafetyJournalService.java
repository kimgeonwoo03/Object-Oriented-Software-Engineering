package service;

import entity.SafetyJournal;
import exception.DuplicateDataException;
import exception.EmptyFieldException;
import repository.SafetyJournalRepository;

import java.util.List;
import java.util.UUID;

public class SafetyJournalService {

    private SafetyJournalRepository repository = new SafetyJournalRepository();

    public boolean createJournal(SafetyJournal journal) {
        formalInspection(journal);
        duplicateInspection(journal);
        journal.setJournalId(UUID.randomUUID().toString());
        return repository.insert(journal);
    }

    public List<SafetyJournal> readJournalList(String startDate, String endDate, String labId) {
        return repository.findByCondition(startDate, endDate, labId);
    }

    public SafetyJournal readJournal(String journalId) {
        return repository.findById(journalId);
    }

    private void formalInspection(SafetyJournal journal) {
        if (journal == null) {
            throw new EmptyFieldException("안전교육일지 정보가 없습니다.");
        }
        if (journal.getLabId() == null || journal.getLabId().trim().isEmpty()) {
            throw new EmptyFieldException("연구실 ID 값은 필수입니다.");
        }
        if (journal.getEducationDate() == null || journal.getEducationDate().trim().isEmpty()) {
            throw new EmptyFieldException("교육 일자 값은 필수입니다.");
        }
        if (!journal.getEducationDate().matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new EmptyFieldException("교육 일자 형식이 올바르지 않습니다. (yyyy-MM-dd)");
        }
        if (journal.getContent() == null || journal.getContent().trim().isEmpty()) {
            throw new EmptyFieldException("교육 내용 값은 필수입니다.");
        }
        if (journal.getAuthorId() == null || journal.getAuthorId().trim().isEmpty()) {
            throw new EmptyFieldException("작성자 정보가 없습니다.");
        }
        if (journal.getAttendeeCount() < 1) {
            throw new EmptyFieldException("참석자 수는 1명 이상이어야 합니다.");
        }
    }

    private void duplicateInspection(SafetyJournal journal) {
        if (repository.existsByDateAndLab(journal.getEducationDate(), journal.getLabId())) {
            throw new DuplicateDataException("동일 교육일자·연구실의 안전교육일지가 이미 존재합니다.");
        }
    }
}
