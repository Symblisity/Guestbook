package fa25a.guestbook.controller;

import fa25a.guestbook.Mapper.GuestbookEntryMapper;
import fa25a.guestbook.dto.GuestbookEntryRequest;
import fa25a.guestbook.dto.GuestbookEntryRequestNoId;
import fa25a.guestbook.dto.GuestbookEntryResponse;
import fa25a.guestbook.model.GuestbookEntry;
import fa25a.guestbook.service.GuestbookEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/guestbook/entries")
@RequiredArgsConstructor
public class GuestbookEntryController {

    private final GuestbookEntryService service;
    private final GuestbookEntryMapper mapper;

    @PostMapping
    public ResponseEntity<GuestbookEntryResponse> addGuestbookEntry(
            @RequestBody GuestbookEntryRequestNoId request
    ) {
        GuestbookEntry guestbookEntryRequest = mapper.mapRequestToModel(request);
        GuestbookEntry guestbookEntryResponse = service.create(guestbookEntryRequest);
        GuestbookEntryResponse response = mapper.mapModelToResponse(guestbookEntryResponse);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GuestbookEntryResponse> getGuestbookEntry(
            @PathVariable Long id
    ) {
        GuestbookEntry guestbookEntryResponse = service.readById(id);
        GuestbookEntryResponse response = mapper.mapModelToResponse(guestbookEntryResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<GuestbookEntryResponse>> getAllGuestbookEntries() {
        List<GuestbookEntry> guestbookEntryList = service.readAll();
        List<GuestbookEntryResponse> responseList = new ArrayList<>();
        for(GuestbookEntry guestbookEntry : guestbookEntryList) {
            responseList.add(mapper.mapModelToResponse(guestbookEntry));
        }
        return new  ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteGuestbookEntryByUrl(
            @PathVariable Long id
    ) {
        service.delete(id);
        return new  ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping()
    public ResponseEntity<GuestbookEntryResponse> updateGuestbookEntry(
            @RequestBody GuestbookEntryRequest request
    ) {
        GuestbookEntry guestbookEntryRequest = mapper.mapRequestToModel(request);
        GuestbookEntry guestbookEntryResponse = service.update(guestbookEntryRequest);
        GuestbookEntryResponse response = mapper.mapModelToResponse(guestbookEntryResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
