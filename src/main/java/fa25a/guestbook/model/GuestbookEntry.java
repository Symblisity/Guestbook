package fa25a.guestbook.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Audited;

import java.util.Date;

@Data
@Entity
@Audited.Table(name= "Entry")
public class GuestbookEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;
    private String comment;
    private String commenter;
    private Date date = new Date();
}
