package fa25a.guestbook.dto;

import lombok.Getter;

@Getter
public class GuestbookEntryRequest {

    private long id;
    private String title;
    private String comment;
    private String commenter;

}
