package fa25a.guestbook.dto;

import lombok.Data;

@Data
public class GuestbookEntryResponse {

    private long id;
    private String title;
    private String comment;
    private String commenter;

}
