package fa25a.guestbook.Mapper;

import fa25a.guestbook.dto.GuestbookEntryRequest;
import fa25a.guestbook.dto.GuestbookEntryRequestNoId;
import fa25a.guestbook.dto.GuestbookEntryResponse;
import fa25a.guestbook.model.GuestbookEntry;
import org.springframework.stereotype.Service;

@Service
public class GuestbookEntryMapper {

    public GuestbookEntry mapRequestToModel(GuestbookEntryRequest request){
        GuestbookEntry guestbookEntry = new GuestbookEntry();
        guestbookEntry.setId(request.getId());
        guestbookEntry.setTitle(request.getTitle());
        guestbookEntry.setCommenter(request.getCommenter());
        guestbookEntry.setComment(request.getComment());
        return guestbookEntry;
    }

    public GuestbookEntry mapRequestToModel(GuestbookEntryRequestNoId request){
        GuestbookEntry guestbookEntry = new GuestbookEntry();
        guestbookEntry.setTitle(request.getTitle());
        guestbookEntry.setCommenter(request.getCommenter());
        guestbookEntry.setComment(request.getComment());
        return guestbookEntry;
    }

    public GuestbookEntryResponse mapModelToResponse(GuestbookEntry model){
        GuestbookEntryResponse guestbookEntryResponse = new GuestbookEntryResponse();
        guestbookEntryResponse.setId(model.getId());
        guestbookEntryResponse.setTitle(model.getTitle());
        guestbookEntryResponse.setCommenter(model.getCommenter());
        guestbookEntryResponse.setComment(model.getComment());
        return guestbookEntryResponse;
    }

}
