package fa25a.guestbook.repository;

import fa25a.guestbook.model.GuestbookEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestbookEntryRepository extends JpaRepository<GuestbookEntry, Long> {
}
