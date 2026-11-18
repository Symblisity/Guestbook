package fa25a.guestbook.dto;

import lombok.Getter;

@Getter
public class GuestbookEntryRequestNoId {

    private String title;
    private String comment;
    private String commenter;

}
