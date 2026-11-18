package fa25a.guestbook.service;

import fa25a.guestbook.model.GuestbookEntry;
import fa25a.guestbook.repository.GuestbookEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GuestbookEntryService {

    private final GuestbookEntryRepository guestbookEntryRepository;

    @Transactional
    public GuestbookEntry create(GuestbookEntry request) {
        return guestbookEntryRepository.save(request);
    }

    public List<GuestbookEntry> readAll() {
        return guestbookEntryRepository.findAll();
    }

    @Transactional
    public void delete(long id) {
        guestbookEntryRepository.deleteById(id);
    }

    public GuestbookEntry readById(long id) {
        return guestbookEntryRepository.findById(id).get();
    }

    @Transactional
    public GuestbookEntry update(GuestbookEntry request) {
        GuestbookEntry serializedGuestbookEntry = readById(request.getId());
        serializedGuestbookEntry.setTitle(request.getTitle());
        serializedGuestbookEntry.setComment(request.getComment());
        serializedGuestbookEntry.setCommenter(request.getCommenter());
        return serializedGuestbookEntry;
    }

    public List<GuestbookEntry> readAllOrderByIdDesc() {
        return guestbookEntryRepository.findAllByOrderByIdDesc();
    }

}
